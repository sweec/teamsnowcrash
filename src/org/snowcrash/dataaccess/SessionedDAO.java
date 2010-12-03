package org.snowcrash.dataaccess;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import org.snowcrash.utilities.CloningUtility;


public class SessionedDAO extends Observable implements DAO, DAOExceptionMessages
{
	private static final SessionedDAO READ_LOCK_SESSION = new SessionedDAO( null );
	
	private static Map<Class<?>,SessionedDAO> tableLockMap = 
		new HashMap<Class<?>,SessionedDAO>();
	
	private static Map<Class<?>,Map<Object,SessionedDAO>> objectLockMap = 
		new HashMap<Class<?>,Map<Object,SessionedDAO>>();
	
	private static boolean lock( Class<?> type, SessionedDAO locker )
	{
		boolean success = false;
		
		synchronized ( tableLockMap )
		{
			if ( !tableLockMap.containsKey( type ) )
			{
				tableLockMap.put( type, locker );
				success = true;
			}
		}
		
		return success;
	}
	
	private static boolean lock( Class<?> type, Object key, SessionedDAO locker )
	{
		boolean success = false;
		
		synchronized ( tableLockMap )
		{
			if ( !tableLockMap.containsKey( type ) )
			{
				synchronized ( objectLockMap )
				{
					Map<Object,SessionedDAO> objectMap = objectLockMap.get( type );
					
					if ( objectMap == null )
					{
						objectMap = new HashMap<Object,SessionedDAO>();
						objectLockMap.put( type, objectMap );
					}
					
					if ( !objectMap.containsKey( key ) )
					{
						objectMap.put( key, locker );
						success = true;
					}
				}
			}
		}
		
		return success;
	}
	
	private static void unlock( Class<?> type, SessionedDAO locker )
	{
		synchronized ( tableLockMap )
		{
			if ( tableLockMap.containsKey( type ) && tableLockMap.get( type ) == locker )
			{
				tableLockMap.remove( type );
			}
		}
	}
	
	private static void unlock( Class<?> type, Object key, SessionedDAO locker )
	{
		synchronized ( tableLockMap )
		{
			if ( !tableLockMap.containsKey( type ) )
			{
				synchronized ( objectLockMap )
				{
					Map<Object,SessionedDAO> objectMap = objectLockMap.get( type );
					
					if ( objectMap != null && !objectMap.containsKey( key ) && objectMap.get( key ) == locker )
					{
						objectMap.remove( key );
					}
				}
			}
		}
	}
	
	private static boolean hasLock( Class<?> type, SessionedDAO locker )
	{
		boolean hasLock = false;
		
		synchronized ( tableLockMap )
		{
			if ( tableLockMap.containsKey( type ) )
			{
				hasLock = ( tableLockMap.get( type ) == locker );
			}
		}
		
		return hasLock;
	}
	
	private static boolean hasLock( Class<?> type, Object key, SessionedDAO locker )
	{
		boolean hasLock = false;
		
		synchronized ( tableLockMap )
		{
			if ( !tableLockMap.containsKey( type ) )
			{
				synchronized ( objectLockMap )
				{
					Map<Object,SessionedDAO> objectMap = objectLockMap.get( type );
					
					if ( objectMap != null && objectMap.containsKey( key ) )
					{
						hasLock = ( objectMap.get( key ) == locker );
					}
				}
			}
		}
		
		return hasLock;
	}
	
	private static boolean isLockAvailable( Class<?> type )
	{
		boolean isAvailable = false;
		
		synchronized ( tableLockMap )
		{
			synchronized ( objectLockMap )
			{
				isAvailable = ( !tableLockMap.containsKey( type ) &&
						( !objectLockMap.containsKey( type ) || 
								objectLockMap.get( type ).isEmpty() ) );
			}
		}
		
		return isAvailable;
	}
	
	private static boolean isAvailable( Class<?> type, Object key )
	{
		boolean isAvailable = false;
		
		synchronized ( tableLockMap )
		{
			synchronized ( objectLockMap )
			{
				Map<Object,SessionedDAO> objectMap = objectLockMap.get( type );
				
				isAvailable = isLockAvailable( type ) && ( objectMap != null && !objectMap.containsKey( key ) );
			}
		}
		
		return isAvailable;
	}
	
	
	/*
	 * The temporary cache.
	 */
	private Map<Class<?>,CachedTable<? extends DatabaseObject>> cache = 
		new HashMap<Class<?>,CachedTable<? extends DatabaseObject>>();
	
	private Map<Class<?>,CachedTable<? extends DatabaseObject>> deleted = 
		new HashMap<Class<?>,CachedTable<? extends DatabaseObject>>();
	
	private boolean isSessioned = false;
	
	private DAO delegate;
	
	public SessionedDAO( DAO delegate )
	{
		this.delegate = delegate;
	}
	
	public void startSession()
	{
		isSessioned = true;
	}
	
	public void endSession( boolean commit ) throws DAOException
	{
		if ( isSessioned )
		{
			if ( commit )
			{
				// -- Process deletes.
				for ( Class<?> type : deleted.keySet() )
				{
					CachedTable<? extends DatabaseObject> table = deleted.get( type );
					
					for ( DatabaseObject object : table.read( type ) )
					{
						Object id = object.getId();
						
						delegate.delete( type, id );
					}
				}
				
				// -- Process updates and creates.
				for ( Class<?> type : cache.keySet() )
				{
					CachedTable<? extends DatabaseObject> table = cache.get( type );
					
					for ( DatabaseObject object : table.read( type ) )
					{
						Object id = object.getId();
						
						// -- Process update.
						if ( isInDatabase( type, id ) && !isDeleted( type, id ) )
						{
							delegate.update( object );
						}
						
						// -- Process create.
						else if ( !isInDatabase( type, id ) )
						{
							delegate.create( object );
						}
						
						if ( hasLock( type, id, this ) )
						{
							unlock( type, id, this );
						}
					}
					
					if ( hasLock( type, this ) )
					{
						unlock( type, this );
					}
				}
			}
			
			// -- Release locks.
			for ( Class<?> type : cache.keySet() )
			{
				for ( DatabaseObject object : cache.get( type ).read( type ) )
				{
					Object id = object.getId();
					
					if ( hasLock( type, id, this ) )
					{
						unlock( type, id, this );
					}
				}
				
				if ( hasLock( type, this ) )
				{
					unlock( type, this );
				}
			}
			
			for ( Class<?> type : deleted.keySet() )
			{
				for ( DatabaseObject object : deleted.get( type ).read( type ) )
				{
					Object id = object.getId();
					
					if ( hasLock( type, id, this ) )
					{
						unlock( type, id, this );
					}
				}
				
				if ( hasLock( type, this ) )
				{
					unlock( type, this );
				}
			}
			
			// -- Clear caches.
			cache = new HashMap<Class<?>,CachedTable<? extends DatabaseObject>>();
			deleted = new HashMap<Class<?>,CachedTable<? extends DatabaseObject>>();
			
			// -- Notify observers.
			if ( commit )
			{
				setChanged();
				notifyObservers();
			}
			
			isSessioned = false;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void create(DatabaseObject o) throws DAOException
	{
		if ( hasLock( o.getClass(), this ) )
		{
			if ( isSessioned )
			{
				if ( ( isInDatabase( o.getClass(), o.getId() ) && !isDeleted( o.getClass(), o.getId() ) ) ||
						isCached( o.getClass(), o.getId() ) )
				{
					throw new InvalidInputDAOException( ALREADY_EXISTS_MESSAGE );
				}
				else
				{
					CachedTable<? extends DatabaseObject> table = cache.get( o.getClass() );
					
					if ( table == null )
					{
						table = new CachedTable( o.getClass() );
						cache.put( o.getClass(), table );
					}
					
					DatabaseObject clone = CloningUtility.clone( o );
					table.create( clone );
				}
			}
			else
			{
				delegate.create( o );
			}
		}
		else
		{
			if ( isLockAvailable( o.getClass() ) )
			{
				lock( o.getClass(), this );
				
				create( o );
			}
			else
			{
				throw new LockingException( CANNOT_LOCK_TABLE_MESSAGE );
			}
		}
	}
	
	public DatabaseObject[] read(Class<?> type) throws DAOException
	{
		DatabaseObject[] results = null;
		
		if ( hasLock( type, READ_LOCK_SESSION ) )
		{
			if ( isSessioned )
			{
				Map<Object,DatabaseObject> resultMap = new HashMap<Object,DatabaseObject>();
				
				results = delegate.read( type );
				
				for ( DatabaseObject result : results )
				{
					if ( !isDeleted( result.getClass(), result.getId() ) )
					{
						resultMap.put( result.getId(), result );
					}
				}
				
				CachedTable<? extends DatabaseObject> table = cache.get( type );
				
				if ( table != null )
				{
					for ( DatabaseObject result : table.read( type ) )
					{
						resultMap.put( result.getId(), result );
					}
				}
				
				results = resultMap.values().toArray( results );
			}
			else
			{
				results = delegate.read( type );
			}
		}
		else
		{
			if ( isLockAvailable( type ) )
			{
				lock( type, READ_LOCK_SESSION );
				
				results = read( type );
			}
			else
			{
				throw new LockingException( CANNOT_LOCK_TABLE_MESSAGE );
			}
		}
		
		return results;
	}

	public DatabaseObject read(Class<?> type, Object id) throws DAOException
	{
		DatabaseObject result = null;
		
		if ( hasLock( type, id, READ_LOCK_SESSION ) )
		{
			if ( isSessioned )
			{
				result = delegate.read( type, id );
				
				if ( isDeleted( type, id ) )
				{
					result = null;
				}
				
				CachedTable<? extends DatabaseObject> table = cache.get( type );
				
				if ( table != null )
				{
					DatabaseObject object = table.read( type, id );
					
					if ( object != null )
					{
						result = object;
					}
				}
			}
			else
			{
				result = delegate.read( type, id );
			}
		}
		else
		{
			if ( isAvailable( type, id ) )
			{
				lock( type, id, READ_LOCK_SESSION );
				
				result = read( type, id );
			}
			else
			{
				throw new LockingException( CANNOT_LOCK_OBJECT_MESSAGE );
			}
		}
		
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update(DatabaseObject o) throws DAOException
	{
		if ( hasLock( o.getClass(), o.getId(), this ) )
		{
			if ( isSessioned )
			{
				if ( ( !isInDatabase( o.getClass(), o.getId() ) && !isCached( o.getClass(), o.getId() ) ) ||
						( isInDatabase( o.getClass(), o.getId() ) && isDeleted( o.getClass(), o.getId() ) &&
								!isCached( o.getClass(), o.getId() ) ) )
				{
					throw new InvalidInputDAOException( DOES_NOT_EXIST_MESSAGE );
				}
				else
				{
					CachedTable<? extends DatabaseObject> table = cache.get( o.getClass() );
					
					if ( table == null )
					{
						table = new CachedTable( o.getClass() );
						cache.put( o.getClass(), table );
					}
					
					DatabaseObject clone = CloningUtility.clone( o );
					table.update( clone );
				}
			}
			else
			{
				delegate.update( o );
			}
		}
		else
		{
			if ( isAvailable( o.getClass(), o.getId() ) )
			{
				lock( o.getClass(), o.getId(), this );
				
				update( o );
			}
			else
			{
				throw new LockingException( CANNOT_LOCK_OBJECT_MESSAGE );
			}
		}
	}

	public void delete(DatabaseObject o) throws DAOException
	{
		delete( o.getClass(), o.getId() );
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void delete(Class<?> type, Object id) throws DAOException
	{
		if ( hasLock( type, this ) )
		{
			if ( ( !isInDatabase( type, id ) && !isCached( type, id ) ) ||
					( isInDatabase( type, id ) && isDeleted( type, id ) ) )
			{
				throw new InvalidInputDAOException( DOES_NOT_EXIST_MESSAGE );
			}
			else
			{
				if ( isSessioned )
				{
					DatabaseObject objectInDb = delegate.read( type, id );
					
					if ( objectInDb != null )
					{
						CachedTable<? extends DatabaseObject> table = deleted.get( type );
						
						if ( table == null )
						{
							table = new CachedTable( type );
							deleted.put( type, table );
						}
						
						table.create( objectInDb );
					}
					
					CachedTable<? extends DatabaseObject> table = cache.get( type );
					
					if ( table != null )
					{
						DatabaseObject objectInCache = table.read( type, id );
						
						if ( objectInCache != null )
						{
							table.delete( objectInCache );
						}
					}
				}
				else
				{
					delegate.delete( type, id );
				}
			}
		}
		else
		{
			if ( isLockAvailable( type ) )
			{
				lock( type, this );
				
				delete( type, id );
			}
			else
			{
				throw new LockingException( CANNOT_LOCK_TABLE_MESSAGE );
			}
		}
	}
	
	public void nuke()
	{
		delegate.nuke();
		
		cache = new HashMap<Class<?>,CachedTable<? extends DatabaseObject>>();
		tableLockMap = new HashMap<Class<?>,SessionedDAO>();
		objectLockMap = new HashMap<Class<?>,Map<Object,SessionedDAO>>();
	}
	
	private boolean isInDatabase( Class<?> type, Object id ) throws DAOException
	{
		return ( delegate.read( type, id ) != null );
	}
	
	private boolean isDeleted( Class<?> type, Object id ) throws DAOException
	{
		boolean isDeleted = false;
		
		CachedTable<? extends DatabaseObject> table = deleted.get( type );
		isDeleted = ( table != null ) && ( table.read( type, id ) != null );
		
		return isDeleted;
	}
	
	private boolean isCached( Class<?> type, Object id ) throws DAOException
	{
		boolean isCached = false;
		
		CachedTable<? extends DatabaseObject> table = cache.get( type );
		isCached = ( table != null ) && ( table.read( type, id ) != null );
		
		return isCached;
	}
}

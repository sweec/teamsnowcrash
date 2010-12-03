package org.snowcrash.dataaccess;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import org.snowcrash.utilities.CloningUtility;


/**
 * 
 * This class represents a database, and is implemented as a map from type to table.
 * 
 * @author Mike
 *
 */
class CachedDAO extends Observable implements DAO, DAOExceptionMessages
{
	/*
	 * The database.
	 */
	private Map<Class<?>,CachedTable<? extends DatabaseObject>> database = 
		new HashMap<Class<?>,CachedTable<? extends DatabaseObject>>();
	
	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#create(org.snowcrash.dataaccess.DatabaseObject)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void create(DatabaseObject o) throws DAOException
	{
		CachedTable<? extends DatabaseObject> table = database.get( o.getClass() );
		
		if ( table == null )
		{
			/*
			 * Lazy-init.
			 */
			table = new CachedTable( o.getClass() );
			database.put( o.getClass(), table );
		}
		
		/*
		 * Any exceptions from the table are automatically passed up.
		 */
		DatabaseObject clone = CloningUtility.clone( o );
		table.create( clone );
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#read(java.lang.Class)
	 */
	public DatabaseObject[] read(Class<?> type) throws DAOException
	{
		DatabaseObject[] results = null;
		
		CachedTable<? extends DatabaseObject> table = database.get( type );
		
		if ( table != null )
		{
			results = table.read(type);
		}
		else
		{
			throw new InvalidInputDAOException( String.format( 
					UNSUPPORTED_TYPE_MESSAGE, type.getSimpleName() ) );
		}
		
		return results;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#read(int)
	 */
	public DatabaseObject read(Class<?> type, Object id) throws DAOException
	{
		DatabaseObject object = null;
		
		CachedTable<? extends DatabaseObject> table = database.get( type );
		
		if ( table != null )
		{
			/*
			 * Any exceptions from the table are automatically passed up.
			 */
			object = table.read(type, id);
		}
		else
		{
			throw new InvalidInputDAOException( String.format( 
					UNSUPPORTED_TYPE_MESSAGE, type.getSimpleName() ) );
		}
		
		return object;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#update(org.snowcrash.dataaccess.DatabaseObject)
	 */
	public void update(DatabaseObject o) throws DAOException
	{
		CachedTable<? extends DatabaseObject> table = database.get( o.getClass() );
		
		if ( table != null )
		{
			/*
			 * Any exceptions from the table are automatically passed up.
			 */
			DatabaseObject clone = CloningUtility.clone( o );
			table.update( clone );
		}
		else
		{
			throw new InvalidInputDAOException( String.format( 
					UNSUPPORTED_TYPE_MESSAGE, o.getClass().getSimpleName() ) );
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#delete(org.snowcrash.dataaccess.DatabaseObject)
	 */
	public void delete(DatabaseObject o) throws DAOException
	{
		CachedTable<? extends DatabaseObject> table = database.get( o.getClass() );
		
		if ( table != null )
		{
			/*
			 * Any exceptions from the table are automatically passed up.
			 */
			table.delete( o );
		}
		else
		{
			throw new InvalidInputDAOException( String.format( 
					UNSUPPORTED_TYPE_MESSAGE, o.getClass().getSimpleName() ) );
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#delete(java.lang.Class, int)
	 */
	public void delete(Class<?> type, Object id) throws DAOException
	{
		CachedTable<? extends DatabaseObject> table = database.get( type );
		
		if ( table != null )
		{
			/*
			 * Any exceptions from the table are automatically passed up.
			 */
			table.delete( type, id );
		}
		else
		{
			throw new InvalidInputDAOException( String.format( 
					UNSUPPORTED_TYPE_MESSAGE, type.getSimpleName() ) );
		}
	}
	
	public void nuke()
	{
		database = new HashMap<Class<?>,CachedTable<? extends DatabaseObject>>();
	}
}

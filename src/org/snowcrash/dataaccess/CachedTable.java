package org.snowcrash.dataaccess;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;


/**
 * 
 * This class represents a table in a database, and is implemented as a map from 
 * index to value.
 * 
 * @author Mike
 *
 * @param <T> the type of objects stored in the table
 * 
 */
class CachedTable<T extends DatabaseObject> extends Observable implements DAO
{
	private static final String ALREADY_EXISTS_MESSAGE = "Data already exists in the database.";
	private static final String UNSUPPORTED_TYPE_MESSAGE = "UnsupportedType %s.";
	private static final String DOES_NOT_EXIST_MESSAGE = "Object does not exist in the database.";
	
	/*
	 * The type of objects stored in the table.
	 */
	private Class<T> type;
	
	/*
	 * The table of objects.
	 */
	private Map<Object,T> table = new HashMap<Object,T>();
	
	/**
	 * 
	 * Constructor.
	 * 
	 * @param type the type of objects stored in the table
	 * 
	 */
	public CachedTable( Class<T> type )
	{
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#create(org.snowcrash.dataaccess.DatabaseObject)
	 */
	@SuppressWarnings("unchecked")
	public void create(DatabaseObject o) throws InvalidInputDAOException
	{
		if ( type.equals( o.getClass() ) )
		{
			if ( !table.containsKey( o.getId() ) )
			{
				table.put( o.getId(), (T) o );
			}
			else
			{
				// -- Data already exists.
				throw new InvalidInputDAOException( ALREADY_EXISTS_MESSAGE );
			}
		}
		else
		{
			// -- Unsupported type.
			throw new InvalidInputDAOException( String.format( 
					UNSUPPORTED_TYPE_MESSAGE, o.getClass().getSimpleName() ) );
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#read(int)
	 */
	public DatabaseObject read(int id) throws InvalidInputDAOException
	{
		DatabaseObject object = null;
		
		if ( table.containsKey( id ) )
		{
			object = table.get( id );
		}
		else
		{
			// -- Data does not exist.
			throw new InvalidInputDAOException( DOES_NOT_EXIST_MESSAGE );
		}
		
		return object;
	}

	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#update(org.snowcrash.dataaccess.DatabaseObject)
	 */
	@SuppressWarnings("unchecked")
	public void update(DatabaseObject o) throws InvalidInputDAOException
	{
		if ( type.equals( o.getClass() ) )
		{
			if ( table.containsKey( o.getId() ) )
			{
				table.put( o.getId(), (T) o );
			}
			else
			{
				// -- Data does not exist.
				throw new InvalidInputDAOException( DOES_NOT_EXIST_MESSAGE );
			}
		}
		else
		{
			// -- Unsupported type.
			throw new InvalidInputDAOException( String.format( 
					UNSUPPORTED_TYPE_MESSAGE, o.getClass().getSimpleName() ) );
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#delete(org.snowcrash.dataaccess.DatabaseObject)
	 */
	public void delete(DatabaseObject o) throws InvalidInputDAOException
	{
		if ( type.equals( o.getClass() ) )
		{
			if ( table.containsKey( o.getId() ) )
			{
				DatabaseObject value = table.get( o.getId() );
				table.remove( value );
			}
			else
			{
				// -- Data does not exist.
				throw new InvalidInputDAOException( DOES_NOT_EXIST_MESSAGE );
			}
		}
		else
		{
			// -- Unsupported type.
			throw new InvalidInputDAOException( String.format( 
					UNSUPPORTED_TYPE_MESSAGE, o.getClass().getSimpleName() ) );
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.dataaccess.DAO#delete(int)
	 */
	public void delete(int id) throws InvalidInputDAOException
	{
		if ( table.containsKey( id ) )
		{
			DatabaseObject value = table.get( id );
			table.remove( value );
		}
		else
		{
			// -- Data does not exist.
			throw new InvalidInputDAOException( DOES_NOT_EXIST_MESSAGE );
		}
	}
}

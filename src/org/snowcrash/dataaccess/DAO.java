package org.snowcrash.dataaccess;


/**
 * 
 * This interface defines the methods that all DAOs must implement.
 * 
 * @author Mike
 *
 */
public interface DAO
{
	/**
	 * 
	 * Creates an object in the database.
	 * 
	 * @param o the object to create
	 * @throws DAOException
	 * 
	 */
	public void create(Object o) throws DAOException;

	/**
	 * 
	 * Reads an object from the database.
	 * 
	 * @param id the ID of the object to read
	 * @return the object
	 * @throws DAOException
	 * 
	 */
	public Object read(int id) throws DAOException;

	/**
	 * 
	 * Updates an object in the database.
	 * 
	 * @param o the object to update
	 * @throws DAOException
	 * 
	 */
	public void update(Object o) throws DAOException;

	/**
	 * 
	 * Deletes an object from the database.
	 * 
	 * @param o the object to delete
	 * @throws DAOException
	 * 
	 */
	public void delete(Object o) throws DAOException;

	/**
	 * 
	 * Deletes an object from the database.
	 * 
	 * @param id the ID of the object to delete
	 * @throws DAOException
	 * 
	 */
	public void delete(int id) throws DAOException;
}

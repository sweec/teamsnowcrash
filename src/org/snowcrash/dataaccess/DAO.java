package org.snowcrash.dataaccess;

import org.snowcrash.utilities.Observable;


/**
 * 
 * This interface defines the methods that all DAOs must implement.
 * 
 * @author Mike
 *
 */
public interface DAO extends Observable
{
	/**
	 * 
	 * Creates an object in the database.
	 * 
	 * @param o the object to create
	 * @throws DAOException
	 * 
	 */
	public void create(DatabaseObject o) throws DAOException;

	/**
	 * 
	 * Reads an object from the database.
	 * 
	 * @param type the type of the object to read
	 * @param id the ID of the object to read
	 * @return the object
	 * @throws DAOException
	 * 
	 */
	public DatabaseObject read(Class<?> type, Object id) throws DAOException;
	
	/**
	 * 
	 * Reads all objects of a type from the database.
	 * 
	 * @param type the type of the objects to read
	 * @return all objects of the given type
	 * @throws DAOException
	 * 
	 */
	public DatabaseObject[] read(Class<?> type) throws DAOException;

	/**
	 * 
	 * Updates an object in the database.
	 * 
	 * @param o the object to update
	 * @throws DAOException
	 * 
	 */
	public void update(DatabaseObject o) throws DAOException;

	/**
	 * 
	 * Deletes an object from the database.
	 * 
	 * @param o the object to delete
	 * @throws DAOException
	 * 
	 */
	public void delete(DatabaseObject o) throws DAOException;

	/**
	 * 
	 * Deletes an object from the database.
	 * 
	 * @param type the type of the object to delete
	 * @param id the ID of the object to delete
	 * @throws DAOException
	 * 
	 */
	public void delete(Class<?> type, Object id) throws DAOException;
	
	public void notifyChanged();
}

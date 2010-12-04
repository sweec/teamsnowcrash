package org.snowcrash.dataaccess;


/**
 * 
 * This class represents an error where invalid input is passed to the DAO.
 * 
 * @author Mike
 *
 */
@SuppressWarnings("serial")
class InvalidInputDAOException extends DAOException
{
	/*
	 * The default message for this exception.
	 */
	private static final String DEFAULT_MESSAGE = "Invalid input to DAO.";
	
	/**
	 * 
	 * Constructor.
	 * 
	 */
	public InvalidInputDAOException()
	{
		super( DEFAULT_MESSAGE );
	}
	
	/**
	 * 
	 * Constructor.
	 * 
	 * @param message the exception's message
	 * 
	 */
	public InvalidInputDAOException( String message )
	{
		super( message );
	}
}

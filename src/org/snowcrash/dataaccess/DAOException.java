package org.snowcrash.dataaccess;


/**
 * 
 * This class subclasses Exception for errors caught in the dataaccess package.
 * 
 * @author Mike
 *
 */
@SuppressWarnings("serial")
public abstract class DAOException extends Exception
{
	/**
	 * 
	 * Protected constructor.
	 * 
	 * @param message the exception's message
	 * 
	 */
	protected DAOException(String message)
	{
		super(message);
	}
}

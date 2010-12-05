package org.snowcrash.dataaccess;


@SuppressWarnings("serial")
class LockingException extends DAOException
{
	public LockingException( String message )
	{
		super( message );
	}
}

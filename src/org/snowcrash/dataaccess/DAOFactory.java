package org.snowcrash.dataaccess;

import java.util.Observer;

public class DAOFactory {
	private static final DAO TEST_DAO = new TestDAO();
	
	private static final DAO SINGLETON_DAO = new CachedDAO();

	private DAOFactory() {
		// -- Static class.
	}

	public static SessionedDAO getDAO() {
		SessionedDAO session = new SessionedDAO( SINGLETON_DAO );
		
		return session;
	}
	
	public static DAO getTestDAO()
	{
		return TEST_DAO;
	}

	public static boolean addObserver(Observer observer) {
		boolean success = false;

		SINGLETON_DAO.addObserver( observer );
		
		return success;
	}
}

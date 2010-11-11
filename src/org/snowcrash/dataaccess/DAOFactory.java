package org.snowcrash.dataaccess;

import java.util.Observable;
import java.util.Observer;

public class DAOFactory {
	private static final DAO TEST_DAO = new TestDAO();

	private static final DAO SINGLETON_DAO = null;

	private DAOFactory() {
		// -- Static class.
	}

	public static DAO getDAO() {
		return TEST_DAO;
	}

	public static boolean addObserver(Observer observer) {
		boolean success = false;

		if (SINGLETON_DAO instanceof Observable) {
			((Observable) SINGLETON_DAO).addObserver(observer);

			success = true;
		}

		return success;
	}
}

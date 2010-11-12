package org.snowcrash.dataaccess;

import java.util.Observable;

class TestDAO extends Observable implements DAO {

	@Override
	public void create(DatabaseObject o) {
		// TODO Auto-generated method stub

	}

	@Override
	public DatabaseObject read(Class<?> type, Object id) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void update(DatabaseObject o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(DatabaseObject o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Class<?> type, Object id) {
		// TODO Auto-generated method stub

	}

}

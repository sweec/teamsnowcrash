package org.snowcrash.dataaccess;

public interface DAO {
	public void create(Object o);

	public Object read(int id);

	public void update(Object o);

	public void delete(Object o);

	public void delete(int id);
}

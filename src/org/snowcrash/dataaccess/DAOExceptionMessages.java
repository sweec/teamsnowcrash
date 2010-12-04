package org.snowcrash.dataaccess;


interface DAOExceptionMessages
{
	static final String ALREADY_EXISTS_MESSAGE = "Data already exists in the database.";
	static final String UNSUPPORTED_TYPE_MESSAGE = "UnsupportedType %s.";
	static final String DOES_NOT_EXIST_MESSAGE = "Object does not exist in the database.";
	
	static final String CANNOT_LOCK_TABLE_MESSAGE = "Cannot lock table %s.";
	static final String CANNOT_LOCK_OBJECT_MESSAGE = "Cannot lock object in table %s.";
}

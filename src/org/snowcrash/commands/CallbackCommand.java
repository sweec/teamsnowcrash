package org.snowcrash.commands;

import org.snowcrash.utilities.Callback;


/**
 * 
 * This class provides an advanced extension of the AbstractCommand implementation 
 * of the Command interface.  The callback nature of this class's subclasses allows 
 * for data to be returned to the caller.
 * 
 * @author Mike
 *
 */
abstract class CallbackCommand extends AbstractCommand
{
	/*
	 * The callback method to be executed with the results of the command.
	 */
	private Callback callback = null;
	
	/**
	 * 
	 * Constructor.
	 * 
	 * @param callback the callback method to be executed with the results of the command
	 * 
	 */
	CallbackCommand( Callback callback )
	{
		this.callback = callback;
	}
	
	/**
	 * 
	 * Executes the callback method of this command.
	 * 
	 * @param results the results from executing the command
	 * 
	 */
	public void executeCallback( Object results )
	{
		callback.callback( results );
	}
}

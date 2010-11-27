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
abstract class CallbackCommand implements Command
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
	 * Does the basic command logic defined by the subclass.
	 * 
	 * @return the results of the command logic
	 * 
	 */
	protected abstract Object[] executeCommandLogic();
	
	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.commands.Command#execute()
	 */
	public final void execute()
	{
		Object[] results = executeCommandLogic();
		
		callback.callback( results );
	}
}

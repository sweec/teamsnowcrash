package org.snowcrash.commands;


/**
 * 
 * This class provides a common implementation to the Command interface.  Classes 
 * that wish to use a different interface should NOT subclass this one, but should 
 * define their own implementations of Command.
 * 
 * @author Mike
 *
 */
abstract class AbstractCommand implements Command
{
	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.commands.Command#execute()
	 */
	@Deprecated
	public final void execute()
	{
		CommandMediator.handleCommand(this);
	}
}

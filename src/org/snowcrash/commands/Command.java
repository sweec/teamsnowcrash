package org.snowcrash.commands;


/**
 * 
 * This interface defines the available public methods that command subclasses must 
 * implement.
 * 
 * @author Mike
 *
 */
public interface Command
{
	/**
	 * 
	 * Executes this command.
	 * 
	 */
	public void execute();
}

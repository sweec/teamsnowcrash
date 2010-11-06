package org.snowcrash.commands;


public class CommandFactory
{
	private CommandFactory()
	{
		// -- Static class.
	}
	
	public static Command getTestCommand()
	{
		Command testCommand = new TestCommand();
		
		return testCommand;
	}
}

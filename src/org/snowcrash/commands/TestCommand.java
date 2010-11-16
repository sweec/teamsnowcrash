package org.snowcrash.commands;

class TestCommand extends AbstractCommand
{
	private String message = null;
	
	public TestCommand()
	{
		message = "This is a test command!";
	}
	
	public String getMessage()
	{
		return message;
	}
}

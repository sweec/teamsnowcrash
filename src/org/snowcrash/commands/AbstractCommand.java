package org.snowcrash.commands;


abstract class AbstractCommand implements Command
{
	public void execute()
	{
		CommandMediator.handleCommand(this);
	}
}

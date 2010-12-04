package org.snowcrash.commands;


class FinishSimulationCommand implements Command
{
	public void execute()
	{
		CommandMediator.simulateToEnd();
	}
}

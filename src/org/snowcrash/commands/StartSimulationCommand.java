package org.snowcrash.commands;


class StartSimulationCommand implements Command
{
	public void execute()
	{
		CommandMediator.startSimulation();
	}
}

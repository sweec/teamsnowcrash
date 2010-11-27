package org.snowcrash.commands;


class StopSimulationCommand implements Command
{
	public void execute()
	{
		CommandMediator.stopSimulation();
	}
}

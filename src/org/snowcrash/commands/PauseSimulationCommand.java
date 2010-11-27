package org.snowcrash.commands;


class PauseSimulationCommand implements Command
{
	public void execute()
	{
		CommandMediator.pauseSimulation();
	}
}

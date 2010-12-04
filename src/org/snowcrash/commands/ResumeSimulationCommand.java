package org.snowcrash.commands;


class ResumeSimulationCommand implements Command
{
	public void execute()
	{
		CommandMediator.resumeSimulation();
	}
}

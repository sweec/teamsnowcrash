package org.snowcrash.commands;


class LoadSimulationCommand extends FileCommand
{
	public LoadSimulationCommand( String filename )
	{
		super( filename );
	}
	
	public void execute()
	{
		CommandMediator.loadSimulation( super.getFilename() );
	}
}

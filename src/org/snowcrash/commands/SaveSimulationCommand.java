package org.snowcrash.commands;


class SaveSimulationCommand extends FileCommand
{
	public SaveSimulationCommand( String filename )
	{
		super( filename );
	}
	
	public void execute()
	{
		CommandMediator.saveSimulation( super.getFilename() );
	}
}

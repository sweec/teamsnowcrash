package org.snowcrash.commands;


class SaveResultsCommand extends FileCommand
{
	public SaveResultsCommand( String filename )
	{
		super( filename );
	}
	
	public void execute()
	{
		CommandMediator.saveResults( super.getFilename() );
	}
}

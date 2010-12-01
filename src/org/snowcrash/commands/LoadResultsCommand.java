package org.snowcrash.commands;


class LoadResultsCommand extends FileCommand
{
	public LoadResultsCommand( String filename )
	{
		super( filename );
	}
	
	public void execute()
	{
		CommandMediator.loadResults( super.getFilename() );
	}
}

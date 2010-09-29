package org.snowcrash.commands;


class CommandMediator
{
	private CommandMediator()
	{
		// -- Static class.
	}
	
	public static void handleCommand( Command command, Object ... params )
	{
		switch ( command )
		{
		case TEST:
			System.out.println( "This is a test command!!" );
			break;
			
		default:
			throw new UnsupportedOperationException( "Command " + command + " is not yet supported." );
		}
	}
}

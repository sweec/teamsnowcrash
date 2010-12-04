package org.snowcrash.commands;


class SetNumberOfTurnsCommand implements Command
{
	private int numTurns;
	
	public SetNumberOfTurnsCommand( int numTurns )
	{
		this.numTurns = numTurns;
	}
	
	public int getNumTurns()
	{
		return numTurns;
	}
	
	public void execute()
	{
		CommandMediator.setNumberOfTurns(numTurns);
	}
}

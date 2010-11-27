package org.snowcrash.commands;


class SetWorldSizeCommand implements Command
{
	private int size;
	
	public SetWorldSizeCommand( int size )
	{
		this.size = size;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void execute()
	{
		CommandMediator.setWorldSize(size);
	}
}

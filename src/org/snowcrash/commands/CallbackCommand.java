package org.snowcrash.commands;

import org.snowcrash.utilities.Callback;


abstract class CallbackCommand extends AbstractCommand
{
	private Callback callback = null;
	
	CallbackCommand( Callback callback )
	{
		this.callback = callback;
	}
	
	public void executeCallback( Object results )
	{
		callback.callback( results );
	}
}

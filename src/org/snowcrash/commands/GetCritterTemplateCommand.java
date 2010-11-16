package org.snowcrash.commands;

import org.snowcrash.utilities.Callback;


/**
 * 
 * This class allows the retrieval of critter templates.
 * 
 * @author Mike
 *
 */
class GetCritterTemplateCommand extends CallbackCommand
{
	/*
	 * The name of the CritterTemplate to retrieve.
	 */
	private String name;
	
	/**
	 * 
	 * Constructor.
	 * 
	 * @param name the name of the CritterTemplate to retrieve
	 * 
	 */
	public GetCritterTemplateCommand( String name, Callback callback )
	{
		super( callback );
		
		this.name = name;
	}
	
	/**
	 * 
	 * Returns the name of the CritterTemplate to retrieve.
	 * 
	 * @return the name of the CritterTemplate to retrieve
	 * 
	 */
	public String getName()
	{
		return name;
	}
}

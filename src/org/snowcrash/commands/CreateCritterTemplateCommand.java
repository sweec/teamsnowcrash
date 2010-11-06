package org.snowcrash.commands;

import org.snowcrash.critter.CritterPrototype;


/**
 * 
 * This class allows the creation of critter templates.
 * 
 * @author Mike
 *
 */
public class CreateCritterTemplateCommand extends AbstractCommand
{
	/*
	 * The prototype of the critter template.
	 */
	private CritterPrototype prototype;
	
	/*
	 * The name of the critter template.
	 */
	private String name;

	/**
	 * 
	 * Constructor.
	 * 
	 * @param prototype the prototype of the critter template
	 * @param name the name of the critter template
	 * 
	 */
	public CreateCritterTemplateCommand(CritterPrototype prototype, String name)
	{
		this.prototype = prototype;
		this.name = name;
	}

	/**
	 * 
	 * Returns the prototype of the critter template.
	 * 
	 * @return the prototype of the critter template
	 * 
	 */
	public CritterPrototype getPrototype()
	{
		return prototype;
	}

	/**
	 * 
	 * Returns the name of the critter template
	 * 
	 * @return the name of the critter template
	 * 
	 */
	public String getName()
	{
		return name;
	}
}

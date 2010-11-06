package org.snowcrash.commands;

import org.snowcrash.critter.CritterTemplate;


/**
 * 
 * This class allows the modification of critter templates.
 * 
 * @author Mike
 *
 */
public class ModifyCritterTemplateCommand extends AbstractCommand
{
	/*
	 * The critter template to modify.
	 */
	private CritterTemplate template;

	/**
	 * 
	 * Constructor.
	 * 
	 * @param template the critter template to modify
	 * 
	 */
	public ModifyCritterTemplateCommand(CritterTemplate template)
	{
		this.template = template;
	}

	/**
	 * 
	 * Returns the critter template to modify.
	 * 
	 * @return the critter template to modify
	 * 
	 */
	public CritterTemplate getTemplate()
	{
		return template;
	}
}

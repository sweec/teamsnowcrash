package org.snowcrash.commands;

import org.snowcrash.critter.CritterTemplate;


/**
 * 
 * This class allows the deletion of critter templates.
 * 
 * @author Mike
 *
 */
public class DeleteCritterTemplateCommand extends AbstractCommand
{
	/*
	 * The critter template to delete.
	 */
	private CritterTemplate template;

	/**
	 * 
	 * Constructor.
	 * 
	 * @param template the critter template to delete
	 * 
	 */
	public DeleteCritterTemplateCommand(CritterTemplate template)
	{
		this.template = template;
	}

	/**
	 * 
	 * Returns the critter template to delete.
	 * 
	 * @return the critter template to delete
	 * 
	 */
	public CritterTemplate getTemplate()
	{
		return template;
	}
}

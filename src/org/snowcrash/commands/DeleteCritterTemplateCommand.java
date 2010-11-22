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
	
	/*
	 * The name of the critter template to delete.
	 */
	private String templateName;

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
	 * Constructor.
	 * 
	 * @param templateName the name of the critter template to delete
	 * 
	 */
	public DeleteCritterTemplateCommand(String templateName)
	{
		this.templateName = templateName;
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
	
	/**
	 * 
	 * Returns the name of the critter template to delete.
	 * 
	 * @return the name of the critter template to delete
	 * 
	 */
	public String getTemplateName()
	{
		return templateName;
	}
}

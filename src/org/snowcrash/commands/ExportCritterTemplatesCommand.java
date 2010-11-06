package org.snowcrash.commands;

import org.snowcrash.critter.CritterTemplate;


/**
 * 
 * This class allows the export of critter templates.
 * 
 * @author Mike
 *
 */
public class ExportCritterTemplatesCommand extends FileCommand
{
	/*
	 * The critter templates to export.
	 */
	private CritterTemplate[] critters = null;

	/**
	 * 
	 * Constructor.
	 * 
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * @param critters the critter templates to export
	 * 
	 */
	public ExportCritterTemplatesCommand(String filepath, String filename,
			CritterTemplate... critters)
	{
		super(filepath, filename);

		this.critters = critters;
	}

	/**
	 * 
	 * Returns the critter templates to export.
	 * 
	 * @return the critter templates to export
	 * 
	 */
	public CritterTemplate[] getCritters()
	{
		return critters;
	}
}

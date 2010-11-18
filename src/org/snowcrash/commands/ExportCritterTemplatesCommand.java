package org.snowcrash.commands;

import org.snowcrash.critter.CritterTemplate;


/**
 * 
 * This class allows the export of critter templates.
 * 
 * @author Mike
 * 
 * 18 Nov - Updated to reflect that the entire filename is captured via a file's filepath.
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
	 * @deprecated
	 * 
	 */
	@Deprecated
	public ExportCritterTemplatesCommand(String filepath, String filename,
			CritterTemplate... critters)
	{
		super(filepath, filename);

		this.critters = critters;
	}
	
	/**
	 * 
	 * Constructor.
	 * 
	 * @param filename the file name of the file, including the file path
	 * @param critters the critter templates to export
	 * 
	 */
	public ExportCritterTemplatesCommand(String filename, CritterTemplate ... critters)
	{
		super(filename);
		
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

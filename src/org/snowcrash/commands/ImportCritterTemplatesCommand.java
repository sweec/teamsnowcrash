package org.snowcrash.commands;


/**
 * 
 * This class allows the import of critter templates.
 * 
 * @author Mike
 *
 */
public class ImportCritterTemplatesCommand extends FileCommand
{
	/**
	 * 
	 * Constructor.
	 * 
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * 
	 */
	public ImportCritterTemplatesCommand(String filepath, String filename)
	{
		super(filepath, filename);
	}
}

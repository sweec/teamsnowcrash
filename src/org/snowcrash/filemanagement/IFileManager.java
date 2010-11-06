package org.snowcrash.filemanagement;

import org.snowcrash.critter.CritterTemplate;


/**
 * 
 * This interface defines the methods of file manager implementations.
 * 
 * @author Mike
 *
 */
public interface IFileManager
{
	/**
	 * 
	 * Saves the given critter templates to a file.
	 * 
	 * @param critterTemplates the critter templates to save
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * @return true, if it saved successfully; false, otherwise
	 * 
	 */
	public boolean saveCritterTemplates(CritterTemplate[] critterTemplates, 
			String filepath, String filename);

	/**
	 * 
	 * Loads critter templates from a file.
	 * 
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * @return an array of critter templates from the file
	 * 
	 */
	public CritterTemplate[] loadCritterTemplates(String filepath, String filename);
}

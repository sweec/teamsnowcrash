package org.snowcrash.filemanagement;

import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.world.World;


/**
 * 
 * This interface defines more appropriate methods that IFileManager for certain 
 * activities.
 * 
 * @author Mike
 *
 */
public interface IFileManager2 extends IFileManager
{
	/**
	 * 
	 * Saves the given simulation world to a file.
	 * 
	 * @param world the world to save
	 * @param filename the file name of the file, including the file path
	 * @return true, if it saved successfully; false, otherwise
	 * 
	 */
	public boolean saveWorld( World world, String filename );
	
	/**
	 * 
	 * Loads simulation world from a file.
	 * 
	 * @param filename the file name of the file, including the file path
	 * @return an world instance from the file
	 * 
	 */
	public World loadWorld( String filename );
	
	/**
	 * 
	 * Saves the given critter templates to a file.
	 * 
	 * @param critterTemplates the critter templates to save
	 * @param filename the file name of the file, including the file path
	 * @return true, if it saved successfully; false, otherwise
	 * 
	 */
	public boolean saveCritterTemplates(CritterTemplate[] critterTemplates, 
			String filename);

	/**
	 * 
	 * Loads critter templates from a file.
	 * 
	 * @param filename the file name of the file, including the file path
	 * @return an array of critter templates from the file
	 * 
	 */
	public CritterTemplate[] loadCritterTemplates(String filename);
	
}

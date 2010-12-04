/*  
 * IFileManager: interface that FileManager implements. 
 * Copyright (C) 2010  Team Snow Crash
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Artistic License/GNU GPL as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Artistic License/GNU General Public License for more details.
 *
 * You should have received a copy of the Artistic license/GNU General 
 * Public License along with this program.  If not, see
 * <http://dev.perl.org/licenses/artistic.html> and 
 * <http://www.gnu.org/licenses/>.
 * 
 */

package org.snowcrash.filemanagement;

import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.world.World;

/**
 * @author dearnest, dong, Mike
 * 
 * This interface defines the methods of file manager implementations.
 * 
 * When a new simulation starts, call setLogger() first before logging any messages.
 * 
 */
public interface IFileManager
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
	 * Reset the whole simulation world
	 * Load default Critter templates
	 * @return the new world instance
	 * 
	 */
	public World resetWorld();
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
	/**
	 * 
	 * Initialize the default log file.
	 * 
	 */
	public void setLogger();
	/**
	 * 
	 * add message to log file
	 * @param message the message to be logged
	 */
	public void logMessage( String message );
	/**
	 * 
	 * Display the default log file in default window
	 * 
	 */
	public void viewLogFile();
}

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
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * @return true, if it saved successfully; false, otherwise
	 * 
	 */
	public boolean saveWorld( World world, String filepath, String filename );
	
	/**
	 * 
	 * Loads simulation world from a file.
	 * 
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * @return an world instance from the file
	 * 
	 */
	public World loadWorld( String filepath, String filename );
	
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
	
	/**
	 * 
	 * Initialize the default log file.
	 * 
	 */
	public void setLogger();
	
	/**
	 * 
	 * Initialize with user specified log file.
	 * 
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * 
	 */
	public void setLogger(String filepath, String filename);
	
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
	
	/**
	 * 
	 * Display user specified log file in default window.
	 * 
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * 
	 */
	public void viewLogFile(String filepath, String filename);

	/**
	 * 
	 * Display user specified log file in a window of wXh.
	 * Display r rows each time.
	 * 
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * @param w width of the window
	 * @param h height of the window
	 * @param r the number of rows to display each time in the window
	 * 
	 */
	public void viewLogFile(String filepath, String filename, int w, int h, int r);
}

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

import org.snowcrash.critter.Critter;
import org.snowcrash.world.World;

/**
 * @author dearnest, dong
 * interface that FileManager implements.
 * 
 * when a new simulation start
 * call setLogger() first before log any messages
 * Check FileManager.java for test examples
 * 
 */
public interface IFileManager {
	public boolean saveWorld(World world, String filepath, String filename);

	public World loadWorld(String filepath, String filename);
	
	public boolean saveCritters(Critter[] critter, String filepath, String filename);

	public Critter[] loadCritters(String filepath, String filename);
	
	public void setLogger();
	
	public void logMessage(String message);
	
	public void viewLogFile();
}

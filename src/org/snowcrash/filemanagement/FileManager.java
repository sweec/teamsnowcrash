/*  
 * FileManager: utility that handles all file I/O operations. 
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

import org.snowcrash.critter.*;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.world.World;
import org.snowcrash.utilities.*;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;
import com.google.gson.JsonElement;

import java.util.logging.*;

/**
 * @author dong
 * utility that handles all file I/O operations.
 * 
 * gson is used for save/load.
 * setLogger() should be called first each time a new session started.
 * 
 */

public class FileManager implements IFileManager {
	static String  logFile = "EvolutionSim.log";
	static Logger logger = null;
	FileHandler fh = null;

	public boolean saveWorld(World world, String filepath, String filename) {
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filepath + filename)); 
			Gson gson = new Gson();
			//gson.toJson(world, out);	// this works, but can't be used by fromJson
			// gson's fromJson methods require no-arguments constructor for object
			// both Critter and CritterBase can't have constructor
			// World has data type Critter, which is not supported by gson
			// have to save the data manually to satisfy loadWorld
			int[] size = new int[2];
			size[0] = world.getSizeX();
			size[1] = world.getSizeY();
			gson.toJson(size, out);
			gson.toJson(world.getMap(), out);
			out.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
			return false;
		} 	
		return true;
	}

	public World loadWorld(String filepath, String filename) {
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filepath + filename)); 
			Gson gson = new Gson();
			//World world = gson.fromJson(in, World.class);	// this does not work
			// gson's fromJson methods require no-arguments constructor for object
			// both Critter and CritterBase can't have constructor
			// World has data type Critter, which is not supported by gson
			// have to load the data manually
			// Since all Critter types do not differ in terms of the role of container,
			// I just pick Plant. Let's see if this will cause problems.
			World world = null;
			int[] size = null;
			Plant[][] critter = null;
			JsonStreamParser parser = new JsonStreamParser(in);
			JsonElement element;
			if (parser.hasNext()) {
				element = parser.next();
				size = gson.fromJson(element, int[].class);
			}
			if (parser.hasNext()) {
				element = parser.next();
				critter = gson.fromJson(element, Plant[][].class);
			}
			if (size != null && critter != null) {
				world = World.getInstance();
				int sizeX = size[0];
				int sizeY = size[1];
				world.setSize(sizeX, sizeY);
				int x, y;
				for (x = 0;x < sizeX;x++) {
					for (y = 0;y < sizeY;y++) {
						world.add(x, y, critter[x][y]);
					}
				}
			}
			in.close(); 
			return world;
		} catch (IOException e) { 
			e.printStackTrace();
			return null;
		} 
	}
	
	public boolean saveCritters(ICritter[] critter, String filepath, String filename) {
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filepath + filename)); 
			Gson gson = new Gson();
			gson.toJson(critter, out);
			out.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
			return false;
		} 	
		return true;
	}

	public ICritter[] loadCritters(String filepath, String filename) {
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filepath + filename)); 
			
			Gson gson = new Gson();
			// gson's fromJson methods require no-arguments constructor for object
			// both Critter and CritterBase can't have constructor
			// Since all Critter types do not differ in terms of the role of container,
			// I just pick Plant. Let's see if this will cause problems.
			Plant[] critter = gson.fromJson(in, Plant[].class);
			in.close(); 
			return critter;
		} catch (IOException e) { 
			e.printStackTrace();
			return null;
		} 
	}

	public void setLogger() {
	    try {
	    	// clear old log
	    	if (fh != null) {
	    		fh.flush();
	    		fh.close();
	    		if (logger != null) {
	    			logger.removeHandler(fh);
	    		}
	    	}
	    	File file = new File(logFile);
	    	if (file.exists()) {
	    		file.delete();
	    	}
	    	boolean append = true;
	    	fh = new FileHandler(logFile, append);
	    	//fh.setFormatter(new XMLFormatter());
	    	//fh.setFormatter(new SimpleFormatter());
	    	fh.setFormatter(new LogFormatter());
	    	logger = Logger.getLogger(logFile);	// logFile is just a name here
	    	logger.addHandler(fh);
	    }
	    catch (IOException e) {
	    	e.printStackTrace();
	    }
	 }

	public void logMessage(String message) {
		if (logger != null) {
			logger.info(message);
		}
	}
	
	public void viewLogFile() {
		int rows = 40;
        LogViewer v = new LogViewer(rows);
        v.display(logFile, 500, 570);
	}

	public static void main(String[] args) {
		// test saveCritters/loadCritters
		CritterTemplate template = new CritterTemplate();
		template.setPrototype(CritterPrototype.PREY);
		ICritter[] critter = new ICritter[3];
		critter[0] = CritterFactory.getCritter(template);
		critter[1] = CritterFactory.getCritter(template);
		critter[2] = CritterFactory.getCritter(template);
		FileManager mgr = new FileManager();
		mgr.saveCritters(critter, "", "testCritter.csv");
		ICritter[] critter2 = mgr.loadCritters("", "testCritter.csv");
		int i;
		for (i = 0;i < critter2.length;i++) {
			System.out.println(critter2[i]);
		}
		
		// test saveWorld/loadWorld
		World world = new World();
		world.setSize(3, 3);
		world.add(0, 0, critter[0]);
		world.add(1, 1, critter[1]);
		world.add(2, 2, critter[2]);
		mgr.saveWorld(world, "", "testWorld.csv");
		World world2 = mgr.loadWorld("", "testWorld.csv");
		ICritter[][] critters = new ICritter[3][3];
		Pair<Integer, Integer> pair;
		pair = new Pair<Integer, Integer>(0, 0);
		critters[0][0] = world2.get(pair);
		pair = new Pair<Integer, Integer>(1, 1);
		critters[1][1] = world2.get(pair);
		pair = new Pair<Integer, Integer>(2, 2);
		critters[2][2] = world2.get(pair);
		System.out.println(world2.getSizeX()+" "+world2.getSizeY());
		for (i = 0;i < critter2.length;i++) {
			System.out.println(critter2[i]);
		}
		
		// test viewLogFile
		mgr.setLogger();
		for (i = 0;i < 1000;i++) {
			mgr.logMessage("This is line "+i);
		}
		// mgr.viewLogFile();
		
		// test erase log file
		mgr.setLogger();
		for (i = 0;i < 1000;i++) {
			mgr.logMessage("Now we have new line "+i);
		}
		mgr.viewLogFile();
	}
}

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
import org.snowcrash.critter.data.Size;
import org.snowcrash.world.World;
import org.snowcrash.utilities.*;

import java.io.*;
import com.google.gson.Gson;
//import com.google.gson.JsonStreamParser;
//import com.google.gson.JsonElement;

import java.util.logging.*;

/**
 * @author dong
 * utility that handles all file I/O operations.
 * 
 * gson is used for save/load.
 * setLogger() should be called first each time a new session started.
 * check test case for example.
 */

public class FileManager implements IFileManager {
	static String  logFile = "EvolutionSim.log";
	static Logger logger = null;
	FileHandler fh = null;

	public boolean saveWorld(World world, String filepath, String filename) {
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filepath + filename)); 
			Gson gson = new Gson();
			gson.toJson(world, out);
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
			World world = gson.fromJson(in, World.class);
			in.close(); 
			return world;
		} catch (IOException e) { 
			e.printStackTrace();
			return null;
		} 
	}
	
	public boolean saveCritterTemplates(CritterTemplate[] critterTemplates, 
			String filepath, String filename) {
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filepath + filename)); 
			Gson gson = new Gson();
			gson.toJson(critterTemplates, out);
			out.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
			return false;
		} 	
		return true;
	}

	public CritterTemplate[] loadCritterTemplates(String filepath, String filename) {
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filepath + filename)); 
			
			Gson gson = new Gson();
			CritterTemplate[] critterTemplate = gson.fromJson(in, CritterTemplate[].class);
			in.close(); 
			return critterTemplate;
		} catch (IOException e) { 
			e.printStackTrace();
			return null;
		} 
	}

	public void setLogger(String filepath, String filename) {
	    try {
	    	// clear old log
	    	if (fh != null) {
	    		fh.flush();
	    		fh.close();
	    		if (logger != null) {
	    			logger.removeHandler(fh);
	    		}
	    	}
	    	File file = new File(filepath + filename);
	    	if (file.exists()) {
	    		file.delete();
	    	}
	    	boolean append = true;
	    	fh = new FileHandler(filepath + filename, append);
	    	//fh.setFormatter(new XMLFormatter());
	    	//fh.setFormatter(new SimpleFormatter());
	    	fh.setFormatter(new LogFormatter());
	    	logger = Logger.getLogger(filepath + filename);	// arg is just a name here
	    	logger.addHandler(fh);
	    }
	    catch (IOException e) {
	    	e.printStackTrace();
	    }
	}

	public void setLogger() {
		setLogger("", logFile);
	}

	public void logMessage(String message) {
		if (logger != null) {
			logger.info(message);
		}
	}
	
	public void viewLogFile(String filepath, String filename, int w, int h, int r) {
        LogViewer v = new LogViewer(r);
        v.display(filepath + filename, w, h);
	}

	public void viewLogFile(String filepath, String filename) {
		viewLogFile(filepath, filename, 40, 500, 570);
	}

	public void viewLogFile() {
		viewLogFile("", logFile, 40, 500, 570);
	}

	public static void main(String[] args) {
		// test saveCritterTemplate/loadCritterTemplate
		CritterTemplate[] templates = new CritterTemplate[3];
		templates[0] = new CritterTemplate(CritterPrototype.PLANT, "Plant1");
		templates[0].setSize(Size.MEDIUM);
		templates[1] = new CritterTemplate(CritterPrototype.PREY, "Prey1");
		templates[1].setSize(Size.SMALL);
		templates[2] = new CritterTemplate(CritterPrototype.PREDATOR, "Predator1");
		templates[2].setSize(Size.LARGE);
		Critter[] critter = new Critter[3];
		critter[0] = CritterFactory.getCritter(templates[0]);
		critter[1] = CritterFactory.getCritter(templates[1]);
		critter[2] = CritterFactory.getCritter(templates[2]);
		FileManager mgr = new FileManager();
		mgr.saveCritterTemplates(templates, "", "testCritterTemplates.Json");
		CritterTemplate[] template2 = mgr.loadCritterTemplates("", "testCritterTemplates.Json");
		int i;
		for (i = 0;i < template2.length;i++) {
			System.out.println(template2[i]);
		}
		
		// test saveWorld/loadWorld
		World world = new World();
		world.setSize(3, 3);
		world.add(0, 0, critter[0]);
		world.add(1, 1, critter[1]);
		world.add(2, 2, critter[2]);
		mgr.saveWorld(world, "", "testWorld.Json");
		World world2 = mgr.loadWorld("", "testWorld.Json");
		Critter[][] critters = new Critter[3][3];
		Pair<Integer, Integer> pair;
		pair = new Pair<Integer, Integer>(0, 0);
		critters[0][0] = world2.get(pair);
		pair = new Pair<Integer, Integer>(1, 1);
		critters[1][1] = world2.get(pair);
		pair = new Pair<Integer, Integer>(2, 2);
		critters[2][2] = world2.get(pair);
		System.out.println(world2.getSizeX()+" "+world2.getSizeY());
		System.out.println(critters[0][0]);
		System.out.println(critters[1][1]);
		System.out.println(critters[2][2]);
		
		// test viewLogFile
		/*
		mgr.setLogger();
		for (i = 0;i < 1000;i++) {
			mgr.logMessage("This is line "+i);
		}
		mgr.viewLogFile();
		
		// test erase log file
		mgr.setLogger();
		for (i = 0;i < 1000;i++) {
			mgr.logMessage("Now we have new line "+i);
		}
		mgr.viewLogFile();
		*/
	}
}
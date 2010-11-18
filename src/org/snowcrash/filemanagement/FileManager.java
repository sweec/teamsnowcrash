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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.CritterFactory;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.critter.data.Size;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.world.World;

import com.google.gson.Gson;

/**
 * @author dong
 * utility that handles all file I/O operations.
 * 
 * gson is used for save/load.
 * setLogger() should be called first each time a new session started.
 * check test case for example.
 */

public class FileManager implements IFileManager {
	private static String  logFile = "EvolutionSim.log";
	private static Logger logger = null;
	private static FileHandler fh = null;

	public boolean saveWorld(World world, String filepath, String filename) {
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filepath)); 
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
			BufferedReader in = new BufferedReader(new FileReader(filepath)); 
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
		if (critterTemplates == null) {
			DatabaseObject[] objects;
			DAO dao = DAOFactory.getDAO();
			try {
				objects = dao.read( CritterTemplate.class );
			} catch (DAOException e) {
				throw new RuntimeException( e );
			}
			critterTemplates = new CritterTemplate[objects.length];
			int i;
			for (i = 0;i < objects.length;i++)
				critterTemplates[i] = (CritterTemplate) (objects[i]);
		}
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filepath)); 
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
		CritterTemplate[] critterTemplate = null;
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filepath)); 
			
			Gson gson = new Gson();
			critterTemplate = gson.fromJson(in, CritterTemplate[].class);
			in.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
		}
		if (critterTemplate == null) return null;
		DAO dao = DAOFactory.getDAO();
		int i;
		for (i = 0;i < critterTemplate.length;i++) {
			try {
				dao.create( critterTemplate[i] );
			} catch (DAOException e) {
				throw new RuntimeException( e );
			}
		}
		return critterTemplate;
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
	    	File file = new File(filepath);
	    	if (file.exists()) {
	    		file.delete();
	    	}
	    	boolean append = true;
	    	fh = new FileHandler(filepath, append);
	    	//fh.setFormatter(new XMLFormatter());
	    	//fh.setFormatter(new SimpleFormatter());
	    	fh.setFormatter(new LogFormatter());
	    	logger = Logger.getLogger(filepath);	// arg is just a name here
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
        v.display(filepath, w, h);
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
		mgr.saveCritterTemplates(templates, "testCritterTemplates.Json", "");
		CritterTemplate[] template2 = mgr.loadCritterTemplates("testCritterTemplates.Json", "");
		int i;
		for (i = 0;i < template2.length;i++) {
			System.out.println(template2[i]);
		}
		
		// test saveWorld/loadWorld
		World world = World.getInstance();
		world.setSize(3, 3);
		world.add(0, 0, critter[0]);
		world.add(1, 1, critter[1]);
		world.add(2, 2, critter[2]);
		mgr.saveWorld(world, "testWorld.Json", "");
		World world2 = mgr.loadWorld("testWorld.Json", "");
		Critter[][] critters = world2.getMap();
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

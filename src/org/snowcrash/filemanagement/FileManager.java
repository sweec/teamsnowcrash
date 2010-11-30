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
import java.lang.reflect.Type;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.CritterFactory;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.testCritterTemplate;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.critter.data.Size;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.state.Growing;
import org.snowcrash.state.Hunting;
import org.snowcrash.state.Moving;
import org.snowcrash.state.Reproducing;
import org.snowcrash.state.Searching;
import org.snowcrash.state.State;
import org.snowcrash.utilities.Pair;
import org.snowcrash.world.World;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonStreamParser;

/**
 * @author dong
 * utility that handles all file I/O operations.
 * 
 * gson is used for save/load.
 * setLogger() should be called first each time a new session started.
 * check test case for example.
 */

public class FileManager implements IFileManager2 {
	private static String  logFile = "EvolutionSim.log";
	private static Logger logger = null;
	private static FileHandler fh = null;

	/**
	 * save the simulation as well as required critter templates to a file
	 * in the case of saveConfiguration, assume all the settings are already within world instance
	 * that means critters need to be populated first
	 */
	public boolean saveWorld(World world, String filepath, String filename) {
		if (world == null) {
			DatabaseObject[] objects = null;
			DAO dao = DAOFactory.getDAO();
			try {
				objects = dao.read( World.class );
			} catch (DAOException e) {
				throw new RuntimeException( e );
			}
			if (objects == null) return false;
			world = (World)objects[0];
		}
		
		// save templates first
		// followed by simulation world

		//saveCritterTemplates(null, filepath + filename);
		saveTestCritterTemplates(null, filepath + filename);
		
		// world instance need to be complete to be saved
		// needed by saveConfiguration
		//World.getInstance().Populate();	// Populate() creates all critters if not yet
		
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filepath + filename, true));
			
			Gson gson = new GsonBuilder()
			.registerTypeAdapter(State.class, new StateSerializer())
			.create();
			gson.toJson(world, out);
			out.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
			return false;
		} 	
		return true;
	}

	public boolean saveWorld( World world, String filename ) {
		return saveWorld(world, "", filename);
	}
	
	/**
	 * load simulation from file
	 */
	public World loadWorld(String filepath, String filename) {
		World world = null;
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filepath + filename)); 
			JsonStreamParser parser = new JsonStreamParser(in);
			JsonElement element;
			Gson gson = new GsonBuilder()
			.registerTypeAdapter(State.class, new StateDeserializer())
			.create();
			if (parser.hasNext()) {
				element = parser.next();
				//CritterTemplate[] templates = gson.fromJson(element, CritterTemplate[].class);
				testCritterTemplate[] templates = gson.fromJson(element, testCritterTemplate[].class);
				if (templates == null) return null;
				// TODO
				// clear database first
				DAO dao = DAOFactory.getDAO();
				int i;
				for (i = 0;i < templates.length;i++) {
					try {
						dao.create( templates[i] );
					} catch (DAOException e) {
						//throw new RuntimeException( e );
					}
				}
			}
			if (parser.hasNext()) {
				element = parser.next();
				world = gson.fromJson(element, World.class);
				/* World is not implementing DatabaseObject yet
				DAO dao = DAOFactory.getDAO();
				try {
					dao.create( world );
				} catch (DAOException e) {
					throw new RuntimeException( e );
				}
				*/
			}
			in.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
		}
		if (world == null) return null;
		return world;
	}
	
	public World loadWorld(String filename) {
		return loadWorld("", filename);
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

	public boolean saveCritterTemplates(CritterTemplate[] critterTemplates, String filename) {
		return saveCritterTemplates(critterTemplates, "", filename);
	}

	public CritterTemplate[] loadCritterTemplates(String filepath, String filename) {
		CritterTemplate[] templates = null;
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filepath + filename)); 
			
			Gson gson = new Gson();
			templates = gson.fromJson(in, CritterTemplate[].class);
			in.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
		}
		if (templates == null) return null;
		DAO dao = DAOFactory.getDAO();
		int i;
		for (i = 0;i < templates.length;i++) {
			try {
				dao.create( templates[i] );
			} catch (DAOException e) {
				throw new RuntimeException( e );
			}
		}
		return templates;
	}

	public CritterTemplate[] loadCritterTemplates(String filename) {
		return loadCritterTemplates("", filename);
	}
	
	public boolean saveTestCritterTemplates(testCritterTemplate[] critterTemplates, String filename) {
		if (critterTemplates == null) {
			DatabaseObject[] objects;
			DAO dao = DAOFactory.getDAO();
			try {
				objects = dao.read( testCritterTemplate.class );
			} catch (DAOException e) {
				throw new RuntimeException( e );
			}
			critterTemplates = new testCritterTemplate[objects.length];
			int i;
			for (i = 0;i < objects.length;i++)
				critterTemplates[i] = (testCritterTemplate) (objects[i]);
		}
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filename)); 
			Gson gson = new Gson();
			gson.toJson(critterTemplates, out);
			out.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public testCritterTemplate[] loadTestCritterTemplates(String filename) {
		testCritterTemplate[] critterTemplate = null;
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filename)); 
			
			Gson gson = new Gson();
			critterTemplate = gson.fromJson(in, testCritterTemplate[].class);
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
	    	fh = new FileHandler(filepath + filename, append);
	    	//fh.setFormatter(new XMLFormatter());
	    	//fh.setFormatter(new SimpleFormatter());
	    	fh.setFormatter(new LogFormatter());
	    	logger = Logger.getLogger(filename);	// arg is just a name here
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
		viewLogFile(filepath, filename, 40, 500, 500);
	}

	public void viewLogFile() {
        LogViewer v = new LogViewer(40);
        v.display(logFile, 500, 500);
	}

	public static void main(String[] args) {
		// test saveCritterTemplate/loadCritterTemplate
		testCritterTemplate[] templates = new testCritterTemplate[6];
		templates[0] = new testCritterTemplate(CritterPrototype.PLANT, "Plant1");
		templates[0].setSize(Size.MEDIUM);
		templates[0].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));
		templates[0].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));
		templates[0].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(5), new Integer(5)));
		templates[0].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));
		templates[0].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));

		templates[1] = new testCritterTemplate(CritterPrototype.PREY, "Prey1");
		templates[1].setSize(Size.SMALL);
		templates[1].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(3), new Integer(5)));
		templates[1].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(1), new Integer(3)));
		templates[1].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[1].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(1), new Integer(4)));
		templates[1].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(1), new Integer(3)));

		templates[2] = new testCritterTemplate(CritterPrototype.PREDATOR, "Predator1");
		templates[2].setSize(Size.LARGE);
		templates[2].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[2].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(3), new Integer(5)));
		templates[2].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[2].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(2), new Integer(5)));
		templates[2].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(2), new Integer(5)));

		templates[3] = new testCritterTemplate(CritterPrototype.PLANT, "Plant2");
		templates[3].setSize(Size.LARGE);
		templates[3].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(2), new Integer(2)));
		templates[3].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));
		templates[3].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(4), new Integer(4)));
		templates[3].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));
		templates[3].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));

		templates[4] = new testCritterTemplate(CritterPrototype.PREY, "Prey2");
		templates[4].setSize(Size.MEDIUM);
		templates[4].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(3), new Integer(5)));
		templates[4].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[4].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[4].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(1), new Integer(4)));
		templates[4].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(1), new Integer(3)));

		templates[5] = new testCritterTemplate(CritterPrototype.PREDATOR, "Predator2");
		templates[5].setSize(Size.SMALL);
		templates[5].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(2), new Integer(5)));
		templates[5].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(3), new Integer(4)));
		templates[5].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(2), new Integer(3)));
		templates[5].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[5].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));

		Critter[] critter = new Critter[12];
		critter[0] = CritterFactory.getCritter(templates[0]);
		critter[1] = CritterFactory.getCritter(templates[1]);
		critter[2] = CritterFactory.getCritter(templates[2]);
		critter[3] = CritterFactory.getCritter(templates[0]);
		critter[4] = CritterFactory.getCritter(templates[1]);
		critter[5] = CritterFactory.getCritter(templates[2]);
		critter[6] = CritterFactory.getCritter(templates[3]);
		critter[7] = CritterFactory.getCritter(templates[4]);
		critter[8] = CritterFactory.getCritter(templates[5]);
		critter[9] = CritterFactory.getCritter(templates[3]);
		critter[10] = CritterFactory.getCritter(templates[4]);
		critter[11] = CritterFactory.getCritter(templates[5]);
		FileManager mgr = new FileManager();
		//mgr.saveCritterTemplates(templates, "testCritterTemplates.Json", "");
		//CritterTemplate[] template2 = mgr.loadCritterTemplates("testCritterTemplates.Json", "");
		mgr.saveTestCritterTemplates(templates, "testCritterTemplates.Json");
		testCritterTemplate[] template2 = mgr.loadTestCritterTemplates("testCritterTemplates.Json");
		int i;
		for (i = 0;i < template2.length;i++) {
			System.out.println(template2[i]);
		}
		
		// test saveWorld/loadWorld
		World world = World.getInstance();
		world.setSize(20, 20);
		for (i = 0;i < critter.length;i++)
			world.add(new Pair<Integer, Integer>(i, i), critter[i]);
		mgr.saveWorld(world, "testWorld.Json", "");
		World world2 = mgr.loadWorld("testWorld.Json", "");
		Critter[][] critters = world2.getMap();
		for (i = 0;i < critters.length;i++)
			for (int j = 0;j < critters[0].length;j++) {
				if (critters[i][j] != null)
					System.out.println(critters[i][j]);
			}
		// test viewLogFile
		
		mgr.setLogger();
		for (i = 0;i < 1000;i++) {
			mgr.logMessage("This is line "+i);
		}
		mgr.viewLogFile();
		/*
		// test erase log file
		mgr.setLogger();
		for (i = 0;i < 1000;i++) {
			mgr.logMessage("Now we have new line "+i);
		}
		mgr.viewLogFile();
		*/
	}

	private class StateSerializer implements JsonSerializer<State> {
		@Override
		public JsonElement serialize(State src, Type typeOfSrc, JsonSerializationContext context) {
		    if (src == null) return null;
		    String name = src.getClass().getName();
		    if (name.lastIndexOf('.') > 0) {
		        name = name.substring(name.lastIndexOf('.')+1);
		    }
		    return new JsonPrimitive(name);
		}
	}

	private class StateDeserializer implements JsonDeserializer<State> {
		@Override
		public State deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		     	throws JsonParseException {
			 if (json.isJsonNull()) return null;
			 String state = json.getAsJsonPrimitive().getAsString();
			 if (state.contentEquals("Growing")) return new Growing();
			 if (state.contentEquals("Hunting")) return new Hunting();
			 if (state.contentEquals("Moving")) return new Moving();
			 if (state.contentEquals("Reproducing")) return new Reproducing();
			 if (state.contentEquals("Searching")) return new Searching();
			 return null;
		}
	}
}


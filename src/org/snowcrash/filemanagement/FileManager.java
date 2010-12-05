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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.StatisticsCollector;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.critter.data.Size;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.dataaccess.SessionedDAO;
import org.snowcrash.gui.BaseGUI;
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
import com.google.gson.reflect.TypeToken;

/**
 * @author dong
 * utility that handles all file I/O operations.
 * 
 * gson is used for save/load.
 * setLogger() should be called first each time a new session started.
 * check test case for example.
 */

public class FileManager implements IFileManager {
	private final String defaultCritterTemplatesFile = "testCritterTemplates.Json";
	private final String defaultLogFile = "EvolutionSim.log";
	private static Logger logger = null;
	private static FileHandler fh = null;
	private final int defaultLogLinePerPage = 40;
	private final int defaultLogPageWidth = 500;
	private final int defaultLogPageHeight = 500;
	

	/**
	 * save the simulation as well as required critter templates to a file
	 * in the case of saveConfiguration, assume all the settings are already within world instance
	 * that means critters need to be populated first
	 */
	public boolean saveWorld(World world, String filename) {
		// save templates first
		saveCritterTemplates(null,filename);
		
		// get world instance from the database
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
		
		// here assume initTemplateList is already set if saveConfiguration
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filename, true));
			
			Gson gson = new GsonBuilder()
			.registerTypeAdapter(State.class, new StateSerializer())
			.create();
			gson.toJson(world, out);
			gson.toJson(StatisticsCollector.getInstance(), out);
			out.close(); 
		} catch (IOException e) { 
			fileIOErrorWindow(filename);
			e.printStackTrace();
			return false;
		} 	
		return true;
	}

	/**
	 * load simulation from file
	 */
	public World loadWorld(String filename) {
		World world = null;
		CritterTemplate[] templates = null;
		StatisticsCollector old = StatisticsCollector.getInstance();
		StatisticsCollector sc = null;
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filename)); 
			JsonStreamParser parser = new JsonStreamParser(in);
			Gson gson = new GsonBuilder()
			.registerTypeAdapter(State.class, new StateDeserializer())
			.registerTypeAdapter(ArrayList.class, new ArrayListDeserializer())
			.create();
			if (parser.hasNext()) {
				templates = gson.fromJson(parser.next(), CritterTemplate[].class);
				if (templates == null) return null;
			}
			if (parser.hasNext()) {
				world = gson.fromJson(parser.next(), World.class);
				if (world == null) return null;
			}
			if (parser.hasNext()) {
				sc = gson.fromJson(parser.next(), StatisticsCollector.class);
				if (sc == null) return null;
			}
			in.close(); 
		} catch (IOException e) { 
			fileIOErrorWindow(filename);
			e.printStackTrace();
		}
		SessionedDAO dao = DAOFactory.getDAO();
		try {
			dao.nuke();
			dao.startSession();
			for (int i = 0;i < templates.length;i++)
				dao.create( templates[i] );
			dao.create( world );
			dao.endSession(true);
		} catch (DAOException e) {
			throw new RuntimeException( e );
		}
		World.removeObserver(old);
		World.addObserver(sc);
		return world;
	}
	
	public World resetWorld() {
		SessionedDAO dao = DAOFactory.getDAO();
		dao.nuke();
		
		World world = World.reset();
		/*try {
			dao.create( world );
		} catch (DAOException e) {
			throw new RuntimeException( e );
		}*/
		// database change will notifyObserver here
		loadCritterTemplates(defaultCritterTemplatesFile);
		World.removeObserver(StatisticsCollector.getInstance());
		World.addObserver(new StatisticsCollector());
		return world;
	}
	
	public boolean saveCritterTemplates(CritterTemplate[] critterTemplates, String filename) {
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
			BufferedWriter out = new BufferedWriter(new FileWriter(filename)); 
			Gson gson = new Gson();
			gson.toJson(critterTemplates, out);
			out.close(); 
		} catch (IOException e) {
			fileIOErrorWindow(filename);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public CritterTemplate[] loadCritterTemplates(String filename) {
		CritterTemplate[] templates = null;
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filename)); 
			
			Gson gson = new Gson();
			templates = gson.fromJson(in, CritterTemplate[].class);
			in.close(); 
		} catch (IOException e) {
			fileIOErrorWindow(filename);
			e.printStackTrace();
		}
		if (templates == null) return null;
		SessionedDAO dao = DAOFactory.getDAO();
		dao.startSession();
		for (int i = 0;i < templates.length;i++)
			saveCritterTemplateToDatabase(templates[i]);
		try {
			dao.endSession(true);
		} catch (DAOException e) {
			throw new RuntimeException( e );
		}
		return templates;
	}

	// if already exists a template with same name, save with name as name#
	private void saveCritterTemplateToDatabase(CritterTemplate template) {
		SessionedDAO dao = DAOFactory.getDAO();
		String name = template.getName();
		int i = 1;
		boolean isSaved = false;
		while (!isSaved) {
			try {
				dao.create( template );
			} catch (DAOException e) {
				if (e.getMessage().contentEquals("Data already exists in the database.")) {
					i++;
					template.setName(name + i);
					continue;
				} else {
					throw new RuntimeException( e );
				}
			}
			isSaved = true;
		}
	}

	private void fileIOErrorWindow(String filename) {
		String message = "Access" + filename + "failed.";
		JOptionPane.showMessageDialog(BaseGUI.getInstance(), message);
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
	    	File file = new File(filepath+filename);
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
		setLogger("", defaultLogFile);
	}

	public void logMessage(String message) {
		if (logger != null) {
			logger.info(message);
		}
	}
	
	public void viewLogFile(String filename, int w, int h, int r) {
        LogViewer v = new LogViewer(r);
        v.display(filename, w, h);
	}

	public void viewLogFile(String filename) {
		viewLogFile(filename, defaultLogLinePerPage, defaultLogPageWidth, defaultLogPageHeight);
	}

	public void viewLogFile() {
        LogViewer v = new LogViewer(defaultLogLinePerPage);
        v.display(defaultLogFile, defaultLogPageWidth, defaultLogPageHeight);
	}

	public static void main(String[] args) {
		// test saveCritterTemplate/loadCritterTemplate
		CritterTemplate[] templates = new CritterTemplate[6];
		templates[0] = new CritterTemplate(CritterPrototype.PLANT, "Plant1");
		templates[0].setSize(Size.MEDIUM);
		templates[0].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));
		templates[0].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));
		templates[0].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(5), new Integer(5)));
		templates[0].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));
		templates[0].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));

		templates[1] = new CritterTemplate(CritterPrototype.PREY, "Prey1");
		templates[1].setSize(Size.SMALL);
		templates[1].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(3), new Integer(5)));
		templates[1].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(1), new Integer(3)));
		templates[1].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[1].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(1), new Integer(4)));
		templates[1].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(1), new Integer(3)));

		templates[2] = new CritterTemplate(CritterPrototype.PREDATOR, "Predator1");
		templates[2].setSize(Size.LARGE);
		templates[2].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[2].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(3), new Integer(5)));
		templates[2].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[2].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(2), new Integer(5)));
		templates[2].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(2), new Integer(5)));

		templates[3] = new CritterTemplate(CritterPrototype.PLANT, "Plant2");
		templates[3].setSize(Size.LARGE);
		templates[3].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(2), new Integer(2)));
		templates[3].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));
		templates[3].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(4), new Integer(4)));
		templates[3].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));
		templates[3].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(1), new Integer(1)));

		templates[4] = new CritterTemplate(CritterPrototype.PREY, "Prey2");
		templates[4].setSize(Size.MEDIUM);
		templates[4].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(3), new Integer(5)));
		templates[4].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[4].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[4].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(1), new Integer(4)));
		templates[4].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(1), new Integer(3)));

		templates[5] = new CritterTemplate(CritterPrototype.PREDATOR, "Predator2");
		templates[5].setSize(Size.SMALL);
		templates[5].setTraitRange(Trait.CAMO, new Pair<Integer, Integer>(new Integer(2), new Integer(5)));
		templates[5].setTraitRange(Trait.COMBAT, new Pair<Integer, Integer>(new Integer(3), new Integer(4)));
		templates[5].setTraitRange(Trait.ENDURANCE, new Pair<Integer, Integer>(new Integer(2), new Integer(3)));
		templates[5].setTraitRange(Trait.SPEED, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));
		templates[5].setTraitRange(Trait.VISION, new Pair<Integer, Integer>(new Integer(2), new Integer(4)));

		FileManager mgr = new FileManager();
		mgr.saveCritterTemplates(templates, "testCritterTemplates.Json");
		CritterTemplate[] template2 = mgr.loadCritterTemplates("testCritterTemplates.Json");
		int i;
		for (i = 0;i < template2.length;i++) {
			System.out.println(template2[i]);
		}
		
		// test saveWorld/loadWorld
		World world = World.getInstance();
		world.setSize(50, 50);	// map only initiated here
		ArrayList<Pair<CritterTemplate,Integer>> list = new ArrayList<Pair<CritterTemplate,Integer>>();
		for (i = 0;i < templates.length;i++)
			list.add(new Pair<CritterTemplate, Integer>(templates[i], 2));
		world.randomPopulate(list);
		mgr.saveWorld(world, "testWorld.Json");
		World world2 = mgr.loadWorld("testWorld.Json");
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
		
		// test erase log file
		mgr.setLogger();
		for (i = 0;i < 1000;i++) {
			mgr.logMessage("Now we have new line "+i);
		}
		mgr.viewLogFile();
		
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

	private class ArrayListDeserializer implements JsonDeserializer<ArrayList<Pair<CritterTemplate,Integer>>> {
		@Override
		public ArrayList<Pair<CritterTemplate,Integer>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		     	throws JsonParseException {
			 if (json.isJsonNull()) return null;
			 Type type = new TypeToken<ArrayList<Pair<CritterTemplate,Integer>>>(){}.getType();
			 LinkedList data = (new Gson()).fromJson(json, type);
			 return new ArrayList<Pair<CritterTemplate,Integer>>(data);
		}
	}
}


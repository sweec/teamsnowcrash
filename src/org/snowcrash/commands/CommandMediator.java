package org.snowcrash.commands;

import java.util.ArrayList;

import org.snowcrash.configurationservice.IConfigurationManager;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.filemanagement.IFileManager;
import org.snowcrash.timeengine.TimeEngine;
import org.snowcrash.utilities.Pair;
import org.snowcrash.world.World;


/**
 * 
 * This class handles commands and promotes decoupling by allowing any area of the 
 * application to request that something happens without having to know how it 
 * happens.  There was a conscious decision to handle the commands in a mediator, 
 * rather than within the command implementations themselves, as it is easier to 
 * ensure that the commands' references to managers do not get changes at run time.
 * 
 * @author Mike
 *
 */
public class CommandMediator
{
	/*
	 * Reference to the file manager.
	 */
	private static IFileManager fileManager = null;

	/*
	 * Reference to the configuration manager.
	 */
	private static IConfigurationManager configManager = null;
	
	/*
	 * Private constructor enforces use as a static class.
	 */
	private CommandMediator()
	{
		// -- Static class.
	}
	
	/**
	 * 
	 * Returns the critter templates from the database.
	 * 
	 * @return the critter templates from the database
	 * 
	 */
	static CritterTemplate[] getCritterTemplates()
	{
		return configManager.getCritterTemplates();
	}
	
	/**
	 * 
	 * Returns the critter template from the database.
	 * 
	 * @param name the name of the critter template
	 * @return the critter template from the database
	 * 
	 */
	static CritterTemplate getCritterTemplate( String name )
	{
		return configManager.getCritterTemplateByName(name);
	}
	
	/**
	 * 
	 * Sets the size of the simulation World.getInstance().
	 * 
	 * @param worldSize the size of the simulation World.getInstance()
	 * 
	 */
	static void setWorldSize( int worldSize )
	{
		World.getInstance().setSize(worldSize, worldSize);
	}
	
	/**
	 * 
	 * Sets the number of turns in the simulation.
	 * 
	 * @param numberOfTurns the number of turns in the simulation
	 * 
	 */
	static void setNumberOfTurns( int numberOfTurns )
	{
		World.getInstance().setTurns(numberOfTurns);
		TimeEngine.setTimeLimit(numberOfTurns);
	}
	
	/**
	 * 
	 * Saves critter templates to a file.
	 * 
	 * @param filename the filename of the file
	 * 
	 */
	static void saveCritterTemplate( String filename )
	{
		CritterTemplate[] templates = getCritterTemplates();
		
		fileManager.saveCritterTemplates(templates, filename);
	}
	
	/**
	 * 
	 * Load critter templates from a file.
	 * 
	 * @param filename the filename of the file
	 * 
	 */
	static void loadCritterTemplates( String filename )
	{
		fileManager.loadCritterTemplates(filename);
	}
	
	/**
	 * 
	 * Save the current configuration to a file.
	 * 
	 * @param filename the filename of the file
	 * 
	 */
	static void saveConfiguration( String filename )
	{
		// need call World.getInstance().randomPopulate(list)
		// to get critter numbers saved
		fileManager.saveWorld(World.getInstance(), filename);
	}
	
	/**
	 * 
	 * Load a saved configuration from a file.
	 * 
	 * @param filename the filename of the file
	 * 
	 */
	static void loadConfiguration( String filename )
	{
		World oldWorld = World.getInstance();
		World newWorld = fileManager.loadWorld(filename);
		if (newWorld == null) return;	// IO error, go back
		TimeEngine.removeTimeListener(oldWorld);
		TimeEngine.stopTimer();
		TimeEngine.addTimeListener(newWorld);
	}
	
	static void saveSimulation( String filename )
	{
		fileManager.saveWorld(World.getInstance(), filename);
	}
	
	static void loadSimulation( String filename )
	{
		World oldWorld = World.getInstance();
		World newWorld = fileManager.loadWorld(filename);
		if (newWorld == null) return;
		TimeEngine.removeTimeListener(oldWorld);
		TimeEngine.stopTimer();
		TimeEngine.addTimeListener(newWorld);
	}
	
	static void saveResults( String filename )
	{
		fileManager.saveWorld(World.getInstance(), filename);
	}
	
	static void loadResults( String filename )
	{
		World oldWorld = World.getInstance();
		World newWorld = fileManager.loadWorld(filename);
		if (newWorld == null) return;	// IO error, go back
		TimeEngine.removeTimeListener(oldWorld);
		TimeEngine.stopTimer();
		TimeEngine.addTimeListener(newWorld);
	}
	
	static void startSimulation()
	{
		World world = World.getInstance();
		if (world.getCurrentTurn() == 0) {
			DAO dao = DAOFactory.getDAO();
			DatabaseObject[] objects = null;
			try {
				objects = dao.read(CritterTemplate.class);
			} catch (DAOException e) {
				throw new RuntimeException(e) ;
			}
			ArrayList<Pair<CritterTemplate,Integer>> list = new ArrayList<Pair<CritterTemplate,Integer>>();
			for (int i = 0;i < objects.length;i++) {
				CritterTemplate template = (CritterTemplate)objects[i];
				int count = template.getStartingInstancesCount();
				if (count != 0)
					list.add(new Pair<CritterTemplate, Integer>(template, count));
			}
			if (list.isEmpty()) {
				System.out.println("no critterTemplate is selected.");
				return;
			}
			world.randomPopulate(list);
		}
		// in case of load simulation/results
		// timeLimit left need to be calculated
		int timeLimit = world.getTurns() - world.getCurrentTurn();
		if (timeLimit <= 0) return;
		TimeEngine.setTimeLimit(timeLimit);
		TimeEngine.startTimer();
	}
	
	static void pauseSimulation()
	{
		TimeEngine.pauseTimer();
	}
	
	static void resumeSimulation()
	{
		TimeEngine.resumeTimer();
	}
	
	static void stopSimulation()
	{
		TimeEngine.stopTimer();
	}
	
	static void simulateToEnd()
	{
		TimeEngine.pauseTimer();
		TimeEngine.setTickInterval(0);
		TimeEngine.resumeTimer();
	}

	/**
	 * 
	 * Handles the given command.
	 * 
	 * @param command the command that should be handled
	 * @deprecated
	 * 
	 */
	@Deprecated
	static void handleCommand( Command command )
	{
		if (command instanceof TestCommand)
		{
			System.out.println( ((TestCommand) command).getMessage() );
		}
		else if ( command instanceof CreateCritterTemplateCommand )
		{
			CreateCritterTemplateCommand c = (CreateCritterTemplateCommand) command;

			configManager.createCritterTemplate( c.getPrototype(), c.getName() );
		}
		else if ( command instanceof ModifyCritterTemplateCommand )
		{
			ModifyCritterTemplateCommand c = (ModifyCritterTemplateCommand) command;

			configManager.updateCritterTemplate( c.getTemplate() );
		}
		else if ( command instanceof DeleteCritterTemplateCommand )
		{
			DeleteCritterTemplateCommand c = (DeleteCritterTemplateCommand) command;

			configManager.deleteCritterTemplate(c.getTemplate());
		}
		else
		{
			throw new UnsupportedOperationException( "The command " + 
					command.getClass().getSimpleName() + " is not yet supported." );
		}
	}
	
	/**
	 * 
	 * Sets the file manager implementation for the CommandMediator to use.
	 * 
	 * @param fileManager the file manager to use
	 * 
	 */
	public static void setFileManager( IFileManager fileManager )
	{
		CommandMediator.fileManager = fileManager;
	}
	
	/**
	 * 
	 * Sets the configuration manager implementation for the CommandMediator to use.
	 * 
	 * @param configurationManager the configuration manager to use
	 * 
	 */
	public static void setConfigurationManager( IConfigurationManager configurationManager )
	{
		CommandMediator.configManager = configurationManager;
	}
	
	static void openLog() {
		fileManager.viewLogFile();
	}
	
	static void reset() {
		TimeEngine.stopTimer();
		TimeEngine.removeTimeListener(World.getInstance());
		TimeEngine.addTimeListener(fileManager.resetWorld());
	}
}

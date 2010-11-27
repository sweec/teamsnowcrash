package org.snowcrash.commands;

import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.utilities.Callback;


/**
 * 
 * This class provides methods for constructing new commands of various types.  The 
 * implementation is abstracted from the client code, and the client code does not 
 * have to know about the individual types of commands, allowing for a greater level 
 * of decoupling.
 * 
 * @author Mike
 *
 */
public class CommandFactory
{
	private CommandFactory()
	{
		// -- Static class.
	}
	
	/**
	 * 
	 * Returns a test command.
	 * 
	 * FIXME: This method should be removed before delivering the software.
	 * 
	 * @return a test command
	 * 
	 */
	public static Command getTestCommand()
	{
		Command testCommand = new TestCommand();
		
		return testCommand;
	}
	
	/**
	 * 
	 * Returns an import template command.
	 * 
	 * @param filepath the filepath of the file to import from
	 * @param filename the filename of the file to import from
	 * @return an import template command
	 * 
	 */
	public static Command getImportTemplatesCommand( String filepath, String filename )
	{
		Command command = new ImportCritterTemplatesCommand(filepath);
		
		return command;
	}
	
	/**
	 * 
	 * Returns an export template command.
	 * 
	 * @param filepath the filepath of the file to export to
	 * @param filename the filename of the file to export to
	 * @return an export template command
	 * 
	 */
	public static Command getExportTemplatesCommand( String filepath, String filename )
	{
		Command command = new ExportCritterTemplatesCommand(filepath);
		
		return command;
	}
	
	/**
	 * 
	 * Returns a create critter template command.
	 * 
	 * @param prototype the prototype of the CritterTemplate to create
	 * @param name the name of the CritterTemplate to create
	 * @return a create critter template command
	 * 
	 */
	public static Command getCreateTemplateCommand( CritterPrototype prototype, String name )
	{
		Command command = new CreateCritterTemplateCommand(prototype, name);
		
		return command;
	}
	
	/**
	 * 
	 * Returns a modify critter template command.
	 * 
	 * @param template the template to modify
	 * @return a modify critter template command
	 * 
	 */
	public static Command getModifyTemplateCommand( CritterTemplate template )
	{
		Command command = new ModifyCritterTemplateCommand(template);
		
		return command;
	}
	
	/**
	 * 
	 * Returns a delete critter template command.
	 * 
	 * @param template the critter template to delete
	 * @return a delete critter template command
	 * 
	 */
	public static Command getDeleteTemplateCommand( CritterTemplate template )
	{
		Command command = new DeleteCritterTemplateCommand(template);
		
		return command;
	}
	
	/**
	 * 
	 * Returns a delete critter template command.
	 * 
	 * @param templateName the name of the critter template to delete
	 * @return a delete critter template command
	 * 
	 */
	public static Command getDeleteTemplateCommand( String templateName )
	{
		Command command = new DeleteCritterTemplateCommand(templateName);
		
		return command;
	}
	
	/**
	 * 
	 * Returns a retrieve critter template command.
	 * 
	 * @param name the name of the critter template to retrieve
	 * @param callback the method that should be called to operate on the retrieved critter template
	 * @return a retrieve critter template command
	 * 
	 */
	public static Command getRetrieveTemplateCommand( String name, Callback callback )
	{
		Command command = new GetCritterTemplateCommand( name, callback );
		
		return command;
	}
	
	/**
	 * 
	 * Returns a save configuration command.
	 * 
	 * @param filepath the filepath of the file to save to
	 * @param filename the filename of the file to save to
	 * @return a save configuration command
	 * 
	 */
	public static Command getSaveConfigurationCommand( String filepath, String filename )
	{
		Command command = new SaveConfigurationCommand(filepath);
		
		return command;
	}
	
	/**
	 * 
	 * Returns a load configuration command.
	 * 
	 * @param filepath the filepath of the file to load from
	 * @param filename the filename of the file to load from
	 * @return a load configuration command
	 * 
	 */
	public static Command getLoadConfigurationCommand( String filepath, String filename )
	{
		Command command = new LoadConfigurationCommand(filepath);
		
		return command;
	}
	
	public static Command getSetWorldSizeCommand( int size )
	{
		Command command = new SetWorldSizeCommand(size);
		
		return command;
	}
	
	public static Command getSetNumberOfTurnsCommand( int turns )
	{
		Command command = new SetNumberOfTurnsCommand( turns );
		
		return command;
	}
	
	public static Command getStartSimulationCommand()
	{
		Command command = new StartSimulationCommand();
		
		return command;
	}
	
	public static Command getPauseSimulationCommand()
	{
		Command command = new PauseSimulationCommand();
		
		return command;
	}
	
	public static Command getResumeSimulationCommand()
	{
		Command command = new ResumeSimulationCommand();
		
		return command;
	}
	
	public static Command getStopSimulationCommand()
	{
		Command command = new StopSimulationCommand();
		
		return command;
	}
	
	public static Command getFinishSimulationCommand()
	{
		Command command = new FinishSimulationCommand();
		
		return command;
	}
}

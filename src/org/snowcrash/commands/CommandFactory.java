package org.snowcrash.commands;

import org.snowcrash.critter.CritterPrototype;
import org.snowcrash.critter.CritterTemplate;


public class CommandFactory
{
	private CommandFactory()
	{
		// -- Static class.
	}
	
	public static Command getTestCommand()
	{
		Command testCommand = new TestCommand();
		
		return testCommand;
	}
	
	public static Command getImportTemplatesCommand( String filepath, String filename )
	{
		Command command = new ImportCritterTemplatesCommand(filepath, filename);
		
		return command;
	}
	
	public static Command getExportTemplatesCommand( String filepath, String filename )
	{
		Command command = new ExportCritterTemplatesCommand(filepath, filename);
		
		return command;
	}
	
	public static Command getCreateTemplateCommand( CritterPrototype prototype, String name )
	{
		Command command = new CreateCritterTemplateCommand(prototype, name);
		
		return command;
	}
	
	public static Command getModifyTemplateCommand( CritterTemplate template )
	{
		Command command = new ModifyCritterTemplateCommand(template);
		
		return command;
	}
	
	public static Command getDeleteTemplateCommand( CritterTemplate template )
	{
		Command command = new DeleteCritterTemplateCommand(template);
		
		return command;
	}
	
	public static Command getSaveConfigurationCommand( String filepath, String filename )
	{
		Command command = new SaveConfigurationCommand(filepath, filename);
		
		return command;
	}
	
	public static Command getLoadConfigurationCommand( String filepath, String filename )
	{
		Command command = new LoadConfigurationCommand(filepath, filename);
		
		return command;
	}
}

package org.snowcrash.commands;

import org.snowcrash.configurationservice.IConfigurationManager;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.filemanagement.IFileManager;
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
	 * Handles the given command.
	 * 
	 * @param command the command that should be handled
	 * 
	 */
	static void handleCommand( Command command )
	{
		Object[] results = null;
		
		if (command instanceof TestCommand)
		{
			System.out.println( ((TestCommand) command).getMessage() );
		}
		else if (command instanceof ExportCritterTemplatesCommand)
		{
			ExportCritterTemplatesCommand c = (ExportCritterTemplatesCommand) command;
			
			CritterTemplate[] templates = configManager.getCritterTemplates();

			fileManager.saveCritterTemplates( templates, c.getFilepath(),
					c.getFilename() );
		}
		else if (command instanceof ImportCritterTemplatesCommand)
		{
			ImportCritterTemplatesCommand c = (ImportCritterTemplatesCommand) command;

			fileManager.loadCritterTemplates( c.getFilepath(), c.getFilename() );
		}
		else if (command instanceof SaveConfigurationCommand)
		{
			SaveConfigurationCommand c = (SaveConfigurationCommand) command;
			
			fileManager.saveWorld( World.getInstance(), c.getFilepath(), c.getFilename() );
		}
		else if (command instanceof LoadConfigurationCommand)
		{
			LoadConfigurationCommand c = (LoadConfigurationCommand) command;
			
			fileManager.loadWorld( c.getFilepath(), c.getFilename() );
		}
		else if ( command instanceof CreateCritterTemplateCommand )
		{
			CreateCritterTemplateCommand c = (CreateCritterTemplateCommand) command;

			configManager.createCritterTemplate( c.getPrototype(), c.getName() );
		}
		else if ( command instanceof GetCritterTemplateCommand )
		{
			GetCritterTemplateCommand c = (GetCritterTemplateCommand) command;
			
			results = new Object[]
			{
				configManager.getCritterTemplateByName( c.getName() )
			};
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
		
		if ( command instanceof CallbackCommand )
		{
			( (CallbackCommand) command ).executeCallback( results );
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
}

package org.snowcrash.commands;

import org.snowcrash.configurationservice.IConfigurationManager;
import org.snowcrash.filemanagement.IFileManager;


class CommandMediator
{
	private static IFileManager fileService = null;

	private static IConfigurationManager configService = null;

	private CommandMediator()
	{
		// -- Static class.
	}

	public static void handleCommand( Command command, Object ... params )
	{
		Object[] results = null;
		
		if (command instanceof TestCommand)
		{
			System.out.println( ((TestCommand) command).getMessage() );
		}
		/*
		else if ( command instanceof CreateCritterTemplateCommand )
		{
			CreateCritterTemplateCommand c = (CreateCritterTemplateCommand) command;

			Object o = configService.createCritterTemplate( c.getPrototype(), c.getName() );
			results = new Object[] { o };
		}
		else if ( command instanceof ModifyCritterTemplateCommand )
		{
			ModifyCritterTemplateCommand c = (ModifyCritterTemplateCommand) command;

			configService.updateCritterTemplate( c.getTemplate() );
		}
		else if ( command instanceof DeleteCritterTemplateCommand )
		{
			DeleteCritterTemplateCommand c = (DeleteCritterTemplateCommand) command;

			configService.deleteCritterTemplate(c.getTemplate());
		}
		else if (command instanceof ExportCrittersCommand)
		{
			ExportCrittersCommand c = (ExportCrittersCommand) command;

			fileService.saveCritters( c.getCritters(), c.getFilepath(),
					c.getFilename() );
		}
		else if (command instanceof ImportCrittersCommand)
		{
			ImportCrittersCommand c = (ImportCrittersCommand) command;

			fileService.loadCritters( c.getFilepath(), c.getFilename() );
		}
		*/
		else
		{
			throw new UnsupportedOperationException( "Command "
					+ command.getClass().getSimpleName()
					+ " is not yet supported." );
		}
		
		/*
		if ( command instanceof CallbackCommand )
		{
			( (CallbackCommand) command ).executeCallback( results );
		}
		*/
	}
	
	public void setFileManager( IFileManager fileManager )
	{
		fileService = fileManager;
	}
	
	public void setConfigurationManager( IConfigurationManager configurationManager )
	{
		configService = configurationManager;
	}
}

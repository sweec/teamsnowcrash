import org.snowcrash.commands.CommandFactory;
import org.snowcrash.commands.CommandMediator;
import org.snowcrash.configurationservice.ConfigurationManager;
import org.snowcrash.configurationservice.IConfigurationManager;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.filemanagement.FileManager;
import org.snowcrash.filemanagement.IFileManager;
import org.snowcrash.gui.BaseGUI;


public class Main
{
	public static void main(String... args)
	{
		// -- Create managers.
		IFileManager fileManager = new FileManager();
		IConfigurationManager configManager = new ConfigurationManager();
		
		// -- Add managers to the CommandMediator.
		CommandMediator.setFileManager(fileManager);
		CommandMediator.setConfigurationManager(configManager);
		
		// -- Run GUI.
		BaseGUI screenManager = new BaseGUI();
		
		// -- Make BaseGUI observer to DAO
		DAOFactory.addObserver(screenManager);
		
		// -- Reset the world
		CommandFactory.getResetCommand().execute();
	}
}

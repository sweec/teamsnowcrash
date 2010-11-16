import org.snowcrash.commands.CommandMediator;
import org.snowcrash.configurationservice.ConfigurationManager;
import org.snowcrash.configurationservice.IConfigurationManager;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.filemanagement.FileManager;
import org.snowcrash.filemanagement.IFileManager;
import org.snowcrash.gui.ConfigScreen;


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
		
		// -- Get DAO reference.
		DAO dao = DAOFactory.getDAO();
		
		// -- Add listeners to the DAO.
		
		// -- Run GUI.
		new ConfigScreen();
	}
}

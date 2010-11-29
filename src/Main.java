import java.util.Observable;
import java.util.Observer;

import org.snowcrash.commands.CommandMediator;
import org.snowcrash.configurationservice.ConfigurationManager;
import org.snowcrash.configurationservice.IConfigurationManager;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.filemanagement.FileManager;
import org.snowcrash.filemanagement.IFileManager2;
import org.snowcrash.gui.ConfigScreen;


public class Main
{
	public static void main(String... args)
	{
		// -- Create managers.
		IFileManager2 fileManager = new FileManager();
		IConfigurationManager configManager = new ConfigurationManager();
		
		// -- Add managers to the CommandMediator.
		CommandMediator.setFileManager(fileManager);
		CommandMediator.setConfigurationManager(configManager);
		
		// -- Get DAO reference.
		DAO dao = DAOFactory.getDAO();
		
		// -- Add listeners to the DAO.
		dao.addObserver( new Observer()
		{
			public void update(Observable arg0, Object arg1)
			{
				System.out.println( arg1 );
			}
		});
		
		// -- Run GUI.
		new ConfigScreen();
	}
}

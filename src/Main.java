import java.util.Observable;
import java.util.Observer;

import org.snowcrash.commands.CommandMediator;
import org.snowcrash.configurationservice.ConfigurationManager;
import org.snowcrash.configurationservice.IConfigurationManager;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.filemanagement.FileManager;
import org.snowcrash.filemanagement.IFileManager2;
import org.snowcrash.gui.BaseGUI;
import org.snowcrash.world.World;


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
		
		// -- initialize World and save it to database
		// -- since it's not done elsewhere
		World world = new World();
		DAO dao = DAOFactory.getDAO();
		try {
			dao.create( world );
		} catch (DAOException e) {
			throw new RuntimeException( e );
		}
		
		// -- Run GUI.
		BaseGUI screenManager = new BaseGUI();
		
		// -- Make BaseGUI observer to DAO and World
		dao.addObserver(screenManager);
		World.addObserver(screenManager);
	}
}

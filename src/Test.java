import javax.swing.JFrame;

import org.snowcrash.commands.Command;
import org.snowcrash.commands.CommandFactory;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.gui.widgets.CritterTemplateWidget;
import org.snowcrash.gui.widgets.SimulationProgressBar;
import org.snowcrash.timeengine.TimeEngine;
import org.snowcrash.timeengine.TimeListener;


@SuppressWarnings( "unused" )
public class Test {
	public static void main(String... args) throws DAOException {
		/*
		 * Insert your own test code here. If you want to check-in changes,
		 * please split your added code into a different method.
		 */
		
	}

	private static void testCommands() {
		Command command2 = CommandFactory.getTestCommand();
		command2.execute();
	}

	private static void testTimer() throws InterruptedException {
		TimeEngine.addTimeListener(new TimeListener() {
			private int counter = 0;

			@Override
			public void tickOccurred() {
				counter++;
				System.out.println("Tick occurred!");
				System.out.println("Tick #" + counter);
			}

			@Override
			public void timeExpired() {
				System.out.println("Time expired!");
				System.out.println("Number of ticks: " + counter);
			}

			@Override
			public void timerStopped() {
				// TODO Auto-generated method stub

			}
		});

		TimeEngine.setTimeLimit(10);

		TimeEngine.startTimer();
		
		Thread.sleep( 5000 );
		TimeEngine.pauseTimer();
		
		Thread.sleep( 3000 );
		TimeEngine.resumeTimer();
	}
	
	private static void testCritterTemplateWidget()
	{
		JFrame frame = new JFrame();
		frame.setSize( 800, 600 );
		
		CritterTemplateWidget ctw = new CritterTemplateWidget( "Test Critter", 0 );
		frame.add( ctw );
		
		CritterTemplateWidget.setMaxCritterCount( 50 );
		
		frame.setVisible( true );
	}
	
	private static void testSimulationProgressBar()
	{
		JFrame frame = new JFrame();
		frame.setSize( 800, 600 );
		
		SimulationProgressBar spb = new SimulationProgressBar();
		frame.add( spb );
		
		spb.setSize( 600, 20 );
		spb.setNumberOfTicks( 28 );
		
		frame.setVisible( true );
	}
	
	private static void testDatabase() throws DAOException
	{
		DAO dao = DAOFactory.getDAO();
		
		CritterTemplate template = new CritterTemplate( CritterPrototype.PLANT, "Plant" );
		
		dao.create( template );
		
		template.setPrototype( CritterPrototype.PREDATOR );
		
		template = (CritterTemplate) dao.read( CritterTemplate.class, template.getId() );
		System.out.println( template.getPrototype() + "\t" + template.getName() );
		
		template.setPrototype( CritterPrototype.PREY );
		dao.update( template );
			
		template = (CritterTemplate) dao.read( CritterTemplate.class, template.getId() );
		System.out.println( template.getPrototype() + "\t" + template.getName() );
		
		
		CritterTemplate template2 = new CritterTemplate( CritterPrototype.PREDATOR, "Test" );
		
		DatabaseObject[] objects = dao.read( CritterTemplate.class );
		
		CritterTemplate[] templates = new CritterTemplate[ objects.length ];
		
		for ( int i = 0; i < objects.length; i++ )
		{
			templates[i] = (CritterTemplate) objects[i];
		}
	}
}

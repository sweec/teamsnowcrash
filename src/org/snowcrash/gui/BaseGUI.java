package org.snowcrash.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Observable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.snowcrash.commands.Command;
import org.snowcrash.commands.CommandFactory;
import org.snowcrash.critter.Critter;
import org.snowcrash.gui.widgets.SimulationProgressBar;
import org.snowcrash.world.World;
import org.snowcrash.world.WorldObserver;

/**
 * 
 * TODO
 * create a method addListener so that others can register for interested Actions happened in GUI
 * Add listener to turns and end of simulation so that GUI can be updated
 */
public class BaseGUI extends JFrame implements ActionListener, ComponentListener, WorldObserver
{
	public static final int WIDTH = 800;	// minimum window width
	public static final int HEIGHT = 600;	// minimum window height
	
	private static final Icon playIcon = new ImageIcon( "images/Play24.gif" );
	private static final Icon pauseIcon = new ImageIcon( "images/Pause24.gif" );
	
	// objects for the menus and media panel
	private JMenuItem rewind, play, stop, ff, saveSim;
	private JButton rewindButton, playButton, stopButton, ffButton;
	private ConfigScreen configScreen = null;
	private SimResScreen simResScreen = null;
	private boolean isInConfiguration, isInSimulation, isPaused, skipToEnd;
	private SimulationProgressBar simPBar;
	
	// universal cross-platform newline
	public static String newline = System.getProperty("line.separator");

	static private BaseGUI instance = null;
	static public BaseGUI getInstance() {
		if (instance == null) instance = new BaseGUI();
		return instance;
	}
	
	public BaseGUI()
	{	
		instance = this;
		
		Container content = getContentPane();
		content.setLayout( new BorderLayout() );
		
		// display all menus and menu items
		JMenuBar mBar = new JMenuBar();
		JMenu menu = new JMenu(); 
		menu = fileMenu();
		mBar.add(menu);
		menu = configurationMenu();
		mBar.add(menu);
		menu = simulationMenu();
		mBar.add(menu);
		menu = resultsMenu();
		mBar.add(menu);
		menu = helpMenu();
		mBar.add(menu);
		
		// add menus to the menu bar
		setJMenuBar(mBar);
		JPanel mpanel = mediaButtonPanel();
		content.add(mpanel, BorderLayout.SOUTH);
		
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("SnowCrash");
		
		addComponentListener(this);
		goConfiguration();
		World.addObserver(this);	// Move back
		setVisible(true);
	}

	public void goConfiguration() {
		if ((simResScreen != null)
				&& isAncestorOf(simResScreen))
			remove(simResScreen);
		if (configScreen == null)
			configScreen = new ConfigScreen();
		if (!isAncestorOf(configScreen))
			add(configScreen);

		rewind.setEnabled(false);
		play.setEnabled(true);
		stop.setEnabled(false);
		ff.setEnabled(false);
		saveSim.setEnabled(true);
		
		rewindButton.setEnabled(false);
		playButton.setEnabled(true);
		stopButton.setEnabled(false);
		ffButton.setEnabled(false);
		
		playButton.setIcon( playIcon );
		
		isInConfiguration = true;
		isInSimulation = false;
		
		World world = World.getInstance();
		simPBar.setNumberOfTicks(world.getTurns());
		simPBar.setCurrentTick(world.getCurrentTurn());
		// For unknown reason, to correctly refresh the display upon window size changed before switch 
		// Both repaint and revalidate are needed
		repaint();
		configScreen.revalidate();
	}
	
	public void goSimulation() {
		if ((configScreen != null)
				&& isAncestorOf(configScreen))
		remove(configScreen);
		// always build a new SimResScreen
		simResScreen = new SimResScreen();
		add(simResScreen);

		rewind.setEnabled(true);
		play.setEnabled(true);
		stop.setEnabled(true);
		ff.setEnabled(true);
		saveSim.setEnabled(true);
		
		rewindButton.setEnabled(true);
		playButton.setEnabled(true);
		stopButton.setEnabled(true);
		ffButton.setEnabled(true);
		
		isInConfiguration = false;
		isInSimulation = true;
		isPaused = false;
		skipToEnd = false;
		
		// repaint has no effect here
		simResScreen.revalidate();
		World.removeObserver(this);
		World.addObserver(this);
	}
	
	public void goResults() {
		if (simResScreen == null)
			goSimulation();
		if (simResScreen != null)
			simResScreen.goResults();
		
		rewind.setEnabled(true);
		play.setEnabled(false);
		stop.setEnabled(false);
		ff.setEnabled(false);
		saveSim.setEnabled(true);
		
		rewindButton.setEnabled(true);
		playButton.setEnabled(false);
		stopButton.setEnabled(false);
		ffButton.setEnabled(false);
		
		isInConfiguration = false;
		isInSimulation = false;
	}

	public boolean isInConfiguration() {
		return isInConfiguration;
	}
	
	public boolean isInSimulation() {
		return isInSimulation;
	}
	
	public void reset() {
		// this clears configuration screen
		/* if configScreen get update from DAO, below is not needed
		if ((configScreen != null)
				&& isAncestorOf(configScreen))
		remove(configScreen);
		configScreen = new ConfigScreen();
		*/
		goConfiguration();
		World.removeObserver(this);
		World.addObserver(this);
	}
	
	public void updateWorld( World world ) {
		if (isInSimulation && (simResScreen != null)) {
			if (!skipToEnd) simResScreen.updateWorld(world);
			simPBar.gotoNextTick();
		}
		System.out.println("update World in GUI");
	}

	public void update(Observable arg0, Object arg1)
	{
		if (isInConfiguration && (configScreen != null)) {
			configScreen.update();
			repaint();
			configScreen.revalidate();
		}
	}

	JMenu fileMenu() // file menu
	{
		JMenu file = new JMenu("File");
		
		// menu item - "Reset"
		final JMenuItem reset = new JMenuItem("Reset");
		file.add(reset);
		
		file.addSeparator();
		
		// menu item - "Exit"
		final JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		
		ActionListener fileMenuListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
            	if (e.getActionCommand().equals("Exit"))
            	{
        			System.exit(0);
            	}
            	else if (e.getActionCommand().equals("Reset"))
            	{
            		BaseGUI.getInstance().reset();
            		Command command = CommandFactory.getResetCommand();
            		command.execute();
           	}
            	else
            	{
            		// Do nothing
            	}
            }
        };

        exit.addActionListener( fileMenuListener );
        reset.addActionListener(fileMenuListener);
        
        return file;
	}
	
	JMenu configurationMenu() // configuration menu
	{
		JMenu configuration = new JMenu("Configuration");
		
		/*
		// menu item - "New Critter Template"
		final JMenuItem newCritterTemp = new JMenuItem("New Critter Template");
		configuration.add(newCritterTemp);
		
		// menu item - "Delete Critter Template"
		final JMenuItem delCritterTemp = new JMenuItem("Delete Critter Template");
		configuration.add(delCritterTemp);
		
		configuration.addSeparator();
		*/
		// menu item - "Import Critter Template"
		final JMenuItem impCritterTemp = new JMenuItem("Import Critter Templates");
		configuration.add(impCritterTemp);
		
		// menu item - "Export Critter Template"
		final JMenuItem expCritterTemp = new JMenuItem("Export Critter Templates");
		configuration.add(expCritterTemp);
		
		configuration.addSeparator();
		
		// menu item - "Load Configuration"
		final JMenuItem loadConfig = new JMenuItem("Load Configuration");
		configuration.add(loadConfig);
		
		// menu item - "Load Simulation"
		final JMenuItem loadSim = new JMenuItem("Load Simulation");
		configuration.add(loadSim);
		
		// menu item - "Load Results"
		final JMenuItem loadResults = new JMenuItem("Load Results");
		configuration.add(loadResults);
		
		configuration.addSeparator();
		
		// menu item - "Save Configuration"
		final JMenuItem saveConfig = new JMenuItem("Save Configuration");
		configuration.add(saveConfig);
		
		configuration.addSeparator();
		
		// menu item - "Start Simulation"
		final JMenuItem startSim = new JMenuItem("Start Simulation");
		configuration.add(startSim);
		
		final JFileChooser fc = new JFileChooser();
		
		ActionListener configMenuListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
            	/*
            	if (e.getActionCommand().equals("New Critter Template"))
            	{
        			// Do Something
            	}
            	else if (e.getActionCommand().equals("Delete Critter Template"))
            	{
            		// Do Something
            	}
            	else */
            	if (e.getActionCommand().equals("Import Critter Templates"))
            	{
    				fc.setDialogTitle("Import Critter Template");
            		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            		{
            			Command command = CommandFactory.getImportTemplatesCommand(fc.getSelectedFile().getPath(), "");
            			command.execute();
            		}
            	}
            	else if (e.getActionCommand().equals("Export Critter Templates"))
            	{
            		fc.setDialogTitle("Export Critter Template");
            		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            		{
            			Command command = CommandFactory.getExportTemplatesCommand(fc.getSelectedFile().getPath(), "");
            			command.execute();
            		}
            	}
            	else if (e.getActionCommand().equals("Load Configuration"))
            	{
            		fc.setDialogTitle("Load Configuration");
            		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            		{
            			Command command = CommandFactory.getLoadConfigurationCommand(fc.getSelectedFile().getPath(), "");
            			command.execute();
            			BaseGUI.getInstance().reset();
            		}
            	}
            	else if (e.getActionCommand().equals("Load Simulation"))
            	{
            		fc.setDialogTitle("Load Simulation");
            		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            		{
             			Command command = CommandFactory.getLoadSimulationCommand(fc.getSelectedFile().getPath());
            			command.execute();
            			BaseGUI.getInstance().reset();
            			// result in configuration screen
            			// because user may want to check the settings first before start
            			// need user click Play to continue simulation
            		}
            	}
            	else if (e.getActionCommand().equals("Load Results"))
            	{
            		fc.setDialogTitle("Load Results");
            		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            		{
           				Command command = CommandFactory.getLoadResultsCommand(fc.getSelectedFile().getPath());
           				command.execute();
           				BaseGUI.getInstance().reset();
           				// switch to results
           				BaseGUI.getInstance().goResults();
            		}
            	}
            	else if (e.getActionCommand().equals("Save Configuration"))
            	{
            		fc.setDialogTitle("Save Configuration");
            		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            		{
            			Command command = CommandFactory.getSaveConfigurationCommand(fc.getSelectedFile().getPath(), "");
            			command.execute();
            		}
            	}
            	else if (e.getActionCommand().equals("Start Simulation"))
            	{
         			Command command = CommandFactory.getStartSimulationCommand();
        			command.execute();
        			//simPBar.setNumberOfTicks(configScreen.getTotalTurns());	// moved to goSimulation()
            		BaseGUI.getInstance().goSimulation();
            	}
            	else
            	{
            		// Do nothing
            	}
            }
        };

        /*
        newCritterTemp.addActionListener( configMenuListener );
        delCritterTemp.addActionListener(configMenuListener);
        */
        impCritterTemp.addActionListener(configMenuListener);
        expCritterTemp.addActionListener(configMenuListener);
        loadConfig.addActionListener(configMenuListener);
        loadSim.addActionListener(configMenuListener);
        loadResults.addActionListener(configMenuListener);
        saveConfig.addActionListener(configMenuListener);
        startSim.addActionListener(configMenuListener);
		
		return configuration;
	}
	
	JMenu simulationMenu() // simulation menu
	{
		JMenu simulation = new JMenu("Simulation");
		
		// menu item - "Back to Configuration"
		ImageIcon rewindIcon = new ImageIcon("images/Rewind24.gif");
		rewind = new JMenuItem("Back to Configuration", rewindIcon);
		simulation.add(rewind);
		
		// menu item - "Play/Pause Simulation"
		play = new JMenuItem("Play/Pause", playIcon);
		simulation.add(play);
		
		// menu item - "Abort to Results"
		ImageIcon stopIcon = new ImageIcon("images/Stop24.gif");
		stop = new JMenuItem("Abort to Results", stopIcon);
		simulation.add(stop);
		
		// menu item - "Simulate to End"
		ImageIcon ffIcon = new ImageIcon("images/FastForward24.gif");
		ff = new JMenuItem("Simulate to End", ffIcon);
		simulation.add(ff);
		
		simulation.addSeparator();
		
		// menu item - "Save Simulation"
		saveSim = new JMenuItem("Save Simulation");
		simulation.add(saveSim);
		
		final JFileChooser fc = new JFileChooser();
		
		ActionListener simMenuListener = new ActionListener()
        {
			public void actionPerformed( ActionEvent e )
            {       
				fc.setDialogTitle("Save Simulation");
        		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
        		{
        			Command command = CommandFactory.getSaveSimulationCommand(fc.getSelectedFile().getPath());
        			command.execute();
        		}
            }
        };

        rewind.addActionListener( this );
        play.addActionListener(this);
        stop.addActionListener(this);
        ff.addActionListener(this);
        saveSim.addActionListener(simMenuListener);
		
		return simulation;
	}
	
	JMenu resultsMenu() // results menu
	{
		JMenu results = new JMenu("Results");
		
		// menu item - "Open Log"
		final JMenuItem openLog = new JMenuItem("Open Log");
		results.add(openLog);
		
		results.addSeparator();
		
		// menu item - "Save Results"
		final JMenuItem saveResults = new JMenuItem("Save Results");
		results.add(saveResults);
		
		final JFileChooser fc = new JFileChooser();
		
		
		ActionListener resMenuListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
            	if (e.getActionCommand().equals("Open Log"))
            	{
            		/*
        			fc.setDialogTitle("Open Log");
            		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            		{
            			Command command = CommandFactory.getOpenLogCommand(fc.getSelectedFile().getPath());
            			command.execute();
            		}
            		*/
            		// only default log file is supported at this time
        			Command command = CommandFactory.getOpenLogCommand();
        			command.execute();
            	}
            	else if (e.getActionCommand().equals("Save Results"))
            	{
            		fc.setDialogTitle("Save Results");
            		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            		{
             			Command command = CommandFactory.getSaveResultsCommand(fc.getSelectedFile().getPath());
            			command.execute();
            		}
            		
            	}
            	else
            	{
            		// Do nothing
            	}
            }
        };

        openLog.addActionListener( resMenuListener );
        saveResults.addActionListener( resMenuListener );
		
		return results;
	}
	
	JMenu helpMenu() // help menu
	{
		JMenu help = new JMenu("Help");
		
		// menu item - "About"
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		
		ActionListener helpMenuListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
            	ImageIcon raptorIcon = new ImageIcon("images/predator-right.png");
            			
            	JOptionPane.showMessageDialog(null, "SnowCrash - A Simulation" + newline + 
            			newline + "Developed by:" + newline + "Dale Earnest, Jeff Dunn," +
            			newline + "Dong Luo, Mike McWilliams" + newline +
            			newline + "Licensed under the Artistic License", "About SnowCrash",
                        JOptionPane.INFORMATION_MESSAGE, raptorIcon);
            }
        };

        about.addActionListener( helpMenuListener );
		
		return help;
	}
	
	JPanel mediaButtonPanel() // media panel contains rewind, play, stop, ff buttons
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(Short.MAX_VALUE, 45));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		// rewind button, aka "Back to Configuration"
		rewindButton = new JButton();
		ImageIcon rewindIcon = new ImageIcon("images/Rewind24.gif");
		rewindButton.setIcon(rewindIcon);
		rewindButton.setActionCommand("Back to Configuration");
		rewindButton.setToolTipText("Back to Configuration");
		rewindButton.setAlignmentY(TOP_ALIGNMENT);
		
		// play/pause button
		playButton = new JButton();
		playButton.setIcon(playIcon);		
		playButton.setActionCommand("Play/Pause");
		playButton.setToolTipText("Play/Pause");
		playButton.setAlignmentY(TOP_ALIGNMENT);
		
		// stop button, aka "Abort to Results"
		stopButton = new JButton();
		ImageIcon stopIcon = new ImageIcon("images/Stop24.gif");
		stopButton.setIcon(stopIcon);
		stopButton.setActionCommand("Abort to Results");
		stopButton.setToolTipText("Abort to Results");
		stopButton.setAlignmentY(TOP_ALIGNMENT);
		
		// fast-forward button, aka "Simulate to End"
		ffButton = new JButton();
		ImageIcon ffIcon = new ImageIcon("images/FastForward24.gif");
		ffButton.setIcon(ffIcon);
		ffButton.setActionCommand("Simulate to End");
		ffButton.setToolTipText("Simulate to End");
		ffButton.setAlignmentY(TOP_ALIGNMENT);
		
		// simulation progress bar
		simPBar = new SimulationProgressBar();
		simPBar.setSize(100,20);
		simPBar.setSize(WIDTH-300, 35);
		simPBar.setAlignmentY(TOP_ALIGNMENT);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(5,10)));
		buttonPanel.add(rewindButton);
		buttonPanel.add(playButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(ffButton);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(simPBar);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,10)));

        rewindButton.addActionListener( this );
        playButton.addActionListener( this );
        stopButton.addActionListener( this );
        ffButton.addActionListener( this );
		
		return buttonPanel;
	}
	
	// event handler for simulation menu and media panel
	public void actionPerformed(ActionEvent e)
	{	
		if (e.getActionCommand().equals("Back to Configuration")) // rewind
    	{
			Command pause = CommandFactory.getPauseSimulationCommand();
			pause.execute();
			
			// warning user the simulation data will be lost
			Command timerCommand = null;
			String message = "This will clear current simulation/results. Go ahead?";
			int answer = JOptionPane.showConfirmDialog( this, message, "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
			if (answer == JOptionPane.YES_OPTION) { 
				if (!isInConfiguration) {
					goConfiguration();
					World.getInstance().restart();
				}
				
				timerCommand = CommandFactory.getStopSimulationCommand();
			}
			else
			{
				timerCommand = CommandFactory.getResumeSimulationCommand();
			}
			
			timerCommand.execute();
    	}
    	else if (e.getActionCommand().equals("Play/Pause")) // play/pause
    	{
    		if (isInConfiguration) {
       			Command command = CommandFactory.getStartSimulationCommand();
    			command.execute();
    			playButton.setIcon( pauseIcon );
      			goSimulation();
    		}
    		else if (isInSimulation) {
    			if (isPaused) {
    				isPaused = false;
    				playButton.setIcon(pauseIcon);
         			Command command = CommandFactory.getResumeSimulationCommand();
        			command.execute();
    			} else {
    				isPaused = true;
    				playButton.setIcon(playIcon);
         			Command command = CommandFactory.getPauseSimulationCommand();
        			command.execute();
    			}
    		}
    	}
    	else if (e.getActionCommand().equals("Abort to Results")) // stop
    	{
    		if (isInSimulation) {
 				playButton.setIcon(playIcon);		
      			Command command = CommandFactory.getStopSimulationCommand();
     			command.execute();
     			goResults();
    		}
    	}
    	else if (e.getActionCommand().equals("Simulate to End")) // fast forward
    	{
  			Command command = CommandFactory.getFinishSimulationCommand();
 			command.execute();
 			skipToEnd = true;
    	}
	}
	
	public void componentResized(ComponentEvent e) 
    {
       int currentWidth = getWidth();
       int currentHeight = getHeight();
     //we check if either the currentWidth
     //or the currentHeight are below minimum
     boolean resize = false;
       if (currentWidth < WIDTH) 
       {
            resize = true;
            currentWidth = WIDTH;
       }
       if (currentHeight < HEIGHT) 
       {
            resize = true;
            currentHeight = HEIGHT;
       }
       if (resize) 
       {
           setSize(currentWidth, currentHeight);
       }
    }
	
	public void componentMoved(ComponentEvent e) 
    {
    }
    
    public void componentShown(ComponentEvent e) 
    {
    }
    
    public void componentHidden(ComponentEvent e) 
    {
    }

	@Override
	public void updateStatistics(Critter[] critters) {
		// Nothing need to be done here
		
	}	
    
	public void notifyTheEnd() {
		if (isInSimulation && (simResScreen != null)) {
			goResults();
		}
	}
}
package org.snowcrash.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
import org.snowcrash.gui.widgets.SimulationProgressBar;

public class BaseGUI extends JFrame implements ActionListener
{
	public static final int WIDTH = 800;	// minimum window width
	public static final int HEIGHT = 600;	// minimum window height
	
	// objects for the menus and media panel
	JMenuItem rewind, play, stop, ff, saveSim;
	JButton rewindButton, playButton, stopButton, ffButton;
	
	// universal cross-platform newline
	public static String newline = System.getProperty("line.separator");

	public BaseGUI()
	{	
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
		setVisible(true);
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
            		// Do Something
            	}
            	else
            	{
            		// Do Something
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
		
		// menu item - "New Critter Template"
		final JMenuItem newCritterTemp = new JMenuItem("New Critter Template");
		configuration.add(newCritterTemp);
		
		// menu item - "Delete Critter Template"
		final JMenuItem delCritterTemp = new JMenuItem("Delete Critter Template");
		configuration.add(delCritterTemp);
		
		configuration.addSeparator();
		
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
            	String filePath, fileName;
            	int impCritTempVal, expCritTempVal, loadConfigVal, loadSimVal, lResVal, saveConfigVal;
            	if (e.getActionCommand().equals("New Critter Template"))
            	{
        			// Do Something
            	}
            	else if (e.getActionCommand().equals("Delete Critter Template"))
            	{
            		// Do Something
            	}
            	else if (e.getActionCommand().equals("Import Critter Templates"))
            	{
    				fc.setDialogTitle("Import Critter Template");
            		impCritTempVal = fc.showOpenDialog(null);
            		if (impCritTempVal == JFileChooser.APPROVE_OPTION)
            		{
            			File f=fc.getSelectedFile();
            			filePath=f.getPath();
            			fileName=f.getName();
            			Command command = CommandFactory.getImportTemplatesCommand(filePath, fileName);
            			command.execute();
            		}
            	}
            	else if (e.getActionCommand().equals("Export Critter Templates"))
            	{
            		fc.setDialogTitle("Export Critter Template");
            		impCritTempVal = fc.showSaveDialog(null);
            		if (impCritTempVal == JFileChooser.APPROVE_OPTION)
            		{
            			File f=fc.getSelectedFile();
            			filePath=f.getPath();
            			fileName=f.getName();
            			Command command = CommandFactory.getExportTemplatesCommand(filePath, fileName);
            			command.execute();
            		}
            	}
            	else if (e.getActionCommand().equals("Load Configuration"))
            	{
            		fc.setDialogTitle("Load Configuration");
            		loadConfigVal = fc.showOpenDialog(null);
            		if (loadConfigVal == JFileChooser.APPROVE_OPTION)
            		{
            			File f=fc.getSelectedFile();
            			filePath=f.getPath();
            			fileName=f.getName();
            			//Command command = CommandFactory.getImportTemplatesCommand(filePath, fileName);
            			//command.execute();
            		}
            	}
            	else if (e.getActionCommand().equals("Load Simulation"))
            	{
            		fc.setDialogTitle("Load Simulation");
            		loadSimVal = fc.showOpenDialog(null);
            		if (loadSimVal == JFileChooser.APPROVE_OPTION)
            		{
            			File f=fc.getSelectedFile();
            			filePath=f.getPath();
            			fileName=f.getName();
            			//Command command = CommandFactory.getImportTemplatesCommand(filePath, fileName);
            			//command.execute();
            		}
            	}
            	else if (e.getActionCommand().equals("Load Results"))
            	{
            		fc.setDialogTitle("Load Results");
            		lResVal = fc.showOpenDialog(null);
            		if (lResVal == JFileChooser.APPROVE_OPTION)
            		{
            			File f=fc.getSelectedFile();
            			filePath=f.getPath();
            			fileName=f.getName();
            			//Command command = CommandFactory.getImportTemplatesCommand(filePath, fileName);
            			//command.execute();
            		}
            	}
            	else if (e.getActionCommand().equals("Save Configuration"))
            	{
            		fc.setDialogTitle("Save Configuration");
            		saveConfigVal = fc.showSaveDialog(null);
            		if (saveConfigVal == JFileChooser.APPROVE_OPTION)
            		{
            			File f=fc.getSelectedFile();
            			filePath=f.getPath();
            			fileName=f.getName();
            			//Command command = CommandFactory.getImportTemplatesCommand(filePath, fileName);
            			//command.execute();
            		}
            	}
            	else if (e.getActionCommand().equals("Start Simulation"))
            	{
            		// Do Something
            	}
            	else
            	{
            		// Do Something
            	}
            }
        };

        newCritterTemp.addActionListener( configMenuListener );
        delCritterTemp.addActionListener(configMenuListener);
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
		ImageIcon playIcon = new ImageIcon("images/Play24.gif");
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
	            int sSimVal;
	            String filePath, fileName;
				fc.setDialogTitle("Save Simulation");
        		sSimVal = fc.showSaveDialog(null);
        		if (sSimVal == JFileChooser.APPROVE_OPTION)
        		{
        			File f=fc.getSelectedFile();
        			filePath=f.getPath();
        			fileName=f.getName();
        			//Command command = CommandFactory.getImportTemplatesCommand(filePath, fileName);
        			//command.execute();
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
            	int sResVal, openLogVal;
            	String filePath, fileName;
            	if (e.getActionCommand().equals("Open Log"))
            	{
        			fc.setDialogTitle("Open Log");
            		openLogVal = fc.showOpenDialog(null);
            		if (openLogVal == JFileChooser.APPROVE_OPTION)
            		{
            			File f=fc.getSelectedFile();
            			filePath=f.getPath();
            			fileName=f.getName();
            			//Command command = CommandFactory.getImportTemplatesCommand(filePath, fileName);
            			//command.execute();
            		}
            	}
            	else if (e.getActionCommand().equals("Save Results"))
            	{
            		fc.setDialogTitle("Save Results");
            		sResVal = fc.showSaveDialog(null);
            		if (sResVal == JFileChooser.APPROVE_OPTION)
            		{
            			File f=fc.getSelectedFile();
            			filePath=f.getPath();
            			fileName=f.getName();
            			//Command command = CommandFactory.getImportTemplatesCommand(filePath, fileName);
            			//command.execute();
            		}
            		
            	}
            	else
            	{
            		// Do Something
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
		ImageIcon playIcon = new ImageIcon("images/Play24.gif");
		playButton.setIcon(playIcon);		
		playButton.setActionCommand("Play/Pause");
		playButton.setToolTipText("Play/Pause");
		playButton.setAlignmentY(TOP_ALIGNMENT);
		
		// pause icon for play/pause button
		ImageIcon pauseIcon = new ImageIcon("images/Pause24.gif");
		
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
		SimulationProgressBar simPBar = new SimulationProgressBar();
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
		ImageIcon playIcon = new ImageIcon("images/Play24.gif");
		ImageIcon pauseIcon = new ImageIcon("images/Pause24.gif");
		
		if (e.getActionCommand().equals("Back to Configuration")) // rewind
    	{
			// Do Something
    	}
    	else if (e.getActionCommand().equals("Play/Pause")) // play/pause
    	{
			// Do Something
    	}
    	else if (e.getActionCommand().equals("Abort to Results")) // stop
    	{
			// Do Something
    	}
    	else if (e.getActionCommand().equals("Simulate to End")) // fast forward
    	{
			// Do Something
    	}
    	else
    	{
    		// Do Something
    	}
	}
		
}
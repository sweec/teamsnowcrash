package org.snowcrash.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.JProgressBar;

public class BaseGUI extends JFrame implements ActionListener
{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	JMenuItem rewind, play, stop, ff, saveSimulation;
	JButton rewindButton, playButton, stopButton, ffButton;
	public static String newline = System.getProperty("line.separator");

	public BaseGUI()
	{	
		Container content = getContentPane();
		content.setLayout( new BorderLayout() );
		
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
		
		setJMenuBar(mBar);
		JPanel mpanel = mediaButtonPanel();
		content.add(mpanel, BorderLayout.SOUTH);
		
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("SnowCrash");
		setVisible(true);
	}
	/*
	public static void main(String[] args)
	{
		new BaseGUI();
	}
	*/
	
	
	JMenu fileMenu()
	{
		JMenu file = new JMenu("File");
		final JMenuItem reset = new JMenuItem("Reset");
		file.add(reset);
		file.addSeparator();
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
	
	JMenu configurationMenu()
	{
		JMenu configuration = new JMenu("Configuration");
		final JMenuItem newCTemp = new JMenuItem("New Critter Template");
		configuration.add(newCTemp);
		final JMenuItem delCTemp = new JMenuItem("Delete Critter Template");
		configuration.add(delCTemp);
		
		configuration.addSeparator();
		
		final JMenuItem impCTemp = new JMenuItem("Import Critter Templates");
		configuration.add(impCTemp);
		final JMenuItem expCTemp = new JMenuItem("Export Critter Templates");
		configuration.add(expCTemp);
		
		configuration.addSeparator();
		
		final JMenuItem lConfig = new JMenuItem("Load Configuration");
		configuration.add(lConfig);
		final JMenuItem lSim = new JMenuItem("Load Simulation");
		configuration.add(lSim);
		final JMenuItem lResults = new JMenuItem("Load Results");
		configuration.add(lResults);
		
		configuration.addSeparator();
		
		final JMenuItem sConfig = new JMenuItem("Save Configuration");
		configuration.add(sConfig);
		
		configuration.addSeparator();
		
		final JMenuItem startSim = new JMenuItem("Start Simulation");
		configuration.add(startSim);
		
		final JFileChooser fc = new JFileChooser();
		
		ActionListener configMenuListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
            	int impCritTempVal, expCritTempVal, lConfigVal, lSimVal, lResVal, sConfigVal;
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
            	}
            	else if (e.getActionCommand().equals("Export Critter Templates"))
            	{
            		fc.setDialogTitle("Export Critter Template");
            		impCritTempVal = fc.showSaveDialog(null);
            	}
            	else if (e.getActionCommand().equals("Load Configuration"))
            	{
            		fc.setDialogTitle("Load Configuration");
            		lConfigVal = fc.showOpenDialog(null);
            	}
            	else if (e.getActionCommand().equals("Load Simulation"))
            	{
            		fc.setDialogTitle("Load Simulation");
            		lSimVal = fc.showOpenDialog(null);
            	}
            	else if (e.getActionCommand().equals("Load Results"))
            	{
            		fc.setDialogTitle("Load Results");
            		lResVal = fc.showOpenDialog(null);
            	}
            	else if (e.getActionCommand().equals("Save Configuration"))
            	{
            		fc.setDialogTitle("Save Configuration");
            		sConfigVal = fc.showSaveDialog(null);
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

        newCTemp.addActionListener( configMenuListener );
        delCTemp.addActionListener(configMenuListener);
        impCTemp.addActionListener(configMenuListener);
        expCTemp.addActionListener(configMenuListener);
        lConfig.addActionListener(configMenuListener);
        lSim.addActionListener(configMenuListener);
        lResults.addActionListener(configMenuListener);
        sConfig.addActionListener(configMenuListener);
        startSim.addActionListener(configMenuListener);
		
		return configuration;
	}
	
	JMenu simulationMenu()
	{
		JMenu simulation = new JMenu("Simulation");
		ImageIcon rewindIcon = new ImageIcon("images/Rewind24.gif");
		rewind = new JMenuItem("Back to Configuration", rewindIcon);
		simulation.add(rewind);
		ImageIcon playIcon = new ImageIcon("images/Play24.gif");
		play = new JMenuItem("Play/Pause", playIcon);
		simulation.add(play);
		ImageIcon stopIcon = new ImageIcon("images/Stop24.gif");
		stop = new JMenuItem("Abort to Results", stopIcon);
		simulation.add(stop);
		ImageIcon ffIcon = new ImageIcon("images/FastForward24.gif");
		ff = new JMenuItem("Simulate to End", ffIcon);
		simulation.add(ff);
		
		simulation.addSeparator();
		
		saveSimulation = new JMenuItem("Save Simulation");
		simulation.add(saveSimulation);
		
		final JFileChooser fc = new JFileChooser();
		
		ActionListener simMenuListener = new ActionListener()
        {
			public void actionPerformed( ActionEvent e )
            {       
	            int sSimVal;
				fc.setDialogTitle("Save Simulation");
        		sSimVal = fc.showSaveDialog(null);
            }
        };

        rewind.addActionListener( this );
        play.addActionListener(this);
        stop.addActionListener(this);
        ff.addActionListener(this);
        saveSimulation.addActionListener(simMenuListener);
		
		return simulation;
	}
	
	JMenu resultsMenu()
	{
		JMenu results = new JMenu("Results");
		final JMenuItem oLog = new JMenuItem("Open Log");
		results.add(oLog);
		
		results.addSeparator();
		
		final JMenuItem sResults = new JMenuItem("Save Results");
		results.add(sResults);
		
		final JFileChooser fc = new JFileChooser();
		
		
		ActionListener resMenuListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
            	int sResVal, oLogVal;
            	if (e.getActionCommand().equals("Open Log"))
            	{
        			fc.setDialogTitle("Open Log");
            		oLogVal = fc.showOpenDialog(null);
            	}
            	else if (e.getActionCommand().equals("Save Results"))
            	{
            		fc.setDialogTitle("Save Results");
            		sResVal = fc.showSaveDialog(null);
            	}
            	else
            	{
            		// Do Something
            	}
            }
        };

        oLog.addActionListener( resMenuListener );
        sResults.addActionListener( resMenuListener );
		
		return results;
	}
	
	JMenu helpMenu()
	{
		JMenu help = new JMenu("Help");
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
	
	JPanel mediaButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(Short.MAX_VALUE, 45));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		rewindButton = new JButton();
		ImageIcon rewindIcon = new ImageIcon("images/Rewind24.gif");
		rewindButton.setIcon(rewindIcon);
		rewindButton.setActionCommand("Back to Configuration");
		rewindButton.setToolTipText("Back to Configuration");
		rewindButton.setAlignmentY(TOP_ALIGNMENT);
		
		playButton = new JButton();
		ImageIcon playIcon = new ImageIcon("images/Play24.gif");
		playButton.setIcon(playIcon);		
		playButton.setActionCommand("Play/Pause");
		playButton.setToolTipText("Play/Pause");
		playButton.setAlignmentY(TOP_ALIGNMENT);
		
		ImageIcon pauseIcon = new ImageIcon("images/Pause24.gif");
		
		stopButton = new JButton();
		ImageIcon stopIcon = new ImageIcon("images/Stop24.gif");
		stopButton.setIcon(stopIcon);
		stopButton.setActionCommand("Abort to Results");
		stopButton.setToolTipText("Abort to Results");
		stopButton.setAlignmentY(TOP_ALIGNMENT);
		
		ffButton = new JButton();
		ImageIcon ffIcon = new ImageIcon("images/FastForward24.gif");
		ffButton.setIcon(ffIcon);
		ffButton.setActionCommand("Simulate to End");
		ffButton.setToolTipText("Simulate to End");
		ffButton.setAlignmentY(TOP_ALIGNMENT);
		
		JProgressBar simPBar = new JProgressBar(0, 100);
		simPBar.setStringPainted(true);
		simPBar.setToolTipText("Simulation Progress");
		simPBar.setAlignmentY(TOP_ALIGNMENT);
		
		//SimulationProgressBar simPBar = new SimulationProgressBar();
		//simPBar.setSize(100,20);
		//simPBar.setPreferredSize(new Dimension(WIDTH - 300, 35));
		//simPBar.setAlignmentY(TOP_ALIGNMENT);
		
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
	
	public void actionPerformed(ActionEvent e)
	{	
		ImageIcon playIcon = new ImageIcon("images/Play24.gif");
		ImageIcon pauseIcon = new ImageIcon("images/Pause24.gif");
		
		if (e.getActionCommand().equals("Back to Configuration"))
    	{
			// Do Something
    	}
    	else if (e.getActionCommand().equals("Play/Pause"))
    	{
			// Do Something
    	}
    	else if (e.getActionCommand().equals("Abort to Results"))
    	{
			// Do Something
    	}
    	else if (e.getActionCommand().equals("Simulate to End"))
    	{
			// Do Something
    	}
    	else
    	{
    		// Do Something
    	}
	}
		
}
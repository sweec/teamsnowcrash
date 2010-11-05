package org.snowcrash.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class BaseGUI extends JFrame
{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	BaseGUI()
	{
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		JMenu file = fileMenu();
		JMenu configuration = configurationMenu();
		JMenu simulation = simulationMenu();
		JMenu results = resultsMenu();
		JMenu help = helpMenu();
		JMenuBar mBar = new JMenuBar();
		mBar.add(file);
		mBar.add(configuration);
		mBar.add(simulation);
		mBar.add(results);
		mBar.add(help);
		add(mBar);
		setJMenuBar(mBar);
		
		JPanel basePanel = new JPanel();
		basePanel.setLayout( new BorderLayout() );
		JPanel mpanel = mediaButtonPanel();
		add(mpanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args)
	{
		new BaseGUI();
	}
	
	
	
	private JMenu fileMenu()
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
	
	private JMenu configurationMenu()
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
		
		ActionListener configMenuListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
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
            		// Do Something
            	}
            	else if (e.getActionCommand().equals("Export Critter Templates"))
            	{
            		// Do Something
            	}
            	else if (e.getActionCommand().equals("Load Configuration"))
            	{
            		// Do Something
            	}
            	else if (e.getActionCommand().equals("Load Simulation"))
            	{
            		// Do Something
            	}
            	else if (e.getActionCommand().equals("Load Results"))
            	{
            		// Do Something
            	}
            	else if (e.getActionCommand().equals("Save Configuration"))
            	{
            		// Do Something
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
	
	private JMenu simulationMenu()
	{
		JMenu simulation = new JMenu("Simulation");
		ImageIcon rewindIcon = new ImageIcon("images/Rewind24.gif");
		final JMenuItem rewind = new JMenuItem("Back to Configuration", rewindIcon);
		simulation.add(rewind);
		ImageIcon playIcon = new ImageIcon("images/Play24.gif");
		final JMenuItem play = new JMenuItem("Play/Pause", playIcon);
		simulation.add(play);
		ImageIcon stopIcon = new ImageIcon("images/Stop24.gif");
		final JMenuItem stop = new JMenuItem("Abort to Results", stopIcon);
		simulation.add(stop);
		ImageIcon ffIcon = new ImageIcon("images/FastForward24.gif");
		final JMenuItem ff = new JMenuItem("Simulate to End", ffIcon);
		simulation.add(ff);
		
		simulation.addSeparator();
		
		final JMenuItem sSim = new JMenuItem("Save Simulation");
		simulation.add(sSim);
		
		ActionListener simMenuListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
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
            	else if (e.getActionCommand().equals("Save Simulation"))
            	{
            		// Do Something
            	}
            	else
            	{
            		// Do Something
            	}
            }
        };

        rewind.addActionListener( simMenuListener );
        play.addActionListener(simMenuListener);
        stop.addActionListener(simMenuListener);
        ff.addActionListener(simMenuListener);
        sSim.addActionListener(simMenuListener);
		
		return simulation;
	}
	
	private JMenu resultsMenu()
	{
		JMenu results = new JMenu("Results");
		final JMenuItem oLog = new JMenuItem("Open Log");
		results.add(oLog);
		
		results.addSeparator();
		
		final JMenuItem sResults = new JMenuItem("Save Results");
		results.add(sResults);
		
		ActionListener resMenuListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
            	if (e.getActionCommand().equals("Open Log"))
            	{
        			// Do Something
            	}
            	else if (e.getActionCommand().equals("Save Results"))
            	{
            		// Do Something
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
	
	private JMenu helpMenu()
	{
		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		
		ActionListener helpMenuListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
            	if (e.getActionCommand().equals("About"))
            	{
        			// Do Something
            	}
            	else
            	{
            		// Do Something
            	}
            }
        };

        about.addActionListener( helpMenuListener );
		
		return help;
	}
	
	private JPanel mediaButtonPanel()
	{
		JPanel mediaButtonPanel = new JPanel();
		mediaButtonPanel.setLayout( new FlowLayout() );
		
		final JButton rewindButton = new JButton();
		ImageIcon rewindIcon = new ImageIcon("images/Rewind24.gif");
		rewindButton.setIcon(rewindIcon);
		rewindButton.setActionCommand("rewind");
		rewindButton.setToolTipText("Back to Configuration");
		
		final JButton playButton = new JButton();
		ImageIcon playIcon = new ImageIcon("images/Play24.gif");
		playButton.setIcon(playIcon);
		playButton.setActionCommand("play");
		playButton.setToolTipText("Play/Pause");
		
		ImageIcon pauseIcon = new ImageIcon("images/Pause24.gif");
		
		final JButton stopButton = new JButton();
		ImageIcon stopIcon = new ImageIcon("images/Stop24.gif");
		stopButton.setIcon(stopIcon);
		stopButton.setActionCommand("stop");
		stopButton.setToolTipText("Abort to Results");
		
		final JButton ffButton = new JButton();
		ImageIcon ffIcon = new ImageIcon("images/FastForward24.gif");
		ffButton.setIcon(ffIcon);
		ffButton.setActionCommand("ff");
		ffButton.setToolTipText("Simulate to End");
		
		JProgressBar simPBar = new JProgressBar(0, 100);
		simPBar.setStringPainted(true);
		
		mediaButtonPanel.add(rewindButton);
		mediaButtonPanel.add(playButton);
		mediaButtonPanel.add(stopButton);
		mediaButtonPanel.add(ffButton);
		mediaButtonPanel.add(simPBar);
		
		ActionListener mButtonListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
            	if (e.getActionCommand().equals("rewind"))
            	{
        			// Do Something
            	}
            	else if (e.getActionCommand().equals("play"))
            	{
        			// Do Something
            	}
            	else if (e.getActionCommand().equals("stop"))
            	{
        			// Do Something
            	}
            	else if (e.getActionCommand().equals("ff"))
            	{
        			// Do Something
            	}
            	else
            	{
            		// Do Something
            	}
            }
        };

        rewindButton.addActionListener( mButtonListener );
        playButton.addActionListener( mButtonListener );
        stopButton.addActionListener( mButtonListener );
        ffButton.addActionListener( mButtonListener );
		
		return mediaButtonPanel;
	}
		
}
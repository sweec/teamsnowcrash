package org.snowcrash.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class BaseGUI extends JFrame implements ActionListener
{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	JMenuItem rewind, play, stop, ff;
	JButton rewindButton, playButton, stopButton, ffButton;
	public static String newline = System.getProperty("line.separator");

	BaseGUI()
	{	
		Container content = getContentPane();
		content.setLayout( new BorderLayout() );
		
		JMenuBar mBar = new JMenuBar();
		JMenu menu = new JMenu(); 
		menu = fileMenu();
		mBar.add(menu);
		menu = configurationMenu();
		mBar.add(menu);
		menu = simulationMenu(true, true, true, true);
		mBar.add(menu);
		menu = resultsMenu();
		mBar.add(menu);
		menu = helpMenu();
		mBar.add(menu);
		
		setJMenuBar(mBar);
		JPanel mpanel = mediaButtonPanel(true, true, true, true);
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
	
	JMenu simulationMenu(boolean ma, boolean mb, boolean mc, boolean md)
	{
		JMenu simulation = new JMenu("Simulation");
		ImageIcon rewindIcon = new ImageIcon("images/Rewind24.gif");
		rewind = new JMenuItem("Back to Configuration", rewindIcon);
		rewind.setEnabled(ma);
		simulation.add(rewind);
		ImageIcon playIcon = new ImageIcon("images/Play24.gif");
		play = new JMenuItem("Play/Pause", playIcon);
		play.setEnabled(mb);
		simulation.add(play);
		ImageIcon stopIcon = new ImageIcon("images/Stop24.gif");
		stop = new JMenuItem("Abort to Results", stopIcon);
		stop.setEnabled(mc);
		simulation.add(stop);
		ImageIcon ffIcon = new ImageIcon("images/FastForward24.gif");
		ff = new JMenuItem("Simulate to End", ffIcon);
		ff.setEnabled(md);
		simulation.add(ff);
		
		simulation.addSeparator();
		
		final JMenuItem sSim = new JMenuItem("Save Simulation");
		simulation.add(sSim);
		
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
        sSim.addActionListener(simMenuListener);
		
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
	
	JPanel mediaButtonPanel(boolean ma, boolean mb, boolean mc, boolean md)
	{
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setLayout( new FlowLayout() );
		
		rewindButton = new JButton();
		ImageIcon rewindIcon = new ImageIcon("images/Rewind24.gif");
		rewindButton.setIcon(rewindIcon);
		rewindButton.setActionCommand("Back to Configuration");
		rewindButton.setToolTipText("Back to Configuration");
		rewindButton.setEnabled(ma);
		
		playButton = new JButton();
		ImageIcon playIcon = new ImageIcon("images/Play24.gif");
		playButton.setIcon(playIcon);		
		playButton.setActionCommand("Play/Pause");
		playButton.setToolTipText("Play/Pause");
		playButton.setEnabled(mb);
		
		ImageIcon pauseIcon = new ImageIcon("images/Pause24.gif");
		
		stopButton = new JButton();
		ImageIcon stopIcon = new ImageIcon("images/Stop24.gif");
		stopButton.setIcon(stopIcon);
		stopButton.setActionCommand("Abort to Results");
		stopButton.setToolTipText("Abort to Results");
		stopButton.setEnabled(mc);
		
		ffButton = new JButton();
		ImageIcon ffIcon = new ImageIcon("images/FastForward24.gif");
		ffButton.setIcon(ffIcon);
		ffButton.setActionCommand("Simulate to End");
		ffButton.setToolTipText("Simulate to End");
		ffButton.setEnabled(md);
		
		JProgressBar simPBar = new JProgressBar(0, 100);
		simPBar.setStringPainted(true);
		
		ButtonPanel.add(rewindButton);
		ButtonPanel.add(playButton);
		ButtonPanel.add(stopButton);
		ButtonPanel.add(ffButton);
		ButtonPanel.add(simPBar);

        rewindButton.addActionListener( this );
        playButton.addActionListener( this );
        stopButton.addActionListener( this );
        ffButton.addActionListener( this );
		
		return ButtonPanel;
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
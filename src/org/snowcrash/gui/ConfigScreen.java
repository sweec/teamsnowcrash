package org.snowcrash.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ConfigScreen extends BaseGUI
{
	void BaseGUI()
	{
		Container content = getContentPane();
		content.setLayout( new BorderLayout() );
		
		JMenuBar mBar = new JMenuBar(); 
		JMenu menu = fileMenu();
		mBar.add(menu);
		menu = configurationMenu();
		mBar.add(menu);
		menu = simulationMenu(false, true, false, false);
		mBar.add(menu);
		
		menu = resultsMenu();
		mBar.add(menu);
		
		menu = helpMenu();
		mBar.add(menu);
		
		setJMenuBar(mBar);
		JPanel mPanel = mediaButtonPanel(false, true, false, false);
		content.add(mPanel, BorderLayout.SOUTH);
		
		JTabbedPane tabPane;
		CritterPanel critterConfig = new CritterPanel();
		JPanel cPanel = critterConfig.critterObjects();
		tabPane = new JTabbedPane();
		tabPane.addTab("Critters", cPanel);
		content.add(tabPane, BorderLayout.WEST);
		
		TraitsPanel traitsConfig = new TraitsPanel();
		cPanel = traitsConfig.traitsObjects();
		tabPane = new JTabbedPane();
		tabPane.addTab("Traits", cPanel);
		content.add(tabPane, BorderLayout.CENTER);
		
		WorldPanel worldConfig = new WorldPanel();
		cPanel = worldConfig.worldObjects();
		tabPane = new JTabbedPane();
		tabPane.addTab("World Settings", cPanel);
		content.add(tabPane, BorderLayout.EAST);
		
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("SnowCrash");
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		ConfigScreen gaf = new ConfigScreen();
		gaf.BaseGUI();
	}
	
}
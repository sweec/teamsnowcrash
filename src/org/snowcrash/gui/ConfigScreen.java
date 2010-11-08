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
				
		CritterPanel critterConfig = new CritterPanel();
		JPanel cPanel = critterConfig.critterObjects();
		content.add(cPanel, BorderLayout.WEST);
		
		TraitsPanel traitsConfig = new TraitsPanel();
		cPanel = traitsConfig.traitsObjects();
		content.add(cPanel, BorderLayout.CENTER);
		
		WorldPanel worldConfig = new WorldPanel();
		cPanel = worldConfig.worldObjects();
		content.add(cPanel, BorderLayout.EAST);
		
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
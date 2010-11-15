package org.snowcrash.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ConfigScreen extends BaseGUI
{
	public ConfigScreen()
	{
		rewind.setEnabled(false);
		play.setEnabled(true);
		stop.setEnabled(false);
		ff.setEnabled(false);
		saveSimulation.setEnabled(false);
		
		rewindButton.setEnabled(false);
		playButton.setEnabled(true);
		stopButton.setEnabled(false);
		ffButton.setEnabled(false);
		
		Container content = getContentPane();

		JTabbedPane tabPane;
		CritterPanel critterConfig = new CritterPanel();
		JPanel cPanel = critterConfig.critterObjects();
		tabPane = new JTabbedPane();
		tabPane.addTab("Critters", cPanel);
		tabPane.setPreferredSize(new Dimension(WIDTH / 5 + 13, HEIGHT - 100));
		content.add(tabPane, BorderLayout.LINE_START);
		
		TraitsPanel traitsConfig = new TraitsPanel();
		cPanel = traitsConfig.traitsObjects();
		tabPane = new JTabbedPane();
		tabPane.addTab("Traits", cPanel);
		tabPane.setPreferredSize(new Dimension(WIDTH * 3 / 5 - 30, HEIGHT - 100));
		content.add(tabPane, BorderLayout.CENTER);
		
		WorldPanel worldConfig = new WorldPanel();
		cPanel = worldConfig.worldObjects();
		tabPane = new JTabbedPane();
		tabPane.addTab("World Settings", cPanel);
		tabPane.setPreferredSize(new Dimension(WIDTH / 5 - 15, HEIGHT - 100));
		
		content.add(tabPane, BorderLayout.LINE_END);
	}
	
	public static void main(String[] args)
	{
		new ConfigScreen();
	}
	
}
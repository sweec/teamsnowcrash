package org.snowcrash.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
		int contentWidth = content.getWidth();

		JPanel configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.X_AXIS));
		
		configPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		JTabbedPane tabPane;
		CritterPanel critterConfig = new CritterPanel();
		JPanel cPanel = critterConfig.CritterPanel();
		tabPane = new JTabbedPane();
		tabPane.addTab("Critters", cPanel);
		tabPane.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane.setPreferredSize(new Dimension((contentWidth - 20) / 3, Short.MAX_VALUE));
		//tabPane.setMinimumSize(new Dimension(260, Short.MAX_VALUE));
		configPanel.add(tabPane);
		content.add(configPanel);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		TraitsPanel traitsConfig = new TraitsPanel();
		cPanel = traitsConfig.TraitsPanel();
		tabPane = new JTabbedPane();
		tabPane.addTab("Traits", cPanel);
		tabPane.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane.setPreferredSize(new Dimension((contentWidth - 20) / 3, Short.MAX_VALUE));
		//tabPane.setMinimumSize(new Dimension(260, Short.MAX_VALUE));
		configPanel.add(tabPane);
		content.add(configPanel);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		WorldPanel worldConfig = new WorldPanel();
		cPanel = worldConfig.WorldPanel();
		tabPane = new JTabbedPane();
		tabPane.addTab("World Settings", cPanel);
		tabPane.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane.setPreferredSize(new Dimension((contentWidth - 20) / 3, Short.MAX_VALUE));
		//tabPane.setMinimumSize(new Dimension(260, Short.MAX_VALUE));
		configPanel.add(tabPane);	
		content.add(configPanel);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
	}
	
	public static void main(String[] args)
	{
		new ConfigScreen();
	}
	
}
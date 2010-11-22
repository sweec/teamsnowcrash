package org.snowcrash.gui;

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
		saveSim.setEnabled(true);
		
		rewindButton.setEnabled(false);
		playButton.setEnabled(true);
		stopButton.setEnabled(false);
		ffButton.setEnabled(false);
		
		Container content = getContentPane();
		int contentWidth = content.getWidth();

		JPanel configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.X_AXIS));
		
		configPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		// get the critter panel and add a tab
		JTabbedPane tabPane;
		JPanel cPanel = new CritterPanel();
		tabPane = new JTabbedPane();
		tabPane.addTab("Critters", cPanel);
		tabPane.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane.setPreferredSize(new Dimension((contentWidth - 20) / 3, Short.MAX_VALUE));
		configPanel.add(tabPane);
		content.add(configPanel);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		// get the traits panel and add a tab
		TraitsPanel traitsConfig = new TraitsPanel();
		cPanel = traitsConfig.TraitsPanel();
		tabPane = new JTabbedPane();
		tabPane.addTab("Traits", cPanel);
		tabPane.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane.setPreferredSize(new Dimension((contentWidth - 20) / 3, Short.MAX_VALUE));
		configPanel.add(tabPane);
		content.add(configPanel);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		// get the world panel and add a tab
		JScrollPane cScroll = new JScrollPane();
		WorldPanel worldConfig = new WorldPanel();
		cScroll = worldConfig.WorldPanel();
		tabPane = new JTabbedPane();
		tabPane.addTab("World Settings", cScroll);
		tabPane.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane.setPreferredSize(new Dimension((contentWidth - 20) / 3, Short.MAX_VALUE));
		configPanel.add(tabPane);	
		content.add(configPanel);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
	}
	
	public static void main(String[] args)
	{
		new ConfigScreen();
	}
	
}
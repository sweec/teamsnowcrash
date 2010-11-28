package org.snowcrash.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class ConfigScreen extends BaseGUI
{
	public static final int WIDTH = 800;	// minimum window width
	public static final int HEIGHT = 600;	// minimum window height
	
	JTabbedPane tabPane1, tabPane2, tabPane3;
	
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

		JPanel configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.X_AXIS));
		
		configPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		// get the critter panel and add a tab
		JPanel cPanel = new CritterPanel();
		tabPane1 = new JTabbedPane();
		tabPane1.addTab("Critters", cPanel);
		tabPane1.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane1.setPreferredSize(new Dimension
				((WIDTH - 20) / 3, Short.MAX_VALUE));
		configPanel.add(tabPane1);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		// get the traits panel and add a tab
		TraitsPanel traitsConfig = new TraitsPanel();
		cPanel = traitsConfig.TraitsPanel();
		tabPane2 = new JTabbedPane();
		tabPane2.addTab("Traits", cPanel);
		tabPane2.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane2.setPreferredSize(new Dimension
				((WIDTH - 20) / 3, Short.MAX_VALUE));
		configPanel.add(tabPane2);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		// get the world panel and add a tab
		JScrollPane cScroll = new JScrollPane();
		WorldPanel worldConfig = new WorldPanel();
		cScroll = worldConfig.WorldPanel();
		tabPane3 = new JTabbedPane();
		tabPane3.addTab("World Settings", cScroll);
		tabPane3.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane3.setPreferredSize(new Dimension
				((WIDTH - 20) / 3, Short.MAX_VALUE));
		configPanel.add(tabPane3);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
		content.add(configPanel);
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
           tabPane1.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
    	   tabPane2.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
    	   tabPane3.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
       }
       else
       {
    	   tabPane1.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
    	   tabPane2.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
    	   tabPane3.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
       }
    }
	
	public static void main(String[] args)
	{
		ConfigScreen scconfig = new ConfigScreen();
		scconfig.setVisible(true);
	}
	
}
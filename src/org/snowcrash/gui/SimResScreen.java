package org.snowcrash.gui;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.snowcrash.world.World;

public class SimResScreen extends JPanel
{
	public static final int WIDTH = 800;	// minimum window width
	public static final int HEIGHT = 600;	// minimum window height
	
	private JTabbedPane sRTabPane, cTabPane;
	
	private SimuPanel simuPanel = null;
	private ResultsPanel resultsPanel = null;
	private ConsolePanel consolePanel = null;
	private JPanel dummyPanel = null;
	
	public SimResScreen()
	{
		JPanel cPanel = this;
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.X_AXIS));
		
		cPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		sRTabPane = new JTabbedPane();
		World world = World.getInstance();
		simuPanel = new SimuPanel(world.getSizeX(), world.getSizeY());
		sRTabPane.addTab("Simulation", simuPanel.getScrollPane());
		dummyPanel = new JPanel();
		sRTabPane.addTab("Results", dummyPanel);
		sRTabPane.setSelectedIndex(sRTabPane.indexOfTab("Simulation"));
		sRTabPane.setEnabledAt(sRTabPane.indexOfTab("Results"), false);
		sRTabPane.setPreferredSize(new Dimension((WIDTH - 15) * 2 / 3, Short.MAX_VALUE));
		
		cPanel.add(sRTabPane);
		cPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		cTabPane = new JTabbedPane();
		consolePanel = new ConsolePanel();
		cTabPane.addTab("Console", consolePanel.getConsolePanel());
		cTabPane.setPreferredSize(new Dimension((WIDTH - 15) / 3, Short.MAX_VALUE));
		
		cPanel.add(cTabPane);
		cPanel.add(Box.createRigidArea(new Dimension(5, 0)));
	}
	
	public void goResults()
	{
		if (resultsPanel == null) {
			resultsPanel = new ResultsPanel();
			sRTabPane.setComponentAt(sRTabPane.indexOfTab("Results"), resultsPanel);
			sRTabPane.setEnabledAt(sRTabPane.indexOfTab("Results"), true);
		}
		sRTabPane.setSelectedIndex(sRTabPane.indexOfTab("Results"));
	}
	
	public void updateWorld( World world ) {
		if ((world != null) && (simuPanel != null))
			simuPanel.updateworld(world.getMap());
			consolePanel.addMessage(world.getTurnLog());
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
           sRTabPane.setPreferredSize(new Dimension
    			   ((currentWidth - 15) * 2 / 3, Short.MAX_VALUE));
    	   cTabPane.setPreferredSize(new Dimension
    			   ((currentWidth - 15) / 3, Short.MAX_VALUE));
       }
       else
       {
    	   sRTabPane.setPreferredSize(new Dimension
    			   ((currentWidth - 15) * 2 / 3, Short.MAX_VALUE));
    	   cTabPane.setPreferredSize(new Dimension
    			   ((currentWidth - 15) / 3, Short.MAX_VALUE));
       }
    }
	
	public static void main(String[] args)
	{
		//FileManager mgr = new FileManager();
		//mgr.loadTestCritterTemplates("testCritterTemplates.Json");
		SimResScreen sScreen = new SimResScreen();
		JFrame f = new JFrame();
		f.setSize(WIDTH, HEIGHT);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setTitle("SnowCrash");
		f.add(sScreen);
		f.setVisible(true);
	}
}


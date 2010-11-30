package org.snowcrash.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class SimResScreen extends BaseGUI
{
	public static final int WIDTH = 800;	// minimum window width
	public static final int HEIGHT = 600;	// minimum window height
	
	JTabbedPane tabPane;
	private static int worldWidth = 20;
	private static int worldHeight = 20;
	
	JTabbedPane sRTabPane, cTabPane;
	
	public SimResScreen()
	{
		Container content = getContentPane();

		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.X_AXIS));
		
		cPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		sRTabPane = new JTabbedPane();
		SimuPanel simuPanel = new SimuPanel(worldWidth, worldHeight);
		sRTabPane.addTab("Simulation", simuPanel.getScrollPane());
		sRTabPane.addTab("Results", new ResultsPanel());
		sRTabPane.setSelectedIndex(sRTabPane.indexOfTab("Simulation"));
		sRTabPane.setEnabledAt(sRTabPane.indexOfTab("Results"), false);
		sRTabPane.setPreferredSize(new Dimension((WIDTH - 15) * 2 / 3, Short.MAX_VALUE));
		
		cPanel.add(sRTabPane);
		cPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		cTabPane = new JTabbedPane();
		ConsolePanel console = new ConsolePanel();
		cTabPane.addTab("Console", console.ConsolePanel());
		cTabPane.setPreferredSize(new Dimension((WIDTH - 15) / 3, Short.MAX_VALUE));
		
		cPanel.add(cTabPane);
		cPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		content.add(cPanel);
		
		/* Console Test code */
		/*******************/
		ConsolePanel console2 = new ConsolePanel();
		String mishmash;
		Queue<String> testQueue = new LinkedList<String>();
		for (int j=1; j<=20; j++)
		{
			for (int k = 1; k<=20; k++)
			{
				mishmash = "Turn " + j + ", Message # " + k;
				testQueue.offer(mishmash);
			}
			console2.addMessage(testQueue);
			testQueue = new LinkedList<String>();
		}
		/*********************/
		
	}
	
	private void showSimulation()
	{
		rewind.setEnabled(true);
		play.setEnabled(true);
		stop.setEnabled(true);
		ff.setEnabled(true);
		saveSim.setEnabled(true);
		
		rewindButton.setEnabled(true);
		playButton.setEnabled(true);
		stopButton.setEnabled(true);
		ffButton.setEnabled(true);
	}
	
	private void showResults()
	{
		rewind.setEnabled(false);
		play.setEnabled(false);
		stop.setEnabled(false);
		ff.setEnabled(false);
		saveSim.setEnabled(true);
		
		rewindButton.setEnabled(false);
		playButton.setEnabled(false);
		stopButton.setEnabled(false);
		ffButton.setEnabled(false);
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
		SimResScreen simRes = new SimResScreen();
		simRes.setVisible(true);
	}
}


/**
 * 
 */
package org.snowcrash.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

import org.snowcrash.critter.Critter;
import org.snowcrash.filemanagement.FileManager;
import org.snowcrash.world.World;


/**
 * @author dong
 *
 */
public class SimuScreen extends BaseGUI {
	/**
	 * the world size should be set by configuration
	 */
	private static int worldWidth = 20;
	private static int worldHeight = 20;
	private static SimuPanel simuPanel = null;
	private static Critter[][] map = null;

	JTabbedPane simuResultPane = null;

	/**
	 * constructor for simulation screen
	 * @param w the width of the world
	 * @param h the height of the world
	 */
	public SimuScreen(int w, int h)
	{
		rewind.setEnabled(true);
		play.setEnabled(true);
		stop.setEnabled(true);
		ff.setEnabled(true);
		saveSimulation.setEnabled(true);
		
		rewindButton.setEnabled(true);
		playButton.setEnabled(true);
		stopButton.setEnabled(true);
		ffButton.setEnabled(true);

		worldWidth = w;
		worldHeight = h;
		Container content = getContentPane();

		JTabbedPane tabPane = new JTabbedPane();
		simuPanel = new SimuPanel(worldWidth, worldHeight);
		JScrollPane simuScrollPane = new JScrollPane(simuPanel);
		simuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		simuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		ResultPanel resultPanel = new ResultPanel();
		tabPane.addTab("Simulation", simuScrollPane);
		tabPane.addTab("Results", resultPanel);
		tabPane.setSelectedIndex(tabPane.indexOfTab("Simulation"));
		tabPane.setEnabledAt(tabPane.indexOfTab("Results"), false);
		tabPane.setPreferredSize(new Dimension(WIDTH * 3 / 5 - 15, HEIGHT - 100));
		
		simuResultPane = tabPane; 
		content.add(tabPane, BorderLayout.CENTER);
		
		tabPane = new JTabbedPane();
		ConsolePanel consolePanel = new ConsolePanel();
		tabPane.addTab("Console", consolePanel);
		tabPane.setPreferredSize(new Dimension(WIDTH * 2 / 5 - 15, HEIGHT - 100));

		content.add(tabPane, BorderLayout.LINE_END);
		
	}
	
	// translate critter icons in the map, for test purpose
	private void translate(Critter[][] map) {
		if (map == null) return;
		int w = map.length;
		int h = map[0].length;
		Critter temp = map[0][0];
		int i, j;
		for (i = 0;i < w;i++)
			for (j = 0;j < h;j++) {
				if (i != w - 1) map[i][j] = map[i + 1][j];
				else if (j != h - 1) map[i][j] = map[0][j + 1];
				else map[i][j] = temp;
			}
	}
	
	// test simuPanel
	private void test() {
		FileManager mgr = new FileManager();
		World world = mgr.loadWorld("testWorld.Json", "");
		map = world.getMap();
		simuPanel.updateworld(map);
        Timer timer = new Timer(1000, new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
            	translate(map);
        		simuPanel.updateworld(map);
            } 
        }); 
        timer.start(); 
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimuScreen sScreen = new SimuScreen(4,4);
		sScreen.test();
	}

}

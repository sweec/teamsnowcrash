/**
 * 
 */
package org.snowcrash.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

import org.snowcrash.critter.Critter;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.filemanagement.FileManager;
import org.snowcrash.world.World;


/**
 * @author dong
 *
 */
public class testSimuScreen extends BaseGUI {
	/**
	 * the world size should be set by configuration
	 */
	private static int worldWidth = 20;
	private static int worldHeight = 20;
	private static SimuPanel simuPanel = null;
	private static Critter[][] map = null;
	private static JTabbedPane simuResultPane = null;

	/**
	 * constructor for simulation screen
	 * @param w the width of the world
	 * @param h the height of the world
	 */
	public testSimuScreen(int w, int h)
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

		worldWidth = w;
		worldHeight = h;
		
		Container content = getContentPane();
		int contentWidth = content.getWidth();

		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.X_AXIS));
		
		cPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		JTabbedPane tabPane = new JTabbedPane();
		simuPanel = new SimuPanel(worldWidth, worldHeight);
		tabPane.addTab("Simulation", simuPanel.getScrollPane());
		tabPane.addTab("Results", new ResultsPanel());
		tabPane.setSelectedIndex(tabPane.indexOfTab("Simulation"));
		tabPane.setEnabledAt(tabPane.indexOfTab("Results"), false);
		tabPane.setPreferredSize(new Dimension((contentWidth - 20) / 2, Short.MAX_VALUE));
		simuResultPane = tabPane; 
		
		cPanel.add(tabPane);
		cPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Console", new JPanel());
		tabPane.setPreferredSize(new Dimension((contentWidth - 20) / 2, Short.MAX_VALUE));
		
		cPanel.add(tabPane);
		cPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		content.add(cPanel);
		
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
	private void testSimuPanel() {
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
	
	private void testResultsPanel() {
		FileManager mgr = new FileManager();
		mgr.loadCritterTemplates("testCritterTemplates.Json", "test");
		DAO dao = DAOFactory.getDAO();
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testSimuScreen sScreen = new testSimuScreen(20,20);
		simuResultPane.setEnabledAt(simuResultPane.indexOfTab("Results"), true);
		sScreen.testSimuPanel();
		sScreen.testResultsPanel();
	}

}

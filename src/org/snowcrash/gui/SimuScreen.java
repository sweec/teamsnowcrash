/**
 * 
 */
package org.snowcrash.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


/**
 * @author dong
 *
 */
public class SimuScreen extends BaseGUI {

	JTabbedPane simuResultPane = null;

	public SimuScreen()
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

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		
		JTabbedPane tabPane = new JTabbedPane();
		JScrollPane simuScrollPane = SimuPanel.SimuScrollPane();
		ResultPanel resultPanel = new ResultPanel();
		tabPane.addTab("Simulation", simuScrollPane);
		tabPane.addTab("Results", resultPanel);
		tabPane.setSelectedIndex(tabPane.indexOfTab("Simulation"));
		tabPane.setEnabledAt(tabPane.indexOfTab("Results"), false);
		tabPane.setPreferredSize(new Dimension(WIDTH * 3 / 5 - 15, HEIGHT - 100));
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new FlowLayout());
		tempPanel.add(tabPane);
		simuResultPane = tabPane; 
		
		topPanel.add(tempPanel, BorderLayout.WEST);
		
		tabPane = new JTabbedPane();
		ConsolePanel consolePanel = new ConsolePanel();
		tabPane.addTab("Console", consolePanel);
		tabPane.setPreferredSize(new Dimension(WIDTH * 2 / 5 - 15, HEIGHT - 100));
		tempPanel = new JPanel();
		tempPanel.setLayout(new FlowLayout());
		tempPanel.add(tabPane);
		topPanel.add(tempPanel, BorderLayout.EAST);
		
		Container content = getContentPane();
		content.add(topPanel, BorderLayout.NORTH);
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SimuScreen();
	}

}

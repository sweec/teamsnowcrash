/**
 * 
 */
package org.snowcrash.gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.world.World;

/**
 * @author dong
 *
 */
public class ResultsPanel extends JPanel {
	final private int SIZEX = 500, SIZEY = 1000;
	private ArrayList<CritterTemplate> plant = null, prey= null, predator = null;
	
	public ResultsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(critterTemplatePane());
		setupData();
		add(Box.createRigidArea(new Dimension(5,0)));
		add(statisticsPane());
	}

	private void setupData() {
		World world = World.getInstance();
		Critter[][] map = world.getMap();
		if (map == null) return;
		int plantCount = 0, preyCount = 0, predatorCount = 0;
		int i, j;
		for (i = 0;i < map.length;i++)
			for (j = 0;j < map[0].length;j++) {
				if (map[i][j] != null) {
					CritterPrototype type = map[i][j].getPrototype();
					switch (type) {
					case PLANT:
						plantCount++;
						break;
					case PREY:
						preyCount++;
						break;
					case PREDATOR:
						predatorCount++;
						break;
					}
				}
			}
		 
	}
	
	private JScrollPane critterTemplatePane() {
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		
		JPanel plants = this.borderPanel("Plants");
		cPanel.add(plants);
		JPanel prey = this.borderPanel("Prey");
		cPanel.add(prey);
		JPanel predator = this.borderPanel("Predator");
		cPanel.add(predator);
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("Critters", cPanel);
		JScrollPane cScroll = new JScrollPane(tabPane);
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return cScroll;
	}
	
	private JScrollPane statisticsPane() {
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("Statistics", cPanel);
		JScrollPane cScroll = new JScrollPane(tabPane);
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return cScroll;
	}
	
	private JPanel borderPanel(String borderTitle)
	{
		Border critterBorder;
		JPanel cPanel = new JPanel();
		critterBorder = BorderFactory.createTitledBorder(borderTitle);
		cPanel.setBorder(critterBorder);
		cPanel.setAlignmentX(CENTER_ALIGNMENT);
		return cPanel;
	}
	
	/**
	 * return a JScrollPane contains me
	 */
	public JScrollPane getScrollPane() {
		JScrollPane scrollPane = new JScrollPane(this);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPane;
	}

    @Override 
    /**
     * this is critical in set the size correctly of the JPanel
     */
	public Dimension getPreferredSize() {
		return new Dimension(SIZEX, SIZEY);
	}
	
}

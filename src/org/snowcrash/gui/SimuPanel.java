/**
 * 
 */
package org.snowcrash.gui;

import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.snowcrash.critter.Critter;

/**
 * @author dong
 *
 */
public class SimuPanel extends JPanel implements ISimuPanel {
	static SimuPanel instance = null;
	
	static private SimuPanel getInstance() {
		if (instance == null) instance = new SimuPanel();
		return instance;
	}

	static public JScrollPane SimuScrollPane() {
		JScrollPane sScroll = new JScrollPane(getInstance());
		sScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		return sScroll;
	}
	
	/**
	 * 
	 */
	public SimuPanel() {
		// to do
	}

	/**
	 * @param layout
	 */
	public SimuPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isDoubleBuffered
	 */
	public SimuPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public SimuPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void updateworld(Critter[][] map) {
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

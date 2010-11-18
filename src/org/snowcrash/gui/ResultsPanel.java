/**
 * 
 */
package org.snowcrash.gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author dong
 *
 */
public class ResultsPanel extends JPanel {
	final private int SIZEX = 500, SIZEY = 1000;
	
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

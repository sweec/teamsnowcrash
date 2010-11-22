/**
 * 
 */
package org.snowcrash.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.data.CritterPrototype;

/**
 * @author dong
 *
 */
public class SimuPanel extends JPanel {
	final private int line_width = 2;
	final private int offset = line_width;
    final private BasicStroke stroke = new BasicStroke(line_width);
    final private int wUnit = 64, hUnit = 64;
    private Image plant = null;
    private Image prey = null;
    private Image predator = null;
	private Critter[][] critters = null;
	private boolean[][] isDirty = null;
   
	private int width;
	private int height;
	private int sizeX;
	private int sizeY;
	
	/**
	 * constructor
	 * @param w width of world
	 * @param h height of world
	 */
	public SimuPanel (int w, int h) {
		width = w;
		height = h;
		sizeX = w * wUnit;
		sizeY = h * hUnit;
		isDirty = new boolean[w][h];
		
    	try {
    		Image originalPlant = ImageIO.read(new File("images/plant.png"));
    		Image originalPrey = ImageIO.read(new File("images/prey-right.png"));
    		Image originalPredator = ImageIO.read(new File("images/predator-right.png"));
    	    plant = originalPlant.getScaledInstance(wUnit, hUnit, java.awt.Image.SCALE_SMOOTH);
       	    prey = originalPrey.getScaledInstance(wUnit, hUnit, java.awt.Image.SCALE_SMOOTH);
       	    predator = originalPredator.getScaledInstance(wUnit, hUnit, java.awt.Image.SCALE_SMOOTH);
    	} catch (IOException e) {
	    	e.printStackTrace();
    	}
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

	/**
	 * update the view with new world map
	 * @param map new world map of critters
	 */
	public void updateworld(Critter[][] map) {
		critters = map;

        int i, j, x, y;
        for (i = 0;i < width;i++) {
        	for (j = 0;j < height;j++) {
        		if (isDirty[i][j]) {
        			x = i * wUnit;
        			y = j * hUnit;
        			repaint(x, y, x + wUnit, y + hUnit);
        			isDirty[i][j] = false;
        		}
        	}
        }

        if (critters == null) return;
        int w = critters.length;
        if (w > width) w = width;
        int h = critters[0].length;
        if (h > height) h = height;

        for (i = 0;i < w;i++)
        	for (j = 0;j < h;j++) {
        		if (critters[i][j] != null) {
        			x = i * wUnit;
        			y = j * hUnit;
        			repaint(x, y, x + wUnit, y + hUnit);
        			isDirty[i][j] = true;
        		}
        	}
	}
	
    @Override 
    /**
     * this is critical in set the size correctly of the JPanel
     */
	public Dimension getPreferredSize() {
		return new Dimension(sizeX, sizeY);
	}
	
    @Override 
    public void paintComponent(Graphics g) 
    { 
        super.paintComponent(g); 
        
        Graphics2D g2 = (Graphics2D) g.create(); 

        // draw the grid
        g2.setColor(Color.YELLOW);
        g2.setStroke( stroke );

        g2.drawRect(offset, offset, sizeX, sizeY);
        
        int x = wUnit, y = hUnit;
        int i, j;
        for (i = 1; i < width; i++) {
	        g2.drawLine(x, offset, x, sizeY - offset );
	        x += wUnit;
        }
        for (j = 1; j < height; j++) {
        	g2.drawLine(offset, y, sizeX - offset, y);
        	y += hUnit;
        }
        
        // draw critters
        if (critters == null) return;
        int w = critters.length;
        if (w > width) w = width;
        int h = critters[0].length;
        if (h > height) h = height;
        for (i = 0;i < w;i++)
        	for (j = 0;j < h;j++) {
        		if (critters[i][j] != null) {
        			Image img = null;
        			CritterPrototype type = critters[i][j].getPrototype();
        			switch (type) {
        			case PLANT:
        				img = plant;
        				break;
        			case PREY:
        				img = prey;
        				break;
        			case PREDATOR:
        				img = predator;
        				break;
        			}
        			if (img != null)
        				g2.drawImage(img, i * wUnit, j * hUnit, null);
        			
        		}
        	}
        
    } 

    /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

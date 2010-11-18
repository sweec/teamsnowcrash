/**
 * 
 */
package org.snowcrash.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
    private BufferedImage plant = null;
    private BufferedImage prey = null;
    private BufferedImage predator = null;
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
    		BufferedImage originalPlant = ImageIO.read(new File("images/plant.png"));
    		BufferedImage originalPrey = ImageIO.read(new File("images/prey-left.png"));
    		BufferedImage originalPredator = ImageIO.read(new File("images/predator-left.png"));
    	    plant = new BufferedImage(wUnit, hUnit, originalPlant.getType());
    	    prey = new BufferedImage(wUnit, hUnit, originalPrey.getType());
    	    predator = new BufferedImage(wUnit, hUnit, originalPredator.getType());
    	    Graphics2D g = plant.createGraphics();
        	g.drawImage(originalPlant, 0, 0, wUnit, hUnit, null);
        	g.dispose();
    	    g = prey.createGraphics();
        	g.drawImage(originalPrey, 0, 0, wUnit, hUnit, null);
        	g.dispose();
    	    g = predator.createGraphics();
        	g.drawImage(originalPredator, 0, 0, wUnit, hUnit, null);
        	g.dispose();
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
        			BufferedImage img = null;
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

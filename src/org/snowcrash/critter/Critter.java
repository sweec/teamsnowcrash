package org.snowcrash.critter;

/**
 * @author dearnest
 *
 */
public interface Critter {

	public boolean isActed();
	public void setActed(boolean acted);
	public int getActionCost();
	public int getCamo();
	public int getFight();
	public int getHealth();
	public void setHealth(int health);
	public int getMaxHealth();
	public int getSize();
	public int getSpeed();
	public int getVision();
	public void die();
	
}

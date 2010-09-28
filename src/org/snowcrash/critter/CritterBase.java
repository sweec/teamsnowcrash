package org.snowcrash.critter;

import java.util.HashMap;

import org.snowcrash.state.StateContext;

public abstract class CritterBase implements Critter {

	private HashMap<String, Integer> traits;
	private boolean acted = false;
	private int actionCost = 1;
	private int health = 0;
	private int healthMax = 50;
	private int size = 2;
	private StateContext myStateContext;
	
	@Override
	public boolean isActed() {
		return acted;
	}

	@Override
	public void setActed(boolean acted) {
		this.acted = acted;
	}

	@Override
	public int getActionCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCamo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHealth(int health) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getVision() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void die() {
		
	}

	@Override
	public String toString() {
		return "CritterBase [traits=" + traits + ", acted=" + acted
				+ ", actionCost=" + actionCost + ", health=" + health
				+ ", healthMax=" + healthMax + ", size=" + size
				+ ", myStateContext=" + myStateContext + "]";
	}

}

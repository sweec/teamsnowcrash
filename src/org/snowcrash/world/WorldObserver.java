package org.snowcrash.world;

import java.util.Observer;

import org.snowcrash.critter.Critter;


public interface WorldObserver extends Observer
{
	public void updateWorld( World world );
	
	// only critters died are needed for statistics
	// all alive critters will be counted after simulation finished
	public void updateStatistics(Critter[] critters);
	
	// notify observer the end of simulation
	public void notifyTheEnd();
}

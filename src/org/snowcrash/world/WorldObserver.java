package org.snowcrash.world;

import org.snowcrash.critter.Critter;


public interface WorldObserver
{
	public void updateWorld( World world );
	
	// only critters died are needed for statistics
	// all alive critters will be counted after simulation finished
	public void updateStatistics(Critter[] critters);
}

/**
 * 
 */
package org.snowcrash.gui;

import org.snowcrash.critter.Critter;

/**
 * @author dong
 *
 */
public interface ISimuPanel {
	/**
	 * 
	 * Update the display of the simulation world.
	 * 
	 * @param map Critter distribution of the world.
	 * 
	 */
	public void updateworld(Critter[][] map);
}

/*  
 * Critter: Interface that all critter instances implement. 
 * Copyright (C) 2010  Team Snow Crash
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Artistic License/GNU GPL as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Artistic License/GNU General Public License for more details.
 *
 * You should have received a copy of the Artistic license/GNU General 
 * Public License along with this program.  If not, see
 * <http://dev.perl.org/licenses/artistic.html> and 
 * <http://www.gnu.org/licenses/>.
 * 
 */

package org.snowcrash.critter;

import org.snowcrash.critter.data.Size;
import org.snowcrash.critter.data.Trait;

/**
 * @author dearnest
 * Interface that all critter instances implement.
 * 
 * 10/24/10	DE 	Added License notice and comments.
 * 
 */

public interface Critter {

	public boolean isActed();
	public void setActed(boolean acted);
	public int getActionCost();
	public int getHealth();
	public void setHealth(int health);
	public int getMaxHealth();
	public Size getSize();
	public void die();
	public int getTrait(Trait trait);
	
}

/*  
 * Rule: Interface through which an outcome is adjudicated between critters 
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

package org.snowcrash.rule;

import org.snowcrash.critter.Critter;

/**
 * 
 * @author dearnest
 * Interface through which an outcome is adjudicated between critters.  The first critter is
 * always the critter that is acting and the second critter is always the one that is being
 * acted upon.
 * 10/21/10	DE	Created.
 *
 */

public interface Rule {
	/**
	 * Determines the outcome of the implementation of the rule.  It returns a boolean if the
	 * actor is successful in what it attempts to do with the target, otherwise false.
	 * @param actor Critter that acts.
	 * @param target Critter that is acted upon.
	 * @return boolean
	 */
	public abstract boolean determine(Critter actor, Critter target); 
}

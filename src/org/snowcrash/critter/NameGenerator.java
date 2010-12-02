/*  
 * NameGenerator: This class generates ordered names based on the template 
 * name of a given critter.
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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class generates ordered names based on the template name of a given critter. 
 * @author dearnest
 *
 */
public final class NameGenerator {
	
	private static HashMap<String, Integer> templateMap = new HashMap<String, Integer>();
	
	private NameGenerator() {
		// Do nothing.
	}
	
	/**
	 * Resets the NameGenerator HashMap
	 */
	public static void reset() {
		templateMap = new HashMap<String, Integer>();
	}
	
	/**
	 * Gets a Critter name based on the template.  If there is no Template in the cache
	 * it puts the template name in here.
	 * @param templateName
	 * @return
	 */
	public static synchronized String getName(String templateName) {
		Integer i = templateMap.get(templateName);
		if (i == null) {
			i = new Integer(1);
		} 
		templateMap.put(templateName, i + 1);
		return templateName + "-" + i.toString();
	}
	
	public static void initialize(ArrayList<String> templateNames) {
		for (String name: templateNames) {
			templateMap.put(name, new Integer(1));
		}
	}
}

/*  
 * Pair: Generic that stores the genetic pair of trait values. 
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

package org.snowcrash.utilities;

/**
 * 
 * @author dearnest
 * Generic that stores a pair of values.
 * 10/24/10	DE	Created.
 * 
 */

public class Pair<L,R> {

	private L left;
	private R right;
	
	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}
	
	public R getRight() {
		return right;
	}
	
	@Override
	public String toString() {
		return "Pair [left=" + left + ", right=" + right + "]";
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		return o instanceof Pair 
		&& equals(left, ((Pair<L,R>) o).left)
		&& equals(right, ((Pair<L,R>) o).right);
		
	}
	
	private static boolean equals(Object x, Object y) {
		return (x == null && y == null) || (x != null && x.equals(y));
	}
}

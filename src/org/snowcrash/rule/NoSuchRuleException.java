/*  
 * NoSuchRuleException: Exception generated when a Rule class doesn't exist. 
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

/**
 * 
 * @author dearnest
 * Exception generated when a Rule class doesn't exist.  This can happen if the rule
 * is enumerated in Rules but there is no corresponding class to handle the rule.
 * 10/21/10	DE	Created.
 *
 */

public class NoSuchRuleException extends Exception {

	private static final long serialVersionUID = 5518633618631453652L;
	private static final String NO_SUCH_RULE_EXISTS = "No such rule exists.";

	public NoSuchRuleException() {
		super (NO_SUCH_RULE_EXISTS);
	}
}

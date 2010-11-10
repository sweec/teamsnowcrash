/*  
 * LogViewer: popup window showing the log. 
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

package org.snowcrash.filemanagement;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Formatter;
import java.util.Date;

//This custom formatter formats parts of a log record to a single line
class LogFormatter extends Formatter {
    // This method is called for every log records
    public String format(LogRecord rec) {
        return (new Date())+": "+formatMessage(rec)+"\n";
    }

    // This method is called just after the handler using this
    // formatter is created
    public String getHead(Handler h) {
        return (new Date())+": "+"Simulation Started\n";
    }

    // This method is called just after the handler using this
    // formatter is closed
    // :) when we reach here, the log file is deleted soon.
    public String getTail(Handler h) {
        return (new Date())+": "+"Simulation Log Ended\n";
    }
}

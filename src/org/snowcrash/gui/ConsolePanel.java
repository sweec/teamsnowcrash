/*  
 * ConsolePanel: 	Returns the ConsolePanel to SimResScreen. Adds messages 
 * 					to the ConsolePanel.
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

package org.snowcrash.gui;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsolePanel extends JPanel
{
	private JScrollPane consoleScroll = null;
	private JTextArea consoleArea = new JTextArea(80, 20);
	private Queue<String> messageQueue = new LinkedList<String>();
	private int turnNum = 0;
	
	// universal cross-platform newline
	public static String newline = System.getProperty("line.separator");
	
	public ConsolePanel()
	{
		
	}
	
	/**
	 * Places a JTextArea into a JScrollPane.
	 * @return
	 */
	public JScrollPane getConsolePanel()
	{
		//consoleArea = new JTextArea(80, 20);
		consoleArea.setEditable(false);
		consoleScroll = new JScrollPane(consoleArea);
		consoleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		consoleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		return consoleScroll;
	}
	
	/**
	 * Adds a message-queue.
	 */
	public void addMessage(Queue<String> mQueue)
	{
		turnNum++;
		if (turnNum == 11)
		{
			this.wipeConsole();
		}
		messageQueue = mQueue;
		this.dumpToConsole();
	}	
	
	/**
	 * Wipe the console and reset the turn counter to 1.
	 */
	private void wipeConsole()
	{
		consoleArea.setText("");
		turnNum = 1;
	}
	
	/**
	 * Dequeue messages and append to the Console.
	 */
	private void dumpToConsole()
	{	
		while (messageQueue.peek() != null)
		{
			String mtext = messageQueue.poll();
			consoleArea.append(mtext + newline);
			consoleArea.repaint();
			consoleScroll.repaint();
		}
		
	}
}



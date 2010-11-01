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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * @author dong
 * popup window showing large log file with quick response.
 * 
 * Adapted from free source code people shared online.
 * 
 */

public class LogViewer extends JPanel {
    private JScrollPane scrollPane;
    private JScrollBar scrollBar;
    private JTextArea textArea;
    private String file = null;
    private RandomAccessFile raf;
    private int rows = 0;	// how many rows displayed in the window
    private boolean isAdjusting = false; //scrollbar's version seems to throw an event when changed, thus defeating the purpose of having it.
    private boolean closed = true;
 
    // used for determining if our buffer is near the beginning or end of the file.
    private long[] byteMarks;
 
    public LogViewer(int rows) {
        this.rows = rows;
        jbinit();
    }
 
    public void setLogFile(String logfile) {
        //cleanup old logfile
        if (file != null) {
            try{
                raf.close();
                closed = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            file = logfile;
            raf = new RandomAccessFile(logfile, "r");
            closed = false;
            scrollBar.setMinimum(0);
 
            setByteMarks();
            if (byteMarks[0] != -1) {
                scrollBar.setMaximum( (int) byteMarks[0]);
                isAdjusting = true;
                scrollBar.setValue( (int) byteMarks[0]);
                scrollBar.setVisible(true);
            } else {
                scrollBar.setVisible(false);
            }
            viewBottom();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public void close() {
        if (file != null || !closed) {
            try{
                raf.close();
                closed = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    public void view(int row) {
        synchronized (this) {
            try {
                if (scrollBar.getValueIsAdjusting() || isAdjusting) {
                    return;
                }
                if (closed)
                    return;
                if (row < 0)
                    row = 0;
                raf.seek(row);
                if (byteMarks[0] <= raf.getFilePointer()) {
                    viewBottom();
                    return;
                }

                if (row != 0) {
                    raf.readLine(); //read to the end of the line
                }
                textArea.setText("");
                String line = "";
                int length;
                for (int i = 0; i < rows; i++) {
                    line = raf.readLine();
                    if (line == null)
                        return;
                    length = textArea.getText().length();
                    textArea.append(line);
                    if (i != rows - 1) {
                        textArea.append("\n");
                    }
                    textArea.setCaretPosition(length);
                }
                scrollPane.getHorizontalScrollBar().setValue(0);
                scrollBar.setBlockIncrement(textArea.getText().length());
                scrollBar.setUnitIncrement(textArea.getText().length() / 11); // 2 lines worth of bytes? //! maybe read back 1 line?
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    private void jbinit() {
        byteMarks = new long[rows+1]; // last rows and first rows byte index's
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("monospaced",Font.PLAIN,10));
        scrollBar = new JScrollBar(JScrollBar.VERTICAL);
        scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
        add(scrollBar,BorderLayout.EAST);
        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (isAdjusting) {
                    isAdjusting = false;
                    return;
                }
                view(e.getValue());
            }
        });
        scrollBar.setBlockIncrement(1000);
        scrollBar.setUnitIncrement(300);
    }
 
    private void setByteMarks() throws Exception{
        if (closed) return;
        int i = rows-1;
        long pos = raf.length();
        byteMarks[rows] = pos;
        char c = ' ';
        while (i >= 0) {
            if (pos >= 0) {
                raf.seek(pos);
                c = (char) (raf.read() & 0xFF);
                if (c == '\n') {
                    //char is new line
                    byteMarks[i--] = raf.getFilePointer();
                }
            } else {
                byteMarks[i--] = -1;
            }
            pos--;
        }
    }
 
    private void viewBottom() {
        synchronized (this) {
            if (closed)
                return;
            try {
                setByteMarks();
                if (byteMarks[0] != -1) {
                    raf.seek(byteMarks[0]);
                }
                else {
                    raf.seek(0);
                }
                textArea.setText("");
                String line = "";
                for (int i = 0; i < rows; i++) {
                    line = raf.readLine();
                    if (line == null)
                        break;
                    textArea.append(line);
                    if (i != rows - 1) {
                       textArea.append("\n");
                   }
 
                }
                //! watch this for a callback
                isAdjusting = false;
                //scrollBar.setValue( (int) raf.length());
                scrollBar.setValue( (int)byteMarks[0]);
                scrollPane.getHorizontalScrollBar().setValue(0);
                scrollBar.setBlockIncrement(textArea.getText().length());
                scrollBar.setUnitIncrement(textArea.getText().length() / 11);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void display(String log, int sizeX, int sizeY) {
        JFrame frame = new JFrame();
        setLogFile(log);
        frame.getContentPane().add(this);
        setPreferredSize(new Dimension(sizeX, sizeY));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
}
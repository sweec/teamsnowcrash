package org.snowcrash.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class TraitsPanel extends JFrame
{
	public JPanel traitsObjects()
	{
		JPanel cPanelOuter = new JPanel();
		JPanel cPanelInner = new JPanel();
		JScrollPane cScroll = new JScrollPane(cPanelInner);
		JTabbedPane tabPane = new JTabbedPane();
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tabPane.addTab("Traits", cScroll);
		cPanelOuter.add(tabPane);
		
		
		
		return cPanelOuter;
	}
}
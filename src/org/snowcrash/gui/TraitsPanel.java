package org.snowcrash.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class TraitsPanel extends JPanel
{
	public JPanel traitsObjects()
	{
		JPanel cPanelInner = new JPanel();
		JScrollPane cScroll = new JScrollPane(cPanelInner);
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		final JButton applyButton = new JButton("Apply");
		final JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(applyButton);
		buttonPanel.add(cancelButton);
		
		JPanel cPanelOuter = new JPanel();
		cPanelOuter.setLayout(new BorderLayout());
		cPanelOuter.add(cScroll, BorderLayout.CENTER);
		cPanelOuter.add(buttonPanel, BorderLayout.SOUTH);
		
		return cPanelOuter;
	}
}
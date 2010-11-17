package org.snowcrash.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TraitsPanel extends JPanel
{
	public JPanel TraitsPanel()
	{
		JPanel cPanelInner = new JPanel();
		JScrollPane cScroll = new JScrollPane(cPanelInner);
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		final JButton newButton = new JButton("Apply");
		final JButton delButton = new JButton("Cancel");
		buttonPanel.add(newButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(delButton);

		JPanel cPanelOuter = new JPanel();
		cPanelOuter.setLayout(new BoxLayout(cPanelOuter, BoxLayout.Y_AXIS));
		cPanelOuter.add(cScroll);
		cPanelOuter.add(buttonPanel);
		
		return cPanelOuter;
	}
}
package org.snowcrash.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;


public class CritterPanel extends JFrame
{
	public JPanel critterObjects()
	{
		JPanel cPanelOuter = new JPanel();
		JPanel cPanelInner = new JPanel();
		JScrollPane cScroll = new JScrollPane(cPanelInner);
		JTabbedPane tabPane = new JTabbedPane();
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tabPane.addTab("Critters", cScroll);
		cPanelOuter.add(tabPane);
		// Critter Config Objects start
		
		cPanelInner.setLayout(new BorderLayout());
		Border critterBorder;
		
		JPanel plants = new JPanel();
		critterBorder = BorderFactory.createTitledBorder("Plants");
		plants.setBorder(critterBorder);
		cPanelInner.add(plants, BorderLayout.NORTH);

		JPanel prey = new JPanel();
		critterBorder = BorderFactory.createTitledBorder("Prey");
		prey.setBorder(critterBorder);
		cPanelInner.add(prey, BorderLayout.CENTER);
		
		JPanel predators = new JPanel();
		critterBorder = BorderFactory.createTitledBorder("Predators");
		predators.setBorder(critterBorder);
		cPanelInner.add(predators, BorderLayout.SOUTH);
		
		// Critter Config Objects end
		return cPanelOuter;
	}
}
package org.snowcrash.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;


public class CritterPanel extends JPanel
{
	public JPanel critterObjects()
	{
		JPanel cPanelInner = new JPanel();
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
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		final JButton newButton = new JButton("New");
		final JButton delButton = new JButton("Delete");
		buttonPanel.add(newButton);
		buttonPanel.add(delButton);
		
		JScrollPane cScroll = new JScrollPane(cPanelInner);
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel cPanelOuter = new JPanel();
		cPanelOuter.setLayout(new BorderLayout());
		cPanelOuter.add(cScroll, BorderLayout.CENTER);
		cPanelOuter.add(buttonPanel, BorderLayout.SOUTH);
		// Critter Config Objects start
		
		ActionListener critterPanelListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
            	if (e.getActionCommand().equals("New"))
            	{
            		// Do Something
            	}
            	else if (e.getActionCommand().equals("Delete"))
            	{
            		// Do Something
            	}
            }
        };

        newButton.addActionListener( critterPanelListener );
		
		// Critter Config Objects end
		return cPanelOuter;
	}
	
}
package org.snowcrash.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;


public class CritterPanel extends JPanel implements ActionListener
{
	JPanel plants, prey, predators;
	JButton newButton, delButton;
	
	public JPanel CritterPanel()
	{
		JPanel cPanelInner = new JPanel();
		cPanelInner.setLayout(new BoxLayout(cPanelInner, BoxLayout.Y_AXIS));
		
		JPanel plants = this.borderPanel("Plants");
		cPanelInner.add(plants);
		JPanel prey = this.borderPanel("Prey");
		cPanelInner.add(prey);
		JPanel predator = this.borderPanel("Predator");
		cPanelInner.add(predator);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		newButton = new JButton("New");
		delButton = new JButton("Delete");
		buttonPanel.add(newButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(delButton);
		
		JScrollPane cScroll = new JScrollPane(cPanelInner);
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel cPanelOuter = new JPanel();
		cPanelOuter.setLayout(new BoxLayout(cPanelOuter, BoxLayout.Y_AXIS));
		cPanelOuter.add(cScroll);
		cPanelOuter.add(buttonPanel);

        newButton.addActionListener(this);
		
		return cPanelOuter;
	}
	
	public JPanel borderPanel(String borderTitle)
	{
		Border critterBorder;
		JPanel cPanel = new JPanel();
		critterBorder = BorderFactory.createTitledBorder(borderTitle);
		cPanel.setBorder(critterBorder);
		cPanel.setAlignmentX(CENTER_ALIGNMENT);
		return cPanel;
	}
	
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
	
}
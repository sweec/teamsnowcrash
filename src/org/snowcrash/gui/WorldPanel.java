package org.snowcrash.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.snowcrash.commands.Command;
import org.snowcrash.commands.CommandFactory;

public class WorldPanel extends JPanel implements ChangeListener, ActionListener
{
	public static final int SIZEMIN = 20;		// minimum value for each slider
	public static final int SIZEMAX = 100;		// maximum value for each slider
	public static final int SIZEINIT = 50;		// initial value for each slider
	public static final int TURNSINIT = 30;		// initial number of turns
	
	JSlider worldSlider;
	JLabel currentSizeLabel, currentTurnsLabel;
	JTextField TurnsField;
	int worldTurns = TURNSINIT;
	int worldSize = SIZEINIT;
	
	private ConfigScreen parent;
	
	public WorldPanel( ConfigScreen parent )
	{
		this.parent = parent;
	}
	
	public JScrollPane WorldPanel()
	{
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		JLabel cLabel = new JLabel("World Size");
		cLabel.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(cLabel);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		// creates the slider that specifies the world size in grid spaces
		worldSlider = new JSlider(JSlider.HORIZONTAL, SIZEMIN, SIZEMAX, SIZEINIT);
		worldSlider.addChangeListener(this);
		worldSlider.setMajorTickSpacing(80);
		worldSlider.setMinorTickSpacing(1);
		worldSlider.setSnapToTicks(true);
		worldSlider.setPaintLabels(true);
		worldSlider.setMaximumSize( worldSlider.getPreferredSize() );
		worldSlider.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(worldSlider);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,40)));
		
		cLabel = new JLabel("Current Size");
		cLabel.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(cLabel);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		currentSizeLabel = new JLabel(SIZEINIT + " X " + SIZEINIT);
		currentSizeLabel.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(currentSizeLabel);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,40)));
		
		cLabel = new JLabel("World Turns");
		cLabel.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(cLabel);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		// allows the user to change the number of turns
		TurnsField = new JTextField(10);
		TurnsField.setActionCommand("jtext");
		TurnsField.setMaximumSize( TurnsField.getPreferredSize() );
		TurnsField.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(TurnsField);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		currentTurnsLabel = new JLabel(Integer.toString(TURNSINIT));
		currentTurnsLabel.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(currentTurnsLabel);
		
		cPanel.add(Box.createVerticalGlue());
		JScrollPane cScroll = new JScrollPane(cPanel);
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		TurnsField.addActionListener(this);
		
		return cScroll;
	}
	
	public int getTotalTurns()
	{
		return worldTurns;
	}
	
	public void stateChanged(ChangeEvent e) // event handler for the "World Size" slider
	{
		worldSize = worldSlider.getValue();
		if (e.getSource().equals(this.worldSlider) 
				&& !worldSlider.getValueIsAdjusting())
		{
			Command command = CommandFactory.getSetWorldSizeCommand(worldSize);
			command.execute();
		}
		else
		{
			currentSizeLabel.setText(worldSize + " X " + worldSize);
		}
	}
	
	public void actionPerformed(ActionEvent e) // event handler for the "World Turns"
	{
		try // ensure that worldTurns is a number
		{
			int prevWorldTurns = worldTurns;
			worldTurns = Integer.parseInt(TurnsField.getText().trim());
			if (worldTurns >= 10 && worldTurns <= 100)
			{
				currentTurnsLabel.setText(TurnsField.getText());
				TurnsField.setText("");
				Command command = CommandFactory.getSetNumberOfTurnsCommand(worldTurns);
    			command.execute();
    			
    			parent.receiveNumTurns( worldTurns );
			}
			else
			{
				TurnsField.setText("");
				worldTurns = prevWorldTurns;
			}
		}
		catch (NumberFormatException err) 
		{
			TurnsField.setText("");
		} 
	}
}
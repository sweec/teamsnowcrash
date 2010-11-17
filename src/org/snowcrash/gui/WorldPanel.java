package org.snowcrash.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WorldPanel extends JPanel implements ChangeListener, ActionListener
{
	public static final int SIZEMIN = 20;
	public static final int SIZEMAX = 100;
	public static final int SIZEINIT = 30;
	public static final int TURNSINIT = 30;
	JSlider worldSlider;
	JLabel currentSizeLabel, currentTurnsLabel;
	JTextField worldTurns;
	
	public JPanel WorldPanel()
	{
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		JLabel cLabel = new JLabel("World Size");
		cLabel.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(cLabel);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
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
		
		String initWorldSize = Integer.toString(SIZEINIT);
		currentSizeLabel = new JLabel(initWorldSize + " X " + initWorldSize);
		currentSizeLabel.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(currentSizeLabel);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,40)));
		
		cLabel = new JLabel("World Turns");
		cLabel.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(cLabel);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		worldTurns = new JTextField(10);
		worldTurns.setActionCommand("jtext");
		worldTurns.setMaximumSize( worldTurns.getPreferredSize() );
		worldTurns.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(worldTurns);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		currentTurnsLabel = new JLabel(Integer.toString(TURNSINIT));
		currentTurnsLabel.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(currentTurnsLabel);
		
		cPanel.add(Box.createVerticalGlue());
		
		worldTurns.addActionListener(this);
		
		return cPanel;
	}
	
	public void stateChanged(ChangeEvent e)
	{
		String worldSize = Integer.toString(worldSlider.getValue());
		currentSizeLabel.setText(worldSize + " X " + worldSize);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		int turnsint = Integer.parseInt(worldTurns.getText().trim());
		if (turnsint >= 10 && turnsint <= 100)
		{
			currentTurnsLabel.setText(worldTurns.getText());
			worldTurns.setText("");
		}
		else
		{
			worldTurns.setText("");
		}
	}
}
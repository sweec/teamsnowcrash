package org.snowcrash.gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class TraitsPanel extends JPanel
{
	public static final int SLIDEMIN = 1;
	public static final int SLIDEMAX = 5;
	public static final int SLIDEINIT = 1;
	
	JSlider upCamoSlider, lowCamoSlider, upSpeedSlider, lowSpeedSlider;
	JSlider upVisionSlider, lowVisionSlider, upCombatSlider, lowCombatSlider;
	JSlider upEndurSlider, lowEndurSlider, upAgeSlider, lowAgeSlider;
	
	public JPanel TraitsPanel()
	{
		JPanel cPanelInner = new JPanel();
		cPanelInner.setLayout(new BoxLayout(cPanelInner, BoxLayout.Y_AXIS));
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel sizePanel = new JPanel();
		sizePanel = this.sizePanel();
		cPanelInner.add(sizePanel);
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		cPanelInner.add(new JSeparator(SwingConstants.HORIZONTAL));
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel visionPanel = new JPanel();
		visionPanel = this.sliderPairPanel(upVisionSlider, lowVisionSlider, "Vision");
		cPanelInner.add(visionPanel);
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		cPanelInner.add(new JSeparator(SwingConstants.HORIZONTAL));
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel speedPanel = new JPanel();
		speedPanel = this.sliderPairPanel(upSpeedSlider, lowSpeedSlider, "Speed");
		cPanelInner.add(speedPanel);
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		cPanelInner.add(new JSeparator(SwingConstants.HORIZONTAL));
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel camoflagePanel = new JPanel();
		camoflagePanel = this.sliderPairPanel(upCamoSlider, lowCamoSlider, "Camoflage");
		cPanelInner.add(camoflagePanel);
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		cPanelInner.add(new JSeparator(SwingConstants.HORIZONTAL));
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel combatPanel = new JPanel();
		combatPanel = this.sliderPairPanel(upCombatSlider, lowCombatSlider, "Combat");
		cPanelInner.add(combatPanel);
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		cPanelInner.add(new JSeparator(SwingConstants.HORIZONTAL));
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel endurancePanel = new JPanel();
		endurancePanel = this.sliderPairPanel(upEndurSlider, lowEndurSlider, "Endurance");
		cPanelInner.add(endurancePanel);
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		cPanelInner.add(new JSeparator(SwingConstants.HORIZONTAL));
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel agePanel = new JPanel();
		agePanel = this.sliderPairPanel(upAgeSlider, lowAgeSlider, "Age");
		cPanelInner.add(agePanel);
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		cPanelInner.setPreferredSize(new Dimension(Short.MIN_VALUE, 800));
		
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
	
	public JPanel namePanel(String critterType, String critterName)
	{
		JPanel cPanel = new JPanel();
		return cPanel;
	}
	
	public JPanel sizePanel()
	{
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		
		JLabel sizeLabel = new JLabel("Size");
		sizeLabel.setAlignmentX(LEFT_ALIGNMENT);
		cPanel.add(sizeLabel);		
		
		Box sizeBox = new Box(BoxLayout.X_AXIS);
		JRadioButton smallRButton = new JRadioButton("Small", true);
		JRadioButton medRButton = new JRadioButton("Medium", false);
		JRadioButton lrgRButton = new JRadioButton("Large", false);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,5)));
		sizeBox.add(Box.createHorizontalStrut(5));
		sizeBox.add(smallRButton);
		sizeBox.add(Box.createHorizontalStrut(10));
		sizeBox.add(medRButton);
		sizeBox.add(Box.createHorizontalStrut(10));
		sizeBox.add(lrgRButton);
		sizeBox.add(Box.createHorizontalStrut(5));
		cPanel.add(sizeBox);
		cPanel.add(Box.createRigidArea(new Dimension(0,5)));
		
		return cPanel;
	}
	
	public JPanel sliderPairPanel(JSlider upSlider, JSlider lowSlider, String critterTrait)
	{
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		cPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel sliderPairLabel = new JLabel(critterTrait);
		sliderPairLabel.setAlignmentX(LEFT_ALIGNMENT);
		cPanel.add(sliderPairLabel);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		Box upperBox = new Box(BoxLayout.X_AXIS);
		JLabel upperLabel = new JLabel("Upper");
		upperLabel.setAlignmentY(BOTTOM_ALIGNMENT);
		upperBox.add(Box.createHorizontalStrut(10));
		upperBox.add(upperLabel);
		upperBox.add(Box.createHorizontalStrut(10));
		
		upSlider = new JSlider(JSlider.HORIZONTAL, SLIDEMIN, SLIDEMAX, SLIDEINIT);
		//upSlider.addChangeListener(this);
		upSlider.setMajorTickSpacing(1);
		upSlider.setMinorTickSpacing(1);
		upSlider.setSnapToTicks(true);
		upSlider.setPaintLabels(true);
		upSlider.setMaximumSize( upSlider.getPreferredSize() );
		upSlider.setAlignmentY(CENTER_ALIGNMENT);
		upperBox.add(upSlider);
		upperBox.add(Box.createHorizontalStrut(10));
		cPanel.add(upperBox);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		Box lowerBox = new Box(BoxLayout.X_AXIS);
		JLabel lowerLabel = new JLabel("Lower");
		lowerLabel.setAlignmentY(BOTTOM_ALIGNMENT);
		lowerBox.add(Box.createHorizontalStrut(10));
		lowerBox.add(lowerLabel);
		lowerBox.add(Box.createHorizontalStrut(10));
		
		lowSlider = new JSlider(JSlider.HORIZONTAL, SLIDEMIN, SLIDEMAX, SLIDEINIT);
		//lowSlider.addChangeListener(this);
		lowSlider.setMajorTickSpacing(1);
		lowSlider.setMinorTickSpacing(1);
		lowSlider.setSnapToTicks(true);
		lowSlider.setPaintLabels(true);
		lowSlider.setMaximumSize( lowSlider.getPreferredSize() );
		lowSlider.setAlignmentY(CENTER_ALIGNMENT);
		lowerBox.add(lowSlider);
		lowerBox.add(Box.createHorizontalStrut(10));
		cPanel.add(lowerBox);
		
		return cPanel;
	}
}
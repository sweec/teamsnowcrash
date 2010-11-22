package org.snowcrash.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TraitsPanel extends JPanel implements ChangeListener, ActionListener
{
	private static final int SLIDEMIN = 1;		// minimum value for each slider
	private static final int SLIDEMAX = 5;		// maximum value for each slider
	private static final int SLIDEINIT = 1;		// initial value for each slider
	private static final int imgWidth = 75;		// critter icon Width
	private static final int imgHeight = 75;	// critter icon Height
	
	private static int critterPoints = 20;		// variable stores allocation points
	
	// sliders for the Camouflage, Speed, Vision, Combat, Endurance and Age traits
	// radioButtons for specifying critter size
	JSlider upCamoSlider, lowCamoSlider, upSpeedSlider, lowSpeedSlider;
	JSlider upVisionSlider, lowVisionSlider, upCombatSlider, lowCombatSlider;
	JSlider upEndurSlider, lowEndurSlider, upAgeSlider, lowAgeSlider;
	JRadioButton smallRButton, medRButton, lrgRButton;
	JLabel remainingPoints;
	
	JTextField nameField; // indicates and changes the critter name
	
	// determinants that store values for the command factory
	private static String size, name;
	private static int points = critterPoints;
	private static int visionUpper = 1, visionLower = 1, speedUpper = 1, speedLower = 1;
	private static int camoUpper = 1, camoLower = 1, combatUpper = 1, combatLower = 1;
	private static int endurUpper = 1, endurLower = 1, ageUpper = 1, ageLower = 1;
	
	public JPanel TraitsPanel()
	{
		JPanel cPanelInner = new JPanel();
		cPanelInner.setLayout(new BoxLayout(cPanelInner, BoxLayout.Y_AXIS));
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel namePanel = new JPanel();
		namePanel = this.namePanel("predator", "Barney");
		
		JPanel sizePanel = new JPanel();
		sizePanel = this.sizePanel();
		cPanelInner.add(sizePanel);
		this.traitSeparator(cPanelInner);

		// create the Vision trait sliders
		upVisionSlider = this.traitsSlider();
		upVisionSlider.addChangeListener(this);
		lowVisionSlider = this.traitsSlider();
		lowVisionSlider.addChangeListener(this);
		JPanel visionPanel = new JPanel();
		visionPanel = this.sliderPanel(upVisionSlider, lowVisionSlider, "Vision");
		cPanelInner.add(visionPanel);
		this.traitSeparator(cPanelInner);
		
		// create the Speed trait sliders
		upSpeedSlider = this.traitsSlider();
		upSpeedSlider.addChangeListener(this);
		lowSpeedSlider = this.traitsSlider();
		lowSpeedSlider.addChangeListener(this);
		JPanel speedPanel = new JPanel();
		speedPanel = this.sliderPanel(upSpeedSlider, lowSpeedSlider, "Speed");
		cPanelInner.add(speedPanel);
		this.traitSeparator(cPanelInner);
		
		// create the Camouflage trait sliders
		upCamoSlider = this.traitsSlider();
		upCamoSlider.addChangeListener(this);
		lowCamoSlider = this.traitsSlider();
		lowCamoSlider.addChangeListener(this);
		JPanel camoflagePanel = new JPanel();
		camoflagePanel = this.sliderPanel(upCamoSlider, lowCamoSlider, "Camoflage");
		cPanelInner.add(camoflagePanel);
		this.traitSeparator(cPanelInner);
		
		// create the Combat trait sliders
		upCombatSlider = this.traitsSlider();
		upCombatSlider.addChangeListener(this);
		lowCombatSlider = this.traitsSlider();
		lowCombatSlider.addChangeListener(this);
		JPanel combatPanel = new JPanel();
		combatPanel = this.sliderPanel(upCombatSlider, lowCombatSlider, "Combat");
		cPanelInner.add(combatPanel);
		this.traitSeparator(cPanelInner);
		
		// create the Endurance trait sliders
		upEndurSlider = this.traitsSlider();
		upEndurSlider.addChangeListener(this);
		lowEndurSlider = this.traitsSlider();
		lowEndurSlider.addChangeListener(this);
		JPanel endurancePanel = new JPanel();
		endurancePanel = this.sliderPanel(upEndurSlider, lowEndurSlider, "Endurance");
		cPanelInner.add(endurancePanel);
		this.traitSeparator(cPanelInner);
		
		// create the age trait sliders
		upAgeSlider = this.traitsSlider();
		upAgeSlider.addChangeListener(this);
		lowAgeSlider = this.traitsSlider();
		lowAgeSlider.addChangeListener(this);
		JPanel agePanel = new JPanel();
		agePanel = this.sliderPanel(upAgeSlider, lowAgeSlider, "Age");
		cPanelInner.add(agePanel);
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));	
		cPanelInner.setPreferredSize(new Dimension(Short.MIN_VALUE, 900));
		
		JScrollPane cScroll = new JScrollPane(cPanelInner);
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// add the "Apply" and "Cancel" buttons to the buttonpanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		final JButton newButton = new JButton("Apply");
		final JButton delButton = new JButton("Cancel");
		buttonPanel.add(newButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(delButton);

		JPanel cPanelOuter = new JPanel();
		cPanelOuter.setLayout(new BoxLayout(cPanelOuter, BoxLayout.Y_AXIS));
		cPanelOuter.add(namePanel);
		cPanelOuter.add(cScroll);
		cPanelOuter.add(buttonPanel);
		
		return cPanelOuter;
	}
	
	private void traitSeparator(JPanel cPanel) // a custom separator between traits
	{
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		cPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
	}
	
	private ImageIcon resizeIcon(ImageIcon origIcon) // resizes the critter icon
	{
		Image origImage = origIcon.getImage();
		Image newImage = origImage.getScaledInstance(imgWidth, imgHeight, 
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImage);
		return newIcon;
	}
	
	private JPanel namePanel(String crittertype, String critterName) // show critter name
	{
		String imageFile; 
		if (crittertype == "plant")
		{
			imageFile = "images/plant.png";	
		}
		else if (crittertype == "prey")
		{
			imageFile = "images/prey-right.png";
		}
		else if (crittertype == "predator")
		{
			imageFile = "images/predator-right.png";
		}
		else
		{
			imageFile = "";
		}
		
		// loads and resizes the critter icon
		ImageIcon critterIcon = new ImageIcon (imageFile);
		critterIcon = this.resizeIcon(critterIcon);
		
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.X_AXIS));
		JLabel critterLabel = new JLabel(critterIcon);
		cPanel.add(critterLabel);
		cPanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		// displays and allows user to change the critter name
		Box namePointBox = new Box(BoxLayout.Y_AXIS);
		nameField = new JTextField(20);
		nameField.setActionCommand("jtextname");
		nameField.setMaximumSize( nameField.getPreferredSize() );
		nameField.setAlignmentX(CENTER_ALIGNMENT);
		nameField.setText(critterName);
		nameField.addActionListener(this);
		namePointBox.add(nameField);
		
		namePointBox.add(Box.createVerticalStrut(10));
		remainingPoints = new JLabel("Points Remaining: " + critterPoints);
		namePointBox.add(remainingPoints);
		
		cPanel.add(namePointBox);
		
		return cPanel;
	}

	private JPanel sizePanel() // add critter size-trait buttons
	{
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		
		JLabel sizeLabel = new JLabel("Size");
		sizeLabel.setAlignmentX(LEFT_ALIGNMENT);
		cPanel.add(sizeLabel);		
		
		// small, medium and large radiobuttons
		Box sizeBox = new Box(BoxLayout.X_AXIS);
		JRadioButton smallRButton = new JRadioButton("Small", true);
		JRadioButton medRButton = new JRadioButton("Medium", false);
		JRadioButton lrgRButton = new JRadioButton("Large", false);
		
		// group radiobuttons so only one can be selected at a time
		ButtonGroup sizeButtonGrp = new ButtonGroup();
		sizeButtonGrp.add(smallRButton);
		sizeButtonGrp.add(medRButton);
		sizeButtonGrp.add(lrgRButton);
		
		smallRButton.addActionListener(this);
		medRButton.addActionListener(this);
		lrgRButton.addActionListener(this);
		
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
	
	private JSlider traitsSlider() // slider characteristics
	{
		JSlider someSlider = new JSlider(JSlider.HORIZONTAL, SLIDEMIN, SLIDEMAX, SLIDEINIT);
		someSlider.setMajorTickSpacing(1);
		someSlider.setMinorTickSpacing(1);
		someSlider.setSnapToTicks(true);
		someSlider.setPaintLabels(true);
		someSlider.setPreferredSize(new Dimension(120, 40));
		//someSlider.setMaximumSize(new Dimension(120, 40));
		someSlider.setAlignmentY(CENTER_ALIGNMENT);
		return someSlider;
	}
	
	// this method adds lower and upper value critter sliders for some traits
	private JPanel sliderPanel(JSlider upSlider, JSlider lowSlider, String critterTrait)
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
		
		// upper value slider
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
		
		// lower value slider
		lowerBox.add(lowSlider);
		lowerBox.add(Box.createHorizontalStrut(10));
		cPanel.add(lowerBox);
		
		return cPanel;
	}
	
	public void stateChanged(ChangeEvent e) // slider event handler
	{
		// vision trait upper slider
		if (e.getSource().equals(this.upVisionSlider) 
				&& !upVisionSlider.getValueIsAdjusting())
	    {
	        // prevents upper slider from being less than lower slider
			if (upVisionSlider.getValue() < lowVisionSlider.getValue())
	        {
	        	upVisionSlider.setValue(lowVisionSlider.getValue());
	        	points = points - (upVisionSlider.getValue() - visionUpper);
	        	visionUpper = upVisionSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	        // prevents slider from using more than remaining points
			else if (upVisionSlider.getValue() > visionUpper 
	        		&& upVisionSlider.getValue() - visionUpper > points)
	        {
	        	upVisionSlider.setValue(visionUpper + points);
	        	points = points - (upVisionSlider.getValue() - visionUpper);
	        	visionUpper = upVisionSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (upVisionSlider.getValue() - visionUpper);
	        	visionUpper = upVisionSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// vision trait lower slider
		if ( e.getSource().equals( this.lowVisionSlider ) 
				&& !lowVisionSlider.getValueIsAdjusting() )
	    {
			// prevents upper slider from being less than lower slider
			if (upVisionSlider.getValue() < lowVisionSlider.getValue())
	        {
	        	lowVisionSlider.setValue(upVisionSlider.getValue());
	        	points = points - (lowVisionSlider.getValue() - visionLower);
	        	visionLower = lowVisionSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
			// prevents slider from using more than remaining points
			else if (lowVisionSlider.getValue() > visionLower 
	        		&& lowVisionSlider.getValue() - visionLower > points)
	        {
	        	lowVisionSlider.setValue(visionLower + points);
	        	points = points - (lowVisionSlider.getValue() - visionLower);
	        	visionLower = lowVisionSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (lowVisionSlider.getValue() - visionLower);
	        	visionLower = lowVisionSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// speed trait upper slider
		if (e.getSource().equals(this.upSpeedSlider) 
				&& !upSpeedSlider.getValueIsAdjusting())
	    {
	        // prevents upper slider from being less than lower slider
			if (upSpeedSlider.getValue() < lowSpeedSlider.getValue())
	        {
	        	upSpeedSlider.setValue(lowSpeedSlider.getValue());
	        	points = points - (upSpeedSlider.getValue() - speedUpper);
	        	speedUpper = upSpeedSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	        // prevents slider from using more than remaining points
			else if (upSpeedSlider.getValue() > speedUpper 
	        		&& upSpeedSlider.getValue() - speedUpper > points)
	        {
	        	upSpeedSlider.setValue(speedUpper + points);
	        	points = points - (upSpeedSlider.getValue() - speedUpper);
	        	speedUpper = upSpeedSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (upSpeedSlider.getValue() - speedUpper);
	        	speedUpper = upSpeedSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// speed trait lower slider
		if ( e.getSource().equals( this.lowSpeedSlider ) 
				&& !lowSpeedSlider.getValueIsAdjusting() )
	    {
			// prevents upper slider from being less than lower slider
			if (upSpeedSlider.getValue() < lowSpeedSlider.getValue())
	        {
	        	lowSpeedSlider.setValue(upSpeedSlider.getValue());
	        	points = points - (lowSpeedSlider.getValue() - speedLower);
	        	speedLower = lowSpeedSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
			// prevents slider from using more than remaining points
			else if (lowSpeedSlider.getValue() > speedLower 
	        		&& lowSpeedSlider.getValue() - speedLower > points)
	        {
	        	lowSpeedSlider.setValue(speedLower + points);
	        	points = points - (lowSpeedSlider.getValue() - speedLower);
	        	speedLower = lowSpeedSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (lowSpeedSlider.getValue() - speedLower);
	        	speedLower = lowSpeedSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// camouflage trait upper slider
		if (e.getSource().equals(this.upCamoSlider) 
				&& !upCamoSlider.getValueIsAdjusting())
	    {
	        // prevents upper slider from being less than lower slider
			if (upCamoSlider.getValue() < lowCamoSlider.getValue())
	        {
	        	upCamoSlider.setValue(lowCamoSlider.getValue());
	        	points = points - (upCamoSlider.getValue() - camoUpper);
	        	camoUpper = upCamoSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	        // prevents slider from using more than remaining points
			else if (upCamoSlider.getValue() > camoUpper 
	        		&& upCamoSlider.getValue() - camoUpper > points)
	        {
	        	upCamoSlider.setValue(camoUpper + points);
	        	points = points - (upCamoSlider.getValue() - camoUpper);
	        	camoUpper = upCamoSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (upCamoSlider.getValue() - camoUpper);
	        	camoUpper = upCamoSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// camouflage trait lower slider
		if ( e.getSource().equals( this.lowCamoSlider ) 
				&& !lowCamoSlider.getValueIsAdjusting() )
	    {
			// prevents upper slider from being less than lower slider
			if (upCamoSlider.getValue() < lowCamoSlider.getValue())
	        {
	        	lowCamoSlider.setValue(upCamoSlider.getValue());
	        	points = points - (lowCamoSlider.getValue() - camoLower);
	        	camoLower = lowCamoSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
			// prevents slider from using more than remaining points
			else if (lowCamoSlider.getValue() > camoLower 
	        		&& lowCamoSlider.getValue() - camoLower > points)
	        {
	        	lowCamoSlider.setValue(camoLower + points);
	        	points = points - (lowCamoSlider.getValue() - camoLower);
	        	camoLower = lowCamoSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (lowCamoSlider.getValue() - camoLower);
	        	camoLower = lowCamoSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// combat trait upper slider
		if (e.getSource().equals(this.upCombatSlider) 
				&& !upCombatSlider.getValueIsAdjusting())
	    {
	        // prevents upper slider from being less than lower slider
			if (upCombatSlider.getValue() < lowCombatSlider.getValue())
	        {
	        	upCombatSlider.setValue(lowCombatSlider.getValue());
	        	points = points - (upCombatSlider.getValue() - combatUpper);
	        	combatUpper = upCombatSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	        // prevents slider from using more than remaining points
			else if (upCombatSlider.getValue() > combatUpper 
	        		&& upCombatSlider.getValue() - combatUpper > points)
	        {
	        	upCombatSlider.setValue(combatUpper + points);
	        	points = points - (upCombatSlider.getValue() - combatUpper);
	        	combatUpper = upCombatSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (upCombatSlider.getValue() - combatUpper);
	        	combatUpper = upCombatSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// combat trait lower slider
		if ( e.getSource().equals( this.lowCombatSlider ) 
				&& !lowCombatSlider.getValueIsAdjusting() )
	    {
			// prevents upper slider from being less than lower slider
			if (upCombatSlider.getValue() < lowCombatSlider.getValue())
	        {
	        	lowCombatSlider.setValue(upCombatSlider.getValue());
	        	points = points - (lowCombatSlider.getValue() - combatLower);
	        	combatLower = lowCombatSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
			// prevents slider from using more than remaining points
			else if (lowCombatSlider.getValue() > combatLower 
	        		&& lowCombatSlider.getValue() - combatLower > points)
	        {
	        	lowCombatSlider.setValue(combatLower + points);
	        	points = points - (lowCombatSlider.getValue() - combatLower);
	        	combatLower = lowCombatSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (lowCombatSlider.getValue() - combatLower);
	        	combatLower = lowCombatSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// endurance trait upper slider
		if (e.getSource().equals(this.upEndurSlider) 
				&& !upEndurSlider.getValueIsAdjusting())
	    {
	        // prevents upper slider from being less than lower slider
			if (upEndurSlider.getValue() < lowEndurSlider.getValue())
	        {
	        	upEndurSlider.setValue(lowEndurSlider.getValue());
	        	points = points - (upEndurSlider.getValue() - endurUpper);
	        	endurUpper = upEndurSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	        // prevents slider from using more than remaining points
			else if (upEndurSlider.getValue() > endurUpper 
	        		&& upEndurSlider.getValue() - endurUpper > points)
	        {
	        	upEndurSlider.setValue(endurUpper + points);
	        	points = points - (upEndurSlider.getValue() - endurUpper);
	        	endurUpper = upEndurSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (upEndurSlider.getValue() - endurUpper);
	        	endurUpper = upEndurSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// endurance trait lower slider
		if ( e.getSource().equals( this.lowEndurSlider ) 
				&& !lowEndurSlider.getValueIsAdjusting() )
	    {
			// prevents upper slider from being less than lower slider
			if (upEndurSlider.getValue() < lowEndurSlider.getValue())
	        {
	        	lowEndurSlider.setValue(upEndurSlider.getValue());
	        	points = points - (lowEndurSlider.getValue() - endurLower);
	        	endurLower = lowEndurSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
			// prevents slider from using more than remaining points
			else if (lowEndurSlider.getValue() > endurLower 
	        		&& lowEndurSlider.getValue() - endurLower > points)
	        {
	        	lowEndurSlider.setValue(endurLower + points);
	        	points = points - (lowEndurSlider.getValue() - endurLower);
	        	endurLower = lowEndurSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (lowEndurSlider.getValue() - endurLower);
	        	endurLower = lowEndurSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// age trait upper slider
		if (e.getSource().equals(this.upAgeSlider) 
				&& !upAgeSlider.getValueIsAdjusting())
	    {
	        // prevents upper slider from being less than lower slider
			if (upAgeSlider.getValue() < lowAgeSlider.getValue())
	        {
	        	upAgeSlider.setValue(lowAgeSlider.getValue());
	        	points = points - (upAgeSlider.getValue() - ageUpper);
	        	ageUpper = upAgeSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	        // prevents slider from using more than remaining points
			else if (upAgeSlider.getValue() > ageUpper 
	        		&& upAgeSlider.getValue() - ageUpper > points)
	        {
	        	upAgeSlider.setValue(ageUpper + points);
	        	points = points - (upAgeSlider.getValue() - ageUpper);
	        	ageUpper = upAgeSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (upAgeSlider.getValue() - ageUpper);
	        	ageUpper = upAgeSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		// age trait lower slider
		if ( e.getSource().equals( this.lowAgeSlider ) 
				&& !lowAgeSlider.getValueIsAdjusting() )
	    {
			// prevents upper slider from being less than lower slider
			if (upAgeSlider.getValue() < lowAgeSlider.getValue())
	        {
	        	lowAgeSlider.setValue(upAgeSlider.getValue());
	        	points = points - (lowAgeSlider.getValue() - ageLower);
	        	ageLower = lowAgeSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
			// prevents slider from using more than remaining points
			else if (lowAgeSlider.getValue() > ageLower 
	        		&& lowAgeSlider.getValue() - ageLower > points)
	        {
	        	lowAgeSlider.setValue(ageLower + points);
	        	points = points - (lowAgeSlider.getValue() - ageLower);
	        	ageLower = lowAgeSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);	
	        }
	        else
	        {
	        	points = points - (lowAgeSlider.getValue() - ageLower);
	        	ageLower = lowAgeSlider.getValue();
	        	remainingPoints.setText("Points Remaining: " + points);
	        }
	    }
		
		else
		{
			// do something
		}
	}
	
	public void actionPerformed(ActionEvent e) // event handler for other objects
	{
		if (e.getActionCommand().equals("Small")) // for critter size
		{
			size = "small";
		}
		else if (e.getActionCommand().equals("Medium"))
		{
			size = "medium";
		}
		else if (e.getActionCommand().equals("large"))
		{
			size = "large";
		}
		else if (e.getActionCommand().equals("jtextname")) // for critter name field
		{
			// do something
		}
		else
		{
			
		}
	}
}
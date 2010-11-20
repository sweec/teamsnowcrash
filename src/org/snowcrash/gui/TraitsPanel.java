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
	
	JTextField nameField; // indicates and changes the critter name
	
	// determinants that store values for the command factory
	private static String size, name;
	private static int points = critterPoints;
	private static int visionUpper, visionLower, speedUpper, speedLower;
	private static int camoUpper, camoLower, combatUpper, combatLower;
	private static int endurUpper, endurLower, ageUpper, ageLower;
	
	public JPanel TraitsPanel()
	{
		JPanel cPanelInner = new JPanel();
		cPanelInner.setLayout(new BoxLayout(cPanelInner, BoxLayout.Y_AXIS));
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel namePanel = new JPanel();
		namePanel = this.namePanel("predator", "Barney");
		cPanelInner.add(namePanel);
		this.traitSeparator(cPanelInner);
		
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
		
		// displays and allows user to change the critter name
		Box namePointBox = new Box(BoxLayout.Y_AXIS);
		nameField = new JTextField(20);
		nameField.setActionCommand("jtext");
		nameField.setMaximumSize( nameField.getPreferredSize() );
		nameField.setAlignmentX(CENTER_ALIGNMENT);
		nameField.setText(critterName);
		nameField.addActionListener(this);
		namePointBox.add(nameField);
		
		namePointBox.add(Box.createVerticalStrut(10));
		JLabel remainingPoints = new JLabel("Points Remaining: " + critterPoints);
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
		someSlider.setMaximumSize( someSlider.getPreferredSize() );
		someSlider.setAlignmentY(CENTER_ALIGNMENT);
		return someSlider;
	}
	
	private JPanel sliderPanel(JSlider upSlider, JSlider lowSlider, String critterTrait)
	{	// this method adds lower and upper value critter sliders for some traits
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
		if (upVisionSlider.getValueIsAdjusting()) // for vision trait
		{
			// do something
		}
		else if (lowVisionSlider.getValueIsAdjusting())
		{
			// do something
		}
		else if (upSpeedSlider.getValueIsAdjusting()) // for speed trait
		{
			// do something
		}
		else if (lowSpeedSlider.getValueIsAdjusting())
		{
			// do something
		}
		else if (upCamoSlider.getValueIsAdjusting()) // for camouflage trait
		{
			// do something
		}
		else if (lowCamoSlider.getValueIsAdjusting())
		{
			// do something
		}
		else if (upCombatSlider.getValueIsAdjusting()) // for combat trait
		{
			// do something
		}
		else if (lowCombatSlider.getValueIsAdjusting())
		{
			// do something
		}
		else if (upEndurSlider.getValueIsAdjusting()) // for endurance trait
		{
			// do something
		}
		else if (lowEndurSlider.getValueIsAdjusting())
		{
			// do something
		}
		else if (upAgeSlider.getValueIsAdjusting()) // for age trait
		{
			// do something
		}
		else if (lowAgeSlider.getValueIsAdjusting())
		{
			// do something
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
		else if (e.getActionCommand().equals("jtext")) // for critter name field
		{
			// do something
		}
		else
		{
			
		}
	}
}
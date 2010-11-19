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
	
	public JPanel TraitsPanel()
	{
		JPanel cPanelInner = new JPanel();
		cPanelInner.setLayout(new BoxLayout(cPanelInner, BoxLayout.Y_AXIS));
		
		cPanelInner.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel namePanel = new JPanel();
		namePanel = this.namePanel("predator", "Dopey");
		cPanelInner.add(namePanel);
		this.traitSeparator(cPanelInner);
		
		JPanel sizePanel = new JPanel();
		sizePanel = this.sizePanel();
		cPanelInner.add(sizePanel);
		this.traitSeparator(cPanelInner);

		// create the Vision trait sliders
		JPanel visionPanel = new JPanel();
		visionPanel = this.sliderPairPanel(upVisionSlider, lowVisionSlider, "Vision");
		cPanelInner.add(visionPanel);
		this.traitSeparator(cPanelInner);
		
		// create the Speed trait sliders
		JPanel speedPanel = new JPanel();
		speedPanel = this.sliderPairPanel(upSpeedSlider, lowSpeedSlider, "Speed");
		cPanelInner.add(speedPanel);
		this.traitSeparator(cPanelInner);
		
		// create the Camouflage trait sliders
		JPanel camoflagePanel = new JPanel();
		camoflagePanel = this.sliderPairPanel(upCamoSlider, lowCamoSlider, "Camoflage");
		cPanelInner.add(camoflagePanel);
		this.traitSeparator(cPanelInner);
		
		// create the Combat trait sliders
		JPanel combatPanel = new JPanel();
		combatPanel = this.sliderPairPanel(upCombatSlider, lowCombatSlider, "Combat");
		cPanelInner.add(combatPanel);
		this.traitSeparator(cPanelInner);
		
		// create the Endurance trait sliders
		JPanel endurancePanel = new JPanel();
		endurancePanel = this.sliderPairPanel(upEndurSlider, lowEndurSlider, "Endurance");
		cPanelInner.add(endurancePanel);
		this.traitSeparator(cPanelInner);
		
		// create the age trait sliders
		JPanel agePanel = new JPanel();
		agePanel = this.sliderPairPanel(upAgeSlider, lowAgeSlider, "Age");
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
	
	private JPanel sliderPairPanel(JSlider upSlider, JSlider lowSlider, String critterTrait)
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
		upSlider = new JSlider(JSlider.HORIZONTAL, SLIDEMIN, SLIDEMAX, SLIDEINIT);
		upSlider.addChangeListener(this);
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
		
		// lower value slider
		lowSlider = new JSlider(JSlider.HORIZONTAL, SLIDEMIN, SLIDEMAX, SLIDEINIT);
		lowSlider.addChangeListener(this);
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
	
	public void stateChanged(ChangeEvent e)
	{
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Small"))
		{

		}
	}
}
package org.snowcrash.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.snowcrash.critter.data.CritterPrototype;


@SuppressWarnings("serial")
public class NewCritterTemplateWindow extends JDialog implements ActionListener
{
	private static final CritterPrototype[] PROTOTYPES = CritterPrototype.values();
	
	private String name = null;
	private CritterPrototype prototype = null;
	
	private boolean finished = false;
	
	private JButton btnOk = new JButton( "OK" );
	private JButton btnCancel = new JButton( "Cancel" );
	private JTextField txtName = new JTextField();
	private JComboBox cboPrototype = new JComboBox( PROTOTYPES );
	
	public NewCritterTemplateWindow()
	{
		super();
		setTitle( "New Template" );
		setSize( 300, 200 );
		setResizable( false );
		setModal( true );
		
		btnOk.addActionListener( this );
		btnCancel.addActionListener( this );
		
		Box structureBox = new Box( BoxLayout.Y_AXIS );
		Box contentBox = new Box( BoxLayout.Y_AXIS );
		Box horizontalMarginBox = Box.createHorizontalBox();
		Box verticalMarginBox = Box.createVerticalBox();
		structureBox.add( horizontalMarginBox );
		horizontalMarginBox.add( Box.createHorizontalStrut( 20 ) );
		horizontalMarginBox.add( verticalMarginBox );
		horizontalMarginBox.add( Box.createHorizontalStrut( 20 ) );
		verticalMarginBox.add( Box.createVerticalStrut( 20 ) );
		verticalMarginBox.add( contentBox );
		verticalMarginBox.add( Box.createVerticalStrut( 20 ) );
		
		contentBox.setAlignmentX( LEFT_ALIGNMENT );
		
		JLabel lblName = new JLabel( "Name: " );
		JLabel lblPrototype = new JLabel( "Prototype: " );
		
		lblName.setLabelFor( txtName );
		Box boxName = new Box( BoxLayout.X_AXIS );
		boxName.add( lblName );
		boxName.add( txtName );
		contentBox.add( boxName );
		
		contentBox.add( Box.createVerticalStrut( 20 ) );
		
		lblPrototype.setLabelFor( cboPrototype );
		Box boxPrototype = new Box( BoxLayout.X_AXIS );
		boxPrototype.add( lblPrototype );
		boxPrototype.add( cboPrototype );
		contentBox.add( boxPrototype );
		
		contentBox.add( Box.createVerticalStrut( 20 ) );
		
		Box boxButtons = new Box( BoxLayout.X_AXIS );
		boxButtons.add( btnOk );
		boxButtons.add( Box.createHorizontalStrut( 20 ) );
		boxButtons.add( btnCancel );
		contentBox.add( boxButtons );
		
		add( structureBox );
	}
	
	public String getTemplateName()
	{
		return name;
	}
	
	public CritterPrototype getTemplatePrototype()
	{
		return prototype;
	}
	
	public boolean isFinished()
	{
		return finished;
	}

	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == btnOk )
		{
			this.name = txtName.getText();
			this.prototype = (CritterPrototype) cboPrototype.getSelectedItem();
			
			this.finished = true;
			this.setVisible( false );
		}
		else if ( e.getSource() == btnCancel )
		{
			this.setVisible( false );
		}
	}
}

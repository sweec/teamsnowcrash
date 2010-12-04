package org.snowcrash.gui.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * 
 * @author Mike
 * 
 * This widget allows the user to enter a number into a text field with the given label.
 * 
 * Validation is performed by the widget, but the client must first set the max critter 
 * count.  If the user's input is not valid, the old value is restored in the text field 
 * and the user is presented an error message.
 * 
 * The user must double-click the widget before he may enter text.  This ensures that 
 * the user does not accidentally alter data.  The user's data will not be saved until 
 * he hits Enter, at which point the widget will not be editable until the user double-
 * clicks again.
 * 
 * This widget does not actively maintain any information about critters or critter 
 * templates other than the number that will be created in the simulation world.  The 
 * client is fully responsible for maintaining critter template information and setting 
 * the critter template name of this widget.
 * 
 * 26 Oct - Created.
 *
 */
@SuppressWarnings("serial")
public class CritterTemplateWidget extends Box implements ActionListener, 
		MouseListener, FocusListener
{
	// -- Keeps track of the current critter count across all instances of this widget.
	private static int critterCount = 0;
	
	// -- Keeps track of the maximum critter count.
	private static int maxCritterCount = 0;
	
	private static boolean validate = true;
	
	/**
	 * 
	 * Updates the maximum number of critters that can inhabit the world.
	 * 
	 * @param maxCritterCount the maximum number of critters
	 * 
	 */
	public static void setMaxCritterCount( int maxCritterCount )
	{
		CritterTemplateWidget.maxCritterCount = maxCritterCount;
	}
	
	public static void setValidate( boolean validate )
	{
		CritterTemplateWidget.validate = validate;
	}
	
	
	// -- The JTextField component.
	private JTextField txtNumCritters = new JTextField();
	
	// -- The JLabel component.
	private JLabel lblCritterTemplateName = new JLabel();
	
	// -- The number of critters that will be added to the game world.
	private int numCritters = 0;
	
	// -- The observers of double-click events.
	private List<MouseListener> mouseListeners = new ArrayList<MouseListener>();
	
	
	/**
	 * 
	 * Constructor.
	 * 
	 * @param critterTemplateName the name of the critter template
	 * 
	 */
	public CritterTemplateWidget( String critterTemplateName )
	{
		super( BoxLayout.X_AXIS );
		setAlignmentX( LEFT_ALIGNMENT );
		
		/*
		 * Using a Box so I don't have to mess with layouts.
		 * 
		 * The horizontal struct is just a separator.
		 */
		Box box = new Box( BoxLayout.LINE_AXIS );
		box.add( txtNumCritters );
		box.add( Box.createHorizontalStrut( 10 ) );
		box.add( lblCritterTemplateName );
		add( box );
		
		/*
		 * Add this class as a listener for events to the text field.
		 */
		txtNumCritters.addActionListener( this );
		txtNumCritters.addMouseListener( this );
		
		/*
		 * Set the width of the text field to two columns.
		 */
		txtNumCritters.setColumns( 2 );
		
		/*
		 * Add this class as a listener for events to the label.  These events are 
		 * forwarded on to this widget's mouse listener.
		 */
		lblCritterTemplateName.addMouseListener( this );
		
		/*
		 * Make it so the text field cannot be edited.
		 */
		setDisabled();
		
		/*
		 * Set starting values.
		 */
		lblCritterTemplateName.setText( critterTemplateName );
		txtNumCritters.setText( Integer.toString( numCritters ) );
	}
	
	/**
	 * 
	 * Sets the critter template name that is displayed in the JLabel.
	 * 
	 * @param critterTemplateName the critter template name
	 * 
	 */
	public void setCritterTemplateName( String critterTemplateName )
	{
		lblCritterTemplateName.setText( critterTemplateName );
	}
	
	/**
	 * 
	 * Returns the number of critters the user specified in this widget.
	 * 
	 * @return the number of critters
	 * 
	 */
	public int getNumCritters()
	{
		return numCritters;
	}
	
	public String getCritterTemplateName()
	{
		return lblCritterTemplateName.getText();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Component#addMouseListener(java.awt.event.MouseListener)
	 */
	@Override
	public void addMouseListener( MouseListener listener )
	{
		mouseListeners.add( listener );
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Component#removeMouseListener(java.awt.event.MouseListener)
	 */
	@Override
	public void removeMouseListener( MouseListener listener )
	{
		mouseListeners.remove( listener );
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed( ActionEvent e )
	{
		// -- If the event is coming from this widget's JTextField...
		if ( e.getSource() == txtNumCritters )
		{
			commitChanges();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked( MouseEvent e )
	{
		// -- If the event is coming from the widget's JTextField...
		if ( e.getSource() == txtNumCritters && e.getClickCount() == 2 )
		{
			// -- Allow the user to edit the data.
			setEnabled();
			
			// -- Re-dispatch the event.
			for ( MouseListener mouseListener : mouseListeners )
			{
				e.setSource( this );
				mouseListener.mouseClicked( e );
			}
		}
		else if ( e.getSource() != txtNumCritters )
		{
			// -- Re-dispatch the event.
			for ( MouseListener mouseListener : mouseListeners )
			{
				e.setSource( this );
				mouseListener.mouseClicked( e );
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered( MouseEvent e )
	{
		// -- Re-dispatch the event.
		for ( MouseListener mouseListener : mouseListeners )
		{
			e.setSource( this );
			mouseListener.mouseEntered( e );
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited( MouseEvent e )
	{
		// -- Re-dispatch the event.
		for ( MouseListener mouseListener : mouseListeners )
		{
			e.setSource( this );
			mouseListener.mouseExited( e );
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed( MouseEvent e )
	{
		// -- Re-dispatch the event.
		for ( MouseListener mouseListener : mouseListeners )
		{
			e.setSource( this );
			mouseListener.mousePressed( e );
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased( MouseEvent e )
	{
		// -- Re-dispatch the event.
		for ( MouseListener mouseListener : mouseListeners )
		{
			e.setSource( this );
			mouseListener.mouseReleased( e );
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained( FocusEvent e ) {}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost( FocusEvent e )
	{
		if ( e.getSource() == txtNumCritters )
		{
			commitChanges();
		}
	}
	
	private void commitChanges()
	{
		// -- Perform validation on the user input.
		if ( !isValid( txtNumCritters.getText() ) )
		{
			JOptionPane.showMessageDialog( this, "Invalid input.", "Invalid Input", JOptionPane.ERROR_MESSAGE );
			
			// -- Reset the text back to what it was before the user changed it.
			txtNumCritters.setText( Integer.toString( numCritters ) );
			
			// -- Select all text so the user can easily change it again.
			txtNumCritters.selectAll();
		}
		else
		{
			// -- Adjust the critter count and persist the user-entered data.
			critterCount -= numCritters;
			numCritters = Integer.parseInt( txtNumCritters.getText() );
			critterCount += numCritters;
			
			// -- Prevent the user from editing the text field.
			setDisabled();
		}
	}
	
	/*
	 * Determines whether the given object is valid user input.
	 */
	private boolean isValid( Object o )
	{
		boolean valid = false;
		
		if ( o instanceof Integer )
		{
			Integer i = (Integer) o;
			
			if ( validate && i >= 0 && ( critterCount - numCritters + i ) <= maxCritterCount )
			{
				valid = true;
			}
		}
		else if ( o instanceof String )
		{
			try
			{
				Integer i = Integer.parseInt( o.toString() );
				
				valid = isValid( i );
			}
			catch ( NumberFormatException e )
			{
				valid = false;
			}
		}
		
		return valid;
	}
	
	/*
	 * Allows the user to edit the JTextField.
	 */
	private void setEnabled()
	{
		txtNumCritters.setFocusable( true );
		txtNumCritters.setEditable( true );
		txtNumCritters.grabFocus();
	}
	
	/*
	 * Disallows the user from editing the JTextField.
	 */
	private void setDisabled()
	{
		txtNumCritters.setFocusable( false );
		txtNumCritters.setEditable( false );
	}
}

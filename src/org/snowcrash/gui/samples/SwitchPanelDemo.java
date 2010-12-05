package org.snowcrash.gui.samples;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;


/*
 * Don't worry about the SuppressWarnings annotation.  I just added it so Eclipse 
 * wouldn't underline the class name in yellow.
 */
@SuppressWarnings("serial")
class SwitchPanelDemo extends JFrame
{
	public static void main( String ... args )
	{
		new SwitchPanelDemo();
	}
	
	
	/*
	 * Just a lazy way of representing a finite number of states.  Still better than 
	 * using ints or Strings though!
	 */
	enum State { RED, GREEN, BLUE };
	
	/*
	 * Some actions.  These are represented as both buttons and menu items.  You only
	 * have to change the properties on the Action to change them on all representations 
	 * of that Action.  Check out the setState() method below.
	 */
	Action switchToRedPanelAction = new AbstractAction()
	{
		public void actionPerformed( ActionEvent e )
		{
			setState( State.RED );
		}
	};
	
	Action switchToGreenPanelAction = new AbstractAction()
	{
		public void actionPerformed( ActionEvent e )
		{
			setState( State.GREEN );
		}
	};
	
	Action switchToBluePanelAction = new AbstractAction()
	{
		public void actionPerformed( ActionEvent e )
		{
			setState( State.BLUE );
		}
	};
	
	/*
	 * Constructor.
	 */
	SwitchPanelDemo()
	{
		setSize( 800,600 );
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		
		init();
		
		setVisible( true );
	}
	
	private void init()
	{
		/*
		 * Note that setLayout() is actually a proxy for getContentPane().setLayout().
		 * This means that the parent container of the panels is NOT the JFrame, but 
		 * rather the JFrame's contentPane.  Note that when I tell the layout which 
		 * panel to show, I need to pass it getContentPane(), rather than "this".
		 */
		setLayout( new CardLayout() );
		
		JPanel redPanel = new JPanel();
		redPanel.setBackground( Color.red );
		add( redPanel, "RED" );
		
		JPanel greenPanel = new JPanel();
		greenPanel.setBackground( Color.green );
		add( greenPanel, "GREEN" );
		
		JPanel bluePanel = new JPanel();
		bluePanel.setBackground( Color.blue );
		add( bluePanel, "BLUE" );
		
		/*
		 * Using boxes because I hate messing with layouts.
		 * 
		 * Creates new JButtons defined by their associated actions!
		 */
		Box redBox = new Box( BoxLayout.LINE_AXIS );
		redBox.add( new JButton( switchToRedPanelAction ) );
		redBox.add( new JButton( switchToGreenPanelAction ) );
		redBox.add( new JButton( switchToBluePanelAction ) );
		redPanel.add( redBox );
		
		Box greenBox = new Box( BoxLayout.LINE_AXIS );
		greenBox.add( new JButton( switchToRedPanelAction ) );
		greenBox.add( new JButton( switchToGreenPanelAction ) );
		greenBox.add( new JButton( switchToBluePanelAction ) );
		greenPanel.add( greenBox );
		
		Box blueBox = new Box( BoxLayout.LINE_AXIS );
		blueBox.add( new JButton( switchToRedPanelAction ) );
		blueBox.add( new JButton( switchToGreenPanelAction ) );
		blueBox.add( new JButton( switchToBluePanelAction ) );
		bluePanel.add( blueBox );
		
		/*
		 * Create menus.
		 * 
		 * Creates new JMenuItems defined by their associated actions!
		 */
		setJMenuBar( new JMenuBar() );
		
		JMenu mnuChangePanel = new JMenu( "Change Panel" );
		getJMenuBar().add( mnuChangePanel );
		
		mnuChangePanel.add( switchToRedPanelAction );
		mnuChangePanel.add( switchToGreenPanelAction );
		mnuChangePanel.add( switchToBluePanelAction );
		
		/*
		 * Start with the red panel.
		 */
		setState( State.RED );
	}
	
	/*
	 * This should really be handled differently; I'm just doing it this way 
	 * for the purpose of this example.  A better way of doing it would be 
	 * having setRedState(), setBlueState(), and setGreenState() methods.
	 * 
	 * An even better way of doing it would be defining an interface State 
	 * with a method enterState(), and creating three inner classes that 
	 * implement that method appropriately.  If you need me to create a demo 
	 * for that, let me know and I'll create SwitchPanelDemo2 that uses this 
	 * technique.
	 */
	private void setState( State newState )
	{
		/*
		 * We have to cast to a CardLayout because it uses methods that are 
		 * not exposed by the LayoutManager interface.
		 */
		CardLayout layout = (CardLayout) getContentPane().getLayout();
		
		switch ( newState )
		{
		/*
		 * Manages the "red" state.
		 */
		case RED:
			/*
			 * Instructs the CardLayout to show the "RED" panel.
			 */
			layout.show( getContentPane(), "RED" );
			
			switchToRedPanelAction.setEnabled( false );
			switchToGreenPanelAction.setEnabled( true );
			switchToBluePanelAction.setEnabled( true );
			
			switchToRedPanelAction.putValue( Action.NAME, "RED" );
			switchToGreenPanelAction.putValue( Action.NAME, "GREEN" );
			switchToBluePanelAction.putValue( Action.NAME, "BLUE" );
			
			break;
			
		/*
		 * Manages the "green" state.
		 */
		case GREEN:
			layout.show( getContentPane(), "GREEN" );
			
			/*
			 * Toggle the enabled property on each of the Actions.  Notice that this
			 * changes BOTH the JButton and the JMenuItem.  This makes having multiple
			 * widgets that share state and functionality a LOT easier to manage.
			 */
			switchToRedPanelAction.setEnabled( true );
			switchToGreenPanelAction.setEnabled( false );
			switchToBluePanelAction.setEnabled( true );
			
			switchToRedPanelAction.putValue( Action.NAME, "ROJO" );
			switchToGreenPanelAction.putValue( Action.NAME, "VERDE" );
			switchToBluePanelAction.putValue( Action.NAME, "AZUL" );
			
			break;
			
		/*
		 * Manages the "blue" state.
		 */
		case BLUE:
			layout.show( getContentPane(), "BLUE" );
			
			switchToRedPanelAction.setEnabled( true );
			switchToGreenPanelAction.setEnabled( true );
			switchToBluePanelAction.setEnabled( false );
			
			/*
			 * As above, notice that this changes BOTH the JButton and the JMenuItem.
			 * If you don't think this is pretty cool already, play around with adding 
			 * icons to the Actions.  Google "java 6 Action" (without the quotes) for 
			 * details on what constants to use for putValue().
			 */
			switchToRedPanelAction.putValue( Action.NAME, "ROSSO" );
			switchToGreenPanelAction.putValue( Action.NAME, "VERDE" );
			switchToBluePanelAction.putValue( Action.NAME, "AZZURRO" );
			
			break;
		}
	}
}

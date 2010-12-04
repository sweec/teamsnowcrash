package org.snowcrash.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.snowcrash.commands.Command;
import org.snowcrash.commands.CommandFactory;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.gui.widgets.CritterTemplateWidget;
import org.snowcrash.gui.widgets.MultiPanelList;
import org.snowcrash.gui.widgets.SelectableComponent;
import org.snowcrash.utilities.Callback;
import org.snowcrash.utilities.SelectionEvent;
import org.snowcrash.utilities.SelectionListener;


@SuppressWarnings("serial")
public class CritterPanel extends JPanel implements SelectionListener
{
	private static final String PLANTS_TITLE = "Plants";
	private static final String PREY_TITLE = "Prey";
	private static final String PREDATORS_TITLE = "Predators";
	
	private static final String NEW_BUTTON_CAPTION = "New";
	private static final String DEL_BUTTON_CAPTION = "Delete";
	
	private static final Dimension SEPARATOR_DIM = new Dimension( 5, 0 );
	
	private MultiPanelList list = new MultiPanelList( PLANTS_TITLE, PREY_TITLE, PREDATORS_TITLE );
	
	private List<SelectionListener> selectionListeners = new ArrayList<SelectionListener>();
	
	private SelectableComponent<CritterTemplateWidget> currentSelection = null;
	
	private ConfigScreen parentRef = null;
	
	public CritterPanel()
	{
		Box buttonPanel = createButtonPanel();
		
		JScrollPane listScrollPane = new JScrollPane( list );
		listScrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		listScrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		
		setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
		add( listScrollPane );
		add( buttonPanel );
		
		list.addSelectionListener( this );
	}
	
	public void addSelectionListener( SelectionListener listener )
	{
		selectionListeners.add( listener );
	}
	
	public void removeSelectionListener( SelectionListener listener )
	{
		selectionListeners.remove( listener );
	}
	
	public void setParent( ConfigScreen parent )
	{
		parentRef = parent;
	}
	
	public String getSelectedCritterName()
	{
		return list.getSelected().toString();
	}
	
	public void clearSelection()
	{
		list.clearSelections();
	}
	
	public void addData( Collection<CritterTemplate> data )
	{
		for ( CritterTemplate template : data )
		{
			CritterPrototype prototype = template.getPrototype();
			String name = template.getName();
			int count = template.getStartingInstancesCount();
			String listTitle = null;
			
			switch ( prototype )
			{
			case PLANT:
				listTitle = PLANTS_TITLE;
				break;
				
			case PREDATOR:
				listTitle = PREDATORS_TITLE;
				break;
				
			case PREY:
				listTitle = PREY_TITLE;
				break;
				
			default:
			}
			
			list.addItemToList( name, count, listTitle );
		}
	}
	
	@SuppressWarnings("unchecked")
	public void selectionOccurred( SelectionEvent e )
	{
		if ( e.getSource() instanceof SelectableComponent )
		{
			currentSelection = (SelectableComponent<CritterTemplateWidget>) e.getSource();
		}
		
		// -- Pass the event to listeners.
		for ( SelectionListener selectionListener : selectionListeners )
		{
			selectionListener.selectionOccurred( e );
		}
	}
	
	private Box createButtonPanel()
	{
		Box buttonPanel = new Box( BoxLayout.X_AXIS );
		
		JButton btnNew = new JButton( NEW_BUTTON_CAPTION );
		JButton btnDelete = new JButton( DEL_BUTTON_CAPTION );
		
		btnNew.addActionListener( btnNew_actionPerformed() );
		btnDelete.addActionListener( btnDelete_actionPerformed() );
		
		buttonPanel.add( btnNew );
		buttonPanel.add( Box.createRigidArea( SEPARATOR_DIM ) );
		buttonPanel.add( btnDelete );
		
		return buttonPanel;
	}
	
	private ActionListener btnNew_actionPerformed()
	{
		return new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				NewCritterTemplateWindow nctw = new NewCritterTemplateWindow();
				nctw.setVisible( true );
				
				CritterPrototype prototype = nctw.getTemplatePrototype();
				String name = nctw.getTemplateName();
				
				if ( prototype != null && name != null )
				{
					Command createCommand = CommandFactory.getCreateTemplateCommand(prototype, name);
					
					try
					{
						createCommand.execute();
					}
					catch ( RuntimeException re )
					{
						if ( re.getCause() instanceof DAOException )
						{
							JOptionPane.showMessageDialog(
									parentRef, "That name is already in use.", "Invalid Input", 
									JOptionPane.ERROR_MESSAGE );
							
							return;
						}
					}
					
					String listTitle = null;
					
					switch ( prototype )
					{
					case PLANT:
						listTitle = PLANTS_TITLE;
						break;
						
					case PREDATOR:
						listTitle = PREDATORS_TITLE;
						break;
						
					case PREY:
						listTitle = PREY_TITLE;
						break;
						
					default:
					}
					
					list.addItemToList( name, 0, listTitle );
				}
				
				CritterPanel.this.validate();
				CritterPanel.this.repaint();
			}
		};
	}
	
	private ActionListener btnDelete_actionPerformed()
	{
		return new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				JComponent component = currentSelection.getDelegate();
				
				if ( component instanceof CritterTemplateWidget )
				{
					String critterTemplateName = ( (CritterTemplateWidget) component ).getCritterTemplateName();
					
					Command readCommand = CommandFactory.getRetrieveTemplateCommand(critterTemplateName, new Callback()
					{
						public void callback( Object ... results )
						{
							if ( results.length == 1 && results[0] instanceof CritterTemplate )
							{
								CritterTemplate template = (CritterTemplate) results[0];
								
								Command deleteCommand = CommandFactory.getDeleteTemplateCommand( template );
								deleteCommand.execute();
							}
						}
					});
					readCommand.execute();
					
					list.removeItemFromList( critterTemplateName );
					
					if ( parentRef != null )
					{
						parentRef.cancelTraits();
					}
				}
				
				CritterPanel.this.validate();
				CritterPanel.this.repaint();
			}
		};
	}
}
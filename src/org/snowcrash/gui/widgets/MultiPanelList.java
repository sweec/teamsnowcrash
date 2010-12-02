package org.snowcrash.gui.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import org.snowcrash.utilities.Selectable;
import org.snowcrash.utilities.SelectionEvent;
import org.snowcrash.utilities.SelectionListener;


@SuppressWarnings("serial")
public class MultiPanelList extends Box implements SelectionListener
{
	private Map<String,BorderedList> subLists = new HashMap<String,BorderedList>();
	private Selectable selection = null;
	
	private Map<String,SelectableComponent<CritterTemplateWidget>> componentMapping = 
		new HashMap<String,SelectableComponent<CritterTemplateWidget>>();
	
	private List<SelectionListener> selectionListeners = new ArrayList<SelectionListener>();
	
	protected MultiPanelList()
	{
		super( BoxLayout.Y_AXIS );
		setAlignmentX( LEFT_ALIGNMENT );
	}
	
	public MultiPanelList( String ... titles )
	{
		this();
		
		for ( String title : titles )
		{
			BorderedList panel = new BorderedList( title );
			
			subLists.put( title, panel );
			panel.addSelectionListener( this );
			add( panel );
		}
	}
	
	public void addSelectionListener( SelectionListener listener )
	{
		selectionListeners.add( listener );
	}
	
	public void removeSelectionListener( SelectionListener listener )
	{
		selectionListeners.remove( listener );
	}
	
	public void removeSelectionListeners()
	{
		selectionListeners.clear();
	}
	
	public void addItemToList( String item, String listTitle )
	{
		ComponentList<SelectableComponent<CritterTemplateWidget>> list = subLists.get( listTitle );
		
		if ( list != null )
		{
			SelectableComponent<CritterTemplateWidget> component = 
				new SelectableComponent<CritterTemplateWidget>( new CritterTemplateWidget( item ) );
			component.setAlignmentX( JComponent.LEFT_ALIGNMENT );
			
			component.setSelectionBehavior( CritterTemplateSelectionBehavior.getInstance() );
			component.setDeselectionBehavior( CritterTemplateDeselectionBehavior.getInstance() );
			
			componentMapping.put( item, component );
			
			list.add( component );
		}
	}
	
	public void removeItemFromList( String item )
	{
		SelectableComponent<CritterTemplateWidget> component = componentMapping.get( item );
		
		for ( ComponentList<SelectableComponent<CritterTemplateWidget>> list : subLists.values() )
		{
			list.remove( component );
		}
	}
	
	public Object getSelected()
	{
		return selection;
	}
	
	public void clearSelections()
	{
		for ( BorderedList subList : subLists.values() )
		{
			subList.clearSelections();
		}
	}
	
	public void selectionOccurred( SelectionEvent e )
	{
		if ( e.getSource() instanceof Selectable && e.getProxy() instanceof BorderedList )
		{
			Selectable source = (Selectable) e.getSource();
			BorderedList proxy = (BorderedList) e.getProxy();
			
			for ( BorderedList subList : subLists.values() )
			{
				if ( subList != proxy )
				{
					subList.clearSelections();
				}
				else
				{
					subList.select( source );
					selection = source;
				}
			}
		}
		
		// -- Re-dispatch the event.
		e.setProxy( this );
		
		for ( SelectionListener selectionListener : selectionListeners )
		{
			selectionListener.selectionOccurred( e );
		}
	}
	
	private class BorderedList extends ComponentList<SelectableComponent<CritterTemplateWidget>>
	{
		public BorderedList( String title )
		{
			super();
			
			setBorder( BorderFactory.createTitledBorder( title ) );
			setAlignmentX( LEFT_ALIGNMENT );
		}
	}
}

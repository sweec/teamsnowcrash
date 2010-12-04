package org.snowcrash.gui.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import org.snowcrash.utilities.Selectable;
import org.snowcrash.utilities.SelectableComposite;
import org.snowcrash.utilities.SelectionEvent;
import org.snowcrash.utilities.SelectionListener;


@SuppressWarnings("serial")
public class ComponentList<T extends JComponent & Selectable> extends Box
		implements SelectableComposite, SelectionListener
{
	private Set<Selectable> selectables = new HashSet<Selectable>();
	
	private List<SelectionListener> selectionListeners = new ArrayList<SelectionListener>();
	
	public ComponentList()
	{
		super( BoxLayout.Y_AXIS );
		
		add( Box.createGlue() );
		
		super.setAlignmentX( LEFT_ALIGNMENT );
	}
	
	public void add( T component )
	{
		super.add( component );
		
		component.addSelectionListener( this );
		
		selectables.add( component );
		
		super.validate();
		super.repaint();
	}
	
	public void remove( T component )
	{
		super.remove( component );
		
		component.removeSelectionListener( this );
		
		selectables.remove( component );
		
		super.repaint();
	}
	
	public void addSelectionListener( SelectionListener listener )
	{
		selectionListeners.add( listener );
	}
	
	public void removeSelectionListener( SelectionListener listener )
	{
		selectionListeners.remove( listener );
	}
	
	public boolean hasSelected()
	{
		boolean hasSelected = false;
		
		for ( Selectable selectable : selectables )
		{
			if ( selectable.isSelected() )
			{
				hasSelected = true;
				break;
			}
		}
		
		return hasSelected;
	}
	
	public void select( Selectable object )
	{
		for ( Selectable selectable : selectables )
		{
			if ( selectable.equals( object ) )
			{
				selectable.select();
			}
		}
	}
	
	public void clearSelections()
	{
		for ( Selectable selectable : selectables )
		{
			selectable.deselect();
		}
	}
	
	public void selectionOccurred( SelectionEvent e )
	{
		if ( e.getSource() instanceof Selectable )
		{
			Selectable source = (Selectable) e.getSource();
			
			for ( Selectable selectable : selectables )
			{
				if ( selectable == source )
				{
					selectable.select();
				}
				else
				{
					selectable.deselect();
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
}

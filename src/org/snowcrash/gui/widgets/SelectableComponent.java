package org.snowcrash.gui.widgets;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.snowcrash.utilities.Selectable;
import org.snowcrash.utilities.SelectionEvent;
import org.snowcrash.utilities.SelectionListener;


@SuppressWarnings("serial")
public class SelectableComponent<T extends JComponent> extends JPanel implements Selectable, MouseListener
{
	private boolean isSelected = false;
	private T delegate;
	
	private SelectionStateBehavior<T> selectBehavior = null;
	private SelectionStateBehavior<T> deselectBehavior = null;
	
	private List<SelectionListener> selectionListeners = new ArrayList<SelectionListener>();
	
	public SelectableComponent( T delegate )
	{
		this.delegate = delegate;
		
		this.add( delegate );
		this.delegate.addMouseListener( this );
	}
	
	public SelectableComponent( T delegate, SelectionStateBehavior<T> selectBehavior, 
			SelectionStateBehavior<T> deselectBehavior )
	{
		this( delegate );
		
		this.selectBehavior = selectBehavior;
		this.deselectBehavior = deselectBehavior;
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
	
	public void setSelectionBehavior( SelectionStateBehavior<T> selectBehavior )
	{
		this.selectBehavior = selectBehavior;
	}
	
	public void setDeselectionBehavior( SelectionStateBehavior<T> deselectBehavior )
	{
		this.deselectBehavior = deselectBehavior;
	}
	
	public JComponent getDelegate()
	{
		return delegate;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	
	public void select()
	{
		isSelected = true;
		
		if ( selectBehavior != null )
		{
			selectBehavior.execute( delegate );
		}
	}
	
	public void deselect()
	{
		isSelected = false;
		
		if ( deselectBehavior != null )
		{
			deselectBehavior.execute( delegate );
		}
	}
	
	public void mouseClicked( MouseEvent e )
	{
		select();
		
		SelectionEvent event = new SelectionEvent( this );
		
		for ( SelectionListener selectionListener : selectionListeners )
		{
			selectionListener.selectionOccurred( event );
		}
	}
	
	public void mouseEntered( MouseEvent e ) {}
	
	public void mouseExited( MouseEvent e ) {}
	
	public void mousePressed( MouseEvent e ) {}
	
	public void mouseReleased( MouseEvent e ) {}
}

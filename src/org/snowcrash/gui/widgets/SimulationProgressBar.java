package org.snowcrash.gui.widgets;

import java.awt.Dimension;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;
import javax.swing.plaf.metal.MetalSliderUI;

/**
 * 
 * @author Mike
 * 
 * This widget allows the parent component an easy interface for updating the 
 * progress slider.
 * 
 * No validation is performed in this implementation; any validation is performed 
 * by the JSlider class.
 * 
 * 09 Nov - Created.
 *
 */
@SuppressWarnings("serial")
public class SimulationProgressBar extends JPanel implements ChangeListener
{
	private static final int DEFAULT_VALUE = 0;
	private static final int DEFAULT_EXTENT = 0;
	private static final int DEFAULT_MINIMUM = 0;
	private static final int DEFAULT_MAXIMUM = 1;
	
	// -- Keeps track of the model for this component.
	private BoundedRangeModel model = new DefaultBoundedRangeModel( 
			DEFAULT_VALUE, DEFAULT_EXTENT, DEFAULT_MINIMUM, DEFAULT_MAXIMUM );
	
	// -- Keeps track of the display of this component.
	private final JSlider slider = new JSlider( model );
	
	private final SliderUI UNSCROLLABLE_SLIDER_UI = new UneditableSliderUI( slider );
	
	/**
	 * 
	 * Constructor.
	 * 
	 */
	public SimulationProgressBar()
	{
		/*
		 * Add the slider component to this panel.
		 */
		this.add( slider );
		
		/*
		 * Set the spacing of the ticks to one so the knob moves every time the 
		 * value updates.
		 */
		slider.setMajorTickSpacing( 1 );
		
		slider.setUI( UNSCROLLABLE_SLIDER_UI );
		
		/*
		 * Add this class as a listener for events to the slider.
		 * 
		 * This will trigger the stateChanged method when a change is made to the 
		 * slider's properties.
		 */
		slider.addChangeListener( this );
	}
	
	/**
	 * 
	 * Sets the number of ticks that this progress bar will count to.  It is the 
	 * responsibility of the parent container to update the number of ticks to be 
	 * consistent with the engine.
	 * 
	 * @param numTicks the maximum number of ticks
	 * 
	 */
	public void setNumberOfTicks( int numTicks )
	{
		model.setMaximum( numTicks );
	}
	
	/**
	 * 
	 * Moves the knob of the slider to the next tick.  This method provides some 
	 * safety so the client cannot specify a different number of ticks to move.
	 * 
	 * If we end up making it so the user can move the slider manually (he cannot 
	 * currently), we will need to expose an additional method, but this one should 
	 * be kept as-is.
	 * 
	 */
	public void gotoNextTick()
	{
		model.setValue( model.getValue() + 1 );
	}
	
	public void reset()
	{
		model.setValue( 0 );
	}
	
	public void setCurrentTick( int tick )
	{
		model.setValue( tick );
	}
	
	/**
	 * 
	 * Overrides the setSize() method of the JPanel superclass to apply specifically 
	 * to the JSlider component.  The JPanel superclass should always "hug" the 
	 * slider component, so it is not necessary to access the JPanel's default 
	 * setSize() method.
	 * 
	 * @param width the width of the slider in pixels
	 * @param height the height of the slider in pixels
	 * 
	 */
	public void setSize( int width, int height )
	{
		slider.setPreferredSize( new Dimension( width, height ) );
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged( ChangeEvent e )
	{
		if ( e != null )
		{
			// -- Do nothing for now.
		}
	}
	
	/**
	 * 
	 * Private class to ensure that the scrollbar is not editable.
	 * 
	 * @author Mike
	 *
	 */
	protected class UneditableSliderUI extends MetalSliderUI
	{
		/**
		 * 
		 * Constructor.
		 * 
		 * @param slider the component for this UI
		 * 
		 */
		public UneditableSliderUI( JSlider slider )
		{
			super();
		}
		
		/*
		 * (non-Javadoc)
		 * @see javax.swing.plaf.basic.BasicSliderUI#installListeners(javax.swing.JSlider)
		 */
		protected void installListeners( JSlider slider )
		{
			slider.getModel().addChangeListener( super.changeListener );
		}
	}
}

package org.snowcrash.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.snowcrash.commands.Command;
import org.snowcrash.commands.CommandFactory;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.gui.widgets.CritterTemplateListCellRenderer;
import org.snowcrash.gui.widgets.MultiPanelList;


@SuppressWarnings("serial")
public class CritterPanel extends JPanel
{
	private static final String PLANTS_TITLE = "Plants";
	private static final String PREY_TITLE = "Prey";
	private static final String PREDATORS_TITLE = "Predators";
	
	private static final String NEW_BUTTON_CAPTION = "New";
	private static final String DEL_BUTTON_CAPTION = "Delete";
	
	private static final Dimension SEPARATOR_DIM = new Dimension( 5, 0 );
	
	private MultiPanelList list = new MultiPanelList( PLANTS_TITLE, PREY_TITLE, PREDATORS_TITLE );
	
	public CritterPanel()
	{
		Box buttonPanel = createButtonPanel();
		
		JScrollPane listScrollPane = new JScrollPane( list );
		listScrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		listScrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		
		list.setCustomCellRenderer( new CritterTemplateListCellRenderer() );
		
		setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
		add( listScrollPane );
		add( buttonPanel );
		
		list.addItemToList( "Test1", PLANTS_TITLE );
		list.addItemToList( "Test2", PLANTS_TITLE );
		
		list.addItemToList( "Test3", PREY_TITLE );
	}
	
	public void setSelectedCritterByName( String name )
	{
		list.setSelected( name );
	}
	
	public String getSelectedCritter()
	{
		return list.getSelected();
	}
	
	public void clearSelection()
	{
		list.clearSelection();
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
				CritterPrototype prototype = CritterPrototype.PLANT;
				String name = "Plant";
				
				Command createCommand = CommandFactory.getCreateTemplateCommand(prototype, name);
				createCommand.execute();
				
				setSelectedCritterByName(name);
			}
		};
	}
	
	private ActionListener btnDelete_actionPerformed()
	{
		return new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				// TODO
			}
		};
	}
}
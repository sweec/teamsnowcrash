package org.snowcrash.gui;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.snowcrash.commands.Command;
import org.snowcrash.commands.CommandFactory;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.testCritterTemplate;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.gui.widgets.CritterTemplateWidget;
import org.snowcrash.utilities.Callback;
import org.snowcrash.utilities.SelectionEvent;
import org.snowcrash.utilities.SelectionListener;

public class ConfigScreen extends JPanel implements SelectionListener
{
	public static final int WIDTH = 800;	// minimum window width
	public static final int HEIGHT = 600;	// minimum window height
	
	JTabbedPane tabPane1, tabPane2, tabPane3;
	WorldPanel worldConfig;
	
	public ConfigScreen()
	{
		JPanel configPanel = this;
		configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.X_AXIS));
		
		configPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		// get the critter panel and add a tab
		JPanel cPanel = new CritterPanel();
		tabPane1 = new JTabbedPane();
		tabPane1.addTab("Critters", cPanel);
		tabPane1.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane1.setPreferredSize(new Dimension
				((WIDTH - 20) / 3, Short.MAX_VALUE));
		configPanel.add(tabPane1);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		// get the traits panel and add a tab
		TraitsPanel traitsConfig = new TraitsPanel();
		cPanel = traitsConfig.TraitsPanel();
		tabPane2 = new JTabbedPane();
		tabPane2.addTab("Traits", cPanel);
		tabPane2.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane2.setPreferredSize(new Dimension
				((WIDTH - 20) / 3, Short.MAX_VALUE));
		configPanel.add(tabPane2);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		// get the world panel and add a tab
		JScrollPane cScroll = new JScrollPane();
		worldConfig = new WorldPanel();
		cScroll = worldConfig.WorldPanel();
		tabPane3 = new JTabbedPane();
		tabPane3.addTab("World Settings", cScroll);
		tabPane3.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane3.setPreferredSize(new Dimension
				((WIDTH - 20) / 3, Short.MAX_VALUE));
		configPanel.add(tabPane3);
		
		configPanel.add(Box.createRigidArea(new Dimension(5,0)));
	}
	
	public void cancelTraits()
	{
		tabPane2.removeAll();
		TraitsPanel traitsConfig = new TraitsPanel();
		tabPane2.add(traitsConfig.TraitsPanel());
		tabPane2.repaint();
	}
	
	public int getTotalTurns()
	{
		return worldConfig.getTotalTurns();
	}
	
	// DAO changed, update critterPanel
	// here replaced by new critterPanel
	public void update() {
		DatabaseObject[] objects;
		DAO dao = DAOFactory.getDAO();
		try {
			//objects = dao.read( CritterTemplate.class );
			objects = dao.read( testCritterTemplate.class );
		} catch (DAOException e) {
			throw new RuntimeException( e );
		}
		Collection<CritterTemplate> critterTemplates = new ArrayList<CritterTemplate>(objects.length);
		int i;
		for (i = 0;i < objects.length;i++)
			critterTemplates.add((CritterTemplate) (objects[i]));
		CritterPanel cPanel = new CritterPanel();
		cPanel.addData(critterTemplates);
		tabPane1.setComponentAt(tabPane1.indexOfTab("Critters"), cPanel);
		// shall we replace traitsPanel?
		//JPanel tPanel = (new TraitsPanel()).TraitsPanel();
		//tabPane1.setComponentAt(tabPane1.indexOfTab("Traits"), tPanel);
	}
	
	public void componentResized(ComponentEvent e) 
    {
       int currentWidth = getWidth();
       int currentHeight = getHeight();
     //we check if either the currentWidth
     //or the currentHeight are below minimum
     boolean resize = false;
       if (currentWidth < WIDTH) 
       {
            resize = true;
            currentWidth = WIDTH;
       }
       if (currentHeight < HEIGHT) 
       {
            resize = true;
            currentHeight = HEIGHT;
       }
       if (resize) 
       {
           setSize(currentWidth, currentHeight);
           tabPane1.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
    	   tabPane2.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
    	   tabPane3.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
       }
       else
       {
    	   tabPane1.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
    	   tabPane2.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
    	   tabPane3.setPreferredSize(new Dimension
    			   ((currentWidth - 20) / 3, Short.MAX_VALUE));
       }
    }
	
	public void selectionOccurred( SelectionEvent e )
	{
		if ( e.getSource() instanceof CritterTemplateWidget )
		{
			CritterTemplateWidget ctw = (CritterTemplateWidget) e.getSource();
			String critterTemplateName = ctw.getCritterTemplateName();
			
			Command command = CommandFactory.getRetrieveTemplateCommand(critterTemplateName, new Callback()
			{
				public void callback( Object ... results )
				{
					if ( results.length == 1 && results[0] instanceof CritterTemplate )
					{
						CritterTemplate template = (CritterTemplate) results[0];
						
						// -- TODO update traits panel
					}
				}
			});
		}
	}
	
	public static void main(String[] args)
	{
		ConfigScreen scconfig = new ConfigScreen();
		scconfig.setVisible(true);
	}
	
}
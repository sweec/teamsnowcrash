/**
 * 
 */
package org.snowcrash.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.snowcrash.critter.testCritterTemplate;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.critter.data.Size;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;

/**
 * @author dong
 *
 */
@SuppressWarnings("serial")
public class ResultsPanel extends JPanel {
	private static ResultsPanel instance = null;
	final private int SIZEX = 500, SIZEY = 1000;
	
	// critterTemplate Panel
	final private ArrayList<testCritterTemplate> plantTemplates = new ArrayList<testCritterTemplate>();
	final private ArrayList<testCritterTemplate> preyTemplates = new ArrayList<testCritterTemplate>();
	final private ArrayList<testCritterTemplate> predatorTemplates = new ArrayList<testCritterTemplate>();
	private JList plantList = null, preyList = null, predatorList = null;
	
	// statistics Panel
	private static final int imgWidth = 75;		// critter icon Width
	private static final int imgHeight = 75;	// critter icon Height
    private ImageIcon plant = null;
    private ImageIcon prey = null;
    private ImageIcon predator = null;
	private JLabel critterImage, critterName, critterSize;
	private JLabel startPopulation, endPopulation, totalPopulation;
	private JLabel minAge, maxAge, averageAge;
	private HashMap<Trait, JLabel> startTraitsLabel = new HashMap<Trait, JLabel>(), endTraitsLabel = new HashMap<Trait, JLabel>();
	
	public ResultsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane.addTab("Critters", critterTemplatePane());
		add(tabPane);
		add(Box.createRigidArea(new Dimension(5,0)));
		tabPane = new JTabbedPane();
		tabPane.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane.addTab("Statistics", statisticsPanel());
		add(tabPane);
		instance = this;
	}

	private void classifyTemplates() {
		DAO dao = DAOFactory.getDAO();
		DatabaseObject[] object = null;
		try {
			// get CritterTemplates
			object = dao.read(testCritterTemplate.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (object == null) return;
		int i;
		for (i = 0;i < object.length;i++) {
			testCritterTemplate template = (testCritterTemplate) (object[i]);
			if (template.getStartPopulation() == 0) continue;
			CritterPrototype type = template.getPrototype();
			switch (type) {
			case PLANT:
				plantTemplates.add(template);
				break;
			case PREY:
				preyTemplates.add(template);
				break;
			case PREDATOR:
				predatorTemplates.add(template);
				break;
			}
		}
	}
	
	private JScrollPane critterTemplatePane() {
		// initialize data to be displayed
		classifyTemplates();
		
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		
		// add plant scroll list
		JPanel plants = this.borderPanel("Plants");
		ListModel plantModel = new AbstractListModel() {
		    public int getSize() { return plantTemplates.size(); }
		    public Object getElementAt(int index) { return ((testCritterTemplate)plantTemplates.get(index)).getName(); }
		};
		plantList = new JList(plantModel);
		plantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		plantList.addListSelectionListener(
			new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (preyList != null) preyList.clearSelection();
					if (predatorList != null) predatorList.clearSelection();
					if (instance != null) instance.updateStatistics((testCritterTemplate)plantTemplates.get(plantList.getSelectedIndex()));
				}
			}
		);
		plantList.setSelectedIndex(0);
		plants.add(new JScrollPane(plantList));
		cPanel.add(plants);
		
		// add prey scroll list
		JPanel prey = this.borderPanel("Prey");
		ListModel preyModel = new AbstractListModel() {
		    public int getSize() { return preyTemplates.size(); }
		    public Object getElementAt(int index) { return ((testCritterTemplate)preyTemplates.get(index)).getName(); }
		};
		preyList = new JList(preyModel);
		preyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		preyList.addListSelectionListener(
			new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (plantList != null) plantList.clearSelection();
					if (predatorList != null) predatorList.clearSelection();
					if (instance != null) instance.updateStatistics((testCritterTemplate)preyTemplates.get(preyList.getSelectedIndex()));
				}
			}
		);
		prey.add(new JScrollPane(preyList));
		cPanel.add(prey);
		
		// add predator scroll list
		JPanel predator = this.borderPanel("Predator");
		ListModel predatorModel = new AbstractListModel() {
		    public int getSize() { return predatorTemplates.size(); }
		    public Object getElementAt(int index) { return ((testCritterTemplate)predatorTemplates.get(index)).getName(); }
		};
		predatorList = new JList(predatorModel);
		predatorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		predatorList.addListSelectionListener(
			new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (plantList != null) plantList.clearSelection();
					if (preyList != null) preyList.clearSelection();
					if (instance != null) instance.updateStatistics((testCritterTemplate)predatorTemplates.get(predatorList.getSelectedIndex()));
				}
			}
		);
		predator.add(new JScrollPane(predatorList));
		cPanel.add(predator);
		JScrollPane cScroll = new JScrollPane(cPanel);
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		return cScroll;
	}
	
	private JPanel statisticsPanel() {
		// initialize resource
		ImageIcon critterIcon = new ImageIcon ("images/plant.png");
		plant = this.resizeIcon(critterIcon);
		critterIcon = new ImageIcon ("images/prey-right.png");
		prey = this.resizeIcon(critterIcon);
		critterIcon = new ImageIcon ("images/predator-right.png");
		predator = this.resizeIcon(critterIcon);

		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));

		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		JPanel populationPanel = this.populationPanel();
		cPanel.add(populationPanel);
		this.traitSeparator(cPanel);

		JPanel agePanel = this.agePanel();
		cPanel.add(agePanel);
		this.traitSeparator(cPanel);

		JPanel traitPanel = this.traitPanel(Trait.VISION);
		cPanel.add(traitPanel);
		this.traitSeparator(cPanel);

		traitPanel = this.traitPanel(Trait.SPEED);
		cPanel.add(traitPanel);
		this.traitSeparator(cPanel);

		traitPanel = this.traitPanel(Trait.CAMO);
		cPanel.add(traitPanel);
		this.traitSeparator(cPanel);
		
		traitPanel = this.traitPanel(Trait.COMBAT);
		cPanel.add(traitPanel);
		this.traitSeparator(cPanel);

		traitPanel = this.traitPanel(Trait.ENDURANCE);
		cPanel.add(traitPanel);

		JScrollPane cScroll = new JScrollPane(cPanel);
		cScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel namePanel = this.namePanel();
		JPanel oPanel = new JPanel();
		oPanel.setLayout(new BoxLayout(oPanel, BoxLayout.Y_AXIS));
		oPanel.add(namePanel);
		oPanel.add(cScroll);
		
		return oPanel;
	}
	
	private void updateStatistics(testCritterTemplate template) {
		CritterPrototype type = template.getPrototype();
		switch (type) {
		case PLANT:
			critterImage.setIcon(plant);
			break;
		case PREY:
			critterImage.setIcon(prey);
			break;
		case PREDATOR:
			critterImage.setIcon(predator);
			break;
		}
		
		Size size = template.getSize();
		switch (size) {
		case SMALL:
			critterSize.setText("Size: Small");
			break;
		case MEDIUM:
			critterSize.setText("Size: Medium");
			break;
		case LARGE:
			critterSize.setText("Size: Large");
			break;
		}
		
		critterName.setText("Name: " + template.getName());
		
		startPopulation.setText(Integer.toString(template.getStartPopulation()));
		endPopulation.setText(Integer.toString(template.getEndPopulation()));
		totalPopulation.setText(Integer.toString(template.getTotalPopulation()));
		minAge.setText(Integer.toString(template.getMinAge()));
		maxAge.setText(Integer.toString(template.getMaxAge()));
		averageAge.setText(Integer.toString(template.getAverageAge()));
		
		startTraitsLabel.get(Trait.CAMO).setText("Start: " + template.getStartTrait(Trait.CAMO));
		endTraitsLabel.get(Trait.CAMO).setText("End: " + template.getStartTrait(Trait.CAMO));
		startTraitsLabel.get(Trait.COMBAT).setText("Start: " + template.getStartTrait(Trait.COMBAT));
		endTraitsLabel.get(Trait.COMBAT).setText("End: " + template.getStartTrait(Trait.COMBAT));
		startTraitsLabel.get(Trait.ENDURANCE).setText("Start: " + template.getStartTrait(Trait.ENDURANCE));
		endTraitsLabel.get(Trait.ENDURANCE).setText("End: " + template.getStartTrait(Trait.ENDURANCE));
		startTraitsLabel.get(Trait.SPEED).setText("Start: " + template.getStartTrait(Trait.SPEED));
		endTraitsLabel.get(Trait.SPEED).setText("End: " + template.getStartTrait(Trait.SPEED));
		startTraitsLabel.get(Trait.VISION).setText("Start: " + template.getStartTrait(Trait.SPEED));
		endTraitsLabel.get(Trait.SPEED).setText("End: " + template.getStartTrait(Trait.SPEED));
	}
	
	private ImageIcon resizeIcon(ImageIcon origIcon) // resizes the critter icon
	{
		Image origImage = origIcon.getImage();
		Image newImage = origImage.getScaledInstance(imgWidth, imgHeight, 
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImage);
		return newIcon;
	}
	
	// create a namePanel with default setting
	private JPanel namePanel()
	{
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.X_AXIS));

		critterImage = new JLabel(predator);
		cPanel.add(critterImage);
		cPanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		Box box = new Box(BoxLayout.Y_AXIS);
		critterName = new JLabel("Name: Barney");
		box.add(critterName);
		box.add(Box.createVerticalStrut(10));
		critterSize = new JLabel("Size: Small");
		box.add(critterSize);
		cPanel.add(box);
		
		return cPanel;
	}

	 // create a populationPanel with default setting
	private JPanel populationPanel()
	{
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		cPanel.setAlignmentX(CENTER_ALIGNMENT);

		cPanel.add(new JLabel("Population"));
		cPanel.add(Box.createVerticalStrut(10));
		
		Box box = new Box(BoxLayout.X_AXIS);
		startPopulation = new JLabel("Start: " + 0);
		box.add(startPopulation);
		box.add(Box.createHorizontalStrut(10));
		endPopulation = new JLabel("End: " + 100);
		box.add(endPopulation);
		box.add(Box.createHorizontalStrut(10));
		totalPopulation = new JLabel("Total: " + 100);
		box.add(totalPopulation);
		cPanel.add(box);
		
		return cPanel;
	}

	 // create a agePanel with default setting
	private JPanel agePanel()
	{
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		cPanel.setAlignmentX(CENTER_ALIGNMENT);

		cPanel.add(new JLabel("Age"));
		cPanel.add(Box.createVerticalStrut(10));
		
		Box box = new Box(BoxLayout.X_AXIS);
		minAge = new JLabel("Min age: " + 0);
		box.add(minAge);
		box.add(Box.createHorizontalStrut(10));
		maxAge = new JLabel("Max age: " + 0);
		box.add(maxAge);
		box.add(Box.createHorizontalStrut(10));
		averageAge = new JLabel("Average age: " + 0);
		box.add(averageAge);
		cPanel.add(box);
		
		return cPanel;
	}

	private void traitSeparator(JPanel cPanel) // a custom separator between traits
	{
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		cPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
	}
	
	private JPanel traitPanel(Trait trait)
	{	
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		cPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		String critterTrait = null;
		switch (trait) {
		case CAMO:
			critterTrait = new String("Camoflage");
			break;
		case COMBAT:
			critterTrait = new String("Combat");
			break;
		case ENDURANCE:
			critterTrait = new String("Endurance");
			break;
		case SPEED:
			critterTrait = new String("Speed");
			break;
		case VISION:
			critterTrait = new String("Vision");
			break;
		}
		JLabel sliderPairLabel = new JLabel(critterTrait);
		sliderPairLabel.setAlignmentX(LEFT_ALIGNMENT);
		cPanel.add(sliderPairLabel);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(Box.createHorizontalStrut(10));
		startTraitsLabel.put(trait, new JLabel("Start: " + 1));
		box.add(startTraitsLabel.get(trait));
		box.add(Box.createHorizontalStrut(10));
		endTraitsLabel.put(trait, new JLabel("End: " + 2));
		box.add(endTraitsLabel.get(trait));
		box.add(Box.createHorizontalStrut(10));
		cPanel.add(box);
		
		cPanel.add(box);
		
		return cPanel;
	}

	private JPanel borderPanel(String borderTitle)
	{
		Border critterBorder;
		JPanel cPanel = new JPanel();
		critterBorder = BorderFactory.createTitledBorder(borderTitle);
		cPanel.setBorder(critterBorder);
		cPanel.setAlignmentX(CENTER_ALIGNMENT);
		return cPanel;
	}
	
	/**
	 * return a JScrollPane contains me
	 */
	public JScrollPane getScrollPane() {
		JScrollPane scrollPane = new JScrollPane(this);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPane;
	}

    @Override 
    /**
     * this is critical in set the size correctly of the JPanel
     */
	public Dimension getPreferredSize() {
		return new Dimension(SIZEX, SIZEY);
	}
	
}

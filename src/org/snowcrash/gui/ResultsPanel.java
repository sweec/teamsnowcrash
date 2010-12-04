/**
 * 
 */
package org.snowcrash.gui;

import java.awt.BorderLayout;
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

import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.StatisticsCollector;
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
	private ArrayList<String> plantTemplates = new ArrayList<String>();
	private ArrayList<String> preyTemplates = new ArrayList<String>();
	private ArrayList<String> predatorTemplates = new ArrayList<String>();
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
	
	static public ResultsPanel getInstance() {
		if (instance == null) instance = new ResultsPanel();
		return instance;
	}
	
	public ResultsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane.addTab("Critters", critterTemplatePane());
		tabPane.setPreferredSize(new Dimension(SIZEX / 3, SIZEY));
		add(tabPane);
		add(Box.createRigidArea(new Dimension(5,0)));
		tabPane = new JTabbedPane();
		tabPane.setAlignmentY(BOTTOM_ALIGNMENT);
		tabPane.addTab("Statistics", statisticsPanel());
		add(tabPane);
		instance = this;
	}

	private void setupData() {
		StatisticsCollector sc = StatisticsCollector.getInstance();
		sc.calculateStatistics();
		
		DAO dao = DAOFactory.getDAO();
		DatabaseObject[] objects = null;
		try {
			objects = dao.read(CritterTemplate.class);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		if (objects == null) return;
		int i;
		for (i = 0;i < objects.length;i++) {
			CritterTemplate template = (CritterTemplate) (objects[i]);
			String name = template.getName();
			if (sc.getStartPopulation(name) == 0) continue;
			CritterPrototype type = template.getPrototype();
			switch (type) {
			case PLANT:
				plantTemplates.add(name);
				break;
			case PREY:
				preyTemplates.add(name);
				break;
			case PREDATOR:
				predatorTemplates.add(name);
				break;
			}
		}
	}
	
	private JScrollPane critterTemplatePane() {
		// initialize data to be displayed
		setupData();
		
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		
		// add plant scroll list
		JPanel plants = this.borderPanel("Plants");
		plants.setLayout(new BorderLayout());
		ListModel plantModel = new AbstractListModel() {
		    public int getSize() { return plantTemplates.size(); }
		    public Object getElementAt(int index) { return plantTemplates.get(index); }
		};
		plantList = new JList(plantModel);
		plantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		plantList.addListSelectionListener(
			new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					ResultsPanel.getInstance().updateSelection(CritterPrototype.PLANT);
				}
			}
		);
		//plantList.setFixedCellWidth(listWidth);
		// do not call setSelectedIndex() here, cause ResultsPanel is not available yet
		plants.add(new JScrollPane(plantList), BorderLayout.CENTER);
		cPanel.add(plants);
		
		// add prey scroll list
		JPanel preys = this.borderPanel("Prey");
		preys.setLayout(new BorderLayout());
		ListModel preyModel = new AbstractListModel() {
		    public int getSize() { return preyTemplates.size(); }
		    public Object getElementAt(int index) { return preyTemplates.get(index); }
		};
		preyList = new JList(preyModel);
		preyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		preyList.addListSelectionListener(
			new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					ResultsPanel.getInstance().updateSelection(CritterPrototype.PREY);
				}
			}
		);
		preys.add(new JScrollPane(preyList), BorderLayout.CENTER);
		cPanel.add(preys);
		
		// add predator scroll list
		JPanel predators = this.borderPanel("Predator");
		predators.setLayout(new BorderLayout());
		ListModel predatorModel = new AbstractListModel() {
		    public int getSize() { return predatorTemplates.size(); }
		    public Object getElementAt(int index) { return predatorTemplates.get(index); }
		};
		predatorList = new JList(predatorModel);
		predatorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		predatorList.addListSelectionListener(
			new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					ResultsPanel.getInstance().updateSelection(CritterPrototype.PREDATOR);
				}
			}
		);
		predators.add(new JScrollPane(predatorList), BorderLayout.CENTER);
		cPanel.add(predators);
		
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
	
	private void updateSelection(CritterPrototype type) {
		String name = null;
		switch (type) {
		case PLANT:
			if (plantList.isSelectionEmpty()) return;
			if (preyList != null) preyList.clearSelection();
			if (predatorList != null) predatorList.clearSelection();
			critterImage.setIcon(plant);
			name = plantTemplates.get(plantList.getSelectedIndex());
			break;
		case PREY:
			if (preyList.isSelectionEmpty()) return;
			if (plantList != null) plantList.clearSelection();
			if (predatorList != null) predatorList.clearSelection();
			critterImage.setIcon(prey);
			name = preyTemplates.get(preyList.getSelectedIndex());
			break;
		case PREDATOR:
			if (predatorList.isSelectionEmpty()) return;
			if (plantList != null) plantList.clearSelection();
			if (preyList != null) preyList.clearSelection();
			critterImage.setIcon(predator);
			name = predatorTemplates.get(predatorList.getSelectedIndex());
			break;
		}
		
		DAO dao = DAOFactory.getDAO();
		CritterTemplate template = null;
		try {
			template = (CritterTemplate) dao.read(CritterTemplate.class, name);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		if (template == null) return;
		
		critterName.setText("Name: " + name);
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
		
		StatisticsCollector sc = StatisticsCollector.getInstance();
		startPopulation.setText("Start: " + sc.getStartPopulation(name));
		endPopulation.setText("End: " + sc.getEndPopulation(name));
		totalPopulation.setText("Total: " + sc.getTotalPopulation(name));
		minAge.setText("Min age: " + sc.getMinAge(name));
		maxAge.setText("Max age: " + sc.getMaxAge(name));
		averageAge.setText("Average age: " + sc.getAverageAge(name));
		
		startTraitsLabel.get(Trait.CAMO).setText(String.format("Start: %1.1f", sc.getStartTrait(name, Trait.CAMO)));
		endTraitsLabel.get(Trait.CAMO).setText(String.format("End: %1.1f", sc.getStartTrait(name, Trait.CAMO)));
		startTraitsLabel.get(Trait.COMBAT).setText(String.format("Start: %1.1f", sc.getStartTrait(name, Trait.COMBAT)));
		endTraitsLabel.get(Trait.COMBAT).setText(String.format("End: %1.1f", sc.getStartTrait(name, Trait.COMBAT)));
		startTraitsLabel.get(Trait.ENDURANCE).setText(String.format("Start: %1.1f", sc.getStartTrait(name, Trait.ENDURANCE)));
		endTraitsLabel.get(Trait.ENDURANCE).setText(String.format("End: %1.1f", sc.getStartTrait(name, Trait.ENDURANCE)));
		startTraitsLabel.get(Trait.SPEED).setText(String.format("Start: %1.1f", sc.getStartTrait(name, Trait.SPEED)));
		endTraitsLabel.get(Trait.SPEED).setText(String.format("End: %1.1f", sc.getStartTrait(name, Trait.SPEED)));
		startTraitsLabel.get(Trait.VISION).setText(String.format("Start: %1.1f", sc.getStartTrait(name, Trait.SPEED)));
		endTraitsLabel.get(Trait.VISION).setText(String.format("End: %1.1f", sc.getStartTrait(name, Trait.VISION)));
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

		critterImage = new JLabel();
		cPanel.add(critterImage);
		cPanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		Box box = new Box(BoxLayout.Y_AXIS);
		critterName = new JLabel("Name: ------");
		box.add(critterName);
		box.add(Box.createVerticalStrut(10));
		critterSize = new JLabel("Size: ------");
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

		JLabel population = new JLabel("Population");
		population.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(population);
		cPanel.add(Box.createVerticalStrut(10));
		
		Box box = new Box(BoxLayout.X_AXIS);
		startPopulation = new JLabel("Start: -----");
		box.add(startPopulation);
		box.add(Box.createHorizontalStrut(10));
		endPopulation = new JLabel("End: -----");
		box.add(endPopulation);
		box.add(Box.createHorizontalStrut(10));
		totalPopulation = new JLabel("Total: -----");
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

		JLabel age = new JLabel("Age");
		age.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(age);
		cPanel.add(Box.createVerticalStrut(10));
		
		Box box = new Box(BoxLayout.X_AXIS);
		minAge = new JLabel("Min age: -----");
		box.add(minAge);
		box.add(Box.createHorizontalStrut(10));
		maxAge = new JLabel("Max age: -----");
		box.add(maxAge);
		box.add(Box.createHorizontalStrut(10));
		averageAge = new JLabel("Average age: -----");
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
		JLabel sliderPairLabel = new JLabel(/*"Average " + */critterTrait);
		sliderPairLabel.setAlignmentX(CENTER_ALIGNMENT);
		cPanel.add(sliderPairLabel);
		
		cPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(Box.createHorizontalStrut(10));
		startTraitsLabel.put(trait, new JLabel("Start: --"));
		box.add(startTraitsLabel.get(trait));
		box.add(Box.createHorizontalStrut(10));
		endTraitsLabel.put(trait, new JLabel("End: --"));
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

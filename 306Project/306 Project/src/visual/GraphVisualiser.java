package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import data_structures.AdjacencyList;
import data_structures.NodeObject;
import data_structures.UserOptions;
import graph306.SolutionTree;
import scala.collection.mutable.Queue;

public class GraphVisualiser{
	public static Graph bestTimeTree = new SingleGraph("Best Time Tree");
//	public static Graph datGraph = new SingleGraph("Directed Acyclic Task Graph");
	public int nid = 0;

	public static JLabel bestTimeLabel = new JLabel("Current Best Time:");
	public static JLabel bestTimeScheduleLabel = new JLabel("Best Schedule:");
	public static JLabel bestTimeCountLabel = new JLabel("Optimal Schedules Found:");
	public static JLabel equalBestTimeCountLabel = new JLabel("Duplicate Best Schedules Found:");

	public static JLabel nodesSearchedLabel = new JLabel("Nodes Searched:");
	public static JLabel processorsUsedLabel = new JLabel("Processors Used:");
	public static JLabel idleProcessorsLabel = new JLabel("Idle Processors:");
	public static JLabel partialScheduleLabel = new JLabel("Current Schedule:");
	public static JLabel processorEndTimeLabel = new JLabel("Processor End Time:");

	public static JLabel validScheduleCountLabel = new JLabel("Valid Schedules Discovered:");
	public static JLabel validScheduleLabel = new JLabel("Current Valid Schedule:");
	public static JLabel boundValueLabel = new JLabel("Bound Value:");

	public long nodeNumber = 0;
	public long bestTimeCount = 0;
	public long validScheduleCount = 0;
	public long equalBestTimeCount = 0;
	// Stores the time for the current shortest schedule.
	public static int minimumTime = Integer.MAX_VALUE;
	// A list containing the current best schedule.
	protected static List<NodeObject> bestSchedule = new ArrayList<NodeObject>();
	
	protected AdjacencyList inputGraph;
	private NodeObject rootNode;
	private List<String> nodeList;
	protected int numberofProcessors;
	SolutionTree solution;
	public Queue inputQueue = new Queue();
	
	/**
	 * Constructor that initialises the adjacency list to this class and makes a list of all nodes to use when checking if a node has been seen or not
	 * @param inputGraph
	 */
	public GraphVisualiser(AdjacencyList inputGraph, SolutionTree solution){
		this.inputGraph = inputGraph;
		nodeList = new ArrayList<String>();
		for(String entry : inputGraph.getNodes()){
    		nodeList.add(entry);
    	}
		this.solution = solution;
		numberofProcessors = UserOptions.getInstance().getProcessors();
		//create the root node object
		rootNode = new NodeObject(0, new ArrayList<NodeObject>(), "rootNode", new int[numberofProcessors], 0, 0);		
	}
	
	
	public void updateGraph(NodeObject currentNode){ // not updating
		bestTimeCount++;
		//when tree all the way down, and the time is lower than the global flag, set the new time
		//and set the new schedule to it
		for (Edge e : bestTimeTree.getEdgeSet()) {
			e.addAttribute("ui.style", "fill-color: rgb(0,0,0);");
		}
		minimumTime = solution.maxTimeAtPoint(currentNode);
		bestTimeLabel.setText("Current Best Time: " + solution.maxTimeAtPoint(currentNode));
		bestTimeCountLabel.setText("Faster Schedules Found: " + Long.toString(bestTimeCount));
		bestSchedule = currentNode.getCurrentPath();
		String oldNode = new String();
		String newNode = new String();
		int i = 0;
		Edge e;
		String bestPath = new String();
		for (NodeObject node : currentNode.getCurrentPath()) {
			if (node.getNodeName().equals("rootNode"))
				continue;
			newNode += node.getNodeName() + node.getProcessor();
			if (bestTimeTree.getNode(newNode) == null) {
				Node n = bestTimeTree.addNode(newNode);

				n.addAttribute("ui.label", node.getNodeName() + "(" + node.getProcessor() + ")");
//                n.addAttribute("ui.label", nid);
				n.addAttribute("layout.frozen");

				n.addAttribute("y", -i * 15);
				if (i == 0)
					n.addAttribute("x", nid);
				if (i > 0) {
					if ((int) bestTimeTree.getNode(oldNode).getAttribute("x") < 0) {
						n.addAttribute("x", -java.lang.Math.abs(nid));
					} else {
						n.addAttribute("x", java.lang.Math.abs(nid));
					}
				}
			}
			if (i > 0) {
				if(!(bestTimeTree.getEdge(newNode+oldNode)==null))
				bestTimeTree.removeEdge(newNode, oldNode);
				e = bestTimeTree.addEdge(newNode+oldNode, newNode, oldNode);
				e.setAttribute("ui.style", "fill-color:red;");

			}

			oldNode = newNode;
			i++;
			if (nid < 0)
				nid--;
			else
				nid++;
			//SPEED FACTOR: lower=faster
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			bestPath += node.getNodeName() + "(" + (node.getProcessor() + 1) + ") ";
		}
		bestTimeScheduleLabel.setText("Best Path: " + bestPath);
		nid *= -1;
	}

	public void notify(NodeObject input){
		System.out.println(input.getNodeName());
		updateGraph(input);
	}
	
	public static void isVisual() {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		GraphVisualiser.bestTimeTree.addAttribute("ui.stylesheet", "node { " +
				"size:12px; " +
				"text-color:#AD2A1A; " +
				"text-size:12px; " +
				"text-alignment:above;" +
				"text-padding: 3px;" +
				"text-background-mode:rounded-box;" +
				"}" +
				"edge { " +
				"text-color:#AD2A1A; " +
				"text-size:13px; " +
				"text-background-mode:rounded-box;" +
				"size:6px;" +
				"}" +
				"graph{" +
				"fill-color:#FFFFAA;" +
				"}");
		GraphVisualiser.bestTimeTree.addAttribute("ui.antialias");
		GraphVisualiser.bestTimeTree.setStrict(false);
		GraphVisualiser.bestTimeTree.addAttribute("ui.quality");
		Viewer treeViewer = new Viewer(GraphVisualiser.bestTimeTree, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		JFrame frame = new JFrame("Visualization");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new BorderLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Dimension dim = new Dimension((int)(screenSize.getWidth()*0.9), (int)(screenSize.height/1.2));
		ViewPanel tV = treeViewer.addDefaultView(false);

		JPanel statPanel = new JPanel();
		statPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
		statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));

		JPanel bestPathStats = new JPanel();
		bestPathStats.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		bestPathStats.add(GraphVisualiser.bestTimeLabel);
		bestPathStats.add(GraphVisualiser.bestTimeScheduleLabel);
		bestPathStats.add(GraphVisualiser.bestTimeCountLabel);
		bestPathStats.add(GraphVisualiser.equalBestTimeCountLabel);
		statPanel.add(bestPathStats);

		JPanel processorPanel = new JPanel();
		processorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		processorPanel.add(GraphVisualiser.nodesSearchedLabel);
		processorPanel.add(GraphVisualiser.processorsUsedLabel);
		processorPanel.add(GraphVisualiser.idleProcessorsLabel);
		processorPanel.add(GraphVisualiser.partialScheduleLabel);
		processorPanel.add(GraphVisualiser.processorEndTimeLabel);
		statPanel.add(processorPanel);

		JPanel validSchedulePanel = new JPanel();
		validSchedulePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		validSchedulePanel.add(GraphVisualiser.validScheduleCountLabel);
		validSchedulePanel.add(GraphVisualiser.validScheduleLabel);
		statPanel.add(validSchedulePanel);

		JPanel boundValuePanel = new JPanel();
		boundValuePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		boundValuePanel.add(GraphVisualiser.boundValueLabel);
		statPanel.add(boundValuePanel);


		tV.setPreferredSize(dim);
		panel.add(tV, BorderLayout.CENTER);
		panel.add(statPanel, BorderLayout.PAGE_START);

		frame.getContentPane().add(panel);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

		frame.setVisible(true);
	}
}

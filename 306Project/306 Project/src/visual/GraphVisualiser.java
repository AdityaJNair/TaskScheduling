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

/**
 * The Class that is used to visualise the graphs from GraphStream
 */
public class GraphVisualiser{
	//fields that are the JLabels for visualisation
	public static Graph bestTimeTree = new SingleGraph("Best Time Tree");
	public int nid = 0;

	public static JLabel bestTimeLabel = new JLabel("Current Best Time:");
	public static JLabel bestTimeScheduleLabel = new JLabel("Best Schedule:");
	public static JLabel bestTimeCountLabel = new JLabel("Optimal Schedules Found:");
	public static JLabel nodesSearchedLabel = new JLabel("Nodes Searched:");
	public static JLabel processorsUsedLabel = new JLabel("Processors Used:");
	public static JLabel idleProcessorsLabel = new JLabel("Idle Processors:");
	public static JLabel processorEndTimeLabel = new JLabel("Processor End Time:");
	public static JLabel validScheduleCountLabel = new JLabel("Valid Schedules Discovered:");
	public static JLabel validScheduleLabel = new JLabel("Current Valid Schedule:");
	public static JLabel semaphoreLabel = new JLabel("Semaphor thread counter: ");

	public long nodeNumber = 0;
	public long bestTimeCount = 0;
	public long validScheduleCount = 0;
	public long equalBestTimeCount = 0;
	// Stores the time for the current shortest schedule.
	public static int minimumTime = Integer.MAX_VALUE;
	// A list containing the current best schedule.
	protected static List<NodeObject> bestSchedule = new ArrayList<NodeObject>();
	
	protected AdjacencyList inputGraph;
	private List<String> nodeList;
	protected int numberofProcessors;
	SolutionTree solution;
	
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
		new NodeObject(0, new ArrayList<NodeObject>(), "rootNode", new int[numberofProcessors], 0, 0);		
	}
	
	/**
	 * Updates the Solution Tree information to the graph view model
	 * @param currentNode
	 */
	public void updateFirstGraph(NodeObject currentNode){
		//counts the number of valid schedules
		validScheduleCount++;
		validScheduleCountLabel.setText("Valid Schedules Discovered: "+Long.toString(validScheduleCount));

		String procText = new String();
		ArrayList<Integer> procList = new ArrayList<Integer>();
		for(NodeObject node : currentNode.getCurrentPath()){
			int processor = node.getProcessor();
			if(!procList.contains(processor)){
				procList.add(processor);
				procText += "["+Integer.toString(processor+1)+"] ";
			}
		}
		String idleText = new String();
		for(int i = 0; i < numberofProcessors; i++){
			if(!procList.contains(i)){
				idleText += "["+Integer.toString(i+1)+"] ";
			}
		}
		if (idleText.isEmpty()){
			idleText = "None";
		}
		//find the number of processors that are used
		processorsUsedLabel.setText("Processors Used: "+ procText);
		//find the number of processors that are un-used
		idleProcessorsLabel.setText("Idle Processors: "+ idleText);

		//find the current valid schedule
		String currentValidSchedule = new String();
		for(NodeObject node : currentNode.getCurrentPath()){
			if(!(node.getNodeName()=="rootNode")){
				currentValidSchedule += node.getNodeName() +"("+(node.getProcessor()+1)+")";
			}
		}
		
		//display valid schedule
		validScheduleLabel.setText("Current Valid Schedule: "+currentValidSchedule);

	}
	
	/**
	 * Updates the GUi with the display for best path at current time
	 * @param currentNode
	 * @param maxTime
	 * @param endArraya
	 */
	public void updateGraph(NodeObject currentNode, int maxTime, int[] endArraya){ // not updating
		//count how many times we found a faster path than before
		bestTimeCount++;
		//when tree all the way down, and the time is lower than the global flag, set the new time
		//and set the new schedule to it
		for (Edge e : bestTimeTree.getEdgeSet()) {
			e.addAttribute("ui.style", "fill-color:#ADB5C7;");
		}
		bestTimeLabel.setText("Current Best Time: " + maxTime);
		bestTimeCountLabel.setText("Faster Schedules Found: " + Long.toString(bestTimeCount));
		
		//find the times in each processor
        int[] endArray = endArraya;
        String endArrayString = new String();
        int procID = 1;
        for(int eA : endArray){
            endArrayString+=Integer.toString(procID)+": "+eA+" ";
            procID++;
        }
        processorEndTimeLabel.setText("Processor End Time: "+endArrayString);
		String oldNode = new String();
		String newNode = new String();
		int i = 0;
		Edge e;
		String bestPath = new String();
		//display the best schedules in tree form
		for (NodeObject node : currentNode.getCurrentPath()) {
			if (node.getNodeName().equals("rootNode"))
				continue;
			newNode += node.getNodeName() + node.getProcessor();
			if (bestTimeTree.getNode(newNode) == null) {
				Node n = bestTimeTree.addNode(newNode);

				n.addAttribute("ui.label", node.getNodeName() + "(" + (node.getProcessor()+1) + ")");
//                n.addAttribute("ui.label", nid);
				n.addAttribute("layout.frozen");

				n.addAttribute("y", -i *15);
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
				bestTimeTree.removeEdge(newNode, oldNode);
				e = bestTimeTree.addEdge(Integer.toString(nid), newNode, oldNode);
				e.setAttribute("ui.style", "fill-color:red;");

			}

			oldNode = newNode;
			i++;
			if (nid < 0)
				nid--;
			else
				nid++;
			//SPEED FACTOR: lower=faster
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
	
	/**
	 * visualisation in parallel
	 * @param currentNode
	 * @param maxTime
	 * @param endArraya
	 */
	public void oneLineGraph(NodeObject currentNode, int maxTime, int[] endArraya) {
		bestTimeCount++;
        int[] endArray = endArraya;
        String endArrayString = new String();
        int procID = 1;
        for(int eA : endArray){
            endArrayString+=Integer.toString(procID)+": "+eA+" ";
            procID++;
        }
        //reset after each graph creation
		bestTimeLabel.setText("Current Best Time: " + maxTime);
		bestTimeCountLabel.setText("Faster Schedules Found: " + Long.toString(bestTimeCount));
		processorEndTimeLabel.setText("Processor End Time: "+endArrayString);
		bestTimeTree.clear();
		bestTimeTree.addAttribute("ui.stylesheet", "node { " +
				"size:50px; " +
				"text-color:#000000; " +
				"text-size:18px; " +
				"text-alignment:at-right;" +
				"text-padding: 3px;" +
				"text-background-mode:none;" +
				"shape:cross;"+
				"fill-color:red;" +
				"}" +
				"edge { " +
				"text-color:#AD2A1A; " +
				"text-size:24px; " +
				"text-background-mode:rounded-box;" +
				"size:6px;" +
				"}" +
				"graph{" +
				"fill-color:#CCFFFF;" +
				"}");
		
		//store the new path and fields such as nodes which are used to make graph
		String bestPath = new String();
		bestPath = currentNode.getCurrentPath().get(1).getNodeName() + "(" + currentNode.getCurrentPath().get(1).getProcessor() + ")";
		int i = 0;
		Node n = bestTimeTree.addNode(currentNode.getCurrentPath().get(1).getNodeName());
		n.addAttribute("ui.label", "Name:"+currentNode.getCurrentPath().get(1).getNodeName() + " On Processor(" + currentNode.getCurrentPath().get(1).getProcessor() + ")"
				+" Start_time: "+ currentNode.getCurrentPath().get(1).getStartTime() + " , End_time: "+ currentNode.getCurrentPath().get(1).getEndTime());
		n.addAttribute("x", i);
		n.addAttribute("y", i);
		i++;
		
		//iterate through the path and make it into a graph
		for(int j = 2; j<currentNode.getCurrentPath().size()-1;j++){
			Node x = bestTimeTree.addNode(currentNode.getCurrentPath().get(j).getNodeName());
			x.addAttribute("ui.label", "Name:"+currentNode.getCurrentPath().get(j).getNodeName() + " On Processor(" + currentNode.getCurrentPath().get(j).getProcessor() + ")"
					+" Start_time: "+ currentNode.getCurrentPath().get(j).getStartTime() + " , End_time: "+ currentNode.getCurrentPath().get(j).getEndTime());
			x.addAttribute("x", i);
			x.addAttribute("y", i);
			bestTimeTree.addEdge(n.toString()+x.toString(), n, x, true);
			n=x;
			i++;
			bestPath += currentNode.getCurrentPath().get(j).getNodeName() + "(" + (currentNode.getCurrentPath().get(j).getProcessor() + 1) + ") ";

		} 
		try {
			Thread.sleep(100);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		bestTimeScheduleLabel.setText("Best Path: " + bestPath);

	}

	/**
	 * update the node count
	 * @param nodeNumber2
	 */
	public void updateNodeNumber(long nodeNumber2) {
		nodesSearchedLabel.setText("Nodes Searched: "+Long.toString(nodeNumber2));
	}
	/**
	 * Notifies the graph that a new schedule was found.
	 * @param input
	 */
	public void notifyGraph(NodeObject input, int maxtime, int[] end){
		if(UserOptions.getInstance().isParallel()){
			oneLineGraph(input, maxtime, end);
		}else{
			updateGraph(input, maxtime, end );
			
		}
	}
	
	/**
	 * notifies the first part of inputs used in Solution Tree
	 * @param input
	 */
	public void notifyFirstGraph(NodeObject input){
			updateFirstGraph(input);
	}
	
	/**
	 * notifies the second part of inputs used in Solution Tree
	 * @param input
	 */
	public void notifySecondGraph(long number){
			updateNodeNumber(number);
	}
	
	/**
	 * notifies the information required to show parallel information
	 * @param input
	 */
	public void notifyParallelGraph(long number, int semaphor){
		nodesSearchedLabel.setText("Nodes Searched: "+Long.toString(number));
		semaphoreLabel.setText("Semaphor thread counter: " + semaphor);
	}
	
	
	/**
	 * initialises the visual class in main method. Sets the pannels and fields such that if visual is true we have this made
	 */
	public static void isVisual() {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		GraphVisualiser.bestTimeTree.addAttribute("ui.stylesheet", "node { " +
				"size:12px; " +
				"text-size:12px; " +
				"text-alignment:above;" +
				"text-padding: 3px;" +
				"text-background-mode:rounded-box;" +
				"}" +
				"edge { " +
				"text-size:13px; " +
				"text-background-mode:rounded-box;" +
				"size:5px;" +
				"}" +
				"graph{" +
				"fill-color:#D6D8DD;" +
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

		
		//pannels used in visualisation
		JPanel statPanel = new JPanel();
		statPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
		statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));

		JPanel bestPathStats = new JPanel();
		bestPathStats.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		bestPathStats.add(GraphVisualiser.bestTimeLabel);
		bestPathStats.add(GraphVisualiser.bestTimeScheduleLabel);
		bestPathStats.add(GraphVisualiser.processorEndTimeLabel);
		bestPathStats.add(GraphVisualiser.bestTimeCountLabel);
		statPanel.add(bestPathStats);

		JPanel countPanel = new JPanel();
		countPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		countPanel.add(GraphVisualiser.nodesSearchedLabel);
		statPanel.add(countPanel);

		JPanel validSchedulePanel = new JPanel();
		validSchedulePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		validSchedulePanel.add(GraphVisualiser.validScheduleCountLabel);
		validSchedulePanel.add(GraphVisualiser.validScheduleLabel);
		statPanel.add(validSchedulePanel);

		JPanel processorPanel = new JPanel();
		processorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		processorPanel.add(GraphVisualiser.processorsUsedLabel);
		processorPanel.add(GraphVisualiser.idleProcessorsLabel);
		statPanel.add(processorPanel);
		if(UserOptions.getInstance().isParallel()){
			JPanel paraPanel = new JPanel();
			paraPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
			paraPanel.add(GraphVisualiser.semaphoreLabel);
			JLabel threadIDLabel = new JLabel("Number of Threads to Use:" + UserOptions.getInstance().getParallelThreads());
			paraPanel.add(threadIDLabel);
			statPanel.add(paraPanel);

		}

		tV.setPreferredSize(dim);
		panel.add(tV, BorderLayout.CENTER);
		panel.add(statPanel, BorderLayout.PAGE_START);
		//setting the frame and enabling visibility
		frame.getContentPane().add(panel);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

		frame.setVisible(true);

	}

}

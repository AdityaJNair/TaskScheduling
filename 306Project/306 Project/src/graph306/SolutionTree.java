package graph306;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class creates a solution tree from the input adjacency list.
 * 
 *
 */
public class SolutionTree {
	public static Graph gsGraph = new SingleGraph("DAG");

	// Stores the time for the current shortest schedule.
	private static int minimumTime = Integer.MAX_VALUE;
	// A list containing the current best schedule.
	private static List<NodeObject> bestSchedule = new ArrayList<NodeObject>();

    private int nid = 0;
	private String oldNode = "";
	private String newNode;
	private AdjacencyList inputGraph;
	private NodeObject rootNode;
	private List<String> nodeList;
	private int numberofProcessors;
	
	/**
	 * Constructor that initialises the adjacency list to this class and makes a list of all nodes to use when checking if a node has been seen or not
	 * @param inputGraph
	 */
	public SolutionTree(AdjacencyList inputGraph){
		this.inputGraph = inputGraph;
		nodeList = new ArrayList<String>();
		for(String entry : inputGraph.getIndices().keySet()){
    		nodeList.add(entry);
    	}
		numberofProcessors = UserOptions.getInstance().getProcessors();
		//create the root node object
		rootNode = new NodeObject(0, new ArrayList<NodeObject>(), "rootNode", new int[numberofProcessors], 0, 0);		
	}
	
	/** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 */
	public void calculateTime(NodeObject currentNode, List<String> nodesToCheck) throws InterruptedException {
		// Exit condition for exiting recursion
		if(nodesToCheck.size() == 0){
			// Calculate time
			// Compare with minimumTime to see if this solution is better
			if(maxTimeAtPoint(currentNode) < minimumTime){
				//when tree all the way down, and the time is lower than the global flag, set the new time
				//and set the new schedule to it
				gsGraph.addAttribute("ui.style", "fill-color:black;");
				minimumTime = maxTimeAtPoint(currentNode);
				bestSchedule = currentNode.getCurrentPath();
				String oldNode = new String();
				String newNode = new String();
				int i = 0;
				for(NodeObject node : currentNode.getCurrentPath()){
					if(node.getNodeName().equals("rootNode"))
						continue;
					newNode+=node.getNodeName()+node.getProcessor();
					if(gsGraph.getNode(newNode)==null) {
						Node n = gsGraph.addNode(newNode);
//						n.addAttribute("ui.label", newNode);
						if (i > 0) {
							Edge e = gsGraph.addEdge(Integer.toString(nid), newNode, oldNode);
							e.addAttribute("ui.label", Integer.toString(node.getEndTime()));
							e.addAttribute("ui.style","fill-color: cyan;");
						}
					}
					oldNode = newNode;
					i++;
					nid++;
					Thread.sleep(20);
				}
			}
			return;
		}
		
		//if the time of current node but has not finished path is greater than optimal path which has finished dont bother looking
		if(maxTimeAtPoint(currentNode) >= minimumTime){
			return;
		}
		// Look through the list of unseen nodes and recursively call this method on nodes 
		// that do not have any parents on the nodesToCheck list.
		for(String nodeToCheckStr : nodesToCheck){
			if(isValidOption(nodeToCheckStr, nodesToCheck)){
				for(int j = 0; j < numberofProcessors; j++){
					
					//UPDATE THE NEW NODE FOR RECURSION
					
					//create a newpath that is the same as current which includes the currentNode as well
					//same thing but only copying the processor array --not checking for times at this place
					ArrayList<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());
					int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);
					
					//initialising the fields for the new NodalObject to recurse through
					String newNodeName = nodeToCheckStr;
					int newProcessor = j;
					int nodalWeight = getNodalWeight(newNodeName);
					int newStartTime = checkProcessStartTimeTask(currentNode, newNodeName, newProcessor);
					int newEndTime = newStartTime+nodalWeight;
					processorArray[newProcessor] = newEndTime;
					
					//INITIALISE THE NEW NODE WITH UPDATED FIELDS
					NodeObject nextNode = new NodeObject(newProcessor, nextPath, newNodeName, processorArray, newStartTime, newEndTime);
					//copy the nodesToCheck list and need to remove the current node from it for recursion
					List<String> newUpdatedListWithoutCurrentNode = new LinkedList<String>(nodesToCheck);
					newUpdatedListWithoutCurrentNode.remove(newNodeName);
					String nn = new String();
//					int oldNodePos = 0;
//					for (NodeObject temp : currentNode.getCurrentPath()){
//						if (temp.getNodeName().equals("rootNode")) {
//							continue;
//						}
//						nn+= temp.getNodeName() + Integer.toString(temp.getProcessor());
//						if (oldNodePos == currentNode.getCurrentPath().size()-2){
//							oldNode = nn;
//						}
//						oldNodePos++;
//					}
					//recursive call
//                    Thread.sleep(10);
					calculateTime(nextNode, newUpdatedListWithoutCurrentNode);
				}
			}		
		}
	}
	
	/**
	 * Checks if the node is a valid option for option tree
	 * @param node : The node we are checking the validity for.
	 * @param nodesToCheck : List of unseen nodes at a given point in time
	 * @return
	 */
	private boolean isValidOption(String node, List<String> nodesToCheck){
		
		//root node
		if(checkAdjacencyListNullMap(node)){ 
			return true;
		}
		
			// valid if element has no parent. 
			//inside this if statement if a parent is present that has not been seen this will be set to true and return false
			//for an invalid option
			 if(checkValidSolutionDepency(node, nodesToCheck)){
				return false;
			}
		return true;
	}
	
	/**
	 * Using the adjacency list, get the index of the String node using the indicies map.
	 * Once the index is found, get the map in the adjanceny list and check the size of the map
	 * If the map size is 0 then it is a source node that we have not seen so it is a valid option.
	 * @param nodeName
	 * @return
	 */
	public boolean checkAdjacencyListNullMap(String nodeName){
		
		int indexAtListForMap = inputGraph.getIndices().get(nodeName);
		if(inputGraph.getAdjacencyList().get(indexAtListForMap).size() == 0){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Check if the node we are currently looking at has unseen parent nodes.
	 * @param nodeName
	 * @param nodesToCheck
	 * @return
	 */
	public boolean checkValidSolutionDepency(String nodeName, List<String> nodesToCheck){
		//found c
		int indexAtListForMap = inputGraph.getIndices().get(nodeName);
		//get map for c
		for(String entry : inputGraph.getAdjacencyList().get(indexAtListForMap).keySet()){
			//we check to see if the nodesToCheck has a or b inside of it.
			//if it does that means we have not seen a node that it is dependent on. So invalid
			if(nodesToCheck.contains(entry)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Find the largest time in the processor assuming that the node is updated.
	 * Each element in this array has the end time for the index processor (assuming index 0 = processor 1)
	 * @param node
	 * @return largest end time amongst all processors
	 */
	public int maxTimeAtPoint(NodeObject node){
		int largest = -1;
		for(int i: node.getTimeWeightOnEachProcessor()){
			if(i > largest){
				largest = i;
			}
		}
		return largest;
		
	}
	
	/**
	 * checkProcessStartTimeTask finds the time when to add the task on the processor. This is required as need to check on the
	 * constraints which are the communication costs. The dependencies constraint is met in the checkValidSolutionDepency method called
	 * before. So just finds the start time for the particular task.
	 * @return finds the start time for the particular task.
	 */
	public int checkProcessStartTimeTask(NodeObject currentNode, String newNode, int processor){
		if(checkAdjacencyListNullMap(newNode)){
			//returns the endtime for particular processor as the new start time if the node in question was a source node
			//so it has no dependencies and can be placed directly at the end of any processor
			return currentNode.getTimeWeightOnEachProcessor()[processor];
		}else{
			//has parents
			int maxTime = -1;
			int tmpTime = -1;
			//iterate through the current path of nodes visited to see the latest time to add the particular node
			for(NodeObject node: currentNode.getCurrentPath()){
				//check parents if processor is same
				if(isDependent(node,newNode)){
					if(node.getProcessor() == processor){
						//if it is same process, the next time it can go on is directly after it so endtime = starttime
						tmpTime = node.getEndTime();
					} else {
						//if process are different; add communication cost so next time it can go on is directly after the communication cost.
						tmpTime = node.getEndTime() + getEdgeWeight(node, newNode);
					}
				}
				//getting the greater of the times, such that maxTime is the latest point where we can add that node in processor
				//this is only based on the dependency rule. So only dependencies are checked to see if the constraints are met.
				if(maxTime < tmpTime){
					maxTime = tmpTime;
				}
			}
			//This is the case when all dependencies are finished and the node can just be added at the end of the processor
			//Can act as a source node since the startTime would be greater than the maxTime if added after a dependency.
			if(currentNode.getTimeWeightOnEachProcessor()[processor] > maxTime){
				return currentNode.getTimeWeightOnEachProcessor()[processor];
			} else {
				//if the dependency requires a time greater than the endtime of latest node in same processor. 
				//Must mean there is a communication cost required from a different processor
				//The startTime to run on current processor
				return maxTime;
			}
		}
	}


	/**
	 * Checks if the newNode(String) is dependent on node(Any particular Node with a string name)
	 * @return true if is dependent and is inside the adjacency list : false if not in the map
	 */
	public boolean isDependent(NodeObject node, String newNode){
		//if the newNode in the adjacency list has the NodalObject that is a parent to it, return true
		int index = inputGraph.getIndices().get(newNode);
		if(inputGraph.getAdjacencyList().get(index).containsKey(node.getNodeName())){
			return true;
		}
		//if not a parent return false
		return false;
	}

	
	/**
	 * getting the nodal weight from a string of node name
	 * @param node
	 * @return
	 */
	public int getNodalWeight(String node){
		if(node.equals("rootNode")){
			return 0;
		}
		return inputGraph.getNodeWeights().get(node);
	}
	
	/**
	 * Getting the edge weight from the currentNode to the new valid node
	 * @param currentNode
	 * @param nextNode
	 * @return
	 */
	public int getEdgeWeight(NodeObject currentNode, String nextNode){
		if(currentNode.getNodeName().equals("rootNode")){
			return 0;
		}
		int index = inputGraph.getIndices().get(nextNode);
		int value = inputGraph.getAdjacencyList().get(index).get(currentNode.getNodeName());
		return value;
	}

	/*
	 * GETTERS AND SETTERS FOR THIS CLASS
	 */
	
	
	public static int getMinimumTime() {
		return minimumTime;
	}

	public static void setMinimumTime(int minimumTime) {
		SolutionTree.minimumTime = minimumTime;
	}

	public static List<NodeObject> getBestSchedule() {
		return bestSchedule;
	}

	public static void setBestSchedule(List<NodeObject> bestSchedule) {
		SolutionTree.bestSchedule = bestSchedule;
	}

	public AdjacencyList getInputGraph() {
		return inputGraph;
	}

	public void setInputGraph(AdjacencyList inputGraph) {
		this.inputGraph = inputGraph;
	}

	public NodeObject getRootNode() {
		return rootNode;
	}

	public void setRootNode(NodeObject rootNode) {
		this.rootNode = rootNode;
	}

	public List<String> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<String> nodeList) {
		this.nodeList = nodeList;
	}

	
}

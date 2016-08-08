package graph306;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class creates a solution tree from the input adjacency list.
 * @author Priyankit
 *
 */
public class SolutionTree {

	// Stores the time for the current shortest schedule.
	private static int minimumTime = Integer.MAX_VALUE;
	// A list containing the current best schedule.
	private static List<NodeObject> bestSchedule = new ArrayList<NodeObject>();
	private AdjacencyList inputGraph;
	private NodeObject rootNode;
	private List<String> nodeList;
	private int numberofProcessors;
	
	/**
	 * 
	 * @param inputGraph
	 */
	public SolutionTree(AdjacencyList inputGraph){
		this.inputGraph = inputGraph;
		nodeList = new ArrayList<String>();
		for(String entry : inputGraph.getIndices().keySet()){
    		nodeList.add(entry);
    	}
		numberofProcessors = UserOptions.getInstance().getProcessors();
		rootNode = new NodeObject(0, new ArrayList<NodeObject>(), "rootNode", new int[numberofProcessors]);		
	}
	
	/** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 */
	public void calculateTime(NodeObject currentNode, List<String> nodesToCheck){
		// Exit condition for exiting recursion
		if(nodesToCheck.size() == 0){
			// Calculate time
			// Compare with minimumTime to see if this solution is better
			if(maxTimeAtPoint(currentNode) < minimumTime){
				//when tree all the way down, and the time is lower than the global flag, set the new time
				//and set the new schedule to it
				minimumTime = maxTimeAtPoint(currentNode);
				bestSchedule = currentNode.getCurrentPath();
			}
			return;
		}
		//if the time of current node but has not finished path is greater than optimal path which has finished dont bother looking
		if(maxTimeAtPoint(currentNode) >= minimumTime){
			return;
		}
		// Look through the list of unseen nodes and recursively call this method on nodes 
		// that do not have any parents on the nodesToCheck list.
		for(int i = 0 ; i < nodesToCheck.size() ; i++){
			if(isValidOption(nodesToCheck.get(i), nodesToCheck)){	
				for(int j = 0; j < numberofProcessors; j++){
					List<NodeObject> nextPath = new ArrayList<NodeObject>(currentNode.getCurrentPath());
					int[] processorArray = Arrays.copyOf(currentNode.getTimeWeightOnEachProcessor(), currentNode.getTimeWeightOnEachProcessor().length);
					
					
					
					
					
					//NodeObject nextNode = new NodeObject(j, ));
				}
				//for loop for each processor
				//create a node object
				//update the nodelist for the recursion only
				//cost of weight
				//time if it is greater than the minimumBest time
				
				//List<String> listForChild = nodesToCheck;
				//listForChild.remove(i);
				// create new node object for each processor
				//String nextNodeName = nodesToCheck.get(i);
				//NodeObject nextNode = getNodeObject(nextNodeName);
				//calculateTime(nextNode, listForChild);
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
		
		// Loop through the nodes that depend on current node and see if they are present in nodesToCheck
		for(int i = 0 ; i < nodesToCheck.size(); i++){
			// valid of element has no parent. 
			 if(checkValidSolutionDepency(nodesToCheck.get(i), nodesToCheck)){
				// return false if one of the parents is present in the nodesToCheck list
				return true;
			}
		}
		return false;
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
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Find the largest time in the processor assuming that the node is updated.
	 * @param node
	 * @return
	 */
	public int maxTimeAtPoint(NodeObject node){
		int largest = -1;
		for(int i=0; i< node.getTimeWeightOnEachProcessor().length -1 ; i++){
			if(node.getTimeWeightOnEachProcessor()[i] > largest){
				largest = node.getTimeWeightOnEachProcessor()[i];
			}
		}
		return largest;
		
	}
	
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

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
		int numberofProcessors = UserOptions.getInstance().getProcessors();
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
		// Look through the list of unseen nodes and recursively call this method on nodes 
		// that do not have any parents on the nodesToCheck list.
		for(int i = 0 ; i < nodesToCheck.size() ; i++){
			if(isValidOption(nodesToCheck.get(i), nodesToCheck)){
				List<String> listForChild = nodesToCheck;
				listForChild.remove(i);
				// create new node object for each processor
				String nextNodeName = nodesToCheck.get(i);
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
			 if(){
				// return false if one of the parents is present in the nodesToCheck list
				return false;
			}
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
		//
		for(String entry : inputGraph.getAdjacencyList().get(indexAtListForMap).keySet()){
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

package graph306;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a solution tree from the input adjacency list.
 * @author Priyankit
 *
 */
public class SolutionTree {
	private List nodeList = new ArrayList();
	// Stores the time for the current shortest schedule.
	private static int minimumTime = Integer.MAX_VALUE;
	// A list containing the current best schedule.
	private static List<NodeObject> bestSchedule = new ArrayList<NodeObject>();
	private AdjacencyList inputGraph;
	
	public SolutionTree(AdjacencyList inputGraph){
		this.inputGraph = inputGraph;
		for(String entry : inputGraph.getIndices().keySet()){
    		nodeList.add(entry);
    	}
	}
	
	/** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 */
	public void calculateTime(NodeObject currentNode, List<String> nodesToCheck){
		// Exit condition for exiting recursion
		if(nodesToCheck.size() == 0){
			// Calculate time
			// Compare with minimumTime to see if this solution is better
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
				NodeObject nextNode = getNodeObject(nextNodeName);
				calculateTime(nextNode, listForChild);
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
		// Get the parents of current node inputGraph.getParents()
		ArrayList<NodeObject> parentList = new ArrayList<NodeObject>(); // CHANGE THIS TO getDependencies() CALLED ON AdjacencyList
		
		// valid of element has no parent. 
		if(parentList.isEmpty()){ 
			return true;
		}
		// Loop through the nodes that depend on current node and see if they are present in nodesToCheck
		for(int i = 0 ; i < parentList.size(); i++){
			if(nodesToCheck.contains(parentList.get(i))){
				// return false if one of the parents is present in the nodesToCheck list
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * Gets a node object from its name.
	 * @param nodeName
	 * @return
	 */
	public NodeObject getNodeObject(String nodeName){
		return null;
		
	}
	
}

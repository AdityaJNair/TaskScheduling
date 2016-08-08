package graph306;

import java.util.ArrayList;
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
	
	public AdjacencyList inputGraph;
	
	public SolutionTree(AdjacencyList inputGraph){
		this.inputGraph = inputGraph;
	}
	
	/** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 */
	private void calculateTime(List<NodeObject> nodesToCheck){
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
				List<NodeObject> listForChild = nodesToCheck;
				listForChild.remove(i);
				// create new node object for each processor
				// calculateTime(listForChild);
			}		
		}
	}
	
	/**
	 * Checks if the node is a valid option for option tree
	 * @param parentList
	 * @param nodesToCheck
	 * @return
	 */
	private boolean isValidOption(NodeObject node, List<NodeObject> nodesToCheck){
		// Get the parents of current node inputGraph.getParents()
		ArrayList<NodeObject> parentList = new ArrayList<NodeObject>(); // CHANGE THIS TO getDependencies() CALLED ON AdjacencyList
		
		// Loop through the nodes that depend on current node and see if they are present in nodesToCheck
		for(int i = 0 ; i < parentList.size(); i++){
			if(nodesToCheck.contains(parentList.get(i))){
				// return false if one of the parents is present in the nodesToCheck list
				return false;
			}
		}
		
		return true;
		
	}
	
	
}

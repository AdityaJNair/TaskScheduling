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
	 * @param nodesToCheck
	 */
	private void calculateTime(List<NodeObject> nodesToCheck){
		if(nodesToCheck.size() == 0){
			// Calculate time
			return;
		}
		
		
		for(int i = 0 ; i < nodesToCheck.size() ; i++){
			
			
			if( true/* currentNodes[i]'s parents are not in the list*/){
				List<NodeObject> listForChild = nodesToCheck;
				listForChild.remove(i);
				
//				for(int j = 0 ; j < processorNumber ; j++){
//					// create new node object for each processor
//					calculateTime(listForChild);
//				}
				
			}
			
		}
		
	}
}

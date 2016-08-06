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
	
	
}

/**
 * 
 */
package graph306;
import pt.queues.FifoLifoQueue;
import pt.runtime.*;

import java.util.List;

/**
 * @author Priyankit
 *
 */
public class ParallalSolutionTree extends SolutionTree {

	FifoLifoQueue<List<NodeObject>> bestScheduleThreaded = new FifoLifoQueue<List<NodeObject>>();
	FifoLifoQueue<Integer> bestTimeThreaded= new FifoLifoQueue<Integer>();
	
	/**
	 * @param inputGraph
	 */
	public ParallalSolutionTree(AdjacencyList inputGraph) {
		super(inputGraph);
		bestTimeThreaded.addGlobal(Integer.MAX_VALUE);
		bestScheduleThreaded.addGlobal(null);
	}
	
	/** 
	 * Pseudocode for recursively calculating the time taken to execute each path
	 * in the solution tree.
	 * @param nodesToCheck - List of nodes that have not yet been seen in this branch
	 * of the solution tree
	 * @param currentNode - the current node 
	 */
	public void calculateTime(NodeObject currentNode, List<String> nodesToCheck, boolean isThreaded){
		if(!isThreaded){
			calculateTime(currentNode, nodesToCheck);
			return;
		}
		
		// create threads here.
		TaskIDGroup id1 = new TaskIDGroup(20);
	}

}

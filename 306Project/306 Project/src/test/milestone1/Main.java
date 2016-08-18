package milestone1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graph306.CustomGraphReader;
import graph306.NodeObject;
import graph306.SolutionTree;
import graph306.UserOptions;

public class Main {

	public static Long[] main(String[] args) throws IOException {
		//start timing
		long startTime = System.nanoTime();

		CustomGraphReader graphReader = new CustomGraphReader(args);
		//run a read method on DAG
		graphReader.readDAG();
		SolutionTree solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
		solver.calculateTime(solver.getRootNode(), solver.getNodeList());
		
		//end timing
		//Edited to output return values for testing.
		Long[] times = new Long[2];
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
		times[0] = duration;							//The runtime for the algorithm. Anything we do must improve this.
		times[1] = new Long(solver.getMinimumTime());	//The optimal time for the schedule. Anything we do must return this.
		return times;
//			//Details of each node in the schedule. Not important.
//			for(NodeObject a: solver.getBestSchedule()) {
//				System.out.println("Node_name= " + a.getNodeName() + " Processor= " + (a.getProcessor() + 1) + " Start_time= " + a.getStartTime() + " End_time= " + a.getEndTime());
//			}
//		//Create .dot file at the end. Not important.
//		DotWriter writer = new DotWriter();
//		writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());
	}

}

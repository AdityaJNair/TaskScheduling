package milestone1;

import java.io.IOException;

import milestone1.oldgraph306.CustomGraphReader;
import milestone1.oldgraph306.SolutionTree;

public class Main {

	public static Long[] main(String[] args) throws IOException {

		Long[] times = new Long[2];
		long startTime = System.nanoTime();

		//for (int i=0; i<3; i++) {
			CustomGraphReader graphReader = new CustomGraphReader(args);
			//run a read method on DAG
			graphReader.readDAG();
			SolutionTree solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
			solver.calculateTime(solver.getRootNode(), solver.getNodeList());
			//if (i==0){
				times[1] = new Long(solver.getMinimumTime());	//The optimal time for the schedule.
			//}
		//}

		long endTime = System.nanoTime();

		long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
		//duration = duration/3;
		times[0] = duration;							//The runtime for the algorithm. Anything we do must improve this.
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

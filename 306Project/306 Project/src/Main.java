import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graph306.CustomGraphReader;
import graph306.NodeObject;
import graph306.SolutionTree;
import graph306.UserOptions;

public class Main {

	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();

		CustomGraphReader graphReader = new CustomGraphReader(args);
		//run a read method on DAG
		graphReader.readDAG();
		SolutionTree solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
		solver.calculateTime(solver.getRootNode(), solver.getNodeList());
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
		System.out.println(duration+" miliseconds");
		System.out.println("Minimum cost of time "+solver.getMinimumTime());
		for(NodeObject a: solver.getBestSchedule()){
			System.out.println(a.getNodeName() + "  " + a.getProcessor() + "  " + a.getStartTime() + "  " + a.getEndTime());
		}
		
		DotWriter writer = new DotWriter();
		writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());
		
	}

}

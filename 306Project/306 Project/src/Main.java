import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graph306.CustomGraphReader;
import graph306.NodeObject;
import graph306.SolutionTree;

public class Main {

	public static void main(String[] args) throws IOException {
		CustomGraphReader graphReader = new CustomGraphReader(args);
		//run a read method on DAG
		graphReader.readDAG();
		SolutionTree solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
		solver.calculateTime(solver.getRootNode(), solver.getNodeList());
		System.out.println();
		System.out.println(solver.getMinimumTime());
		for(NodeObject a: solver.getBestSchedule()){
			System.out.println(a.getNodeName() + "  " + a.getProcessor() + "  " + a.getStartTime() + "  " + a.getEndTime());
		}
	}

}

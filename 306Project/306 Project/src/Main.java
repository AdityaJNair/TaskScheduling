import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import graph306.CustomGraphReader;
import graph306.NodeObject;
import graph306.Parallalism;
import graph306.SolutionTree;
import graph306.UserOptions;
import pt.runtime.ParaTask;

public class Main {
	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();

		ParaTask.init();
		CustomGraphReader graphReader = new CustomGraphReader(args);
		//run a read method on DAG
		graphReader.readDAG();
		if(UserOptions.getInstance().isParallel()){
			System.out.println("Doing process in Parallel mode");
			Parallalism parallal = new Parallalism(graphReader.getGraphAdapter().getAdjacencyList());
			parallal.calculateTime(parallal.getRootNode(), parallal.getNodeList(), true);
			DotWriter writer = new DotWriter();
			writer.createDot(parallal.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),parallal.getInputGraph());
			System.out.println(parallal.nodeNumber);
		} else {
			System.out.println("Doing process in sequential mode");
			SolutionTree solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
			solver.calculateTime(solver.getRootNode(), solver.getNodeList());
			DotWriter writer = new DotWriter();
			writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());
			System.out.println(solver.nodeNumber);
		}		
		
		//end timing
		//	print statements to see the time and the minimum path time
//			long endTime = System.nanoTime();
//			long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
//			System.out.println(duration+" miliseconds");
//			System.out.println("Minimum time to do all tasks "+solver.getMinimumTime());
//			for(NodeObject a: solver.getBestSchedule()){
//				System.out.println("Node_name= "+a.getNodeName() + " Processor= " + (a.getProcessor()+1) + " Start_time= " + a.getStartTime() + " End_time= " + a.getEndTime());
//			}
		//Create .dot file at the end
		
		System.out.println("Output file was created");
	}

}

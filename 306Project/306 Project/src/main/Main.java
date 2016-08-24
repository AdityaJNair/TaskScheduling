package main;
import java.io.IOException;

import org.graphstream.ui.view.View;

import data_structures.UserOptions;
import graph306.CustomGraphReader;
import graph306.ParallelSearchTree;
import graph306.SolutionTree;
import visual.GraphVisualiser;
import visual.SolutionTreeVisual;

public class Main {
	
	public static View view;


	public static void main(String[] args) throws IOException, InterruptedException {      
        
		long startTime = System.nanoTime();

		CustomGraphReader graphReader = new CustomGraphReader(args);
		//run a read method on DAG
		graphReader.readDAG();
		
		
		if(UserOptions.getInstance().isVisible() && UserOptions.getInstance().isParallel()){
			GraphVisualiser.isVisual();
			// Parallel visual team's code here
			System.out.println("Doing process in Parallel visual mode");
			ParallelSearchTree solver = new ParallelSearchTree(graphReader.getGraphAdapter().getAdjacencyList());
			solver.recursiveMethod(solver.getRootNode(), solver.getNodeList(), false);
			//Create .dot file at the end
			DotWriter writer = new DotWriter();
			writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());
			SolutionTreeVisual.bestTimeTree.addAttribute("ui.stylesheet", "graph{fill-color: green;}");
			
		} else if(UserOptions.getInstance().isVisible() && !UserOptions.getInstance().isParallel()){
			GraphVisualiser.isVisual();
			SolutionTree solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
			solver.calculateTime(solver.getRootNode(), solver.getNodeList());
			//Create .dot file at the end
			DotWriter writer = new DotWriter();
			writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());
			SolutionTreeVisual.bestTimeTree.addAttribute("ui.stylesheet", "graph{fill-color: green;}");

		} else if(UserOptions.getInstance().isParallel() && !UserOptions.getInstance().isVisible()){
			ParallelSearchTree solver = new ParallelSearchTree(graphReader.getGraphAdapter().getAdjacencyList());
			solver.recursiveMethod(solver.getRootNode(), solver.getNodeList(), false);
			//Create .dot file at the end
			DotWriter writer = new DotWriter();
			writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());
		} else {
			SolutionTree solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
			solver.calculateTime(solver.getRootNode(), solver.getNodeList());
			//Create .dot file at the end
			DotWriter writer = new DotWriter();
			writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());
		}
	}

}

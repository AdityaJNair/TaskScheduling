package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import data_structures.NodeObject;
import data_structures.UserOptions;
import graph306.CustomGraphReader;
import graph306.ParallelSearchTree;
import graph306.SolutionTree;
import visual.GraphVisualiser;
import visual.ParallelSearchVisualTree;
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
			//Parallalism solver = new Parallalism(graphReader.getGraphAdapter().getAdjacencyList());
			//solver.calculateTime(solver.getRootNode(), solver.getNodeList(), true);
			solver.recursiveMethod(solver.getRootNode(), solver.getNodeList(), false);
			System.out.println("Nodes visited: " + solver.nodeNumber);
			//Create .dot file at the end
			long endTime = System.nanoTime();
			long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
			System.out.println(duration+" miliseconds");
			System.out.println("Minimum time to do all tasks "+solver.getMinimumTime());
			for(NodeObject a: solver.getBestSchedule()){
				System.out.println("Node_name= "+a.getNodeName() + " Processor= " + (a.getProcessor()+1) + " Start_time= " + a.getStartTime() + " End_time= " + a.getEndTime());
			}
			DotWriter writer = new DotWriter();
			writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());

			SolutionTreeVisual.bestTimeTree.addAttribute("ui.stylesheet", "graph{fill-color: green;}");
			System.out.println(solver.nodeNumber);
			System.out.println("HI3");
			
		} else if(UserOptions.getInstance().isVisible() && !UserOptions.getInstance().isParallel()){
			GraphVisualiser.isVisual();
			SolutionTree solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
			solver.calculateTime(solver.getRootNode(), solver.getNodeList());
			
			//end timing
			//	print statements to see the time and the minimum path time
				long endTime = System.nanoTime();
				long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
				System.out.println(duration+" miliseconds");
				System.out.println("Minimum time to do all tasks "+solver.getMinimumTime());
				for(NodeObject a: solver.getBestSchedule()){
					System.out.println("Node_name= "+a.getNodeName() + " Processor= " + (a.getProcessor()+1) + " Start_time= " + a.getStartTime() + " End_time= " + a.getEndTime());
				}
			//Create .dot file at the end
			DotWriter writer = new DotWriter();
			writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());
			System.out.println(solver.nodeNumber);
			System.out.println("HI");

			SolutionTreeVisual.bestTimeTree.addAttribute("ui.stylesheet", "graph{fill-color: green;}");

		} else if(UserOptions.getInstance().isParallel() && !UserOptions.getInstance().isVisible()){
			// Parallel team's code here
			System.out.println("Doing process in Parallel mode");
			ParallelSearchTree solver = new ParallelSearchTree(graphReader.getGraphAdapter().getAdjacencyList());
			//Parallalism solver = new Parallalism(graphReader.getGraphAdapter().getAdjacencyList());
			//solver.calculateTime(solver.getRootNode(), solver.getNodeList(), true);
			solver.recursiveMethod(solver.getRootNode(), solver.getNodeList(), false);
			System.out.println("Nodes visited: " + solver.nodeNumber);
			//Create .dot file at the end
			long endTime = System.nanoTime();
			long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
			System.out.println(duration+" miliseconds");
			System.out.println("Minimum time to do all tasks "+solver.getMinimumTime());
			for(NodeObject a: solver.getBestSchedule()){
				System.out.println("Node_name= "+a.getNodeName() + " Processor= " + (a.getProcessor()+1) + " Start_time= " + a.getStartTime() + " End_time= " + a.getEndTime());
			}
			DotWriter writer = new DotWriter();
			writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());
			System.out.println(solver.nodeNumber);
			System.out.println("HI3");
		} else {
			System.out.println("Doing process in sequential mode");
			SolutionTree solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
			solver.calculateTime(solver.getRootNode(), solver.getNodeList());
			System.out.println(solver.nodeNumber);
			
			
			//end timing
			//	print statements to see the time and the minimum path time
				long endTime = System.nanoTime();
				long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
				System.out.println(duration+" miliseconds");
				System.out.println("Minimum time to do all tasks "+solver.getMinimumTime());
				for(NodeObject a: solver.getBestSchedule()){
					System.out.println("Node_name= "+a.getNodeName() + " Processor= " + (a.getProcessor()+1) + " Start_time= " + a.getStartTime() + " End_time= " + a.getEndTime());
				}
			//Create .dot file at the end
			DotWriter writer = new DotWriter();
			writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());
			System.out.println(solver.nodeNumber);
			System.out.println("HI3");
		}
		System.out.println("Program finished");
	}

}

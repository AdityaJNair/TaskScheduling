package main;
import java.io.IOException;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.ThreadingModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import data_structures.NodeObject;
import data_structures.UserOptions;
import graph306.CustomGraphReader;
import graph306.SolutionTree;
import visual.SolutionTreeVisual;

public class Main {
	
	public static View view;

    public static class MyFrame extends JFrame
    {
        //private Graph graph = new MultiGraph("embedded");
        //private Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);

        private Viewer viewer = new Viewer(SolutionTreeVisual.gsGraph, ThreadingModel.GRAPH_IN_GUI_THREAD);
        private View view = viewer.addDefaultView(false);

        public MyFrame() {
            setLayout(new BorderLayout());
            this.add((Component) view);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }
    
	public static void main(String[] args) throws IOException, InterruptedException {      
        
		long startTime = System.nanoTime();

		CustomGraphReader graphReader = new CustomGraphReader(args);
		//run a read method on DAG
		graphReader.readDAG();
		if(UserOptions.getInstance().isVisible() && UserOptions.getInstance().isParallel()){
			
			
			
			
			
			
			
		} else if(UserOptions.getInstance().isVisible() && !UserOptions.getInstance().isParallel()){
			
			System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
	        SolutionTreeVisual.gsGraph.addAttribute("ui.stylesheet", "node { " +
	                "size:10px; " +
	                "text-color:red; " +
	                "text-size:20px; " +
	                "text-alignment:above;" +
	                "text-background-mode:rounded-box;" +
	                "}" +
	                "edge { " +
	                "text-color:red; " +
	                "text-size:10px; " +
	                "text-background-mode:rounded-box;" +
	                "size:3px;" +
	                "}");
	        SolutionTreeVisual.gsGraph.addAttribute("ui.antialias");
	        SolutionTreeVisual.gsGraph.setStrict(false);
	        SolutionTreeVisual.gsGraph.addAttribute("ui.quality");
	        Viewer viewer = SolutionTreeVisual.gsGraph.display();
	        view = viewer.getDefaultView();
	        
			SolutionTreeVisual solver = new SolutionTreeVisual(graphReader.getGraphAdapter().getAdjacencyList());
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
			
			
			
		} else if(UserOptions.getInstance().isParallel() && !UserOptions.getInstance().isVisible()){
			
		} else {
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
		}
	}

}

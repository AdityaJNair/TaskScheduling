package main;
import java.awt.geom.Arc2D;
import java.io.IOException;

import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;

import data_structures.NodeObject;
import data_structures.UserOptions;
import graph306.CustomGraphReader;
import graph306.SolutionTree;
import visual.SolutionTreeVisual;

public class Main {
	
	public static View view;


	public static void main(String[] args) throws IOException, InterruptedException {      
        
		long startTime = System.nanoTime();

		CustomGraphReader graphReader = new CustomGraphReader(args);
		//run a read method on DAG
		graphReader.readDAG();
		if(UserOptions.getInstance().isVisible() && UserOptions.getInstance().isParallel()){
			
			
			//if parallel and visual
			
			
			
			
		} else if(UserOptions.getInstance().isVisible() && !UserOptions.getInstance().isParallel()){
<<<<<<< HEAD
			
			
			//if visual only
=======

>>>>>>> b91121853718c3550f4f0672790562207a699a0f
			System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
			SolutionTreeVisual.bestTimeTree.addAttribute("ui.stylesheet", "node { " +
					"size:6px; " +
					"text-color:red; " +
					"text-size:9px; " +
					"text-alignment:above;" +
					"text-background-mode:rounded-box;" +
					"}" +
					"edge { " +
					"text-color:red; " +
					"text-size:13px; " +
					"text-background-mode:rounded-box;" +
					"size:3px;" +
					"}" +
					"graph {" +
					"fill-color:yellow;" +
					"}");
			SolutionTreeVisual.datGraph.addAttribute("ui.stylesheet", "node { " +
					"size:60px; " +
					"text-color:black; " +
					"text-size:15px; " +
					"stroke-mode:plain;" +
					"fill-color:magenta;" +
					"}" +
					"edge { " +
					"text-color:red; " +
					"text-size:13px; " +
					"text-background-mode:rounded-box;" +
					"size:1px;" +
					"}" +
					"graph {" +
					"fill-color:yellow;" +
					"}");
			SolutionTreeVisual.bestTimeTree.addAttribute("ui.antialias");
			SolutionTreeVisual.bestTimeTree.setStrict(false);
			SolutionTreeVisual.bestTimeTree.addAttribute("ui.quality");
			SolutionTreeVisual.datGraph.addAttribute("ui.antialias");
			SolutionTreeVisual.datGraph.setStrict(false);
			SolutionTreeVisual.datGraph.addAttribute("ui.quality");
			Viewer treeViewer = new Viewer(SolutionTreeVisual.bestTimeTree, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
			Viewer graphViewer = new Viewer(SolutionTreeVisual.datGraph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
			graphViewer.enableAutoLayout();
			JFrame frame = new JFrame("Visualization");

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JPanel panel = new JPanel(new BorderLayout());
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            Dimension dim = new Dimension((int)(screenSize.getWidth()/1.5), screenSize.height/3);
			ViewPanel tV = treeViewer.addDefaultView(false);

            JPanel statPanel = new JPanel(new FlowLayout());

            statPanel.add(SolutionTreeVisual.bestTimeLabel);
            statPanel.add(SolutionTreeVisual.nodesSearchedLabel);
            statPanel.add(SolutionTreeVisual.bestTimeCountLabel);
            statPanel.add(SolutionTreeVisual.validScheduleCountLabel);
            statPanel.add(SolutionTreeVisual.bestTimeScheduleLabel);

			tV.setPreferredSize(dim);
			panel.add(tV, BorderLayout.CENTER);
            panel.add(statPanel, BorderLayout.PAGE_START);
			ViewPanel gV = graphViewer.addDefaultView(false);
			gV.setPreferredSize(dim);
			panel.add(gV, BorderLayout.PAGE_END);
//        panel.add(gV, BorderLayout.PAGE_END);
            frame.getContentPane().add(panel);
			frame.setVisible(true);
			frame.pack();

	        
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
			
			//parallel only
		} else {
			
			//normal
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

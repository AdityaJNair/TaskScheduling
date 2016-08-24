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
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import data_structures.NodeObject;
import data_structures.UserOptions;
import graph306.CustomGraphReader;
import graph306.ParallelSearchTree;
import graph306.SolutionTree;
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
			isVisual();
			// Parallel visual team's code here
			System.out.println("Doing process in Parallel visual mode");
			ParallelSearchVisualTree solver = new ParallelSearchVisualTree(graphReader.getGraphAdapter().getAdjacencyList());
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
			
		} else if(UserOptions.getInstance().isVisible() && !UserOptions.getInstance().isParallel()){
			isVisual();
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

	private static void isVisual() {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		SolutionTreeVisual.bestTimeTree.addAttribute("ui.stylesheet", "node { " +
				"size:12px; " +
				"text-size:12px; " +
				"text-alignment:above;" +
				"text-padding: 3px;" +
				"text-background-mode:rounded-box;" +
				"}" +
				"edge { " +
				"text-size:13px; " +
				"text-background-mode:rounded-box;" +
				"size:5px;" +
				"}" +
				"graph{" +
				"fill-color:#D6D8DD;" +
				"}");
		SolutionTreeVisual.bestTimeTree.addAttribute("ui.antialias");
		SolutionTreeVisual.bestTimeTree.setStrict(false);
		SolutionTreeVisual.bestTimeTree.addAttribute("ui.quality");
		Viewer treeViewer = new Viewer(SolutionTreeVisual.bestTimeTree, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		JFrame frame = new JFrame("Visualization");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new BorderLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Dimension dim = new Dimension((int)(screenSize.getWidth()*0.9), (int)(screenSize.height/1.2));
		ViewPanel tV = treeViewer.addDefaultView(false);

		JPanel statPanel = new JPanel();
		statPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
		statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));

		JPanel bestPathStats = new JPanel();
		bestPathStats.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		bestPathStats.add(SolutionTreeVisual.bestTimeLabel);
		bestPathStats.add(SolutionTreeVisual.bestTimeScheduleLabel);
		bestPathStats.add(SolutionTreeVisual.processorEndTimeLabel);
		bestPathStats.add(SolutionTreeVisual.bestTimeCountLabel);
		statPanel.add(bestPathStats);

		JPanel countPanel = new JPanel();
		countPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		countPanel.add(SolutionTreeVisual.nodesSearchedLabel);
		countPanel.add(SolutionTreeVisual.equalBestTimeCountLabel);
		statPanel.add(countPanel);

		JPanel validSchedulePanel = new JPanel();
		validSchedulePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		validSchedulePanel.add(SolutionTreeVisual.validScheduleCountLabel);
		validSchedulePanel.add(SolutionTreeVisual.validScheduleLabel);
		statPanel.add(validSchedulePanel);

		JPanel processorPanel = new JPanel();
		processorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
		processorPanel.add(SolutionTreeVisual.processorsUsedLabel);
		processorPanel.add(SolutionTreeVisual.idleProcessorsLabel);
		statPanel.add(processorPanel);
		if(UserOptions.getInstance().isParallel()){
			JPanel paraPanel = new JPanel();
			paraPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 2));
			paraPanel.add(SolutionTreeVisual.semaphoreLabel);
			JLabel threadIDLabel = new JLabel("Number of Threads to Use:" + UserOptions.getInstance().getProcessors());
			paraPanel.add(threadIDLabel);
			statPanel.add(paraPanel);

		}

		tV.setPreferredSize(dim);
		panel.add(tV, BorderLayout.CENTER);
		panel.add(statPanel, BorderLayout.PAGE_START);

		frame.getContentPane().add(panel);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

		frame.setVisible(true);
	}

}

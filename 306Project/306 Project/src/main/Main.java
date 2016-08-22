package main;

import graph306.CustomGraphReader;
import graph306.SolutionTree;
import graph306.UserOptions;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static ViewPanel view;


    public static void main(String[] args) throws IOException, InterruptedException {
		//start timing
		//long startTime = System.nanoTime();

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        SolutionTree.bestTimeTree.addAttribute("ui.stylesheet", "node { " +
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
        SolutionTree.datGraph.addAttribute("ui.stylesheet", "node { " +
                "size:30px; " +
                "text-color:red; " +
                "text-size:15px; " +
                "stroke-mode:plain;" +
                "fill-color:magenta;" +
                "}" +
                "edge { " +
                "text-color:red; " +
                "text-size:13px; " +
                "text-background-mode:rounded-box;" +
                "size:1px;" +
                "}");
        SolutionTree.bestTimeTree.addAttribute("ui.antialias");
        SolutionTree.bestTimeTree.setStrict(false);
        SolutionTree.bestTimeTree.addAttribute("ui.quality");
        SolutionTree.datGraph.addAttribute("ui.antialias");
        SolutionTree.datGraph.setStrict(false);
        SolutionTree.datGraph.addAttribute("ui.quality");
        Viewer treeViewer = new Viewer(SolutionTree.bestTimeTree, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        Viewer graphViewer = new Viewer(SolutionTree.datGraph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        graphViewer.enableAutoLayout();
        JFrame frame = new JFrame("Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        Dimension dim = new Dimension(500,300);
        ViewPanel tV = treeViewer.addDefaultView(false);

        tV.setPreferredSize(dim);
        panel.add(tV, BorderLayout.LINE_END);
        ViewPanel gV = graphViewer.addDefaultView(false);
        gV.setPreferredSize(dim);
        panel.add(gV, BorderLayout.LINE_START);
//        panel.add(gV, BorderLayout.PAGE_END);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.pack();
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        view.resizeFrame(screenSize.getWidth(), screenSize.getHeight());
//        view.getCamera().setViewCenter(440000, 2503000, 0);
//        view.getCamera().setViewPercent(1);





        CustomGraphReader graphReader = new CustomGraphReader(args);
		//run a read method on DAG
		graphReader.readDAG();
		SolutionTree solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
		solver.calculateTime(solver.getRootNode(), solver.getNodeList());
		
		/*end timing
			print statements to see the time and the minimum path time
			long endTime = System.nanoTime();
			long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
			System.out.println(duration+" miliseconds");
			System.out.println("Minimum time to do all tasks "+solver.getMinimumTime());
			for(NodeObject a: solver.getBestSchedule()){
				System.out.println("Node_name= "+a.getNodeName() + " Processor= " + (a.getProcessor()+1) + " Start_time= " + a.getStartTime() + " End_time= " + a.getEndTime());
			}
		*/
		//Create .dot file at the end
		DotWriter writer = new DotWriter();
		writer.createDot(solver.getBestSchedule(),UserOptions.getInstance(),graphReader.getEdgeList(),solver.getInputGraph());

        SolutionTree.bestTimeTree.addAttribute("ui.stylesheet", "graph{fill-color: rgb(60,141,13);}");
	}

}

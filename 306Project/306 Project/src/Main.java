
import graph306.CustomGraphReader;
import graph306.SolutionTree;
import graph306.UserOptions;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.ThreadingModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {


    public static class MyFrame extends JFrame
    {
        //private Graph graph = new MultiGraph("embedded");
        //private Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        private Viewer viewer = new Viewer(SolutionTree.gsGraph, ThreadingModel.GRAPH_IN_GUI_THREAD);
        private View view = viewer.addDefaultView(false);

        public MyFrame() {
            setLayout(new BorderLayout());
            this.add((Component) view);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            viewer.enableAutoLayout();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
		//start timing
		//long startTime = System.nanoTime();

        Runnable init = new Runnable() {
            public void run() {
                MyFrame frame = new MyFrame();
                frame.setSize(700,700);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                SolutionTree.gsGraph.addAttribute("ui.antialias");
            }
        };
        Thread appThread = new Thread() {
            public void run() {
                try {
                    SwingUtilities.invokeAndWait(init);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Finished on " + Thread.currentThread());
            }
        };
        appThread.start();


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
	}

}

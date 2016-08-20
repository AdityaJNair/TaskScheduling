package test;

import graph306.CustomGraphReader;
import graph306.SolutionTree;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import milestone1.*;

import java.io.IOException;

public class Milestone1Test {
    String[] args = new String[2];
    @Before
    public void setUp(){

        //Change these as needed, filename, number of processes and other arguments.
        String slash = System.getProperty("file.separator");
        String filename = "306 Project"+slash+"src"+slash+"resources"+slash+"Graphs"+slash+"Nodes_11_OutTree.dot";
        args[0] = filename;
        args[1] = "4";

    }
    @Test
    public void compare(){

        Long[] times = new Long[2];
        //Start timing
        long startTime = System.nanoTime();

        SolutionTree solver;
        //Run the algorithm 5 times and average the value.
        //for (int i=0; i<0; i++){
            CustomGraphReader graphReader = new CustomGraphReader(args);
            graphReader.readDAG();
            solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
            solver.startIteration(solver.getRootNode(), solver.getNodeList());
            //if (i==0){
                times[1] = new Long(solver.getMinimumTime());	//The optimal time for the schedule.
            //}
        //}
        //Stop timing
        long endTime = System.nanoTime();
        //Average times
        long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
        //duration = duration/1;                          //Average the time.
        times[0] = duration;							//The runtime for the algorithm.


        try {
            //Run the old main method, in it's own package.
            Long[] oldTimes = oldResults();
            //The old schedule will always get the optimal result. The two must match.
            Assert.assertEquals("Invalid Schedule", oldTimes[1],times[1]);
            //Compare the times of the old and new implmentations.
            //NOTE THAT RUNTIMES MAY VARY, EVEN WITH AVERAGING. IF IT FAILS, LOOK AT THE ERROR MESSAGE FOR THE DIFFERENCE.
            System.out.println("Old Runtime: "+Long.toString(oldTimes[0]));
            System.out.println("New Runtime: "+Long.toString(times[0]));
            Assert.assertTrue(times[0]<oldTimes[0]);
        }catch(IOException e){
            //Theoretically, this is unreachable. The input is defined in the brief and won't change.
            Assert.fail("Invalid input to old main function");
        }

    }
    public Long[] oldResults() throws IOException{
        //Call the old main method, and return it's time values.
        Long[] oldResults = milestone1.Main.main(args);
        //Print methods included for visual inspection.
//      System.out.println("Runtime:");
//      System.out.println(Long.toString(oldResults[0]));
//      System.out.println("Optimal time:");
//      System.out.println(Long.toString(oldResults[1]));
        return oldResults;
    }

    @After
    public void tearDown() {
    }
}

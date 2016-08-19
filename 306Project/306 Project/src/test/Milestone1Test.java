import graph306.CustomGraphReader;
import graph306.SolutionTree;
import milestone1.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class Milestone1Test {
    String[] args = new String[2];
    @Before
    public void setUp(){

        //Change these as needed
        String slash = System.getProperty("file.separator");
        String filename = "306 Project"+slash+"src"+slash+"resources"+slash+"Graphs"+slash+"testGraph.dot";
        args[0] = filename;
        args[1] = "2";

    }
    @Test
    public void compare(){

        Long[] times = new Long[2];
        long startTime = System.nanoTime();

        SolutionTree solver;
        for (int i=0; i<5; i++){
            CustomGraphReader graphReader = new CustomGraphReader(args);
            graphReader.readDAG();
            solver = new SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
            solver.calculateTime(solver.getRootNode(), solver.getNodeList());
            if (i==0){
                times[1] = new Long(solver.getMinimumTime());	//The optimal time for the schedule.
            }
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
        duration = duration/5;                          //Average the time.
        times[0] = duration;							//The runtime for the algorithm.


        try {
            Long[] oldTimes = oldResults();
            Assert.assertEquals("Invalid Schedule", oldTimes[1],times[1]);
            Assert.assertTrue("Runtime Increased by "+Long.toString(times[0]-oldTimes[0]), times[0]<oldTimes[0]);
        }catch(IOException e){
            //Theoretically, this is unreachable. The input is defined in the brief and won't change.
            Assert.fail("Invalid input to old main function");
        }

    }
    public Long[] oldResults() throws IOException{
            Long[] oldResults = milestone1.Main.main(args);
//           System.out.println("Runtime:");
//           System.out.println(Long.toString(oldResults[0]));
//           System.out.println("Optimal time:");
//           System.out.println(Long.toString(oldResults[1]));
            return oldResults;

    }

    @After
    public void tearDown() {
    }
}

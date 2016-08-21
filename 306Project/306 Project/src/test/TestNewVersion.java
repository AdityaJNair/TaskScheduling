import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestNewVersion {

    String[] args = new String[2];

    @Before
    public void setup(){
        args[0] = "\"306 Project/src/resources/Graphs/Nodes_11_OutTree.dot\"";
        args[1] = "4";
    }
    @Test
    public void compare(){

        Long[] current = timeCurrent();
        Long[] old = timeOld();

        assertEquals("Old and new methods disagree on optimal schedule:" +
                        "\nOld minimum time: " + Long.toString(old[1]) +
                        "\nNew minimum time: " + Long.toString(current[1]),
                old[1], current[1]);

        assertTrue("Old implementation was faster:" +
                        "\nOld runtime: " + Long.toString(old[0]) +
                        "\nNew runtime: " + Long.toString(current[0])
                ,current[0]<old[0]);

        assertTrue("Old implementation checked fewer nodes:" +
                        "\nOld nodes checked: " + Long.toString(old[2]) +
                        "\nNew nodes checked: " + Long.toString(current[2])
                ,current[2]<old[2]);
    }

    private Long[] timeCurrent(){

        Long[] output = new Long[3];

        long startTime = System.nanoTime();

        graph306.CustomGraphReader graphReader = new graph306.CustomGraphReader(args);
        //run a read method on DAG
        graphReader.readDAG();
        graph306.SolutionTree solver = new graph306.SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
        solver.calculateTime(solver.getRootNode(), solver.getNodeList());

        //end timing
        //	print statements to see the time and the minimum path time
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
        //System.out.println(duration+" miliseconds");
        output[0] = duration;
        //System.out.println("Minimum time to do all tasks "+solver.getMinimumTime());
        output[1] = Integer.toUnsignedLong(solver.getMinimumTime());
//        for(NodeObject a: solver.getBestSchedule()){
//            System.out.println("Node_name= "+a.getNodeName() + " Processor= " + (a.getProcessor()+1) + " Start_time= " + a.getStartTime() + " End_time= " + a.getEndTime());
//        }
        //System.out.println(solver.nodeNumber);
        output[2] = solver.nodeNumber;
        return output;

    }

    private Long[] timeOld(){

        Long[] output = new Long[3];

        long startTime = System.nanoTime();

        oldVersion.CustomGraphReader graphReader = new oldVersion.CustomGraphReader(args);
        //run a read method on DAG
        graphReader.readDAG();
        oldVersion.SolutionTree solver = new oldVersion.SolutionTree(graphReader.getGraphAdapter().getAdjacencyList());
        solver.calculateTime(solver.getRootNode(), solver.getNodeList());

        //end timing
        //	print statements to see the time and the minimum path time
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
        //System.out.println(duration+" miliseconds");
        output[0] = duration;
        //System.out.println("Minimum time to do all tasks "+solver.getMinimumTime());
        output[1] = Integer.toUnsignedLong(solver.getMinimumTime());
//        for(NodeObject a: solver.getBestSchedule()){
//            System.out.println("Node_name= "+a.getNodeName() + " Processor= " + (a.getProcessor()+1) + " Start_time= " + a.getStartTime() + " End_time= " + a.getEndTime());
//        }
        //System.out.println(solver.nodeNumber);
        output[2] = solver.nodeNumber;
        return output;

    }

}

package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import graph306.AdjacencyList;
import graph306.CustomGraphReader;
import graph306.UserOptions;

/**
 * Created by JSCooke on 29/07/16.
 */

public class ValidityTest {

    CustomGraphReader graphReader;
    UserOptions options = UserOptions.getInstance();
    String[] args = new String[7];

    @Before
    public void setUp(){
        String slash = System.getProperty("file.separator");
        args[0] = "306 Project"+slash+"src"+slash+"resources"+slash+"Graphs"+slash+"testGraph.dot";
        args[1] = "2";
        args[2] = "-p";
        args[3] = "2";
        args[4] = "-v";
        args[5] = "-o";
        args[6] = "OUTPUT";
        graphReader = new CustomGraphReader(args);
    }

    /**
     * Tests that all user options have been correctly stored in the UserOptions class.
     */
    @Test
    public void userOptionsTest(){

        assertEquals(options.getFilenameIn(),args[0]);
        //System.out.println(options.getFilenameIn());

        assertEquals(options.getFilenameOut(),args[6]+".dot");
        //System.out.println(options.getFilenameOut());

        assertTrue(options.isParallel());
        //System.out.println(options.isParallel());

        assertTrue(options.isVisible());
        //System.out.println(options.isVisible());

        assertEquals(options.getParallelThreads(),Integer.parseInt(args[3]));
        //System.out.println(options.getParallelThreads());

        assertEquals(options.getProcessors(),Integer.parseInt(args[1]));
        //System.out.println(options.getProcessors());

        //This value is hard coded from testgraph.dot
        assertEquals(options.getGraphName(),"edgefirst");
        //System.out.println(options.getGraphName());
    }

    @Test
    public void readDAGTest(){
        graphReader.readDAG();
        //Gets the graph from the adapter.
        AdjacencyList testList = graphReader.getGraph().getGraph();
    }
    
    @Test
    public void readDAGTest2(){
        String slash = System.getProperty("file.separator");
        args[0] = "D:"+slash+"Workspace"+slash+"306-Project"+slash+"306Project"+slash+"306 Project"+slash+"src"+slash+"test"+slash+"testGraph.dot";
        args[1] = "2";
        args[2] = "-p";
        args[3] = "2";
        args[4] = "-v";
        args[5] = "-o";
        args[6] = "OUTPUT";
        graphReader = new CustomGraphReader(args);
        graphReader.readDAG();
        //Gets the graph from the adapter.
        AdjacencyList testList = graphReader.getGraph().getGraph();
        System.out.println("Size of the list "+testList.getAdjacencyList().size());
    	for(Map.Entry<String, Integer> entry : testList.getIndices().entrySet()){
    		System.out.println("Node "+entry.getKey() + " is at index " + entry.getValue());
    	}
    	System.out.println();
        for(int i=0; i<testList.getAdjacencyList().size();i++){
        	System.out.println("At index= "+i+" the size of the map is "+testList.getAdjacencyList().get(i).size());
        	for(Map.Entry<String, Integer> entry : testList.getAdjacencyList().get(i).entrySet()){
        		System.out.println("    Contains an entry "+entry.getKey() + " and the edge weight is " + entry.getValue());
        	}
        }

    }

    @After
    public void tearDown(){
        //Reset UserOptions to default values
        options.setFilenameIn(null);
        options.setFilenameOut(null);
        options.setParallel(false);
        options.setVisible(false);
        options.setParallelThreads(-1);
        options.setProcessors(-1);
    }
}

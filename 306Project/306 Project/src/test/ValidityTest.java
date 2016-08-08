package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
    String inputFile;

    @Before
    public void setUp(){
        String slash = System.getProperty("file.separator");
        inputFile = "306 Project"+slash+"src"+slash+"resources"+slash+"Graphs"+slash+"testGraph.dot";
        args[0] = inputFile;
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
     * For visual inspection, remove the commented lines below each assertion.
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

    }

    /**
     * This class automatically tests that the input .dot file has been read correctly.
     * This was coded totally independently of the readDAG method that it tests, and using different classes where possible.
     */
    @Test
    public void readDAGTest(){

        graphReader.readDAG();

        try {
            Scanner scanner = new Scanner(new File(inputFile));
            String s;

            //Check the graph title has been read correctly.
            s=scanner.nextLine();
            String[] nameArray = s.split("\"");
            String title = nameArray[1];
            assertEquals(options.getGraphName(), title);

            String[] lineArray;
            //Lists containing identifiers and weights for each node and edge. I will search these based on what readDAG got.
            Map<String,String> edges = new HashMap<>();
            Map<String,String> nodes = new HashMap<>();
            while (scanner.hasNext()){
                lineArray = scanner.nextLine().split("\\s+");
                try {
                    if (lineArray[1].equals("->")) {
                        //Line is an edge
                        String edgeID = lineArray[0] + lineArray[2];
                        String edgeWeight = lineArray[3].substring(lineArray[3].indexOf("=") + 1, lineArray[3].length() - 1);
                        edges.put(edgeID, edgeWeight);
                    } else {
                        //Line is a node
                        String nodeID = lineArray[0];
                        String nodeWeight = lineArray[1].substring(lineArray[1].indexOf("=") + 1, lineArray[1].length() - 1);
                        nodes.put(nodeID, nodeWeight);
                    }
                }catch (IndexOutOfBoundsException e){
                    // Line has less than 1 space, and so is for formatting. This statement skips that line.
                    continue;
                }
            }

            //Gets the graph from the adapter.
            AdjacencyList testList = graphReader.getGraph().getGraph();

            //Get the map of nodes to weights from the generated graph.
            Map<String, Integer> graphNodes = testList.getNodeWeights();

            //There is no map of edges to edge weights, so one must be created.
            Map<String, Integer> graphEdges = new HashMap<>();

            //Create a reversed version of indices, as we will be looking up values by index, not the other way around.
            Map<String, Integer> indices = testList.getIndices();
            Map<Integer, String> secidni = new HashMap<>();
            for (Map.Entry<String, Integer> e: indices.entrySet()){
                secidni.put(e.getValue(), e.getKey());
            }

            for (int i=0; i<testList.getAdjacencyList().size();i++){
                //This gets the id of the current node and puts it in the value variable.
                String value = secidni.get(i);
                //For each element in the index's map
                for (Map.Entry<String, Integer> m :testList.getAdjacencyList().get(i).entrySet()){
                    //Add an edge with the corresponding weight to the graphEdges map.
                    String id  = m.getKey()+value;
                    graphEdges.put(id,m.getValue());
                }
            }

            //Each value in graphNodes and graphEdges should have a corresponding value in nodes and edges
            for (Map.Entry<String, String> m : nodes.entrySet()){
                String id = m.getKey();
                //Check that the id is present.
                assertTrue(graphNodes.containsKey(id));
                //Check that the id contains the same value.
                assertEquals(Integer.toString(graphNodes.get(id)), m.getValue());
            }
            for (Map.Entry<String, String> m : edges.entrySet()){
                String id = m.getKey();
                //Check that the id is present.
                assertTrue(graphEdges.containsKey(id));
                //Check that the id contains the same value.
                assertEquals(Integer.toString(graphEdges.get(id)), m.getValue());
            }

        } catch(FileNotFoundException e){
            fail("File not found");
        }

    }

    @Test
    public void visualReadDAGTest(){
        String slash = System.getProperty("file.separator");
        //For Eclipse, add this to the path if it is missing. For IntelliJ, remove it if it is present.
        //"D:"+slash+"Workspace"+slash+"306-Project"+slash+"306Project"+slash+
        args[0] = "306 Project"+slash+"src"+slash+"test"+slash+"testGraph.dot";
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
        options.setGraphName(null);
    }
}

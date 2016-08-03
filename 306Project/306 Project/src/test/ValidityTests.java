package test;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.*;
import java.io.*;

import static org.junit.Assert.*;

/**
 * Created by JSCooke on 29/07/16.
 */

public class ValidityTests {

    private Graph testGraph = new SingleGraph("Test Graph 1");

    @Before
    public void setUp(){
        /*
            Create a graph on which to test the algorithm.
            This is the graph from page 3 of the project brief.
        */

        testGraph.addNode("A");
        testGraph.addNode("B");
        testGraph.addNode("C");
        testGraph.addNode("D");

        testGraph.getNode("A").addAttribute("Cost",2);
        testGraph.getNode("B").addAttribute("Cost",3);
        testGraph.getNode("C").addAttribute("Cost",3);
        testGraph.getNode("D").addAttribute("Cost",2);

        testGraph.addEdge("AB", "A", "B");
        testGraph.addEdge("AC", "A", "C");
        testGraph.addEdge("BD", "B", "D");
        testGraph.addEdge("CD", "C", "D");

        testGraph.getEdge("AB").addAttribute("Cost",1);
        testGraph.getEdge("AC").addAttribute("Cost",2);
        testGraph.getEdge("BD").addAttribute("Cost",2);
        testGraph.getEdge("CD").addAttribute("Cost",1);

        testGraph.getEdge("AB").isDirected();
        testGraph.getEdge("AC").isDirected();
        testGraph.getEdge("BD").isDirected();
        testGraph.getEdge("CD").isDirected();

    }

    @Test
    public void validTest(){

        /*
         * Use the program to find the path and verify that the path is valid, from a known set of valid paths.
         * How this will be done depends on the - as of 1 August - undecided output format of the graph.
         */

        //Read from output file, which is assumed to be called "output.dot", and in the same directory as the .jar file.

        try {
            File file = new  File("output.dot");
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            //At this stage, we can read through the file using reader.

        }catch(FileNotFoundException e){
            fail("File not found. There may be an I/O problem.");
        }
    }

    @After
    public void tearDown(){

    }
}

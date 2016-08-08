import graph306.AdjacencyList;
import graph306.CustomGraphReader;
import graph306.UserOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void readDAGTest(){
        graphReader.readDAG();
        //Note that these use hard coded values from testGraph.dot.
        assertEquals(graphReader.getEdgeList().size(), 6);
//        assertEquals(graphReader.getSourceNodes().size(), 7);

    }

    @Test
    public void createDAGTest() {
        graphReader.createDAG();
        AdjacencyList adjacencyList = graphReader.getGraph().getGraph();
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

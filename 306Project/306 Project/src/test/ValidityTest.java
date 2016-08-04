package test;

import graph306.CustomGraphReader;
import graph306.UserOptions;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by JSCooke on 29/07/16.
 */

public class ValidityTest {

    CustomGraphReader graphReader;
    UserOptions options = UserOptions.getInstance();

    @Before
    public void setUp(){}

    @Test
    public void userOptionsTest(){
        String[] args = {"/306 Project/src/resources/Graphs/testGraph.dot", "2", "-p", "2", "-v","-o", "OUTPUT"};

        graphReader = new CustomGraphReader(args);
        
        assertEquals(options.getFilenameIn(),args[0]);
        System.out.println(options.getFilenameIn());

        assertEquals(options.getFilenameOut(),args[6]+".dot");
        System.out.println(options.getFilenameOut());

        assertTrue(options.isParallel());
        System.out.println(options.isParallel());

        assertTrue(options.isVisible());
        System.out.println(options.isVisible());

        assertEquals(options.getParallelThreads(),Integer.parseInt(args[3]));
        System.out.println(options.getParallelThreads());

        assertEquals(options.getProcessors(),Integer.parseInt(args[1]));
        System.out.println(options.getProcessors());
    }

    @Test
    public void readDAGTest(){

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

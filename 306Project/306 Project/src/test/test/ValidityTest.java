package test;

import graph306.CustomGraphReader;
import graph306.UserOptions;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by JSCooke on 29/07/16.
 */

public class ValidityTest {

    @Before
    public void setUp(){}

    @Test
    public void userOptionsTest(){
        String[] args = {"testGraph.dot", "2", "-p", "2", "-v", "-o", "OUTPUT"};

        CustomGraphReader graphReader = new CustomGraphReader(args);
        UserOptions options = UserOptions.getInstance();

        assertEquals(options.getFilenameIn(),args[0]);
        //System.out.println(options.getFilenameIn());

        assertEquals(options.getFilenameOut(),args[6]+".dot");
        //System.out.println(options.getProcessors()));

        assertTrue(options.isParallel());
        //System.out.println(options.isParallel());

        assertTrue(options.isVisible());
        //System.out.println(options.isVisible());

        assertEquals(options.getParallelThreads(),Integer.parseInt(args[3]));
        //System.out.println(options.getParallelThreads());

        assertEquals(options.getProcessors(),Integer.parseInt(args[1]));
        //System.out.println(options.getProcessors());
    }

    @After
    public void tearDown(){
        //Reset UserOptions to default values
        UserOptions options = UserOptions.getInstance();
        options.setFilenameIn(null);
        options.setFilenameOut(null);
        options.setParallel(false);
        options.setVisible(false);
        options.setParallelThreads(-1);
        options.setProcessors(-1);
    }
}

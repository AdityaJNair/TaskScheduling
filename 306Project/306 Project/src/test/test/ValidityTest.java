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
    public void validTest(){
        String[] args = {"testGraph.dot","2"};

        CustomGraphReader graphReader = new CustomGraphReader(args);

        assertEquals(UserOptions.getInstance().getFilenameIn(),args[0]);
        //System.out.println(UserOptions.getInstance().getFilenameIn());

        assertEquals(Integer.toString(UserOptions.getInstance().getProcessors()),args[1]);
        //System.out.println(Integer.toString(UserOptions.getInstance().getProcessors()));
    }

    @After
    public void tearDown(){
        UserOptions userOptions = UserOptions.getInstance();
        userOptions.setFilenameIn(null);
        userOptions.setFilenameOut(null);
        userOptions.setParallel(false);
        userOptions.setVisible(false);
        userOptions.setParallelThreads(-1);
        userOptions.setProcessors(-1);
    }
}

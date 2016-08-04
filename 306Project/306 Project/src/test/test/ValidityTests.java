package test;

import graph306.CustomGraphReader;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by JSCooke on 29/07/16.
 */

public class ValidityTests {

    @Before
    public void setUp(){}

    @Test
    public void validTest(){
        String[] args = {"test.dot","2"};
        CustomGraphReader graphReader = new CustomGraphReader(args);
        
        //run a read method on DAG
        graphReader.readDAG();
        //create the DAG
        graphReader.createDAG();
    }

    @After
    public void tearDown(){}
}

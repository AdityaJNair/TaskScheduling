import milestone1.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class Milestone1Test {
    @Before
    public void setUp(){
    }
    @Test
    public void compare(){
        String[] args = new String[2];
        String slash = System.getProperty("file.separator");
        args[0] = "306 Project"+slash+"src"+slash+"resources"+slash+"Graphs"+slash+"testGraph.dot";
        args[1] = "2";
        try {
            Long[] oldResults = milestone1.Main.main(args);
            System.out.println("Runtime:");
            System.out.println(Long.toString(oldResults[0]));
            System.out.println("Optimal time:");
            System.out.println(Long.toString(oldResults[1]));
        }catch(IOException e){
            System.out.print("Invalid input");
            Assert.fail();
        }
    }
    @After
    public void tearDown() {
    }
}

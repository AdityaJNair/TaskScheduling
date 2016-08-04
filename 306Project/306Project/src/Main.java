import java.io.IOException;

import graph306.CustomGraphReader;

public class Main {

	public static void main(String[] args) throws IOException {
		CustomGraphReader graphReader = new CustomGraphReader(args);
		//run a read method on DAG
		graphReader.readDAG();
		//create the DAG
		graphReader.createDAG();
		graphReader.graph.display();
	}

}

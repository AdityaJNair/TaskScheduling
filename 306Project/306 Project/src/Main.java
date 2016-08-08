import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graph306.CustomGraphReader;
import graph306.SolutionTree;

public class Main {

	public static void main(String[] args) throws IOException {
		CustomGraphReader graphReader = new CustomGraphReader(args);
		//run a read method on DAG
		graphReader.readDAG();
		
		List<String> nodeList = new ArrayList<String>();
		for(String entry : graphReader.getGraph().getGraph().getIndices().keySet()){
    		nodeList.add(entry);
    	}
		
		SolutionTree solver = new SolutionTree(graphReader.getGraph().getGraph());
	}

}

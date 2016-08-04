package graph306;

import java.awt.List;
import java.util.HashSet;

/**
 * Class to read in a directed graph from a .dot file and create a directed graph.
 * Also notes which nodes have no dependents.
 */
public class CustomGraphReader {
	UserOptions userOptions;
	HashSet<String> sourceNodes;
	List edgeList;
	
	public CustomGraphReader(String[] args){
		userOptions.setFilenameIn(args[0]);
		userOptions.setFilenameOut(args[0]);
		
		userOptions.setProcessors(Integer.parseInt(args[1]));
		for(int i = 2; i < args.length; i++) {
			if(args[i].equals("-v")) {
				userOptions.setVisible(true);
			}
			else if(args[i].equals("-p")){
				userOptions.setParallel(true);
				i++;
				userOptions.setParallelThreads(Integer.parseInt(args[i]));
			}
			else if(args[i].equals("-o")){
				i++;
				userOptions.setFilenameOut(args[i]);
			}
		}
		
		readDAG();
		createDAG();
	}
	
	private String getFileName(String file){
		
		return "";
	}
	
	private void readDAG(){
		
	}
	
	private void createDAG(){
		
	}
}

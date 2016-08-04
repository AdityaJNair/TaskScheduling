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
	
	/**
	 * Constructor for the CustomGraphReader that reads in the options from the
	 * main arguments.
	 *  Options are
	 *  	INPUT.dot P [Option{
	 *  		-p N 		:Use N threads for execution in parallel (default is sequential)
	 *  		-v			:Visualize the search
	 *  		-o OUTPUT	:Output file is named OUTPUT (default is INPUT-output.dot)
	 *  
	 *  		-..... 		:for future options
	 * @param args
	 */
	public CustomGraphReader(String[] args){
		//sets the filename for later use
		userOptions.setFilenameIn(args[0]);
		//sets the output file name default with INPUT-ouput.dot
		userOptions.setFilenameOut(args[0].substring(0,args[0].length()-4) + "-output.dot");
		//adds the number of processors used in algorithm
		userOptions.setProcessors(Integer.parseInt(args[1]));
		
		//iterates through other options if there are any
		for(int i = 2; i < args.length; i++) {
			//if visible option set flag as true
			if(args[i].equals("-v")) {
				userOptions.setVisible(true);
			}
			//if parallel option set flag as true
			else if(args[i].equals("-p")){
				userOptions.setParallel(true);
				//iterate once to get number of parallel threads required
				i++;
				userOptions.setParallelThreads(Integer.parseInt(args[i]));
			}
			//if sent to an output file with given name
			else if(args[i].equals("-o")){
				i++;
				userOptions.setFilenameOut(args[i]+".dot");
			}
		}
		
		//run a read method on DAG
		readDAG();
		//create the DAG
		createDAG();
	}
	
	private void readDAG(){
		
	}
	
	private void createDAG(){
		
	}
}

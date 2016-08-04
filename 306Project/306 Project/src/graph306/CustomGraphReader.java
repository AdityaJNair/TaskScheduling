package graph306;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Class to read in a directed graph from a .dot file and create a directed graph.
 * Also notes which nodes have no dependents.
 */
public class CustomGraphReader {
	UserOptions userOptions = null;
	HashSet<String[]> sourceNodes;
	List<String> edgeList = new ArrayList<String>();
	GraphAdapter graph = null;
	
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
		try(BufferedReader br = new BufferedReader(new FileReader(userOptions.getFilenameIn()))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        if(line.contains("digraph")){
		        	Pattern p = Pattern.compile("\"([^\"]*)\"");
		        	Matcher m = p.matcher(line);
		        	while (m.find()) { // first line. Contains graph name
		        	  graph = new GraphAdapter(); // create new graph
		        	}
		        	continue;
		        } else if(line.contains("->")){ // Line with edges and weights
		        	edgeList.add(line);
		        	continue;
//		        	String[] words = line.split("\\s+");
//		        	System.out.println(words[0] + " to " + words[2]);
//		        	graph.addEdge(words[0]+words[2], words[0], words[2], true); // Add to edgelist
		        } else { // add Vertices to the graph
		        	if(line.contains("}")) break; // exit if end of file
		        	String[] words = line.split("\\s+");
		        	sourceNodes.add(words); // Add nodes to source nodes list
		        	String weight = words[3]; 
		        	weight = weight.replaceAll("[^0-9]+", " ");
		        	int weightInt = Integer.parseInt(weight);
		        	graph.addNode(words[0], weightInt);
		        }
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * read list
	 * add edges to the graph
	 * remove sources
	 * store after loop finishes
	 */
	private void createDAG(){
		for(String edge: edgeList){
			
		}
	}
}

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
	UserOptions userOptions = UserOptions.getInstance();
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
			System.out.println("The input file was not found.");
		} catch (IOException e) {
			System.out.println("An IO Exception has occurred.");
		}
	}
	
	/**
	 * createDAG() - iterates through the edge List that contains all the dependencies between nodes.
	 * It formats the string and sets the two nodes and gives the graph their dependencies and the cost.
	 * Additionally the node that is dependent on another (B) in A->B is removed from the source hashset.
	 */
	private void createDAG(){
		//added edges to the graph
		for(String edge: edgeList){
			//remove white-spaces from the list string input to get A,->,B,[Weight=2];"
			String[] dependencyArray = edge.split("\\s+");
			int edgeWeight = Integer.parseInt(dependencyArray[3].replaceAll("[^0-9]+", ""));
			//added the weight and the dependencies to the graph
			graph.addEdge(dependencyArray[0], dependencyArray[2], edgeWeight);
			//remove the node that is dependent on another node as it is no longer source node
			sourceNodes.remove(dependencyArray[2]);
		}

	}
}

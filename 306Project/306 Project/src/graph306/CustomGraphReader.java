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
	HashSet<String> sourceNodes = new HashSet<String>();
	List<String> edgeList = new ArrayList<String>();
	public GraphAdapter graph = null;
	
	public UserOptions getUserOptions() {
		return userOptions;
	}

	public HashSet<String> getSourceNodes() {
		return sourceNodes;
	}

	public List<String> getEdgeList() {
		return edgeList;
	}

	public GraphAdapter getGraph() {
		return graph;
	}
	
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

	}
	
	/**
	 * Reads the input file and adds nodes to the graph. Also adds the nodes to the source nodes list
	 * which is later manipulated to contain only the source nodes.
	 */
	public void readDAG(){
		
		try(BufferedReader br = new BufferedReader(new FileReader(userOptions.getFilenameIn()))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        if(line.contains("digraph")){
		        	Pattern p = Pattern.compile("\"([^\"]*)\"");
		        	Matcher m = p.matcher(line);
		        	
		        	// first line. Contains graph name
		        	while (m.find()) { 
		        		// create new graph object
		        		graph = new GraphAdapter(); 
		        	}
		        	continue;
		        } else if(line.contains("->")){ // Line with edges and weights
		        	edgeList.add(line);
		        	continue;
		        } else { // add Vertices to the graph
		        	// exit if end of file
		        	if(line.contains("}")) break; 
		        
		        	String[] words = line.split("\\s+");
		        	
		        	// Add nodes to source nodes list
		        	sourceNodes.add(words[0]);
		        	
		        	String weight = words[1]; 
		        	weight = weight.replaceAll("[^0-9]+", "");
		        	int weightInt = Integer.parseInt(weight);

		        	// add vertex to the graph
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
	public void createDAG(){
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

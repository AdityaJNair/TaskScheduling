package oldVersion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to read in a directed graph from a .dot file and create a directed graph.
 * Also notes which nodes have no dependents.
 */
public class CustomGraphReader {
	UserOptions userOptions = UserOptions.getInstance();
	public GraphAdapter graph = null;
	public ArrayList<String> edges = new ArrayList<String>();

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
	 * Reads the input file and adds nodes to the graph (Adjacency List).
	 * Stores information from the input graph and stores it as information in fields for the singleton class of UserOptions
	 * Checks and adds the name of the graph
	 * Checks for any edges and adds those nodes if not seen
	 * Checks for nodes and adds the weights in the list
	 */
	public void readDAG(){
		try(BufferedReader br = new BufferedReader(new FileReader(userOptions.getFilenameIn()))) {
			String line;
		    while((line = br.readLine()) != null ) {
		    	line=line.trim();
		    	//sets the name of the graph and creates a graph
		        if(line.contains("digraph")){
		        	//checks first line and finds the text inside quotation marks which is graph name (used for output)
		        	Pattern p = Pattern.compile("\"([^\"]*)\"");
		        	Matcher m = p.matcher(line);
		        	// first line. Contains graph name
		        	while (m.find()) {
		        		// create new graph object and sets the name of the graph to userOptions
		        		userOptions.setGraphName(m.group(1));
		        		graph = new GraphAdapter();
		        	}
		        	continue;
		        } else if(line.contains("->")){ // Line with edges and weights add to adjacency list
		        	String[] edgeString = line.split("\\s+");
		        	graph.addEdge(edgeString[0], edgeString[2], Integer.parseInt(edgeString[3].replaceAll("\\D+", "")));
		        	edges.add(line);
		        	continue;
		        } else { 
		        	// exit if end of file
		        	if(line.contains("}")){
		        		break; 
		        	}
		        	// add Vertices to the graph
		        	String[] words = line.split("\\s+");
		        	graph.addNode(words[0], Integer.parseInt(words[1].replaceAll("[^0-9]+", "")));
		        }
		    }
		    //checking for errors
		} catch (FileNotFoundException e) {
			System.out.println("The input file was not found in readDAG() for CustomGraphReader.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("An IO Exception has occurred in readDAG() for CustomGraphReader.");
			e.printStackTrace();
		}
	}
	
	//GETTERS

	public GraphAdapter getGraphAdapter() {
		return graph;
	}
	
	public ArrayList<String> getEdgeList(){
		return edges;
	}
}

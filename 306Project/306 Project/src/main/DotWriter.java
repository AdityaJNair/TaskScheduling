package main;

import graph306.AdjacencyList;
import graph306.NodeObject;
import graph306.UserOptions;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
/**
 * The main.DotWriter class is used to output a .dot File
 *
 */
public class DotWriter {
	
	/**
	 * The createDot method is used to create the dot file.
	 * @param schedule - Is used to get the path and display it, it also gets the
	 * 					start time of each node and which processor it is in.
	 * @param userOptions - used to get the desired output name and the graph name
	 * @param edges - the list of edges in the path
	 * @param nodeList - is used to get the weight of each node
	 */
	public void createDot(List<NodeObject> schedule, UserOptions userOptions,ArrayList<String> edges, AdjacencyList nodeList) {
		try {
			int weight;
			int processor;
			String cleanEdge; //this string contains an edge with no trailing spaces
			//Creating the PrintWriter to start writing into a dot file.
			PrintWriter writer = new PrintWriter(userOptions.getFilenameOut());
			writer.println("digraph "+"\"output"+userOptions.getGraphName()+"\""+" {");
	
			//Adds all the nodes and the info that come along with it -> weight, start time and processor
			for(int i=1; i<schedule.size();i++){
				weight = nodeList.getNodeWeights().get(schedule.get(i).getNodeName());
				processor = schedule.get(i).getProcessor() + 1;
				writer.println(schedule.get(i).getNodeName() + " [ Weight="+weight+", Start ="+schedule.get(i).getStartTime()+", Processor ="+processor+"];");
			}
			
			//Adds all the edges into the file.
			for(String edge: edges){
				//makes sure that there is no trailing white spaces so that we can check if there is a ";"
				cleanEdge = edge.trim();
				//If the edge does not contain a ";" than add it else just write the edge into the file.
				if(!cleanEdge.endsWith(";")){
					writer.println(edge+";");
				} else {
					writer.println(edge);
				}
			}
			writer.println("}");
			writer.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File was not found or something wrong with the .dot file in createDot() for class main.DotWriter");
			e.printStackTrace();
		}
	}

}

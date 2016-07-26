import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDOT;
import org.graphstream.stream.file.FileSourceFactory;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.print("Enter file path: "); // Use args[0] in the real program		
		String filePath = reader.nextLine();
		
		Graph graph = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        if(line.contains("digraph")){
		        	Pattern p = Pattern.compile("\"([^\"]*)\"");
		        	Matcher m = p.matcher(line);
		        	while (m.find()) { // first line. Contains graph name
		        	  System.out.println("Name of graph : " + m.group(1));
		        	  graph = new SingleGraph(m.group(1)); // create new graph
		        	}
		        	continue;
		        } else if(line.contains("->")){ // Line with edges and weights
		        	String[] words = line.split("\\s+");
		        	System.out.println(words[0] + " to " + words[2]);
		        	graph.addEdge(words[0]+words[2], words[0], words[2]);
		        } else { // Vertices
		        	if(line.contains("}")) continue;
		        	String[] words = line.split("\\s+");
		        	graph.addNode(words[0]);
		        	System.out.println(words[0]);
		        }
		    }
		}
		
		graph.display();
		
	}
	
	public void createSampleGraph(){
		Graph graph = new SingleGraph("Sample");
		
		graph.addNode("A" );
		graph.addNode("B" );
		graph.addNode("C" );
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "A");
		
		graph.display();
	}

}

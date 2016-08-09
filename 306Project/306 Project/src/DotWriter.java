import graph306.AdjacencyList;
import graph306.NodeObject;
import graph306.UserOptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DotWriter {
	
	

	
	
	//System.out.println(duration+" miliseconds");
	//System.out.println("Minimum cost of time "+solver.getMinimumTime());
	//for(NodeObject a: solver.getBestSchedule()){
	//	System.out.println(a.getNodeName() + "  " + a.getProcessor() + "  " + a.getStartTime() + "  " + a.getEndTime());
	//}
	//PrintWriter writer = new PrintWriter(UserOptions.)
	
	public void createDot(List<NodeObject> schedule, UserOptions userOptions,ArrayList<String> edges, AdjacencyList nodeList) {
		try {
			int weight;
			PrintWriter writer = new PrintWriter(userOptions.getFilenameOut());
			writer.println("digraph "+userOptions.getFilenameOut()+" {");
			
			///for(NodeObject node: schedule){
			//	weight = nodeList.getNodeWeights().get(node);
			//	writer.println(node.getNodeName() + " [ Weight="+weight+", Start ="+node.getStartTime()+", Processor ="+node.getProcessor()+";");
			//}
			for(int i=1; i<schedule.size();i++){
				weight = nodeList.getNodeWeights().get(schedule.get(i).getNodeName());
				writer.println(schedule.get(i).getNodeName() + " [ Weight="+weight+", Start ="+schedule.get(i).getStartTime()+", Processor ="+schedule.get(i).getProcessor()+"];");
			}
			for(String edge: edges){
				writer.println(edge+";");
			}
			writer.println("}");
			writer.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Opps Sorry.");
		}
	}

}

package graph306;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 *
 */
public class NodeObject {
	private String nodeName;
	private int[] timeWeightOnEachProcessor;
	private int processor;
	private List<NodeObject> currentPath;
	private int startTime;
	private int endTime;

	/**
	 * Initialises a nodeObject that is used to recursively look through and find the best path.
	 * Stores important information for each node such as the name, processor, current path, start time and end time. And also
	 * the cost at each processor at this point (including this node)
	 * @param setProcessor
	 * @param currentPathNotIncludeThis
	 * @param nodeName
	 * @param timeWeightOnEachProcessor
	 * @param startTime
	 * @param endTime
	 */
	public NodeObject(int setProcessor, ArrayList<NodeObject> currentPathNotIncludeThis, String nodeName, int[] timeWeightOnEachProcessor, int startTime, int endTime){
		//set the name of this nodeObject
		this.nodeName = nodeName;
		//add the time weight up to this processor
		this.timeWeightOnEachProcessor = timeWeightOnEachProcessor;
		//store the processor number
		this.processor = setProcessor;
		//add this to the end of the current path
		this.currentPath = currentPathNotIncludeThis;
		this.currentPath.add(this.currentPath.size(), this);
		//remove this node from the list to check for nodes that have not been seen yet
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	//Getters and setters
	
	public int getProcessor() {
		return processor;
	}

	public void setProcessor(int processor) {
		this.processor = processor;
	}
	
	public int[] getTimeWeightOnEachProcessor() {
		return timeWeightOnEachProcessor;
	}

	public void setTimeWeightOnEachProcessor(int[] timeWeightOnEachProcessor) {
		this.timeWeightOnEachProcessor = timeWeightOnEachProcessor;
	}

	public List<NodeObject> getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(List<NodeObject> currentPath) {
		this.currentPath = currentPath;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

}

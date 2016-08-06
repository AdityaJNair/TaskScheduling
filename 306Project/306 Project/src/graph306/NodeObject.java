package graph306;

import java.util.ArrayList;
import java.util.List;

public class NodeObject {
	private String nodeName;
	private int[] timeWeightOnEachProcessor;
	private int processor;
	private List<NodeObject> currentPath;
	private List<String> nodesToCheck;
	private List<NodeObject> childrenNodes;
	
	public NodeObject(int setProcessor, List<NodeObject> currentPathNotIncludeThis, List<String> nodesToCheckUpdated, String nodeName, int[] timeWeightOnEachProcessor){
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
		this.nodesToCheck = nodesToCheckUpdated;
		this.nodesToCheck.remove(nodeName);
		//initialize the field childrenNodes with an ArrayList of 0 children
		this.childrenNodes = new ArrayList<NodeObject>();

	}
	
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

	public List<String> getNodesToCheck() {
		return nodesToCheck;
	}

	public void setNodesToCheck(List<String> nodesToCheck) {
		this.nodesToCheck = nodesToCheck;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public List<NodeObject> getChildrenNodes() {
		return childrenNodes;
	}

	public void setChildrenNodes(List<NodeObject> childrenNodes) {
		this.childrenNodes = childrenNodes;
	}

}

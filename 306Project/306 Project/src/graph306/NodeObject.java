package graph306;

import java.util.List;

public class NodeObject {
	private String nodeName;
	private int[] timeWeightOnEachProcessor;
	private int processor;
	private List<NodeObject> currentPath;
	private List<String> nodesToCheck;
	
	public NodeObject(){
		
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
	

}

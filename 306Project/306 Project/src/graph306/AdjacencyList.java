package graph306;

import java.util.*;

/**
 * NOTE: Removing from the list will cause the indices map to become invalid.
 * NOTE: Adding partway through (not at the end of) the list will cause the indices map to become invalid.
 */
public class AdjacencyList {

    //List of lists containing nodes and the edge weight to them.
    private List<List<String>> adjacencyList = new ArrayList<>();

    //Maps nodes to their index in the adjacency list.
    private Map<String, Integer> indices = new HashMap<>();

    //Maps nodes to their weights.
    private Map<String, Integer> nodeWeights = new HashMap<>();

    //Maps edges to their weights.
    private Map<String, Integer> nodeEdges = new HashMap<>();

    /**
     * Adds a node to the graph, or does nothing, if the node has already been added.
     * This may be updated to an exception.
     * @param id The unique identifier for the node.
     * @param cost The cost, in time units, for the node.
     */
    public void addNode(String id, int cost){

        //Check that the node hasn't already been added. If it has, end the function.
        if (indices.containsKey(id)){
            return;
        }

        //Add node to weights map.
        nodeWeights.put(id,cost);

        //Add a new list to the adjacency list representing that node.
        LinkedList<String> newEntry = new LinkedList<>();
        adjacencyList.add(newEntry);

        //Add node to indices map.
        int newIndex = adjacencyList.size()-1;
        indices.put(id, newIndex);

    }

    /**
     * Adds an edge to the graph.
     * If the edge contains a node that already is in the graph, addNode is called.
     * @param source The "head" of the node.
     * @param Destination The "tail" of the node.
     * @param cost The cost of the edge.
     */
    public void addEdge(String source, String Destination, int cost){

    }
}

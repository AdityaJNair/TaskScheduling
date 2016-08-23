package graph306;

import data_structures.AdjacencyList;

/**
 * A wrapper class so that the graph representation framework in use can be altered without changing methods.
 * Follows the adapter design pattern.
 */

public class GraphAdapter {

    private AdjacencyList graph = new AdjacencyList();

    /**
     * Returns the graph that has been created.
     * @return The graph, as an AdjacencyList.
     */

    public AdjacencyList getAdjacencyList() {

        return graph;

    }

    /**
     * Adds a node to the graph.
     * @param id The unique identifier for the node.
     * @param cost The cost (in time units) of the node.
     */

    public void addNode(String id, int cost){

        graph.addNode(id);
        graph.addNodeWeight(id, cost);

    }

    /**
     * Adds a directed edge to the graph.
     * @param source The identifier of the parent node of the edge.
     * @param destination The identifier of the child node of the edge.
     * @param cost The cost (in time units) of the node.
     */

    public void addEdge(String source, String destination, int cost){

        graph.addEdge(source, destination, cost);

    }

}

package graph306;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

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

    public AdjacencyList getGraph() {

        return graph;

    }

    /**
     * Adds a node to the graph.
     * @param id The unique identifier for the node.
     * @param cost The cost (in time units) of the node.
     */

    public void addNode(String id, int cost){

        //Add node to AdjacencyGraph. If nodes were already created by add edge, don't add them again

    }

    /**
     * Adds a directed edge to the graph.
     * @param source The identifier of the parent node of the edge.
     * @param destination The identifier of the child node of the edge.
     * @param cost The cost (in time units) of the node.
     */

    public void addEdge(String source, String destination, int cost){

        //Add edge to adjacency graph. If nodes don't exist, add both.

    }

}

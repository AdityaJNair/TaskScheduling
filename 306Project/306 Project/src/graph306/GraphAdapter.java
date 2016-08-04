package graph306;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * A wrapper class so that the graph representation framework in use can be altered without changing methods.
 * Follows the adapter design pattern.
 */

public class GraphAdapter {

    private Graph graph = new SingleGraph("Graph under construction");

    /**
     * Returns the graph that has been created.
     * @return The graph, as an org.graphstream.graph.Graph.
     */

    public Graph getGraph() {

        return graph;

    }

    /**
     * Adds a node to the graph.
     * @param id The unique identifier for the node.
     * @param cost The cost (in time units) of the node.
     */

    public void addNode(String id, int cost){

        graph.addNode(id);
        graph.getNode(id).addAttribute("Cost", cost);

    }

    /**
     * Adds a directed edge to the graph.
     * @param source The identifier of the parent node of the edge.
     * @param destination The identifier of the child node of the edge.
     * @param cost The cost (in time units) of the node.
     */

    public void addEdge(String source, String destination, int cost){

        String id = source+destination;
        graph.addEdge(id, source, destination);
        graph.getEdge(id).addAttribute("Cost", cost);
        graph.getEdge(id).isDirected();

    }

}

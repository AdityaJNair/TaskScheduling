package graph306;

import java.util.*;

/**
 * Created by JSCooke on 6/08/16.
 */
public class AdjacencyList {

    //List of lists containing nodes and the edge weight to them.
    private List<List<Map<String,Integer>>> adjacencyList = new ArrayList<>();

    //Maps nodes to their index in the adjacency list.
    private Map<String, Integer> indexes = new HashMap<>();

    //Maps nodes to their weights.
    private Map<String, Integer> weights = new HashMap<>();

}

package pl.edu.agh.gg.ui.graph;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;

public abstract class Drawable {

    private static String HYPER_GRAPH_ID_PREFIX = "hyperGraph";
    private static String HYPER_EDGE_ID_PREFIX = "hyperEdge";
    private static String VERTEX_ID_PREFIX = "vertex";

    public String id = getIdPrefix() + String.valueOf(hashCode());

    private String getIdPrefix() {
        String idPrefix = "";

        if (this instanceof HyperGraph) {
            idPrefix = HYPER_GRAPH_ID_PREFIX;
        } else if (this instanceof HyperEdge) {
            idPrefix = HYPER_EDGE_ID_PREFIX;
        } else if (this instanceof Vertex) {
            idPrefix = VERTEX_ID_PREFIX;
        }

        return idPrefix + "-";
    }
}

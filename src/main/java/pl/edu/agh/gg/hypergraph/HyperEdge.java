package pl.edu.agh.gg.model.hypergraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HyperEdge<V extends Vertex> {

    private static String ID_PREFIX = "hyperEdge-";

    public String id = ID_PREFIX + String.valueOf(hashCode());

    private HyperEdgeType type;
    private List<V> vertices = new ArrayList<>();

    public HyperEdge(HyperEdgeType type) {
        this.type = type;
    }

    public HyperEdge(HyperEdgeType type, List<V> vertices) {
        this.vertices = vertices;
        this.type = type;
    }

    public HyperEdge(HyperEdgeType type, V... vertices) {
        this.vertices = Arrays.asList(vertices);
        this.type = type;
    }

    public List<V> getVertices() {
        return vertices;
    }

    public void setVertices(List<V> vertices) {
        this.vertices = vertices;
    }

    public void addVertex(V vertex) {
        this.vertices.add(vertex);
    }

    public void removeVertex(V vertex) {
        this.vertices.remove(vertex);
    }
}

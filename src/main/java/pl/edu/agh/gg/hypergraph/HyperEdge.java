package pl.edu.agh.gg.hypergraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HyperEdge<V extends Vertex> {

    private HyperEdgeType type;
    private List<V> vertices = new ArrayList<>();
    private boolean canBreak = false;

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

    public HyperEdgeType getType() {
        return type;
    }

    public void setType(HyperEdgeType type) {
        this.type = type;
    }

    public boolean getCanBreak() {
        return canBreak;
    }

    public void setCanBreak(boolean canBreak) {
        this.canBreak = canBreak;
    }
}

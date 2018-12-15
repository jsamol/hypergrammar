package pl.edu.agh.gg.hypergraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HyperGraph<V extends Vertex, E extends HyperEdge<V>> {

    private List<V> vertices = new ArrayList<>();
    private List<E> edges = new ArrayList<>();

    public List<V> getVertices() {
        return vertices;
    }

    public void setVertices(List<V> vertices) {
        this.vertices = vertices;
    }

    public void addVertex(V... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    public void removeVertex(V vertex) {
        this.vertices.remove(vertex);
    }

    public List<E> getEdges() {
        return edges;
    }

    public void setEdges(List<E> edges) {
        this.edges = edges;
    }

    public void addEdge(E... edges) {
        this.edges.addAll(Arrays.asList(edges));
    }

    public void removeEdge(E edge) {
        this.edges.remove(edge);
    }
}

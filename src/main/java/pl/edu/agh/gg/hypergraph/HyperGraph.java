package pl.edu.agh.gg.hypergraph;

import pl.edu.agh.gg.ui.graph.Drawable;

import java.util.*;

public class HyperGraph<V extends Vertex, E extends HyperEdge<V>> extends Drawable {

    private Set<V> vertices = new HashSet<>();
    private List<E> edges = new ArrayList<>();

    public Set<V> getVertices() {
        return vertices;
    }

    private void addVertices(V... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    private void addVertices(List<V> vertices) {
        vertices.forEach(v -> System.out.println(v.hashCode()));
        this.vertices.addAll(vertices);
    }

    private void removeVertex(V vertex) {
        this.vertices.remove(vertex);
    }

    public List<E> getEdges() {
        return edges;
    }

    public void setEdges(List<E> edges) {
        this.edges = edges;
    }

    public void addEdge(E... edges) {
        List<E> edgesList = Arrays.asList(edges);
        this.edges.addAll(edgesList);
        edgesList.forEach(edge -> addVertices(edge.getVertices()));
    }

    public void removeEdge(E edge) {
        this.edges.remove(edge);
    }

}

package pl.edu.agh.gg.hypergraph;

import pl.edu.agh.gg.ui.graph.Drawable;

import java.util.*;

public class HyperGraph extends Drawable {

    private Set<Vertex> vertices = new HashSet<>();
    private List<HyperEdge> edges = new ArrayList<>();

    public Set<Vertex> getVertices() {
        return vertices;
    }

    private void addVertices(Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    private void addVertices(List<Vertex> vertices) {
        this.vertices.addAll(vertices);
    }

    public void removeVertex(Vertex vertex) {
        this.vertices.remove(vertex);
    }

    public List<HyperEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<HyperEdge> edges) {
        this.edges = edges;
    }

    public void addEdge(HyperEdge... edges) {
        List<HyperEdge> edgesList = Arrays.asList(edges);
        this.edges.addAll(edgesList);
        edgesList.forEach(edge -> addVertices(edge.getVertices()));
    }

    public void removeEdge(HyperEdge edge) {
        this.edges.remove(edge);
    }

}

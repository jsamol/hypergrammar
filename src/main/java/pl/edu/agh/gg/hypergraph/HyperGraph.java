package pl.edu.agh.gg.hypergraph;

import pl.edu.agh.gg.ui.graph.Drawable;

import java.util.*;
import java.util.stream.Collectors;

public class HyperGraph extends Drawable {

    private Set<Vertex> vertices = new HashSet<>();
    private List<HyperEdge> edges = new ArrayList<>();

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
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

    public Vertex getConnectingVertex(Vertex vertex1, Vertex vertex2) {
        // find all edges that that contain any of input vertices
        List<HyperEdge> es = edges.stream()
                .filter(edge -> edge.edgeContains(vertex1.getX(), vertex1.getY()) ||
                        edge.edgeContains(vertex2.getX(), vertex2.getY()))
                .collect(Collectors.toList());

        // dirty solution :(
        for (HyperEdge e1 : es) {
            for (HyperEdge e2 : es) {
                if (e1 != e2) {
                    HashSet<Vertex> vs1 = new HashSet<>(e1.getVertices());
                    HashSet<Vertex> vs2 = new HashSet<>(e2.getVertices());

                    // intersection of two sets of vertices
                    vs1.retainAll(vs2);

                    // if common set of vertices results in 1 vertex
                    if (vs1.size() == 1) {
                        return new LinkedList<>(vs1).getFirst();
                    }

                }
            }
        }

        return null;
    }
}
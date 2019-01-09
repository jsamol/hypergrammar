package pl.edu.agh.gg.hypergraph;

import pl.edu.agh.gg.ui.graph.Drawable;

import java.util.*;

public class HyperEdge extends Drawable {

    private HyperEdgeType type;
    private List<Vertex> vertices = new ArrayList<>();
    private boolean canBreak = false;
    private HyperEdgeDirection dir;

    public HyperEdge(HyperEdgeType type) {
        this.type = type;
    }

    public HyperEdge(HyperEdgeType type, List<Vertex> vertices) {
        this.vertices = vertices;
        this.type = type;
    }

    public HyperEdge(HyperEdgeType type, Vertex... vertices) {
        this.vertices = Arrays.asList(vertices);
        this.type = type;
    }

    public HyperEdge(HyperEdgeDirection dir, Vertex vertex) {
        this.vertices = Collections.singletonList(vertex);
        this.type = HyperEdgeType.FACE;
        this.dir = dir;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public void removeVertex(Vertex vertex) {
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

    public HyperEdgeDirection getDir() {
        return dir;
    }

    public void setDir(HyperEdgeDirection dir) {
        this.dir = dir;
    }

    public double getSideLength() {
        if (this.vertices.size() < 2) {
            System.err.println("side length exists only 2 or more vertices");
            return 0.0;
        }

        Set<Vertex> vertices = new HashSet<>(this.vertices);
        Vertex v1 = vertices.stream().findAny().get();
        vertices.remove(v1);
        Vertex v2 = vertices.stream().findAny().get();
        if (v1.getY() != v2.getY()) return Math.abs(v1.getY() - v2.getY());
        if (v1.getX() != v2.getX()) return Math.abs(v1.getX() - v2.getX());

        System.err.println("cannot calculate side length");
        return 0.0;
    }

    public Set<Vertex> findCommonVertices(HyperEdge other) {
        Set<Vertex> commonVertices = new HashSet<>(this.vertices);
        commonVertices.retainAll(other.vertices);
        return commonVertices;
    }
}

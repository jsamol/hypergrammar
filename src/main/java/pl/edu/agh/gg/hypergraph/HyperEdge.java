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

    public HyperEdge(HyperEdgeDirection dir, Vertex... vertices) {
        this.vertices = Arrays.asList(vertices);
        this.type = HyperEdgeType.FACE;
        this.dir = dir;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public boolean edgeContains(int x1, int y1) {
        return this.vertices.stream().anyMatch(v -> v.getGeom().getX() == x1 && v.getGeom().getY() == y1);
    }

    public boolean isEdgeBetween(int x1, int x2, int y1, int y2) {
        return this.vertices.stream().anyMatch(v -> v.getGeom().getX() == x1 && v.getGeom().getY() == y1)
                && this.vertices.stream().anyMatch(v -> v.getGeom().getX() == x2 && v.getGeom().getY() == y2);
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
}

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
}

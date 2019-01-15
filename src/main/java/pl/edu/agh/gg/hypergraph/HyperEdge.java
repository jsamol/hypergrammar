package pl.edu.agh.gg.hypergraph;

import pl.edu.agh.gg.data.Point;
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
        this.vertices.addAll(vertices);
        this.type = type;
    }

    public HyperEdge(HyperEdgeType type, Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
        this.type = type;
    }

    public HyperEdge(HyperEdgeDirection dir, Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
        this.type = HyperEdgeType.FACE;
        this.dir = dir;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public boolean edgeContains(int x1, int y1) {
        return this.vertices.stream().anyMatch(v -> v.getGeom().getX() == x1 && v.getGeom().getY() == y1);
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

    public boolean isEdgeInclude(int x1, int y1) {
        return this.vertices.stream().anyMatch(v -> v.getGeom().isEqual(x1, y1));
    }

    public boolean isEdgeBetween(int x1, int x2, int y1, int y2) {
        return this.vertices.stream().anyMatch(v -> v.getGeom().isEqual(x1, y1)) &&
                this.vertices.stream().anyMatch(v -> v.getGeom().isEqual(x2, y2));

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

    public Point getCenter() {
        for (Vertex v1 : this.vertices) {
            for (Vertex v2 : this.vertices) {
                if (v1.getX() != v2.getX() && v1.getY() != v2.getY()) {
                    return new Point(Math.abs(v1.getX() - v2.getX()) / 2 + Math.min(v1.getX(), v2.getX()),
                            Math.abs(v1.getY() - v2.getY()) / 2 + Math.min(v1.getY(), v2.getY()));
                }
            }
        }

        System.err.println("cannot calculate center");
        return null;
    }
}

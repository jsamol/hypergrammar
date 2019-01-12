package pl.edu.agh.gg.production;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class P3Production implements Production {
    private BufferedImage bitmap;
    private HyperEdge boundaryEdge;

    public git stP3Production(BufferedImage bitmap, HyperEdge boundaryEdge) {
        this.bitmap = bitmap;
        this.boundaryEdge = boundaryEdge;
    }

    @Override
    public void apply(HyperGraph graph) {
        Vertex vertex1 = boundaryEdge.getVertices().get(0);
        Vertex vertex2 = boundaryEdge.getVertices().get(1);

        graph.removeEdge(boundaryEdge);
        Vertex vertex3 = findVertexBetween(graph, vertex1, vertex2);

        if (vertex3 == null) {
            graph.addEdge(boundaryEdge);    //rollback
            throw new IllegalStateException(String.format("Edge not applicable - no vertex between v1 %s and v2 %s",
                    vertex1, vertex2));
        }

        int x1 = vertex1.getX();
        int x2 = vertex2.getX();
        int y1 = vertex1.getY();
        int y2 = vertex2.getY();
        int x3 = vertex3.getX();
        int y3 = vertex3.getY();

        HyperEdgeDirection direction;
        if (x3 < x1 && x1 == x2) {
            direction = HyperEdgeDirection.RIGHT;
        } else if (x3 > x1 && x1 == x2) {
            direction = HyperEdgeDirection.LEFT;
        } else if (y3 < y1 && y1 == y2) {
            direction = HyperEdgeDirection.UP;
        } else if (y3 > y1 && y1 == y2) {
            direction = HyperEdgeDirection.DOWN;
        } else {
            throw new IllegalStateException("Cannot find direction of F edge");
        }

        HyperEdge f1 = graph.getEdges()
                .stream()
                .filter(edge -> edge.getType() == HyperEdgeType.FACE &&
                        edge.edgeContains(vertex3.getX(), vertex3.getY()) &&
                        edge.getDir() == direction)
                .findAny().orElseThrow(() -> new IllegalStateException("Cannot find FACE hyper edge"));

        int newX = 0;
        int newY = 0;
        switch (direction) {
            case UP:
            case DOWN:
                newX = Math.abs(x2 - x1) / 2;
                newY = y1;
                break;
            case LEFT:
            case RIGHT:
                newX = x1;
                newY = Math.abs(y1 - y2) / 2;
                break;
        }

        Vertex newVertex = new Vertex(new Point(newX, newY), new RgbColor(bitmap.getRGB(newX, newY)));

        // add new B edges
        graph.addEdge(new HyperEdge(HyperEdgeType.BOUNDARY, vertex1, newVertex),
                new HyperEdge(HyperEdgeType.BOUNDARY, vertex2, newVertex));

        // remove F edge
        graph.removeEdge(f1);

        // add new F edge
        graph.addEdge(new HyperEdge(direction, vertex3, newVertex));

        // remove I edges
        HyperEdge leftIEdge = findEdgeBetweenPoints(graph, x1, x3, y1, y3, HyperEdgeType.INTERIOR);
        HyperEdge rightIEdge = findEdgeBetweenPoints(graph, x2, x3, y2, y3, HyperEdgeType.INTERIOR);
        graph.removeEdge(leftIEdge);
        graph.removeEdge(rightIEdge);

        List<Vertex> newLeftIEdgeVertices = leftIEdge.getVertices();
        newLeftIEdgeVertices.add(newVertex);
        List<Vertex> rightIEdgeVertices = rightIEdge.getVertices();
        rightIEdgeVertices.add(newVertex);
        // add new I edges
        graph.addEdge(new HyperEdge(HyperEdgeType.INTERIOR, newLeftIEdgeVertices),
                new HyperEdge(HyperEdgeType.INTERIOR, rightIEdgeVertices));

    }

    private HyperEdge findEdgeBetweenPoints(HyperGraph graph, int x1, int x2, int y1, int y2, HyperEdgeType type) {
        return graph.getEdges()
                .stream()
                .filter(edge -> edge.isEdgeBetween(x1, x2, y1, y2) && edge.getType() == type)
                .findFirst().orElseThrow(() -> new IllegalStateException("Cannot find INTERIOR boundaryEdge between points"));
    }

    private Vertex findVertexBetween(HyperGraph graph, Vertex vertex1, Vertex vertex2) {
        // find all edges that that contain any of input vertices
        List<HyperEdge> es = graph.getEdges().stream()
                .filter(edge -> edge.getType() == HyperEdgeType.INTERIOR &&
                        (edge.edgeContains(vertex1.getX(), vertex1.getY()) ^
                                edge.edgeContains(vertex2.getX(), vertex2.getY())))
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

package pl.edu.agh.gg.production;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class P3Production implements Production {
    private BufferedImage bitmap;
    private HyperEdge edge;

    public P3Production(BufferedImage bitmap, HyperEdge edge) {
        this.bitmap = bitmap;
        this.edge = edge;
    }

    @Override
    public void apply(HyperGraph graph) {
        Set<Vertex> vertices = new HashSet<>(edge.getVertices());

        Vertex vertex1 = VertexUtil.findMinXMaxY(vertices).orElseThrow(IllegalStateException::new);
        Vertex vertex2 = VertexUtil.findMaxXMaxY(vertices).orElseThrow(IllegalStateException::new);

        graph.removeEdge(edge);
        Vertex vertex3 = graph.getConnectingVertex(vertex1, vertex2);

        int x1 = vertex1.getX();
        int x2 = vertex2.getX();
        int y1 = vertex1.getY();
        int y2 = vertex2.getY();
        int x3 = vertex3.getX();
        int y3 = vertex3.getY();

        HyperEdgeDirection direction;
        if (y3 > y2 && y3 < y1) {
            direction = HyperEdgeDirection.RIGHT;
        } else if (y3 > y1 && y3 < y2) {
            direction = HyperEdgeDirection.LEFT;
        } else if (x3 > x1 && x3 < x2) {
            direction = HyperEdgeDirection.UP;
        } else if (x3 < x1 && x3 > x2) {
            direction = HyperEdgeDirection.DOWN;
        } else {
            throw new IllegalStateException("Cannot find direction of F edge");
        }

        HyperEdge f1 = graph.getEdges()
                .stream()
                .filter(edge -> edge.getType() == HyperEdgeType.FACE &&
                        edge.edgeContains(vertex3.getX(), vertex3.getY()) &&
                        edge.getDir() == direction)
                .findAny().orElseThrow(() -> new IllegalStateException("Cannot find FACE hyper edge "));

        int newX = (x1 + x2) / 2;
        int newY = (y1 + y2) / 2;
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

        // add new I edges
        graph.addEdge(new HyperEdge(HyperEdgeType.INTERIOR, vertex1, vertex3, newVertex),
                new HyperEdge(HyperEdgeType.INTERIOR, vertex2, vertex3, newVertex));

    }

    private HyperEdge findEdgeBetweenPoints(HyperGraph graph, int x1, int x2, int y1, int y2, HyperEdgeType type) {
        return graph.getEdges()
                .stream()
                .filter(edge -> edge.isEdgeBetween(x1, x2, y1, y2) && edge.getType() == type)
                .findFirst().orElseThrow(() -> new IllegalStateException("Cannot find INTERIOR edge between points"));
    }
}

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
    private int x1;
    private int x2;
    private int x3;
    private int y1;
    private int y2;
    private int y3;

    public P3Production(BufferedImage bitmap, int x1, int x2, int x3, int y1, int y2, int y3) {
        this.bitmap = bitmap;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }

    @Override
    public void apply(HyperGraph graph) {
        Set<Vertex> vertices = new HashSet<>(graph.getVertices());

        HyperEdge f1 = graph.getEdges()
                .stream()
                .filter(edge -> edge.getType() == HyperEdgeType.FACE &&
                        edge.edgeContains(x3, y3) &&
                        edge.getDir() == HyperEdgeDirection.UP)
                .findAny().orElseThrow(() -> new IllegalStateException("Cannot find FACE hyper edge with DOWN direction with" +
                        "x3/y3 coordinates"));

        Vertex vertex1 = VertexUtil.findMinXMaxY(vertices).orElseThrow(IllegalStateException::new);
        Vertex vertex2 = VertexUtil.findMaxXMaxY(vertices).orElseThrow(IllegalStateException::new);
        Vertex vertex3 = graph.getVertices()
                .stream()
                .filter(vertex -> vertex != vertex1 && vertex != vertex2)
                .findAny().orElseThrow(() -> new IllegalStateException("kurła"));

        x1 = vertex1.getX();
        x2 = vertex2.getX();
        x3 = vertex3.getX();
        y1 = vertex1.getY();
        y2 = vertex2.getY();
        y3 = vertex3.getY();

        int newX = (x1 + x2) / 2;
        int newY = (y1 + y2) / 2;
        Vertex newVertex = new Vertex(new Point(newX, newY), new RgbColor(bitmap.getRGB(newX, newY)));

        // remove upper B edge
        graph.removeEdge(findEdgeBetweenPoints(graph, x1, x2, y1, y2, HyperEdgeType.BOUNDARY));

        // add new B edges
        graph.addEdge(new HyperEdge(HyperEdgeType.BOUNDARY, vertex1, newVertex),
                new HyperEdge(HyperEdgeType.BOUNDARY, vertex2, newVertex));

        // remove F edge
        graph.removeEdge(f1);

        // add new F edge
        graph.addEdge(new HyperEdge(HyperEdgeDirection.DOWN, vertex3, newVertex));

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

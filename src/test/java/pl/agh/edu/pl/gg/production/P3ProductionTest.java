package pl.agh.edu.pl.gg.production;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.production.P3Production;
import pl.edu.agh.gg.util.BitmapUtils;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class P3ProductionTest {

    private static final String BITMAP_FILE = "production3/bitmap.bmp";

    private BufferedImage bitmap;
    private HyperGraph graph;
    private P3Production p3Production;

    @Before
    public void before() throws IOException {
        bitmap = BitmapUtils.loadBitmapFromResource(BITMAP_FILE);

        graph = new HyperGraph();

        int x1 = bitmap.getMinX();
        int y1 = bitmap.getMinY() + bitmap.getHeight() - 1;
        int x2 = bitmap.getMinX() + bitmap.getWidth() - 1;
        int y2 = bitmap.getMinY() + bitmap.getHeight() - 1;
        int x3 = (bitmap.getMinX() + bitmap.getWidth() - 1) / 2;
        int y3 = (bitmap.getMinY() + bitmap.getHeight() - 1) / 2;

        Vertex vertex1 = new Vertex(new Point(x1, y1), new RgbColor(bitmap.getRGB(x1, y1)));
        Vertex vertex2 = new Vertex(new Point(x2, y2), new RgbColor(bitmap.getRGB(x2, y2)));
        Vertex vertex3 = new Vertex(new Point(x3, y3), new RgbColor(bitmap.getRGB(x3, y3)));

        HyperEdge boundary = new HyperEdge(HyperEdgeType.BOUNDARY, vertex1, vertex2);
        graph.addEdge(boundary,
                new HyperEdge(HyperEdgeType.INTERIOR, vertex1, vertex3),
                new HyperEdge(HyperEdgeType.INTERIOR, vertex2, vertex3),
                new HyperEdge(HyperEdgeDirection.UP, vertex3));

        p3Production = new P3Production(bitmap, boundary);
    }

    @Test
    public void testApplyingProduction() {
        p3Production.apply(graph);

        verifyWidthAndHeight();
        verifyColors();
        verifyEdgesAndVerticesNumber();
        verifyInteriorEdges();
        verifyFaceEdges();
        verifyBoundaryEdges();

    }

    private void verifyWidthAndHeight() {
        Vertex minXMaxYVertex = VertexUtil.findMinXMaxY(graph.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex maxXMaxYVertex = VertexUtil.findMaxXMaxY(graph.getVertices()).orElseThrow(IllegalStateException::new);

        int graphWidth = maxXMaxYVertex.getX() - minXMaxYVertex.getX() + 1;
        int graphHeight = (maxXMaxYVertex.getY() + 1) / 2;

        Assert.assertEquals(graphWidth, bitmap.getWidth());
        Assert.assertTrue(graphHeight <= bitmap.getHeight());
    }


    private void verifyColors() {
        graph.getVertices().forEach(vertex -> {
            RgbColor bitmapColor = new RgbColor(bitmap.getRGB(vertex.getX(), vertex.getY()));
            Assert.assertEquals(vertex.getColor(), bitmapColor);
        });
    }

    private void verifyEdgesAndVerticesNumber() {
        Assert.assertEquals(5, graph.getEdges().size());
        Assert.assertEquals(4, graph.getVertices().size());
    }

    private void verifyInteriorEdges() {
        Set<HyperEdge> interiorEdges = graph.getEdges().stream()
                .filter(edge -> edge.getType() == HyperEdgeType.INTERIOR)
                .collect(Collectors.toSet());
        Assert.assertEquals(2, interiorEdges.size());

        interiorEdges.forEach(edge -> {
            Assert.assertEquals(3, edge.getVertices().size());
            edge.getVertices().forEach(node -> Assert.assertTrue(graph.getVertices().contains(node)));
        });

        Set<Vertex> v = interiorEdges.stream().flatMap(edge -> edge.getVertices().stream()).collect(Collectors.toSet());
        Assert.assertEquals(4, v.size());
    }

    private void verifyFaceEdges() {
        Set<HyperEdge> faceEdges = graph.getEdges().stream()
                .filter(edge -> edge.getType() == HyperEdgeType.FACE)
                .collect(Collectors.toSet());
        Assert.assertEquals(1, faceEdges.size());

        faceEdges.forEach(edge -> {
            Assert.assertEquals(2, edge.getVertices().size());
            edge.getVertices().forEach(node -> Assert.assertTrue(graph.getVertices().contains(node)));
            edge.getVertices().forEach(vertex -> Assert.assertEquals(bitmap.getWidth() / 2 - 1, vertex.getX()));
            edge.getVertices().forEach(vertex -> Assert.assertTrue((bitmap.getHeight() / 2 - 1) == vertex.getY() || (bitmap.getHeight() - 1) == vertex.getY()));
        });

        Set<HyperEdgeDirection> directions = faceEdges.stream().map(HyperEdge::getDir).collect(Collectors.toSet());
        Assert.assertEquals(1, directions.size());
    }

    private void verifyBoundaryEdges() {
        Set<HyperEdge> borderEdges = graph.getEdges().stream()
                .filter(edge -> edge.getType().equals(HyperEdgeType.BOUNDARY))
                .collect(Collectors.toSet());
        borderEdges.forEach(edge -> Assert.assertEquals(2, edge.getVertices().size()));

        borderEdges.forEach(edge -> {
            Vertex vertex1 = VertexUtil.findMinXMaxY(graph.getVertices()).orElseThrow(IllegalStateException::new);
            Vertex vertex2 = VertexUtil.findMaxXMaxY(graph.getVertices()).orElseThrow(IllegalStateException::new);
            Vertex vertex3 = null;

            for (Vertex vertex : edge.getVertices()) {
                if (vertex.getX() == (vertex1.getX() + vertex2.getX()) / 2 && vertex.getY() == (vertex1.getY() + vertex2.getY()) / 2) {
                    vertex3 = vertex;
                }
            }
            assertThereIsEdgeBetween(vertex1, vertex3, borderEdges);
            assertThereIsEdgeBetween(vertex2, vertex3, borderEdges);
        });
    }

    private void assertThereIsEdgeBetween(Vertex vertex1, Vertex vertex2, Set<HyperEdge> edges) {
        Assert.assertTrue(edges.stream()
                .map(HyperEdge::getVertices)
                .anyMatch(vertices -> vertices.contains(vertex1) && vertices.contains(vertex2)));
    }
}
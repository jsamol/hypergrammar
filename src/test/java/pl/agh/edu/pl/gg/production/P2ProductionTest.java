package pl.agh.edu.pl.gg.production;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.production.P1Production;
import pl.edu.agh.gg.production.P2Production;
import pl.edu.agh.gg.production.P5Production;
import pl.edu.agh.gg.production.Production;
import pl.edu.agh.gg.util.BitmapUtils;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class P2ProductionTest {

    private static final String BITMAP_FILE = "production2/bitmap.bmp";

    private BufferedImage bitmap;
    private HyperGraph graph;
    private P2Production p2Production;

    @Before
    public void before() throws IOException {
        bitmap = BitmapUtils.loadBitmapFromResource(BITMAP_FILE);

        graph = new HyperGraph();
        P1Production p1Production = new P1Production(bitmap);
        p1Production.apply(graph);

        HyperEdge edge = graph.getEdges().stream().filter(e -> e.getType() == HyperEdgeType.INTERIOR).findFirst().get();

        Production p5Production = new P5Production(edge);
        p5Production.apply(graph);

        p2Production = new P2Production(bitmap, edge);
    }

    @Test
    public void testApplyingProduction() {
        p2Production.apply(graph);

        verifyWidthAndHeight();
        verifyColors();
        verifyEdgesAndVerticesNumber();
        verifyInteriorEdges();
        verifyFaceEdges();
        verifyBoundaryEdges();
    }

    private void verifyWidthAndHeight() {
        Vertex minXMinYVertex = VertexUtil.findMinXMinY(graph.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex maxXMaxYVertex = VertexUtil.findMaxXMaxY(graph.getVertices()).orElseThrow(IllegalStateException::new);

        int graphWidth = maxXMaxYVertex.getX() - minXMinYVertex.getX() + 1;
        int graphHeight = maxXMaxYVertex.getY() - minXMinYVertex.getY() + 1;

        Assert.assertEquals(graphWidth, bitmap.getWidth());
        Assert.assertEquals(graphHeight, bitmap.getHeight());
    }


    private void verifyColors() {
        graph.getVertices().forEach(vertex -> {
            RgbColor bitmapColor = new RgbColor(bitmap.getRGB(vertex.getX(), vertex.getY()));
            Assert.assertEquals(vertex.getColor(), bitmapColor);
        });
    }

    private void verifyEdgesAndVerticesNumber() {
        Assert.assertEquals(12, graph.getEdges().size());
        Assert.assertEquals(5, graph.getVertices().size());
    }

    private void verifyInteriorEdges() {
        Set<HyperEdge> interiorEdges = graph.getEdges().stream()
                .filter(edge -> edge.getType() == HyperEdgeType.INTERIOR)
                .collect(Collectors.toSet());
        Assert.assertEquals(4, interiorEdges.size());

        interiorEdges.forEach(edge -> {
            Assert.assertEquals(2, edge.getVertices().size());
            edge.getVertices().forEach(node -> Assert.assertTrue(graph.getVertices().contains(node)));
        });

        Set<Vertex> v = interiorEdges.stream().flatMap(edge -> edge.getVertices().stream()).collect(Collectors.toSet());
        Assert.assertEquals(5, v.size());
    }

    private void verifyFaceEdges() {
        Set<HyperEdge> faceEdges = graph.getEdges().stream()
                .filter(edge -> edge.getType() == HyperEdgeType.FACE)
                .collect(Collectors.toSet());
        Assert.assertEquals(4, faceEdges.size());

        Vertex v = faceEdges.stream().findFirst().orElseThrow(IllegalStateException::new)
                .getVertices().stream().findFirst().orElseThrow(IllegalStateException::new);

        Assert.assertEquals(bitmap.getWidth() / 2 - 1, v.getX());
        Assert.assertEquals(bitmap.getHeight() / 2 - 1, v.getY());

        faceEdges.forEach(edge -> {
            Assert.assertEquals(1, edge.getVertices().size());
            edge.getVertices().forEach(node -> {
                Assert.assertSame(node, v);
                Assert.assertTrue(graph.getVertices().contains(node));
            });
        });

        Set<HyperEdgeDirection> directions = faceEdges.stream().map(HyperEdge::getDir).collect(Collectors.toSet());
        Assert.assertEquals(4, directions.size());
    }

    private void verifyBoundaryEdges() {
        Set<HyperEdge> borderEdges = graph.getEdges().stream()
                .filter(edge -> edge.getType().equals(HyperEdgeType.BOUNDARY))
                .collect(Collectors.toSet());
        borderEdges.forEach(edge -> Assert.assertEquals(2, edge.getVertices().size()));

        Vertex vertex1 = VertexUtil.findMinXMaxY(graph.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex vertex2 = VertexUtil.findMaxXMaxY(graph.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex vertex3 = VertexUtil.findMinXMinY(graph.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex vertex4 = VertexUtil.findMaxXMinY(graph.getVertices()).orElseThrow(IllegalStateException::new);

        assertThereIsEdgeBetween(vertex1, vertex2, borderEdges);
        assertThereIsEdgeBetween(vertex1, vertex3, borderEdges);
        assertThereIsEdgeBetween(vertex2, vertex4, borderEdges);
        assertThereIsEdgeBetween(vertex3, vertex4, borderEdges);
    }

    private void assertThereIsEdgeBetween(Vertex vertex1, Vertex vertex2, Set<HyperEdge> edges) {
        Assert.assertTrue(edges.stream()
                .map(HyperEdge::getVertices)
                .anyMatch(vertices -> vertices.contains(vertex1) && vertices.contains(vertex2)));
    }
}

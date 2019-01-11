package pl.agh.edu.pl.gg.production;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.demo.production4.Production4DemoBuilder;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.production.P1Production;
import pl.edu.agh.gg.production.P4Production;
import pl.edu.agh.gg.production.Production;
import pl.edu.agh.gg.util.BitmapUtils;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.LEFT;
import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.RIGHT;
import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.UP;
import static pl.edu.agh.gg.hypergraph.HyperEdgeType.INTERIOR;

public class P4ProductionTest {

    private static final String BITMAP_FILE = "production4/bitmap.bmp";

    private BufferedImage image;
    private HyperGraph graph;
    private Production p4Production;

    @Before
    public void before() throws IOException {
        image = BitmapUtils.loadBitmapFromResource(BITMAP_FILE);

        graph = new HyperGraph();

        Production4DemoBuilder builder = new Production4DemoBuilder(graph, image);

        p4Production = new P4Production(image, builder.getF2Left(), builder.getF2Right(), builder.getF1Up());
    }

    @Test
    public void verifyWidthAndHeight() {
        verifyWidthAndHeightCore();

        p4Production.apply(graph);

        verifyWidthAndHeightCore();
    }

    private void verifyWidthAndHeightCore() {
        Vertex minX = graph.getVertices().stream().min(Comparator.comparing(Vertex::getX)).get();
        Vertex maxX = graph.getVertices().stream().max(Comparator.comparing(Vertex::getX)).get();
        Vertex minY = graph.getVertices().stream().min(Comparator.comparing(Vertex::getY)).get();
        Vertex maxY = graph.getVertices().stream().max(Comparator.comparing(Vertex::getY)).get();

        int graphWidth = maxX.getX() - minX.getX() + 1;
        int graphHeight = maxY.getY() - minY.getY() + 1;

        Assert.assertEquals(graphWidth, image.getWidth());
        Assert.assertEquals(graphHeight, image.getHeight());
    }


    @Test
    public void verifyColors() {
        verifyColorsCore();

        p4Production.apply(graph);

        verifyColorsCore();
    }

    private void verifyColorsCore() {
        graph.getVertices().forEach(vertex -> {
            RgbColor bitmapColor = new RgbColor(image.getRGB(vertex.getX(), vertex.getY()));
            Assert.assertEquals(vertex.getColor(), bitmapColor);
        });
    }

    @Test
    public void verifyEdgesAndVerticesNumber() {
        Assert.assertEquals(7, graph.getEdges().size());
        int vertices = graph.getVertices().size();

        p4Production.apply(graph);

        Assert.assertEquals(8, graph.getEdges().size());
        Assert.assertEquals(vertices + 1, graph.getVertices().size());
    }

    @Test
    public void verifyInteriorEdge() {
        verifyInteriorEdgeCore(false);

        p4Production.apply(graph);

        verifyInteriorEdgeCore(true);
    }

    public void verifyInteriorEdgeCore(boolean applied) {
        Set<HyperEdge> interiorEdges = graph.getEdges().stream()
                .filter(edge -> edge.getType() == HyperEdgeType.INTERIOR)
                .collect(Collectors.toSet());
        Assert.assertEquals(4, interiorEdges.size());

        interiorEdges.forEach(edge -> {
            Assert.assertTrue(edge.getVertices().size() >= 2 + (applied ? 1 : 0)
                    && edge.getVertices().size() <= 3 + (applied ? 1 : 0));
            edge.getVertices().forEach(node -> Assert.assertTrue(graph.getVertices().contains(node)));
        });
    }

    @Test
    public void verifyFaceEdges() {
        verifyFaceEdgesCore(false);

        p4Production.apply(graph);

        verifyFaceEdgesCore(true);
    }

    private void verifyFaceEdgesCore(boolean applied) {
        Set<HyperEdge> faceEdges = graph.getEdges().stream()
                .filter(edge -> edge.getType() == HyperEdgeType.FACE)
                .collect(Collectors.toSet());

        // check sum of all face edges
        Assert.assertEquals(applied ? 4 : 3, faceEdges.size());

        // check sum of all face edges neighbour vertices
        Assert.assertEquals(applied ? 8 : 4, faceEdges.stream().map(HyperEdge::getVertices).mapToLong(value -> value.stream().count()).sum());

        // check if all face edge has 2 vertices
        if (applied)
            faceEdges.forEach(edge -> {
                Assert.assertEquals(2, edge.getVertices().size());
                edge.getVertices().forEach(node -> {
                    Assert.assertTrue(graph.getVertices().contains(node));
                });
            });

        Set<HyperEdgeDirection> directions = faceEdges.stream().map(HyperEdge::getDir).collect(Collectors.toSet());
        Assert.assertEquals(applied ? 4 : 3, directions.size());
    }

    /*@Test
    public void verifyBoundaryEdges() {
        Set<HyperEdge> borderEdges = graph.getEdges().stream().filter(edge -> edge.getType().equals(HyperEdgeType.BOUNDARY)).collect(Collectors.toSet());
        borderEdges.forEach(borderEdge -> Assert.assertEquals(2, borderEdge.getVertices().size()));

        Vertex vertex1 = VertexUtil.findMinXMaxY(graph.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex vertex2 = VertexUtil.findMaxXMaxY(graph.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex vertex3 = VertexUtil.findMinXMinY(graph.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex vertex4 = VertexUtil.findMaxXMinY(graph.getVertices()).orElseThrow(IllegalStateException::new);

        assertThereIsEdgeBetween(vertex1, vertex2, borderEdges);
        assertThereIsEdgeBetween(vertex1, vertex3, borderEdges);
        assertThereIsEdgeBetween(vertex2, vertex4, borderEdges);
        assertThereIsEdgeBetween(vertex3, vertex4, borderEdges);
    }*/

    private void assertThereIsEdgeBetween(Vertex vertex1, Vertex vertex2, Set<HyperEdge> edges) {
        Assert.assertTrue(edges.stream()
                               .map(HyperEdge::getVertices)
                               .anyMatch(vertices -> vertices.contains(vertex1) && vertices.contains(vertex2)));
    }
}

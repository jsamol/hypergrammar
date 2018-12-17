package pl.agh.edu.pl.gg.production;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.production.P1Production;
import pl.edu.agh.gg.util.BitmapUtils;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class P1ProductionTest {

    private static final String BITMAP_FILE = "production1/bitmap.bmp";

    private BufferedImage bitmap;
    private HyperGraph graph;
    private P1Production p1Production;

    @Before
    public void before() throws IOException {
        bitmap = BitmapUtils.loadBitmapFromResource(BITMAP_FILE);

        graph = new HyperGraph();
        p1Production = new P1Production(bitmap);
    }

    @Test
    public void testApplyingProduction() {
        p1Production.apply(graph);

        verifyWidthAndHeight();
        verifyColors();
        verifyEdgesAndVerticesNumber();
        verifyInteriorEdge();
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
        Assert.assertEquals(5, graph.getEdges().size());
        Assert.assertEquals(4, graph.getVertices().size());
    }

    private void verifyInteriorEdge() {
        HyperEdge interiorEdge = graph.getEdges().stream().filter(edge -> edge.getType().equals(HyperEdgeType.INTERIOR)).findFirst().orElseThrow(IllegalStateException::new);
        Assert.assertEquals(4, interiorEdge.getVertices().size());
        graph.getVertices().forEach(vertex -> Assert.assertTrue(interiorEdge.getVertices().contains(vertex)));
    }

    private void verifyBoundaryEdges() {
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
    }

    private void assertThereIsEdgeBetween(Vertex vertex1, Vertex vertex2, Set<HyperEdge> edges) {
        Assert.assertTrue(edges.stream()
                               .map(HyperEdge::getVertices)
                               .anyMatch(vertices -> vertices.contains(vertex1) && vertices.contains(vertex2)));
    }
}

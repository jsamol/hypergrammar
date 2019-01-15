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
import pl.edu.agh.gg.production.P2Production;
import pl.edu.agh.gg.production.P5Production;
import pl.edu.agh.gg.production.Production;
import pl.edu.agh.gg.util.BitmapUtils;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class GenerationTest {
    private static final String BITMAP_FILE = "production2/bitmap.bmp";

    private BufferedImage bitmap;
    private HyperGraph graph;

    @Before
    public void before() throws IOException {
        bitmap = BitmapUtils.loadBitmapFromResource(BITMAP_FILE);

        graph = new HyperGraph();
        //      P1
        P1Production p1Production = new P1Production(bitmap);
        p1Production.apply(graph);

        //      P5 & P2 (1)
        HyperEdge edge_1 = graph.getEdges().stream().filter(e -> e.getType() == HyperEdgeType.INTERIOR).findFirst().get();

        Production p5Production_1 = new P5Production(edge_1);
        p5Production_1.apply(graph);

        Production p2Production_1 = new P2Production(bitmap, edge_1);
        p2Production_1.apply(graph);

        //        Add 4 x P3

        //      P5 & P2 (2)
        HyperEdge edge_2 = graph.getEdges().stream()
                .filter(e -> e.getType() == HyperEdgeType.INTERIOR && !e.getCanBreak())
                .findFirst().get();
        Production p5Production_2 = new P5Production(edge_2);
        p5Production_2.apply(graph);

        Production p2Production_2 = new P2Production(bitmap, edge_2);
        p2Production_2.apply(graph);

        //      P5 & P2 (3)
        HyperEdge edge_3 = graph.getEdges().stream()
                .filter(e -> e.getType() == HyperEdgeType.INTERIOR && !e.getCanBreak())
                .findFirst().get();
        Production p5Production_3 = new P5Production(edge_3);
        p5Production_3.apply(graph);

        Production p2Production_3 = new P2Production(bitmap, edge_3);
        p2Production_3.apply(graph);

        //      P5 & P2 (4)
        HyperEdge edge_4 = graph.getEdges().stream()
                .filter(e -> e.getType() == HyperEdgeType.INTERIOR && !e.getCanBreak())
                .findFirst().get();
        Production p5Production_4 = new P5Production(edge_4);
        p5Production_4.apply(graph);

        Production p2Production_4 = new P2Production(bitmap, edge_4);
        p2Production_4.apply(graph);

//        Add 6 x P3
//        Add 2 x P4
//        Add P5
//        Add P2
//        Add P5
    }

    @Test
    public void testApplyingProduction() {
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

    private void verifyInteriorEdges() {
        Set<HyperEdge> interiorEdges = graph.getEdges().stream().filter(edge -> edge.getType().equals(HyperEdgeType.INTERIOR)).collect(Collectors.toSet());
        Assert.assertEquals(16, interiorEdges.size());
    }

    private void verifyFaceEdges() {
        Set<HyperEdge> faceEdges = graph.getEdges().stream().filter(edge -> edge.getType().equals(HyperEdgeType.FACE)).collect(Collectors.toSet());
        Assert.assertEquals(20, faceEdges.size());
    }

    private void verifyBoundaryEdges() {
        Set<HyperEdge> borderEdges = graph.getEdges().stream().filter(edge -> edge.getType().equals(HyperEdgeType.BOUNDARY)).collect(Collectors.toSet());
        Assert.assertEquals(14, borderEdges.size());
    }

    private void verifyEdgesAndVerticesNumber() {
        Assert.assertEquals(50, graph.getEdges().size());
        Assert.assertEquals(21, graph.getVertices().size());
    }
}

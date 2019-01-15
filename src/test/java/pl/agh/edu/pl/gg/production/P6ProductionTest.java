package pl.agh.edu.pl.gg.production;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.production.P6Production;
import java.io.IOException;


import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.UP;
import static pl.edu.agh.gg.hypergraph.HyperEdgeType.*;

public class P6ProductionTest {
    private HyperGraph graph;
    private P6Production p6Production;

    private Vertex v06 = new Vertex(0, 6);
    private Vertex v04 = new Vertex(0, 4);
    private Vertex v26 = new Vertex(2, 6);

    private Vertex v20 = new Vertex(2, 0);
    private Vertex v60 = new Vertex(6, 0);
    private Vertex v66 = new Vertex(6, 6);
    private HyperEdge small = new HyperEdge(INTERIOR, v06, v04, v26);
    private HyperEdge big = new HyperEdge(INTERIOR, v20, v60, v26, v66);
    private HyperEdge common = new HyperEdge(UP, v26, v20);

    @Before
    public void before() throws IOException {
        graph = new HyperGraph();

        small.setCanBreak(true);
        big.setCanBreak(false);

        graph.addEdge(small, big, common);
        p6Production = new P6Production(small, big);
    }

    @Test
    public void testApplyingProduction() {
        p6Production.apply(graph);

        verifyEdgesAndVerticesNumber();
        verifySmallEdge();
        verifyBigEdge();
    }

    private void verifyEdgesAndVerticesNumber() {
        Assert.assertEquals(3, graph.getEdges().size());
        Assert.assertEquals(6, graph.getVertices().size());
    }

    private void verifySmallEdge() {
        HyperEdge small = graph.getEdges().stream()
                .filter(e -> e.getType() == INTERIOR)
                .filter(e -> e.getVertices().size() == 3)
                .findFirst().get();
        Assert.assertTrue(small.getCanBreak());
    }

    private void verifyBigEdge() {
        HyperEdge small = graph.getEdges().stream()
                .filter(e -> e.getType() == INTERIOR)
                .filter(e -> e.getVertices().size() == 4)
                .findFirst().get();
        Assert.assertTrue(small.getCanBreak());
    }

    @Test
    public void testSmallHyperEdgeType() {
        small.setType(FACE);

        try {
            p6Production.apply(graph);
        } catch (Exception e){
            String expectedMessage = "both edges should have type INTERIOR";
            Assert.assertEquals( expectedMessage, e.getMessage() );
        }
    }

    @Test
    public void testBigHyperEdgeType() {
        big.setType(BOUNDARY);

        try {
            p6Production.apply(graph);
        } catch (Exception e){
            String expectedMessage = "both edges should have type INTERIOR";
            Assert.assertEquals( expectedMessage, e.getMessage() );
        }
    }

    @Test
    public void testHyperEdgesBreakeOptions() {
        small.setCanBreak(false);

        try {
            p6Production.apply(graph);
        } catch (Exception e){
            String expectedMessage = "canBreak for smaller edge should be true";
            Assert.assertEquals( expectedMessage, e.getMessage() );
        }
    }

    @Test
    public void testHyperEdgesMinVertexRequired() {
        small.removeVertex(v06);
        small.removeVertex(v04);

        try {
            p6Production.apply(graph);
        } catch (Exception e){
            String expectedMessage = "smaller edge should contain more than 1 vertex";
            Assert.assertEquals( expectedMessage, e.getMessage() );
        }
    }

    @Test
    public void testCommonVertex() {
        small.removeVertex(v26);

        try {
            p6Production.apply(graph);
        } catch (Exception e){
            String expectedMessage = "edges should have one common vertex";
            Assert.assertEquals( expectedMessage, e.getMessage() );
        }
    }
}

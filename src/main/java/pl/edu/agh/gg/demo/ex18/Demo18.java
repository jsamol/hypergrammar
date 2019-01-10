package pl.edu.agh.gg.demo.ex18;

import org.junit.Test;
import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.ui.GraphBitmapApproximationDrawer;
import pl.edu.agh.gg.ui.HyperGraphDrawer;

import java.util.Arrays;
import java.util.List;

public class Demo18 {
    private static final boolean DRAW_GRAPH = false;

    private static RgbColor RED = new RgbColor(255, 0, 0);
    private static RgbColor BLUE = new RgbColor(0, 0, 255);
    private static RgbColor GREEN = new RgbColor(0, 255, 0);
    private static RgbColor WHITE = new RgbColor(255, 255, 255);
    private static RgbColor BLACK = new RgbColor(0, 0, 0);
    private static RgbColor DEFAULT_COLOR = RED;

    private static Vertex v000000 = createVertex(0, 0);
    private static Vertex v100000 = createVertex(100, 0);
    private static Vertex v200000 = createVertex(200, 0);
    private static Vertex v300000 = createVertex(300, 0);
    private static Vertex v400000 = createVertex(400, 0);
    private static Vertex v000100 = createVertex(0, 100);
    private static Vertex v100100 = createVertex(100, 100);
    private static Vertex v200100 = createVertex(200, 100);
    private static Vertex v300100 = createVertex(300, 100);
    private static Vertex v400100 = createVertex(400, 100);
    private static Vertex v000200 = createVertex(0, 200);
    private static Vertex v100200 = createVertex(100, 200);
    private static Vertex v200200 = createVertex(200, 200);
    private static Vertex v300200 = createVertex(300, 200);
    private static Vertex v400200 = createVertex(400, 200);
    private static Vertex v000300 = createVertex(0, 300);
    private static Vertex v100300 = createVertex(100, 300);
    private static Vertex v200300 = createVertex(200, 300);
    private static Vertex v300300 = createVertex(300, 300);
    private static Vertex v400300 = createVertex(400, 300);
    private static Vertex v000400 = createVertex(0, 400);
    private static Vertex v100400 = createVertex(100, 400);
    private static Vertex v200400 = createVertex(200, 400);
    private static Vertex v300400 = createVertex(300, 400);
    private static Vertex v400400 = createVertex(400, 400);

    @Test
    public void createRedGreenBlueHyperGraph() {
        HyperGraph graph = new HyperGraph();
        v000000.setColor(RED);
        v100000.setColor(RED);
        v200000.setColor(RED);
        v300000.setColor(BLUE);
        v400000.setColor(BLUE);
        v000100.setColor(RED);
        v100100.setColor(RED);
        v200100.setColor(RED);
        v300100.setColor(BLUE);
        v400100.setColor(BLUE);
        v000200.setColor(GREEN);
        v200200.setColor(GREEN);
        v300200.setColor(RED);
        v400200.setColor(RED);
        v300300.setColor(RED);
        v400300.setColor(RED);
        v000400.setColor(GREEN);
        v200400.setColor(GREEN);
        v300400.setColor(RED);
        v400400.setColor(RED);
        createGraphWithout2VerticesInteriorEdges(graph);

        if (DRAW_GRAPH) {
            HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
            drawer.draw();
        }

        GraphBitmapApproximationDrawer.drawGraphBitmap(graph, "redGreenBlueGraphApprox.bmp");
    }

    @Test
    public void createWhiteBlackHyperGraph() {
        HyperGraph graph = new HyperGraph();
        v000000.setColor(BLACK);
        v100000.setColor(BLACK);
        v200000.setColor(WHITE);
        v300000.setColor(WHITE);
        v400000.setColor(WHITE);
        v000100.setColor(BLACK);
        v100100.setColor(BLACK);
        v200100.setColor(WHITE);
        v300100.setColor(WHITE);
        v400100.setColor(WHITE);
        v000200.setColor(BLACK);
        v200200.setColor(BLACK);
        v300200.setColor(WHITE);
        v400200.setColor(WHITE);
        v300300.setColor(WHITE);
        v400300.setColor(WHITE);
        v000400.setColor(BLACK);
        v200400.setColor(BLACK);
        v300400.setColor(WHITE);
        v400400.setColor(WHITE);
        createGraphWithout2VerticesInteriorEdges(graph);

        if (DRAW_GRAPH) {
            HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
            drawer.draw();
        }

        GraphBitmapApproximationDrawer.drawGraphBitmap(graph, "whiteBlackGraphApprox.bmp");
    }

    @Test
    public void createBlueRedHyperGraph() {
        HyperGraph graph = new HyperGraph();
        v000000.setColor(RED);
        v100000.setColor(RED);
        v200000.setColor(RED);
        v300000.setColor(BLUE);
        v400000.setColor(BLUE);
        v000100.setColor(RED);
        v100100.setColor(RED);
        v200100.setColor(RED);
        v300100.setColor(BLUE);
        v400100.setColor(BLUE);
        v000200.setColor(RED);
        v200200.setColor(RED);
        v300200.setColor(RED);
        v400200.setColor(RED);
        v300300.setColor(RED);
        v400300.setColor(RED);
        v000400.setColor(RED);
        v200400.setColor(RED);
        v300400.setColor(RED);
        v400400.setColor(RED);
        createGraphWithout2VerticesInteriorEdges(graph);

        if (DRAW_GRAPH) {
            HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
            drawer.draw();
        }

        GraphBitmapApproximationDrawer.drawGraphBitmap(graph, "blueRedGraphApprox.bmp");
    }

    @Test
    public void createRedHyperGraph() {
        HyperGraph graph = new HyperGraph();
        v000000.setColor(RED);
        v100000.setColor(RED);
        v200000.setColor(RED);
        v300000.setColor(RED);
        v400000.setColor(RED);
        v000100.setColor(RED);
        v100100.setColor(RED);
        v200100.setColor(RED);
        v300100.setColor(RED);
        v400100.setColor(RED);
        v000200.setColor(RED);
        v200200.setColor(RED);
        v300200.setColor(RED);
        v400200.setColor(RED);
        v300300.setColor(RED);
        v400300.setColor(RED);
        v000400.setColor(RED);
        v200400.setColor(RED);
        v300400.setColor(RED);
        v400400.setColor(RED);
        createGraphWithout2VerticesInteriorEdges(graph);

        if (DRAW_GRAPH) {
            HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
            drawer.draw();
        }

        GraphBitmapApproximationDrawer.drawGraphBitmap(graph, "redGraphApprox.bmp");
    }

    private void createGraphWithout2VerticesInteriorEdges(HyperGraph graph) {
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v000400, v200400, v200200, v000200));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v200400, v300400, v300300));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v300300, v300400, v400300, v400400));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v200200, v300200, v300300));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v300200, v300300, v400200, v400300));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v000100, v000200, v100100));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v100100, v200100, v200200));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v200100, v300100, v200200, v300200));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v300100, v400100, v300200, v400200));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v000000, v100000, v000100, v100100));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v100000, v200000, v100100, v200100));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v200000, v300000, v200100, v300100));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(v300000, v400000, v300100, v400100));

        //horizontally from bottom and from left
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v000000, v100000));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v100000, v200000));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v200000, v300000));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v300000, v400000));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v000100, v100100));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v100100, v200100));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v200100, v300100));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v300100, v400100));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v000200, v200200));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v200200, v300200));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v300200, v400200));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v300300, v400300));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v000400, v200400));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v200400, v300400));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v300400, v400400));

        //vertically from bottom and from left
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v000000, v000100));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v000100, v000200));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v000200, v000400));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v100000, v100100));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v200000, v200100));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v200100, v200200));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v200200, v200400));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v300000, v300100));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v300100, v300200));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v300200, v300300));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(v300300, v300400));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v400000, v400100));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v400100, v400200));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v400200, v400300));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(v400300, v400400));
    }

    private void addHyperEdge(HyperGraph graph, HyperEdgeType edgeType, List<Vertex> vertices) {
        HyperEdge edge = new HyperEdge(edgeType, vertices);
        graph.addEdge(edge);
    }

    private static Vertex createVertex(int x, int y) {
        return new Vertex(new Point(x, y), DEFAULT_COLOR);
    }
}

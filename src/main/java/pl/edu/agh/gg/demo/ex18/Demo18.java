package pl.edu.agh.gg.demo.ex18;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.ui.GraphBitmapApproximationDrawer;
import pl.edu.agh.gg.ui.HyperGraphDrawer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Demo18 {

    private static RgbColor RED = new RgbColor(255, 0, 0);
    private static RgbColor BLUE = new RgbColor(0, 0, 255);
    private static RgbColor GREEN = new RgbColor(0, 255, 0);
    private static RgbColor WHITE = new RgbColor(255, 255, 255);
    private static RgbColor BLACK = new RgbColor(0, 0, 0);

    public static void main(String[] args) throws IOException {
        Demo18 app = new Demo18();
        app.runManuallyCreatedGraph();
    }

    private void runManuallyCreatedGraph() {
        HyperGraph redHyperGraph = createRedHyperGraph();
        HyperGraph redGreenBlueHyperGraph = createRedGreenBlueHyperGraph();
        HyperGraph whiteBlackHyperGraph = createWhiteBlackHyperGraph();
        HyperGraph blueRedHyperGraph = createBlueRedHyperGraph();

        HyperGraphDrawer drawer = new HyperGraphDrawer(blueRedHyperGraph);
        drawer.draw();

        GraphBitmapApproximationDrawer.drawGraphBitmap(redHyperGraph, "redGraphApprox.bmp");
        GraphBitmapApproximationDrawer.drawGraphBitmap(redGreenBlueHyperGraph, "redGreenBlueGraphApprox.bmp");
        GraphBitmapApproximationDrawer.drawGraphBitmap(whiteBlackHyperGraph, "whiteBlackGraphApprox.bmp");
    }

    private HyperGraph createRedGreenBlueHyperGraph() {
        HyperGraph graph = new HyperGraph();
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 400, GREEN), createVertex(200, 400, GREEN), createVertex(200, 200, GREEN), createVertex(0, 200, GREEN)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 400, GREEN), createVertex(300, 400, RED), createVertex(300, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 300, RED), createVertex(300, 400, RED), createVertex(400, 300, RED), createVertex(400, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 200, GREEN), createVertex(300, 200, RED), createVertex(300, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 200, RED), createVertex(300, 300, RED), createVertex(400, 200, RED), createVertex(400, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 100, RED), createVertex(0, 200, GREEN), createVertex(100, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(100, 100, RED), createVertex(200, 100, RED), createVertex(200, 200, GREEN)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 100, RED), createVertex(300, 100, BLUE), createVertex(200, 200, GREEN), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 100, BLUE), createVertex(400, 100, BLUE), createVertex(300, 200, RED), createVertex(400, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 0, RED), createVertex(100, 0, RED), createVertex(0, 100, RED), createVertex(100, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(100, 0, RED), createVertex(200, 0, RED), createVertex(100, 100, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 0, RED), createVertex(300, 0, BLUE), createVertex(200, 100, RED), createVertex(300, 100, BLUE)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 0, BLUE), createVertex(400, 0, BLUE), createVertex(300, 100, BLUE), createVertex(400, 100, BLUE)));

        //horizontally from bottom and from left
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 0, RED), createVertex(100, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(100, 0, RED), createVertex(200, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 0, RED), createVertex(300, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 0, RED), createVertex(400, 0, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 100, RED), createVertex(100, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(100, 100, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 100, RED), createVertex(300, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 100, RED), createVertex(400, 100, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 200, RED), createVertex(200, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 200, RED), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 200, RED), createVertex(400, 200, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 300, RED), createVertex(400, 300, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 400, RED), createVertex(200, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 400, RED), createVertex(300, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 400, RED), createVertex(400, 400, RED)));

        //vertically from bottom and from left
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 0, RED), createVertex(0, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 100, RED), createVertex(0, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 200, RED), createVertex(0, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(100, 0, RED), createVertex(100, 100, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 0, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 100, RED), createVertex(200, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 200, RED), createVertex(200, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 0, RED), createVertex(300, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 100, RED), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 200, RED), createVertex(300, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 300, RED), createVertex(300, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 0, RED), createVertex(400, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 100, RED), createVertex(400, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 200, RED), createVertex(400, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 300, RED), createVertex(400, 400, RED)));

        return graph;
    }

    private HyperGraph createWhiteBlackHyperGraph() {
        HyperGraph graph = new HyperGraph();
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 400, BLACK), createVertex(200, 400, BLACK), createVertex(200, 200, BLACK), createVertex(0, 200, BLACK)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 400, BLACK), createVertex(300, 400, WHITE), createVertex(300, 300, WHITE)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 300, WHITE), createVertex(300, 400, WHITE), createVertex(400, 300, WHITE), createVertex(400, 400, WHITE)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 200, BLACK), createVertex(300, 200, WHITE), createVertex(300, 300, WHITE)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 200, WHITE), createVertex(300, 300, WHITE), createVertex(400, 200, WHITE), createVertex(400, 300, WHITE)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 100, BLACK), createVertex(0, 200, BLACK), createVertex(100, 100, BLACK)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(100, 100, BLACK), createVertex(200, 100, WHITE), createVertex(200, 200, BLACK)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 100, WHITE), createVertex(300, 100, WHITE), createVertex(200, 200, BLACK), createVertex(300, 200, WHITE)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 100, WHITE), createVertex(400, 100, WHITE), createVertex(300, 200, WHITE), createVertex(400, 200, WHITE)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 0, BLACK), createVertex(100, 0, BLACK), createVertex(0, 100, BLACK), createVertex(100, 100, BLACK)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(100, 0, BLACK), createVertex(200, 0, WHITE), createVertex(100, 100, BLACK), createVertex(200, 100, WHITE)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 0, WHITE), createVertex(300, 0, WHITE), createVertex(200, 100, WHITE), createVertex(300, 100, WHITE)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 0, WHITE), createVertex(400, 0, WHITE), createVertex(300, 100, WHITE), createVertex(400, 100, WHITE)));

        //horizontally from bottom and from left
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 0, RED), createVertex(100, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(100, 0, RED), createVertex(200, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 0, RED), createVertex(300, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 0, RED), createVertex(400, 0, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 100, RED), createVertex(100, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(100, 100, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 100, RED), createVertex(300, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 100, RED), createVertex(400, 100, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 200, RED), createVertex(200, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 200, RED), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 200, RED), createVertex(400, 200, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 300, RED), createVertex(400, 300, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 400, RED), createVertex(200, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 400, RED), createVertex(300, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 400, RED), createVertex(400, 400, RED)));

        //vertically from bottom and from left
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 0, RED), createVertex(0, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 100, RED), createVertex(0, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 200, RED), createVertex(0, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(100, 0, RED), createVertex(100, 100, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 0, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 100, RED), createVertex(200, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 200, RED), createVertex(200, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 0, RED), createVertex(300, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 100, RED), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 200, RED), createVertex(300, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 300, RED), createVertex(300, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 0, RED), createVertex(400, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 100, RED), createVertex(400, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 200, RED), createVertex(400, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 300, RED), createVertex(400, 400, RED)));

        return graph;
    }

    private HyperGraph createBlueRedHyperGraph() {
        HyperGraph graph = new HyperGraph();
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 400, RED), createVertex(200, 400, RED), createVertex(200, 200, RED), createVertex(0, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 400, RED), createVertex(300, 400, RED), createVertex(300, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 300, RED), createVertex(300, 400, RED), createVertex(400, 300, RED), createVertex(400, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 200, RED), createVertex(300, 200, RED), createVertex(300, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 200, RED), createVertex(300, 300, RED), createVertex(400, 200, RED), createVertex(400, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 100, RED), createVertex(0, 200, RED), createVertex(100, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(100, 100, RED), createVertex(200, 100, RED), createVertex(200, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 100, RED), createVertex(300, 100, BLUE), createVertex(200, 200, RED), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 100, BLUE), createVertex(400, 100, BLUE), createVertex(300, 200, RED), createVertex(400, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 0, RED), createVertex(100, 0, RED), createVertex(0, 100, RED), createVertex(100, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(100, 0, RED), createVertex(200, 0, RED), createVertex(100, 100, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 0, RED), createVertex(300, 0, BLUE), createVertex(200, 100, RED), createVertex(300, 100, BLUE)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 0, BLUE), createVertex(400, 0, BLUE), createVertex(300, 100, BLUE), createVertex(400, 100, BLUE)));

        //horizontally from bottom and from left
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 0, RED), createVertex(100, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(100, 0, RED), createVertex(200, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 0, RED), createVertex(300, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 0, RED), createVertex(400, 0, RED)));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(0, 100, RED), createVertex(100, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(100, 100, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(200, 100, RED), createVertex(300, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(300, 100, RED), createVertex(400, 100, RED)));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(0, 200, RED), createVertex(200, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(200, 200, RED), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(300, 200, RED), createVertex(400, 200, RED)));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(300, 300, RED), createVertex(400, 300, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 400, RED), createVertex(200, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 400, RED), createVertex(300, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 400, RED), createVertex(400, 400, RED)));

        //vertically from bottom and from left
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 0, RED), createVertex(0, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 100, RED), createVertex(0, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 200, RED), createVertex(0, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(100, 0, RED), createVertex(100, 100, RED)));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(200, 0, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(200, 100, RED), createVertex(200, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(200, 200, RED), createVertex(200, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(300, 0, RED), createVertex(300, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(300, 100, RED), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(300, 200, RED), createVertex(300, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.FACE, Arrays.asList(createVertex(300, 300, RED), createVertex(300, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 0, RED), createVertex(400, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 100, RED), createVertex(400, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 200, RED), createVertex(400, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 300, RED), createVertex(400, 400, RED)));

        return graph;
    }

    private HyperGraph createRedHyperGraph() {
        HyperGraph graph = new HyperGraph();
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 400, RED), createVertex(200, 400, RED), createVertex(200, 200, RED), createVertex(0, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 400, RED), createVertex(300, 400, RED), createVertex(300, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 300, RED), createVertex(300, 400, RED), createVertex(400, 300, RED), createVertex(400, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 200, RED), createVertex(300, 200, RED), createVertex(300, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 200, RED), createVertex(300, 300, RED), createVertex(400, 200, RED), createVertex(400, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 100, RED), createVertex(0, 200, RED), createVertex(100, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(100, 100, RED), createVertex(200, 100, RED), createVertex(200, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 100, RED), createVertex(300, 100, RED), createVertex(200, 200, RED), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 100, RED), createVertex(400, 100, RED), createVertex(300, 200, RED), createVertex(400, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(0, 0, RED), createVertex(100, 0, RED), createVertex(0, 100, RED), createVertex(100, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(100, 0, RED), createVertex(200, 0, RED), createVertex(100, 100, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(200, 0, RED), createVertex(300, 0, RED), createVertex(200, 100, RED), createVertex(300, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.INTERIOR, Arrays.asList(createVertex(300, 0, RED), createVertex(400, 0, RED), createVertex(300, 100, RED), createVertex(400, 100, RED)));

        //horizontally from bottom and from left
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 0, RED), createVertex(100, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(100, 0, RED), createVertex(200, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 0, RED), createVertex(300, 0, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 0, RED), createVertex(400, 0, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 100, RED), createVertex(100, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(100, 100, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 100, RED), createVertex(300, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 100, RED), createVertex(400, 100, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 200, RED), createVertex(200, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 200, RED), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 200, RED), createVertex(400, 200, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 300, RED), createVertex(400, 300, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 400, RED), createVertex(200, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 400, RED), createVertex(300, 400, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 400, RED), createVertex(400, 400, RED)));

        //vertically from bottom and from left
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 0, RED), createVertex(0, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 100, RED), createVertex(0, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(0, 200, RED), createVertex(0, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(100, 0, RED), createVertex(100, 100, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 0, RED), createVertex(200, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 100, RED), createVertex(200, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(200, 200, RED), createVertex(200, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 0, RED), createVertex(300, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 100, RED), createVertex(300, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 200, RED), createVertex(300, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(300, 300, RED), createVertex(300, 400, RED)));

        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 0, RED), createVertex(400, 100, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 100, RED), createVertex(400, 200, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 200, RED), createVertex(400, 300, RED)));
        addHyperEdge(graph, HyperEdgeType.BOUNDARY, Arrays.asList(createVertex(400, 300, RED), createVertex(400, 400, RED)));

        return graph;
    }

    private void addHyperEdge(HyperGraph graph, HyperEdgeType edgeType, List<Vertex> vertices) {
        HyperEdge edge = new HyperEdge(edgeType, vertices);
        graph.addEdge(edge);
    }

    private Vertex createVertex(int x, int y, RgbColor rgbColor) {
        return new Vertex(new Point(x, y), rgbColor);
    }
}

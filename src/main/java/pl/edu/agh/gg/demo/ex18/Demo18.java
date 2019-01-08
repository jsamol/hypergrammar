package pl.edu.agh.gg.demo.ex18;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.production.P1Production;
import pl.edu.agh.gg.ui.GraphBitmapApproximationDrawer;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import pl.edu.agh.gg.util.BitmapUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Demo18 {

    private static RgbColor RED = new RgbColor(255, 0, 0);
    private static RgbColor BLUE = new RgbColor(0, 0, 255);
    private static RgbColor GREEN = new RgbColor(0, 255, 0);

    public static void main(String[] args) throws IOException {
        Demo18 app = new Demo18();
//        app.run();
        app.runManuallyCreatedGraph();
    }

    private void run() throws IOException {
        HyperGraph graph = new HyperGraph();
        BufferedImage image = BitmapUtils.loadBitmapFromResource("production1/bitmap.bmp");

        P1Production production = new P1Production(image);
        production.apply(graph);

        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();
        GraphBitmapApproximationDrawer.drawGraphBitmap(graph, "graphApprox.bmp");
    }

    private void runManuallyCreatedGraph() {
        HyperGraph graph = createHyperGraph();
        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();
        GraphBitmapApproximationDrawer.drawGraphBitmap(graph, "graphApprox.bmp");
    }

    private HyperGraph createHyperGraph() {
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

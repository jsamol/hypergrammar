package pl.edu.agh.gg.demo.production2;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.production.P1Production;
import pl.edu.agh.gg.production.P2Production;
import pl.edu.agh.gg.production.P5Production;
import pl.edu.agh.gg.production.Production;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import pl.edu.agh.gg.util.BitmapUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Production2Demo {

    public static void main(String[] args) throws IOException {
        Production2Demo app = new Production2Demo();
        app.run();
    }

    private void run() throws IOException {
        HyperGraph graph = new HyperGraph();
        BufferedImage image = BitmapUtils.loadBitmapFromResource("production2/bitmap.bmp");

        Production production1 = new P1Production(image);
        production1.apply(graph);

        HyperEdge edge = graph.getEdges().stream().filter(e -> e.getType() == HyperEdgeType.INTERIOR).findFirst().get();

        Production production2 = new P5Production(edge);
        production2.apply(graph);

        Production production3 = new P2Production(image, edge);
        production3.apply(graph);

        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();
    }
}

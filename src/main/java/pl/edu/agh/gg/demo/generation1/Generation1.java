package pl.edu.agh.gg.demo.generation1;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.production.P1Production;
import pl.edu.agh.gg.production.P2Production;
import pl.edu.agh.gg.production.P5Production;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import pl.edu.agh.gg.util.BitmapUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Generation1 {
    public static void main(String[] args) throws IOException {
        Generation1 app = new Generation1();
        app.run();
    }

    private void run() throws IOException {
        HyperGraph graph = new HyperGraph();
        BufferedImage image = BitmapUtils.loadBitmapFromResource("production1/bitmap.bmp");

        P1Production production1 = new P1Production(image);
        production1.apply(graph);

        HyperEdge edge = graph.getEdges().stream().filter(e -> e.getType() == HyperEdgeType.INTERIOR).findFirst().get();

        P5Production production5 = new P5Production(edge);
        production5.apply(graph);

        P2Production production2 = new P2Production(image, edge);
        production2.apply(graph);

//        Add 4 x P3
//        Add 3 x P5
//        Add 3 x P2
//        Add 6 x P3
//        Add 2 x P4
//        Add P5
//        Add P2
//        Add P5

        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();
    }
}

package pl.edu.agh.gg.demo.production1;

import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.production.P1Production;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import pl.edu.agh.gg.util.BitmapUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Production1Demo {

    public static void main(String[] args) throws IOException {
        Production1Demo app = new Production1Demo();
        app.run();
    }

    private void run() throws IOException {
        HyperGraph graph = new HyperGraph();
        BufferedImage image = BitmapUtils.loadBitmapFromResource("production1/bitmap.bmp");

        P1Production production = new P1Production(image);
        production.apply(graph);

        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();
    }
}

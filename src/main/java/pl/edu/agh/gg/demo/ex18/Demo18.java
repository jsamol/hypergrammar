package pl.edu.agh.gg.demo.ex18;

import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.production.P1Production;
import pl.edu.agh.gg.ui.GraphBitmapApproximationDrawer;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import pl.edu.agh.gg.util.BitmapUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Demo18 {

    public static void main(String[] args) throws IOException {
        Demo18 app = new Demo18();
        app.run();
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
}

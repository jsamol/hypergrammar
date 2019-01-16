package pl.edu.agh.gg.demo.production4;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.production.P4Production;
import pl.edu.agh.gg.production.Production;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import pl.edu.agh.gg.util.BitmapUtils;


import java.awt.image.BufferedImage;
import java.io.IOException;

import static pl.edu.agh.gg.hypergraph.HyperEdgeType.*;
import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.*;

public class Production4Demo {

    public static void main(String[] args) throws IOException {
        Production4Demo app = new Production4Demo();
        app.run();
    }

    private void run() throws IOException {
        HyperGraph graph = new HyperGraph();

        BufferedImage image = BitmapUtils.loadBitmapFromResource("production4/bitmap.bmp");
        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);

        Production4DemoBuilder builder = new Production4DemoBuilder(graph, image);


        Production production = new P4Production(image, builder.getF2Left(), builder.getF2Right(), builder.getF1Up());
        production.apply(graph);

        drawer.draw();

    }

}

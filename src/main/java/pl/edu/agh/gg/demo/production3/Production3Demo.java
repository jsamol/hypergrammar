package pl.edu.agh.gg.demo.production3;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.production.P3Production;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import pl.edu.agh.gg.util.BitmapUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Production3Demo {

    public static void main(String[] args) throws IOException {
        Production3Demo app = new Production3Demo();
        app.run();
    }

    private void run() throws IOException {
        HyperGraph graph = new HyperGraph();
        // TODO
        BufferedImage image = BitmapUtils.loadBitmapFromResource("production2/bitmap.bmp");

        int x1 = 0;
        int y1 = 10;
        int x2 = 20;
        int y2 = 10;
        int x3 = 10;
        int y3 = 0;

        Vertex vertex1 = new Vertex(new Point(x1, y1));
        Vertex vertex2 = new Vertex(new Point(x2, y2));
        Vertex vertex3 = new Vertex(new Point(x3, y3));

        graph.addEdge(new HyperEdge(HyperEdgeType.BOUNDARY, vertex1, vertex2),
                new HyperEdge(HyperEdgeType.INTERIOR, vertex1, vertex3),
                new HyperEdge(HyperEdgeType.INTERIOR, vertex2, vertex3),
                new HyperEdge(HyperEdgeDirection.UP, vertex3));

        P3Production p3Production = new P3Production(image, x1, x2, x3, y1, y2, y3);
        p3Production.apply(graph);

        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();
    }
}

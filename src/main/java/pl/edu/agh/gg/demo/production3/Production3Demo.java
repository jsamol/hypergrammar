package pl.edu.agh.gg.demo.production3;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
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
        BufferedImage image = BitmapUtils.loadBitmapFromResource("production3/bitmap.bmp");

        int x1 = image.getMinX();
        int y1 = image.getMinY() + image.getHeight() - 1;
        int x2 = image.getMinX() + image.getWidth() - 1;
        int y2 = image.getMinY() + image.getHeight() - 1;
        int x3 = (image.getMinX() + image.getWidth() - 1) / 2;
        int y3 = (image.getMinY() + image.getHeight() - 1) / 2;

        Vertex vertex1 = new Vertex(new Point(x1, y1), new RgbColor(image.getRGB(x1, y1)));
        Vertex vertex2 = new Vertex(new Point(x2, y2), new RgbColor(image.getRGB(x2, y2)));
        Vertex vertex3 = new Vertex(new Point(x3, y3), new RgbColor(image.getRGB(x3, y3)));

        HyperEdge boundary = new HyperEdge(HyperEdgeType.BOUNDARY, vertex1, vertex2);
        graph.addEdge(boundary,
                new HyperEdge(HyperEdgeType.INTERIOR, vertex1, vertex3),
                new HyperEdge(HyperEdgeType.INTERIOR, vertex2, vertex3),
                new HyperEdge(HyperEdgeDirection.UP, vertex3));

        P3Production p3Production = new P3Production(image, boundary);
        p3Production.apply(graph);

        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();
    }
}

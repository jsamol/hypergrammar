package pl.edu.agh.gg.production;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;

import java.awt.image.BufferedImage;

public class P1Production implements Production {

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private RgbColor point1Color;
    private RgbColor point2Color;
    private RgbColor point3Color;
    private RgbColor point4Color;

    public P1Production(int x1, int x2, int y1, int y2, RgbColor point1Color, RgbColor point2Color, RgbColor point3Color, RgbColor point4Color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.point1Color = point1Color;
        this.point2Color = point2Color;
        this.point3Color = point3Color;
        this.point4Color = point4Color;
    }

    public P1Production(BufferedImage bitmap) {
        this.x1 = bitmap.getMinX();
        this.x2 = bitmap.getMinX() + bitmap.getWidth() - 1;
        this.y1 = bitmap.getMinY();
        this.y2 = bitmap.getMinY() + bitmap.getHeight() - 1;
        this.point1Color = new RgbColor(bitmap.getRGB(x1, y2));
        this.point2Color = new RgbColor(bitmap.getRGB(x2, y2));
        this.point3Color = new RgbColor(bitmap.getRGB(x1, y1));
        this.point4Color = new RgbColor(bitmap.getRGB(x2, y1));
    }

    @Override
    public void apply(HyperGraph graph) {
        Vertex vertex1 = new Vertex(new Point(x1, y2), point1Color);
        Vertex vertex2 = new Vertex(new Point(x2, y2), point2Color);
        Vertex vertex3 = new Vertex(new Point(x1, y1), point3Color);
        Vertex vertex4 = new Vertex(new Point(x2, y1), point4Color);

        HyperEdge edgeBetween1And2 = new HyperEdge(HyperEdgeType.BOUNDARY, vertex1, vertex2);
        HyperEdge edgeBetween2And4 = new HyperEdge(HyperEdgeType.BOUNDARY, vertex2, vertex4);
        HyperEdge edgeBetween1And3 = new HyperEdge(HyperEdgeType.BOUNDARY, vertex1, vertex3);
        HyperEdge edgeBetween3And4 = new HyperEdge(HyperEdgeType.BOUNDARY, vertex3, vertex4);

        HyperEdge interiorEdge = new HyperEdge(HyperEdgeType.INTERIOR, vertex1, vertex2, vertex3, vertex4);
        interiorEdge.setCanBreak(false);

        graph.addEdge(edgeBetween1And2, edgeBetween1And3, edgeBetween2And4, edgeBetween3And4, interiorEdge);
    }

}

package pl.edu.agh.gg.demo.production4;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;

import java.awt.image.BufferedImage;

import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.*;
import static pl.edu.agh.gg.hypergraph.HyperEdgeType.INTERIOR;

public class Production4DemoBuilder {

    private final HyperEdge F2Left;
    private final HyperEdge F2Right;
    private final HyperEdge F1Up;

    public Production4DemoBuilder(HyperGraph graph, BufferedImage image) {

        int x2 = image.getMinX();
        int x3 = image.getMinX() + image.getWidth() - 1;
        int x1 = (x2 + x3) / 2;

        int y1 = image.getMinY();
        int y2 = image.getMinY() + image.getHeight() - 1;
        int y3 = (y1 + y2) / 2;

        Vertex v1 = new Vertex(x2, y2, image);
        Vertex v2 = new Vertex(x3, y2, image);
        Vertex v3 = new Vertex(x2, y1, image);
        Vertex v4 = new Vertex(x3, y1, image);
        Vertex v5 = new Vertex(x1, y2, image);
        Vertex v6 = new Vertex(x3, y3, image);
        Vertex v7 = new Vertex(x1, y1, image);
        Vertex v8 = new Vertex(x2, y3, image);

        HyperEdge edgeBetween1And5And8 = new HyperEdge(INTERIOR, v1, v5, v8);
        HyperEdge edgeBetween2And5And6 = new HyperEdge(INTERIOR, v2, v5, v6);
        HyperEdge edgeBetween3And7And8 = new HyperEdge(INTERIOR, v3, v7, v8);
        HyperEdge edgeBetween4And6And7 = new HyperEdge(INTERIOR, v4, v6, v7);

//        F2Left = new HyperEdge(LEFT, v8);
//        F2Right = new HyperEdge(RIGHT, v6);
//        F1Up = new HyperEdge(UP, v5, v7);

        // Dla produkcji zamienionej:
        F2Left = new HyperEdge(DOWN, v5);
        F2Right = new HyperEdge(UP, v7);
        F1Up = new HyperEdge(LEFT, v8, v6);

        graph.addEdge(edgeBetween1And5And8, edgeBetween2And5And6, edgeBetween3And7And8, edgeBetween4And6And7);
        graph.addEdge(F2Left, F2Right, F1Up);
    }

    public HyperEdge getF2Left() {
        return F2Left;
    }

    public HyperEdge getF2Right() {
        return F2Right;
    }

    public HyperEdge getF1Up() {
        return F1Up;
    }
}

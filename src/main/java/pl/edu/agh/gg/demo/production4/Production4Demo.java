package pl.edu.agh.gg.demo.production4;

import pl.edu.agh.gg.data.Point;
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

//        BufferedImage image = BitmapUtils.loadBitmapFromResource("production4/bitmap.bmp");

        int x1 = 50;
        int x2 = 0;
        int x3 = 100;
        int y1 = 0;
        int y2 = 100;
        int y3 = 50;

        Vertex v1 = new Vertex(x2, y2);
        Vertex v2 = new Vertex(x3, y2);
        Vertex v3 = new Vertex(x2, y1);
        Vertex v4 = new Vertex(x3, y1);
        Vertex v5 = new Vertex(x1, y2);
        Vertex v6 = new Vertex(x3, y3);
        Vertex v7 = new Vertex(x1, y1);
        Vertex v8 = new Vertex(x2, y3);

        HyperEdge edgeBetween1And5And8 = new HyperEdge(INTERIOR, v1, v5, v8);
        HyperEdge edgeBetween2And5And6 = new HyperEdge(INTERIOR, v2, v5, v6);
        HyperEdge edgeBetween3And7And8 = new HyperEdge(INTERIOR, v3, v7, v8);
        HyperEdge edgeBetween4And6And7 = new HyperEdge(INTERIOR, v4, v6, v7);

        HyperEdge F2Left = new HyperEdge(LEFT, v8);
        HyperEdge F2Right = new HyperEdge(RIGHT, v6);
        HyperEdge F1Up = new HyperEdge(UP, v5, v7);

        graph.addEdge(edgeBetween1And5And8, edgeBetween2And5And6, edgeBetween3And7And8, edgeBetween4And6And7);
        graph.addEdge(F2Left, F2Right, F1Up);


        Production production4 = new P4Production(null, F2Left, F2Right, F1Up);
        production4.apply(graph);

        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();
    }

}

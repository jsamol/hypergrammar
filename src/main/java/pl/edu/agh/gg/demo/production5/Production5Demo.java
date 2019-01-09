package pl.edu.agh.gg.demo.production5;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.production.P2Production;
import pl.edu.agh.gg.production.P5Production;
import pl.edu.agh.gg.production.Production;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import pl.edu.agh.gg.util.BitmapUtils;

import java.io.IOException;

import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.LEFT;
import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.UP;
import static pl.edu.agh.gg.hypergraph.HyperEdgeType.*;

public class Production5Demo {

    public static void main(String[] args) throws IOException {
        Production5Demo app = new Production5Demo();
        app.run();
    }

    private void run() throws IOException {
        HyperGraph graph = new HyperGraph();

        Vertex v00 = new Vertex(new Point(0, 0));
        Vertex v02 = new Vertex(new Point(0, 2));
        Vertex v06 = new Vertex(new Point(0, 6));

        Vertex v20 = new Vertex(new Point(2, 0));

        Vertex v40 = new Vertex(new Point(4, 0));
        Vertex v42 = new Vertex(new Point(4, 2));
        Vertex v46 = new Vertex(new Point(4, 6));

        Vertex v60 = new Vertex(new Point(6, 0));
        Vertex v62 = new Vertex(new Point(6, 2));
        Vertex v64 = new Vertex(new Point(6, 4));
        Vertex v66 = new Vertex(new Point(6, 6));

        HyperEdge i1 = new HyperEdge(INTERIOR, v02, v06, v42, v46);
        HyperEdge i2 = new HyperEdge(INTERIOR, v46, v66, v64);
        HyperEdge i3 = new HyperEdge(INTERIOR, v64, v62, v42);
        HyperEdge i4 = new HyperEdge(INTERIOR, v40, v60, v42, v62);
        HyperEdge i5 = new HyperEdge(INTERIOR, v20, v40, v42);
        HyperEdge i6 = new HyperEdge(INTERIOR, v00, v02, v20);

        graph.addEdge(i1, i2, i3, i4, i5, i6);

        HyperEdge b1 = new HyperEdge(BOUNDARY, v00, v02);
        HyperEdge b2 = new HyperEdge(BOUNDARY, v02, v06);
        HyperEdge b3 = new HyperEdge(BOUNDARY, v06, v46);
        HyperEdge b4 = new HyperEdge(BOUNDARY, v46, v66);

        graph.addEdge(b1, b2, b3, b4);

        HyperEdge f1 = new HyperEdge(FACE, v64, v66);
        HyperEdge f2 = new HyperEdge(FACE, v62, v64);
        HyperEdge f3 = new HyperEdge(FACE, v60, v62);
        HyperEdge f4 = new HyperEdge(FACE, v40, v60);
        HyperEdge f5 = new HyperEdge(FACE, v20, v40);
        HyperEdge f6 = new HyperEdge(FACE, v00, v20);
        HyperEdge f7 = new HyperEdge(FACE, v42, v40);
        HyperEdge f8 = new HyperEdge(FACE, v42, v62);
        HyperEdge f9 = new HyperEdge(LEFT, v64);
        HyperEdge f10 = new HyperEdge(UP, v20);

        HyperEdge f11 = new HyperEdge(FACE, v02, v42);
        HyperEdge f12 = new HyperEdge(FACE, v42, v46);


        graph.addEdge(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12);

        Production p1 = new P5Production(i4);
        p1.apply(graph);
        Production p2 = new P2Production(BitmapUtils.loadBitmapFromResource("production2/bitmap.bmp"), i4);
        p2.apply(graph);

        HyperEdge edge = graph.getEdges().stream()
                .filter(e -> e.getVertices().contains(v42))
                .filter(e -> e.getVertices().stream().filter(v -> v.getX() == 5).count() == 1)
                .findFirst().get();

        Production p3 = new P5Production(edge);
        p3.apply(graph);

        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();
    }
}

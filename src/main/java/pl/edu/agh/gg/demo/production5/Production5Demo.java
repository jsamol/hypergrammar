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
        Vertex v02 = new Vertex(new Point(0, 4));
        Vertex v04 = new Vertex(new Point(0, 8));
        Vertex v08 = new Vertex(new Point(0, 16));

        Vertex v20 = new Vertex(new Point(4, 0));
        Vertex v22 = new Vertex(new Point(4, 4));

        Vertex v40 = new Vertex(new Point(8, 0));
        Vertex v42 = new Vertex(new Point(8, 4));
        Vertex v44 = new Vertex(new Point(8, 8));
        Vertex v48 = new Vertex(new Point(8, 16));

        Vertex v60 = new Vertex(new Point(12, 0));
        Vertex v62 = new Vertex(new Point(12, 4));
        Vertex v64 = new Vertex(new Point(12, 8));
        Vertex v66 = new Vertex(new Point(12, 12));
        Vertex v68 = new Vertex(new Point(12, 16));

        Vertex v80 = new Vertex(new Point(16, 0));
        Vertex v82 = new Vertex(new Point(16, 4));
        Vertex v84 = new Vertex(new Point(16, 8));
        Vertex v86 = new Vertex(new Point(16, 12));
        Vertex v88 = new Vertex(new Point(16, 16));

        HyperEdge i1 = new HyperEdge(INTERIOR, v00, v20, v22, v02);
        HyperEdge i2 = new HyperEdge(INTERIOR, v20, v40, v42, v22);
        HyperEdge i3 = new HyperEdge(INTERIOR, v40, v60, v62, v42);
        HyperEdge i4 = new HyperEdge(INTERIOR, v60, v80, v82, v62);
        graph.addEdge(i1, i2, i3, i4);

        HyperEdge i5 = new HyperEdge(INTERIOR, v02, v22, v04);
        HyperEdge i6 = new HyperEdge(INTERIOR, v22, v42, v44);
        HyperEdge i7 = new HyperEdge(INTERIOR, v42, v62, v64, v44);
        HyperEdge i8 = new HyperEdge(INTERIOR, v62, v82, v84, v64);
        graph.addEdge(i5, i6, i7, i8);

        HyperEdge i9 = new HyperEdge(INTERIOR, v04, v44, v48, v08);
        HyperEdge i10 = new HyperEdge(INTERIOR, v44, v64, v66);
        HyperEdge i11 = new HyperEdge(INTERIOR, v64, v84, v86, v66);
        graph.addEdge(i9, i10, i11);

        HyperEdge i12 = new HyperEdge(INTERIOR, v66, v68, v48);
        HyperEdge i13 = new HyperEdge(INTERIOR, v66, v86, v88, v68);
        graph.addEdge(i12, i13);

        HyperEdge b1 = new HyperEdge(BOUNDARY, v00, v20);
        HyperEdge b2 = new HyperEdge(BOUNDARY, v20, v40);
        HyperEdge b3 = new HyperEdge(BOUNDARY, v40, v60);
        HyperEdge b4 = new HyperEdge(BOUNDARY, v60, v80);
        graph.addEdge(b1, b2, b3, b4);

        HyperEdge b5 = new HyperEdge(BOUNDARY, v08, v48);
        HyperEdge b6 = new HyperEdge(BOUNDARY, v48, v68);
        HyperEdge b7 = new HyperEdge(BOUNDARY, v68, v88);
        graph.addEdge(b5, b6, b7);

        HyperEdge b8 = new HyperEdge(BOUNDARY, v00, v02);
        HyperEdge b9 = new HyperEdge(BOUNDARY, v02, v04);
        HyperEdge b10 = new HyperEdge(BOUNDARY, v04, v08);
        graph.addEdge(b8, b9, b10);

        HyperEdge b11 = new HyperEdge(BOUNDARY, v80, v82);
        HyperEdge b12 = new HyperEdge(BOUNDARY, v82, v84);
        HyperEdge b13 = new HyperEdge(BOUNDARY, v84, v86);
        HyperEdge b14 = new HyperEdge(BOUNDARY, v86, v88);
        graph.addEdge(b11, b12, b13, b14);

        HyperEdge f1 = new HyperEdge(FACE, v20, v22);
        HyperEdge f2 = new HyperEdge(FACE, v40, v42);
        HyperEdge f3 = new HyperEdge(FACE, v60, v62);
        HyperEdge f4 = new HyperEdge(UP, v22);
        HyperEdge f5 = new HyperEdge(FACE, v42, v44);
        HyperEdge f6 = new HyperEdge(FACE, v62, v64);
        HyperEdge f7 = new HyperEdge(FACE, v44, v48);
        HyperEdge f8 = new HyperEdge(FACE, v64, v66);
        HyperEdge f9 = new HyperEdge(FACE, v66, v68);

        graph.addEdge(f1, f2, f3, f4, f5, f6, f7, f8, f9);

        HyperEdge f10 = new HyperEdge(FACE, v02, v22);
        HyperEdge f11 = new HyperEdge(FACE, v22, v42);
        HyperEdge f12 = new HyperEdge(FACE, v42, v62);
        HyperEdge f13 = new HyperEdge(FACE, v62, v82);
        HyperEdge f14 = new HyperEdge(FACE, v04, v44);
        HyperEdge f15 = new HyperEdge(FACE, v44, v64);
        HyperEdge f16 = new HyperEdge(FACE, v64, v84);
        HyperEdge f17 = new HyperEdge(LEFT, v66);
        HyperEdge f18 = new HyperEdge(FACE, v66, v86);

        graph.addEdge(f10, f11, f12, f13, f14, f15, f16, f17, f18);

        Production p5_1 = new P5Production(i7);
        p5_1.apply(graph);
        Production p2 = new P2Production(BitmapUtils.loadBitmapFromResource("production2/bitmap.bmp"), i7);
        p2.apply(graph);

        HyperEdge edge = graph.getEdges().stream()
                .filter(e -> e.getVertices().contains(v44))
                .filter(e -> e.getVertices().stream().filter(v -> v.getX() == 10).count() == 1)
                .findFirst().get();

        Production p5_2 = new P5Production(edge);
        p5_2.apply(graph);

        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();
    }
}

package pl.edu.agh.gg.production;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;

import java.awt.image.BufferedImage;

import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.DOWN;

public class P4Production implements Production {

    private BufferedImage bitmap;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int x3;
    private int y3;

    public P4Production(BufferedImage bitmap, int x1, int x2, int x3, int y1, int y2, int  y3) {
        this.bitmap = bitmap;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }

    @Override
    public void apply(HyperGraph graph) {
        HyperEdge F2Left = graph.getEdges()
                                .stream()
                                .filter(f -> f.isEdgeInclude(x2, y3) && f.getDir() == HyperEdgeDirection.LEFT)
                                .findAny().orElse(null);
        HyperEdge F2Right = graph.getEdges()
                                .stream()
                                .filter(f -> f.isEdgeInclude(x3, y3) && f.getDir() == HyperEdgeDirection.RIGHT)
                                .findAny().orElse(null);
        HyperEdge F1Up = graph.getEdges()
                                .stream()
                                .filter(f -> f.isEdgeBetween(x1, x1, y1, y2) && f.getDir() == HyperEdgeDirection.UP)
                                .findAny().orElse(null);
        if (F2Left == null || F2Right == null || F1Up == null) {
            throw new IllegalStateException("Can't find all needed FACES HyperEdges");
        }
        HyperEdge edgeBetween1And5And8 = graph.getEdges()
                .stream()
                .filter(f -> f.isEdgeBetween(x1, x2, y2, y3) && f.getType() == HyperEdgeType.INTERIOR)
                .findAny().orElse(null);
        HyperEdge edgeBetween2And5And6 = graph.getEdges()
                .stream()
                .filter(f -> f.isEdgeBetween(x1, x3, y2, y3) && f.getType() == HyperEdgeType.INTERIOR)
                .findAny().orElse(null);
        HyperEdge edgeBetween3And7And8 = graph.getEdges()
                .stream()
                .filter(f -> f.isEdgeBetween(x1, x2, y1, y3) && f.getType() == HyperEdgeType.INTERIOR)
                .findAny().orElse(null);
        HyperEdge edgeBetween4And6And7 = graph.getEdges()
                .stream()
                .filter(f -> f.isEdgeBetween(x1, x3, y1, y3) && f.getType() == HyperEdgeType.INTERIOR)
                .findAny().orElse(null);
        if (edgeBetween1And5And8 == null || edgeBetween2And5And6 == null || edgeBetween3And7And8 == null || edgeBetween4And6And7 == null) {
            throw new IllegalStateException("Can't find all needed INTERIOR HyperEdges");
        }

        Vertex newVertex = new Vertex(new Point(x1, y3));
//        Vertex newVertex = new Vertex(new Point(x1, y3), new RgbColor(bitmap.getRGB(x1, y3)));
        Vertex oldVertex = F1Up.getVertices().stream().filter(v -> v.getGeom().isEqual(x1, y1)).findAny().orElse(null);

        HyperEdge F1Down = new HyperEdge(DOWN, newVertex, oldVertex);
        F1Up.removeVertex(oldVertex);
        F1Up.addVertex(newVertex);
        F2Left.addVertex(newVertex);
        F2Right.addVertex(newVertex);
        edgeBetween1And5And8.addVertex(newVertex);
        edgeBetween2And5And6.addVertex(newVertex);
        edgeBetween3And7And8.addVertex(newVertex);
        edgeBetween4And6And7.addVertex(newVertex);

        graph.addVertex(newVertex);
        graph.addEdge(F1Down);
    }
}

package pl.edu.agh.gg.production;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.image.BufferedImage;
import java.util.Optional;

import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.DOWN;
import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.UP;

public class P4Production implements Production {

    private BufferedImage bitmap;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int x3;
    private int y3;

    private HyperEdge F2Left;
    private HyperEdge F2Right;
    private HyperEdge F1Up;

    public P4Production(BufferedImage bitmap, int x1, int x2, int x3, int y1, int y2, int  y3) {
        this.bitmap = bitmap;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }

    public P4Production(BufferedImage bitmap, HyperEdge F2Left, HyperEdge F2Right, HyperEdge F1Up) {
        this.bitmap = bitmap;
        this.F2Left = F2Left;
        this.F2Right = F2Right;
        this.F1Up = F1Up;
    }

    private void setF2Left(HyperGraph graph) {
        this.F2Left = graph.getEdges()
                .stream()
                .filter(f -> f.isEdgeInclude(x2, y3) && f.getDir() == HyperEdgeDirection.LEFT)
                .findAny().orElse(null);
    }

    private void setF2Right(HyperGraph graph) {
        this.F2Right = graph.getEdges()
                .stream()
                .filter(f -> f.isEdgeInclude(x3, y3) && f.getDir() == HyperEdgeDirection.RIGHT)
                .findAny().orElse(null);
    }

    private void setF1Up(HyperGraph graph) {
        this.F1Up = graph.getEdges()
                .stream()
                .filter(f -> f.isEdgeBetween(x1, x1, y1, y2) && f.getDir() == HyperEdgeDirection.UP)
                .findAny().orElse(null);
    }

    private HyperEdge findEdgeBetweenPoints(HyperGraph graph, int x1, int x2, int y1, int y2) {
        return graph.getEdges()
                .stream()
                .filter(f -> f.isEdgeBetween(x1, x2, y1, y2) && f.getType() == HyperEdgeType.INTERIOR)
                .findFirst().orElseThrow(IllegalStateException::new);
    }

    @Override
    public void apply(HyperGraph graph) {
        if(this.F2Left == null) { setF2Left(graph); }
        if(this.F2Right == null) { setF2Right(graph); }
        if(this.F1Up == null) { setF1Up(graph); }

        if (this.F2Left == null || this.F2Right == null || this.F1Up == null) {
            throw new IllegalStateException("Can't find all needed FACES HyperEdges");
        }
        if (this.F2Left.getType() != HyperEdgeType.FACE
                || this.F2Right.getType() != HyperEdgeType.FACE
                || this.F1Up.getType() != HyperEdgeType.FACE) {
            throw new IllegalStateException("Some HyperEdge is not FACE type");
        }
        if (this.F2Left.getDir() != HyperEdgeDirection.LEFT
                || this.F2Right.getDir() != HyperEdgeDirection.RIGHT
                || this.F1Up.getDir() != HyperEdgeDirection.UP) {
            throw new IllegalStateException("Some HyperEdge has wrong direction");
        }

        Vertex v5 = VertexUtil.findMinXMaxY(this.F1Up.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex v6 = VertexUtil.findMaxXMaxY(this.F2Right.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex v7 = VertexUtil.findMinXMinY(this.F1Up.getVertices()).orElseThrow(IllegalStateException::new);
        Vertex v8 = VertexUtil.findMinXMaxY(this.F2Left.getVertices()).orElseThrow(IllegalStateException::new);

        x1 = v5.getX();
        x2 = v8.getX();
        x3 = v6.getX();
        y1 = v7.getY();
        y2 = v5.getY();
        y3 = v8.getY();

        // create new vertex
        Vertex v9 = new Vertex(x1, y3, bitmap);

        // find I edges
        HyperEdge edgeBetween1And5And8 = findEdgeBetweenPoints(graph, x1, x2, y2, y3);
        HyperEdge edgeBetween2And5And6 = findEdgeBetweenPoints(graph, x1, x3, y2, y3);
        HyperEdge edgeBetween3And7And8 = findEdgeBetweenPoints(graph, x1, x2, y1, y3);
        HyperEdge edgeBetween4And6And7 = findEdgeBetweenPoints(graph, x1, x3, y1, y3);

        // connect vertex to I edges
        edgeBetween1And5And8.addVertex(v9);
        edgeBetween2And5And6.addVertex(v9);
        edgeBetween3And7And8.addVertex(v9);
        edgeBetween4And6And7.addVertex(v9);

        // add F1 Down
        HyperEdge F1Down = new HyperEdge(DOWN, v9, v7);
        graph.addEdge(F1Down);

        // connect other F edges to vertex
        F1Up.removeVertex(v7);
        F1Up.addVertex(v9);
        F2Left.addVertex(v9);
        F2Right.addVertex(v9);

    }
}

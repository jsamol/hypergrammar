package pl.edu.agh.gg.production;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.*;

public class P4Production implements Production {

    private BufferedImage bitmap;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int x3;
    private int y3;

    private HyperEdge F_1;
    private HyperEdge F_2;
    private HyperEdge F_3;
    private HyperEdge F1;
    private HyperEdge F2_1;
    private HyperEdge F2_2;
    private HyperEdgeDirection missingDir;
    private Set<HyperEdgeDirection> directions = new HashSet<HyperEdgeDirection>();
    private Set<HyperEdgeDirection> missingDirections = new HashSet<HyperEdgeDirection>();




    public P4Production(BufferedImage bitmap, int x1, int x2, int x3, int y1, int y2, int  y3) {
        this.bitmap = bitmap;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }

    public P4Production(BufferedImage bitmap, HyperEdge F_1, HyperEdge F_2, HyperEdge F_3) {
        missingDirections.add(LEFT);
        missingDirections.add(RIGHT);
        missingDirections.add(UP);
        missingDirections.add(DOWN);
        this.bitmap = bitmap;
        this.F_1 = F_1;
        this.F_2 = F_2;
        this.F_3 = F_3;
        directions.add(F_1.getDir());
        directions.add(F_2.getDir());
        directions.add(F_3.getDir());
        missingDirections.removeAll(directions);
    }

    private HyperEdge findEdgeBetweenPoints(HyperGraph graph, int x1, int x2, int y1, int y2) {
        return graph.getEdges()
                .stream()
                .filter(f -> f.isEdgeBetween(x1, x2, y1, y2) && f.getType() == HyperEdgeType.INTERIOR)
                .findFirst().orElseThrow(IllegalStateException::new);
    }

    @Override
    public void apply(HyperGraph graph) {
        if (this.F_1 == null || this.F_2 == null || this.F_3 == null) {
            throw new IllegalStateException("Can't find all needed FACES HyperEdges");
        }
        if (this.F_1.getType() != HyperEdgeType.FACE
                || this.F_2.getType() != HyperEdgeType.FACE
                || this.F_3.getType() != HyperEdgeType.FACE) {
            throw new IllegalStateException("Some HyperEdge is not FACE type");
        }
        if (directions.size() != 3) {
            throw new IllegalStateException("Duplicated HyperEdge directions");
        }

        HyperEdgeDirection missingDirection = missingDirections.stream().findFirst().get();
        Vertex v5, v6, v7, v8;
        switch (missingDirection) {
            case UP:
                this.F1 = (this.F_1.getDir() == DOWN) ? this.F_1 : ((this.F_2.getDir() == DOWN) ? this.F_2 : this.F_3);
                this.F2_1 = (this.F_1.getDir() == LEFT) ? this.F_1 : ((this.F_2.getDir() == LEFT) ? this.F_2 : this.F_3);
                this.F2_2 = (this.F_1.getDir() == RIGHT) ? this.F_1 : ((this.F_2.getDir() == RIGHT) ? this.F_2 : this.F_3);
                v5 = VertexUtil.findMinXMaxY(this.F1.getVertices()).orElseThrow(IllegalStateException::new);
                v6 = VertexUtil.findMaxXMaxY(this.F2_2.getVertices()).orElseThrow(IllegalStateException::new);
                v7 = VertexUtil.findMinXMinY(this.F1.getVertices()).orElseThrow(IllegalStateException::new);
                v8 = VertexUtil.findMinXMaxY(this.F2_1.getVertices()).orElseThrow(IllegalStateException::new);
                break;
            case DOWN:
                this.F1 = (this.F_1.getDir() == UP) ? this.F_1 : ((this.F_2.getDir() == UP) ? this.F_2 : this.F_3);
                this.F2_1 = (this.F_1.getDir() == LEFT) ? this.F_1 : ((this.F_2.getDir() == LEFT) ? this.F_2 : this.F_3);
                this.F2_2 = (this.F_1.getDir() == RIGHT) ? this.F_1 : ((this.F_2.getDir() == RIGHT) ? this.F_2 : this.F_3);
                v5 = VertexUtil.findMinXMaxY(this.F1.getVertices()).orElseThrow(IllegalStateException::new);
                v6 = VertexUtil.findMaxXMaxY(this.F2_2.getVertices()).orElseThrow(IllegalStateException::new);
                v7 = VertexUtil.findMinXMinY(this.F1.getVertices()).orElseThrow(IllegalStateException::new);
                v8 = VertexUtil.findMinXMaxY(this.F2_1.getVertices()).orElseThrow(IllegalStateException::new);
                break;
            case LEFT:
                this.F1 = (this.F_1.getDir() == RIGHT) ? this.F_1 : ((this.F_2.getDir() == RIGHT) ? this.F_2 : this.F_3);
                this.F2_1 = (this.F_1.getDir() == UP) ? this.F_1 : ((this.F_2.getDir() == UP) ? this.F_2 : this.F_3);
                this.F2_2 = (this.F_1.getDir() == DOWN) ? this.F_1 : ((this.F_2.getDir() == DOWN) ? this.F_2 : this.F_3);
                v5 = VertexUtil.findMinXMaxY(this.F2_1.getVertices()).orElseThrow(IllegalStateException::new);
                v6 = VertexUtil.findMaxXMinY(this.F1.getVertices()).orElseThrow(IllegalStateException::new);
                v7 = VertexUtil.findMinXMinY(this.F2_2.getVertices()).orElseThrow(IllegalStateException::new);
                v8 = VertexUtil.findMinXMinY(this.F1.getVertices()).orElseThrow(IllegalStateException::new);
                break;
            case RIGHT:
                this.F1 = (this.F_1.getDir() == LEFT) ? this.F_1 : ((this.F_2.getDir() == LEFT) ? this.F_2 : this.F_3);
                this.F2_1 = (this.F_1.getDir() == UP) ? this.F_1 : ((this.F_2.getDir() == UP) ? this.F_2 : this.F_3);
                this.F2_2 = (this.F_1.getDir() == DOWN) ? this.F_1 : ((this.F_2.getDir() == DOWN) ? this.F_2 : this.F_3);
                v5 = VertexUtil.findMinXMaxY(this.F2_1.getVertices()).orElseThrow(IllegalStateException::new);
                v6 = VertexUtil.findMaxXMinY(this.F1.getVertices()).orElseThrow(IllegalStateException::new);
                v7 = VertexUtil.findMinXMinY(this.F2_2.getVertices()).orElseThrow(IllegalStateException::new);
                v8 = VertexUtil.findMinXMinY(this.F1.getVertices()).orElseThrow(IllegalStateException::new);
                break;
            default:
                throw new IllegalStateException("Missing direction of new HyperEdge");
        }

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

        // add Add F1_2 HyperEdge
        HyperEdge F1_2 = new HyperEdge(missingDirection, v9);
        switch (F1_2.getDir()) {
            case LEFT:
                F1_2.addVertex(v8);
                break;
            case RIGHT:
                F1_2.addVertex(v6);
                break;
            case UP:
                F1_2.addVertex(v5);
                break;
            case DOWN:
                F1_2.addVertex(v7);
                break;
            default:
                throw new IllegalStateException("No such direction!");
        }
        graph.addEdge(F1_2);

        // connect other F edges to vertex
        switch (F1.getDir()) {
            case LEFT:
                F1.removeVertex(v6);
                break;
            case RIGHT:
                F1.removeVertex(v8);
                break;
            case UP:
                F1.removeVertex(v7);
                break;
            case DOWN:
                F1.removeVertex(v5);
                break;
            default:
                throw new IllegalStateException("No such direction!");
        }
        F1.addVertex(v9);
        F2_1.addVertex(v9);
        F2_2.addVertex(v9);

    }
}

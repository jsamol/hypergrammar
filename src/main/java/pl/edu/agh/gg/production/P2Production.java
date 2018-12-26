package pl.edu.agh.gg.production;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class P2Production implements Production {

    private BufferedImage bitmap;
    private HyperEdge hyperEdge;

    public P2Production(BufferedImage bitmap, HyperEdge hyperEdge) {
        this.bitmap = bitmap;
        this.hyperEdge = hyperEdge;
    }

    @Override
    public void apply(HyperGraph graph) {
        if (!hyperEdge.getCanBreak()) {
            throw new IllegalStateException("canBreak should be true");
        }

        if (hyperEdge.getType() != HyperEdgeType.INTERIOR) {
            throw new IllegalStateException("hyperEdge should have type interior");
        }

        Set<Vertex> vertices = new HashSet<>(hyperEdge.getVertices());
        Vertex vertex1 = VertexUtil.findMinXMaxY(vertices).orElseThrow(IllegalStateException::new);
        Vertex vertex2 = VertexUtil.findMaxXMaxY(vertices).orElseThrow(IllegalStateException::new);
        Vertex vertex3 = VertexUtil.findMinXMinY(vertices).orElseThrow(IllegalStateException::new);
        Vertex vertex4 = VertexUtil.findMaxXMinY(vertices).orElseThrow(IllegalStateException::new);

        int newX = (vertex3.getX() + vertex2.getX()) / 2;
        int newY = (vertex3.getY() + vertex2.getY()) / 2;
        RgbColor newColor = new RgbColor(bitmap.getRGB(newX, newY));
        Vertex v = new Vertex(new Point(newX, newY), newColor);

        HyperEdge edgeBetween1AndV = new HyperEdge(HyperEdgeType.INTERIOR, vertex1, v);
        HyperEdge edgeBetween2AndV = new HyperEdge(HyperEdgeType.INTERIOR, vertex2, v);
        HyperEdge edgeBetween3AndV = new HyperEdge(HyperEdgeType.INTERIOR, vertex3, v);
        HyperEdge edgeBetween4AndV = new HyperEdge(HyperEdgeType.INTERIOR, vertex4, v);

        HyperEdge f1 = new HyperEdge(HyperEdgeDirection.UP, v);
        HyperEdge f2 = new HyperEdge(HyperEdgeDirection.RIGHT, v);
        HyperEdge f3 = new HyperEdge(HyperEdgeDirection.DOWN, v);
        HyperEdge f4 = new HyperEdge(HyperEdgeDirection.LEFT, v);

        graph.removeEdge(hyperEdge);
        graph.addEdge(edgeBetween1AndV, edgeBetween2AndV, edgeBetween3AndV, edgeBetween4AndV, f1, f2, f3, f4);
    }

}

package pl.edu.agh.gg.util;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.Vertex;

import java.util.HashSet;
import java.util.Set;

public class EdgeUtil {

    private EdgeUtil() {
        throw new UnsupportedOperationException();
    }

    public static int getSquareEdgeArea(Vertex minXMinY, Vertex maxXMaxY) {
        if (minXMinY.getX() > maxXMaxY.getX() || minXMinY.getY() > maxXMaxY.getY()){
            throw new UnsupportedOperationException();
        }
        int xSize = maxXMaxY.getX() - minXMinY.getX();
        int ySize = maxXMaxY.getY() - minXMinY.getY();
        return xSize * ySize;
    }

    public static Set getSetOfVerticles(HyperEdge hyperEdge) {
        return new HashSet(hyperEdge.getVertices());
    }
}

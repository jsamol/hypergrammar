package pl.edu.agh.gg.util;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.Vertex;

import java.util.HashSet;
import java.util.Set;

public class EdgeUtil {

    private EdgeUtil() {
        throw new UnsupportedOperationException();
    }

    public static int getSquareEdgeArea(int minX, int minY, int maxX, int maxY) {
        if (minX > maxX || minY > maxY){
            throw new UnsupportedOperationException();
        }
        int xSize = maxX - minX;
        int ySize = maxY - minY;
        return xSize * ySize;
    }

    public static Set getSetOfVerticles(HyperEdge hyperEdge) {
        return new HashSet(hyperEdge.getVertices());
    }
}

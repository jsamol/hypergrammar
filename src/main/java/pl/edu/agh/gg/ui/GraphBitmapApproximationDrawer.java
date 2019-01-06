package pl.edu.agh.gg.ui;

import pl.edu.agh.gg.data.BitmapApproximationRGB;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.util.EdgeUtil;
import pl.edu.agh.gg.util.VertexUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

public class GraphBitmapApproximationDrawer {

    public static void drawGraphBitmap(HyperGraph graph, String filename) {
        int x1 = VertexUtil.findMinXMinY(graph.getVertices()).get().getX();
        int y1 = VertexUtil.findMinXMinY(graph.getVertices()).get().getY();
        int x2 = VertexUtil.findMaxXMaxY(graph.getVertices()).get().getX();
        int y2 = VertexUtil.findMaxXMaxY(graph.getVertices()).get().getY();

        int xSize = x2 - x1 + 1;
        int ySize = y2 - y1 + 1;

        BitmapApproximationRGB bitmapApproximationRGB = new BitmapApproximationRGB(new int[xSize][ySize],
                new int[xSize][ySize], new int[xSize][ySize]);


        graph.getEdges().sort(new HyperEdgeSizeComparator());

        for (HyperEdge hyperEdge : graph.getEdges()) {
            BitmapApproximationDrawer.updateBitMapColors();
        }

        BitmapApproximationDrawer.saveImage(x1, y1, x2, y2, bitmapApproximationRGB, filename);
    }

    private static class HyperEdgeSizeComparator implements Comparator<HyperEdge> {

        @Override
        public int compare(HyperEdge edge1, HyperEdge edge2) {
            Set<Vertex> vertices1 = EdgeUtil.getSetOfVerticles(edge1);
            Set<Vertex> vertices2 = EdgeUtil.getSetOfVerticles(edge2);

            int area1 = EdgeUtil.getSquareEdgeArea(VertexUtil.findMinXMinY(vertices1).get(),
                    VertexUtil.findMaxXMaxY(vertices1).get());
            int area2 = EdgeUtil.getSquareEdgeArea(VertexUtil.findMinXMinY(vertices2).get(),
                    VertexUtil.findMaxXMaxY(vertices2).get());

            if (area1 == area2) return 0;
            return area1 > area2 ? 1 : -1;
        }
    }
}

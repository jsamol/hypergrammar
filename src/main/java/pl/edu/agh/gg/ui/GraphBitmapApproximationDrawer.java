package pl.edu.agh.gg.ui;

import pl.edu.agh.gg.data.BitmapApproximationRGB;
import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.util.EdgeUtil;
import pl.edu.agh.gg.util.VertexUtil;

import java.util.*;

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
            HashSet<Vertex> vertexHashSet = new HashSet<>(hyperEdge.getVertices());
            if (hyperEdge.getVertices().size() == 4) {
                handle4VerticesGiven(bitmapApproximationRGB, vertexHashSet);

            } else if (hyperEdge.getVertices().size() == 3) {
                handle3VerticesGiven(bitmapApproximationRGB, vertexHashSet);
            }
        }

        BitmapApproximationDrawer.saveImage(x1, y1, x2, y2, bitmapApproximationRGB, filename);
    }

    private static void handle4VerticesGiven(BitmapApproximationRGB bitmapApproximationRGB, HashSet<Vertex> vertexHashSet) {
        Vertex minXY = VertexUtil.findMinXMinY(vertexHashSet).get();
        Vertex maxXY = VertexUtil.findMaxXMaxY(vertexHashSet).get();
        RgbColor maxXminYColor = VertexUtil.findMaxXMinY(vertexHashSet).get().getColor();
        RgbColor minXmaxYColor = VertexUtil.findMinXMaxY(vertexHashSet).get().getColor();

        BitmapApproximationDrawer.updateBitMapColors(minXY.getX(), minXY.getY(), maxXY.getX(), maxXY.getY(),
               minXmaxYColor, maxXY.getColor(), minXY.getColor(), maxXminYColor, bitmapApproximationRGB);
    }

    private static void handle3VerticesGiven(BitmapApproximationRGB bitmapApproximationRGB, HashSet<Vertex> vertexHashSet) {
        Optional<Vertex> minXY = VertexUtil.findMinXMinY(vertexHashSet);
        Optional<Vertex> maxXY = VertexUtil.findMaxXMaxY(vertexHashSet);
        Optional<Vertex> maxXminY = VertexUtil.findMaxXMinY(vertexHashSet);
        Optional<Vertex> minXmaxY = VertexUtil.findMinXMaxY(vertexHashSet);

        if(!minXY.isPresent()){
            int x = Collections.min(VertexUtil.mapToXCord(vertexHashSet));
            int y = Collections.min(VertexUtil.mapToYCord(vertexHashSet));
            minXY = Optional.of(new Vertex(new Point(x, y)));
            evaluateColor(bitmapApproximationRGB, minXY, x, y);
        }
        if(!maxXY.isPresent()){
            int x = Collections.max(VertexUtil.mapToXCord(vertexHashSet));
            int y = Collections.max(VertexUtil.mapToYCord(vertexHashSet));
            maxXY = Optional.of(new Vertex(new Point(x,y)));
            evaluateColor(bitmapApproximationRGB, maxXY, x, y);
        }
        if(!maxXminY.isPresent()){
            int x = Collections.max(VertexUtil.mapToXCord(vertexHashSet));
            int y = Collections.min(VertexUtil.mapToYCord(vertexHashSet));
            maxXminY = Optional.of(new Vertex(new Point(Collections.max(VertexUtil.mapToXCord(vertexHashSet)),
                    Collections.min(VertexUtil.mapToYCord(vertexHashSet)))));
            evaluateColor(bitmapApproximationRGB, maxXminY, x, y);
        }
        if(!minXmaxY.isPresent()){
            int x = Collections.min(VertexUtil.mapToXCord(vertexHashSet));
            int y = Collections.max(VertexUtil.mapToYCord(vertexHashSet));
            minXmaxY = Optional.of(new Vertex(new Point(Collections.min(VertexUtil.mapToXCord(vertexHashSet)),
                    Collections.max(VertexUtil.mapToYCord(vertexHashSet)))));
            evaluateColor(bitmapApproximationRGB, minXmaxY, x, y);
        }
        BitmapApproximationDrawer.updateBitMapColors(minXY.get().getX(), minXY.get().getY(),
                maxXY.get().getX(), maxXY.get().getY(), minXmaxY.get().getColor(), maxXY.get().getColor(),
                minXY.get().getColor(), maxXminY.get().getColor(), bitmapApproximationRGB);
    }

    private static void evaluateColor(BitmapApproximationRGB bitmapApproximationRGB, Optional<Vertex> minXY, int x, int y) {
        minXY.get().setColor(new RgbColor(
                bitmapApproximationRGB.getApprox_r()[x][y],
                bitmapApproximationRGB.getApprox_g()[x][y],
                bitmapApproximationRGB.getApprox_b()[x][y]));
    }

    private static class HyperEdgeSizeComparator implements Comparator<HyperEdge> {

        @Override
        public int compare(HyperEdge edge1, HyperEdge edge2) {
            Set<Vertex> vertices1 = EdgeUtil.getSetOfVerticles(edge1);
            Set<Vertex> vertices2 = EdgeUtil.getSetOfVerticles(edge2);

            int maxX = Collections.max(VertexUtil.mapToXCord(vertices1));
            int maxY = Collections.max(VertexUtil.mapToYCord(vertices1));
            int minX = Collections.min(VertexUtil.mapToXCord(vertices1));
            int minY = Collections.min(VertexUtil.mapToYCord(vertices1));

            int area1 = EdgeUtil.getSquareEdgeArea(minX, minY, maxX, maxY);

            maxX = Collections.max(VertexUtil.mapToXCord(vertices2));
            maxY = Collections.max(VertexUtil.mapToYCord(vertices2));
            minX = Collections.min(VertexUtil.mapToXCord(vertices2));
            minY = Collections.min(VertexUtil.mapToYCord(vertices2));

            int area2 = EdgeUtil.getSquareEdgeArea(minX, minY, maxX, maxY);

            if (area1 == area2) return 0;
            return area1 > area2 ? 1 : -1;
        }
    }
}

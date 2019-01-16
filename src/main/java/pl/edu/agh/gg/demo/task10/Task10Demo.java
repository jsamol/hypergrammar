package pl.edu.agh.gg.demo.task10;

import com.google.common.collect.Sets;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.production.*;
import pl.edu.agh.gg.ui.HyperGraphDrawer;
import pl.edu.agh.gg.util.BitmapUtils;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Task10Demo {

    public static void main(String[] args) throws IOException {
        new Task10Demo().run();
    }

    public void run() throws IOException {
        BufferedImage image = BitmapUtils.loadBitmapFromResource("production1/bitmap.bmp"); //could be any bitmap
        HyperGraph graph = new HyperGraph();

        applyP1(image, graph);

        applyP5ForAllInteriorEdges(graph);

        applyP2ForAllInteriorEdges(image, graph);

        applyP3ForAllBoundaryEdges(image, graph);

        applyP5ForAllInteriorExceptTopLeftOne(graph);

        applyP2ForAllInteriorExceptTopLeftOne(image, graph);

        applyP3ForAllBoundaryEdges(image, graph);

        applyP4ForAllFaceEdges(image, graph);

        applyP5AndP2ToChosenInteriorEdge(image, graph);

        HyperGraphDrawer.draw(graph);
    }

    private void applyP1(BufferedImage image, HyperGraph graph) {
        P1Production p1Production = new P1Production(image);
        p1Production.apply(graph);
    }

    private void applyP5ForAllInteriorEdges(HyperGraph graph) {
        edgesOfType(HyperEdgeType.INTERIOR, graph).forEach(edge -> {
            P5Production p5Production = new P5Production(edge);
            p5Production.apply(graph);
        });
    }

    private void applyP2ForAllInteriorEdges(BufferedImage image, HyperGraph graph) {
        edgesOfType(HyperEdgeType.INTERIOR, graph).forEach(edge -> {
            P2Production p2Production = new P2Production(image, edge);
            p2Production.apply(graph);
        });
    }

    private void applyP3ForAllBoundaryEdges(BufferedImage image, HyperGraph graph) {
        edgesOfType(HyperEdgeType.BOUNDARY, graph).forEach(edge -> {
            try {
                P3Production p3Production = new P3Production(image, edge);
                p3Production.apply(graph);
            } catch (Throwable e) {
                System.out.println("Could not apply p3");
                //it just means that we cannot apply p3 to this production
            }
        });
    }

    private void applyP2ForAllInteriorExceptTopLeftOne(BufferedImage image, HyperGraph graph) {
        HyperEdge topLeftOneInterior2 = findTopLeftInteriorEdge(graph);
        edgesOfType(HyperEdgeType.INTERIOR, graph).stream()
                .filter(edge -> !edge.equals(topLeftOneInterior2))
                .forEach(edge -> {
                    P2Production p2Production = new P2Production(image, edge);
                    p2Production.apply(graph);
                });
    }

    private void applyP5ForAllInteriorExceptTopLeftOne(HyperGraph graph) {
        HyperEdge topLeftOneInterior = findTopLeftInteriorEdge(graph);
        edgesOfType(HyperEdgeType.INTERIOR, graph).stream()
                .filter(edge -> !edge.equals(topLeftOneInterior))
                .forEach(edge -> {
                    P5Production p5Production = new P5Production(edge);
                    p5Production.apply(graph);
                });
    }

    private void applyP4ForAllFaceEdges(BufferedImage image, HyperGraph graph) {
        getCombinationOfHyperEdges(edgesOfType(HyperEdgeType.FACE, graph), 3).forEach(combination -> {
            try {
                P4Production p4Production = new P4Production(image, combination.toArray(new HyperEdge[0]));
                p4Production.apply(graph);
            } catch (Throwable e) {
                System.out.println("Could not apply p4");
            }
        });
    }

    private void applyP5AndP2ToChosenInteriorEdge(BufferedImage image, HyperGraph graph) {
        HyperEdge edge = findNearestBottomRightInteriorEdgeFromMiddle(graph);

        P5Production p5Production = new P5Production(edge);
        p5Production.apply(graph);

        P2Production p2Production = new P2Production(image, edge);
        p2Production.apply(graph);
    }

    private List<HyperEdge> edgesOfType(HyperEdgeType type, HyperGraph graph) {
        return graph.getEdges()
                .stream()
                .filter(edge -> edge.getType() == type)
                .collect(Collectors.toList());
    }

    private HyperEdge findTopLeftInteriorEdge(HyperGraph graph) {
        List<HyperEdge> edges = edgesOfType(HyperEdgeType.INTERIOR, graph);
        Vertex topLeftVertex = VertexUtil.findMinXMaxY(graph.getVertices()).orElseThrow(IllegalStateException::new);
        return edges.stream()
                .filter(edge -> edge.getVertices().contains(topLeftVertex))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private HyperEdge findNearestBottomRightInteriorEdgeFromMiddle(HyperGraph graph) {
        List<HyperEdge> edges = edgesOfType(HyperEdgeType.INTERIOR, graph);

        int minX = Collections.min(graph.getVertices().stream().map(Vertex::getX).collect(Collectors.toList()));
        int maxX = Collections.max(graph.getVertices().stream().map(Vertex::getX).collect(Collectors.toList()));
        int minY = Collections.min(graph.getVertices().stream().map(Vertex::getY).collect(Collectors.toList()));
        int maxY = Collections.max(graph.getVertices().stream().map(Vertex::getY).collect(Collectors.toList()));

        int middleX = (maxX - minX) / 2;
        int middleY = (maxY - minY) / 2;

        List<Vertex> bottomRightVertices = graph
                .getVertices()
                .stream()
                .filter(vertex -> vertex.getX() > middleX && vertex.getY() < middleY)
                .collect(Collectors.toList());

        Vertex nearestBottomRightVertex = VertexUtil.findMinXMaxY(bottomRightVertices).orElseThrow(IllegalStateException::new);
        return edges.stream()
                .filter(edge -> edge.getVertices().contains(nearestBottomRightVertex))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Set<Set<HyperEdge>> getCombinationOfHyperEdges(List<HyperEdge> hyperEdges, int combinationLength) {
        return Sets.powerSet(new HashSet<>(hyperEdges))
                .stream()
                .filter(set -> set.size() == combinationLength)
                .collect(Collectors.toSet());
    }
}

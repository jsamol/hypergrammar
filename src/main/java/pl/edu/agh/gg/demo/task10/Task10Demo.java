package pl.edu.agh.gg.demo.task10;

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
import java.util.List;
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

        applyP4ForAllFaceEdges(graph);
//
        applyP5AndP2ToChosenInteriorEdge(graph);

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

    private void applyP4ForAllFaceEdges(HyperGraph graph) {
        edgesOfType(HyperEdgeType.FACE, graph).forEach(edge -> {
//            P4Production p4Production = new P4Production();
        });
    }

    private void applyP5AndP2ToChosenInteriorEdge(HyperGraph graph) {
        //apply P5 and P2 to some interior edge TODO
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
}

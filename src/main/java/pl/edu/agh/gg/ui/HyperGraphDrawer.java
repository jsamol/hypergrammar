package pl.edu.agh.gg.ui;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.j2dviewer.J2DGraphRenderer;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.Viewer;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.ui.graph.Attribute;
import pl.edu.agh.gg.ui.graph.HtmlClass;
import pl.edu.agh.gg.ui.graph.StylesheetProvider;

import java.util.IntSummaryStatistics;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HyperGraphDrawer {

    private static double DEFAULT_VIEW_PERCENT = 1.5;

    private HyperGraph hyperGraph;
    private SingleGraph graph;

    // percent of the graph that should be visible on start
    private double viewPercent;

    public HyperGraphDrawer(HyperGraph hyperGraph) {
        this(hyperGraph, DEFAULT_VIEW_PERCENT);
    }

    public HyperGraphDrawer(HyperGraph hyperGraph, double viewPercent) {
        this.hyperGraph = hyperGraph;
        this.graph = new SingleGraph(hyperGraph.id, false, true);
        this.viewPercent = viewPercent;
    }

    public void draw() {
        System.setProperty(Attribute.RENDERER, J2DGraphRenderer.class.getName());

        drawVertices();
        drawHyperEdges();

        graph.setAttribute(Attribute.STYLESHEET, StylesheetProvider.STYLESHEET);
        Viewer viewer = graph.display(false);
        Camera camera = viewer.getDefaultView().getCamera();
        camera.setViewPercent(viewPercent);
    }

    private void drawVertices() {
        hyperGraph.getVertices().forEach(vertex -> {
            Node node = graph.addNode(vertex.id);
            node.addAttribute(Attribute.LABEL, vertex.getGeom() + ", " + vertex.getColor());
            node.addAttribute(Attribute.X, vertex.getGeom().getX());
            node.addAttribute(Attribute.Y, vertex.getGeom().getY());
        });
    }

    private void drawHyperEdges() {
        SpriteManager spriteManager = new SpriteManager(graph);

        hyperGraph.getEdges().forEach(hyperEdge -> {
            Node edgeNode = graph.addNode(hyperEdge.id);
            edgeNode.addAttribute(Attribute.CLASS, HtmlClass.HYPER_EDGE);
            edgeNode.addAttribute(Attribute.LABEL, hyperEdge.getType().label);

            if (hyperEdge.getType() == HyperEdgeType.INTERIOR) {
                Sprite sprite = spriteManager.addSprite("sprite-" + hyperEdge.id);
                sprite.attachToNode(hyperEdge.id);
                sprite.addAttribute(Attribute.CLASS, HtmlClass.HYPER_EDGE);
                sprite.addAttribute(Attribute.LABEL, "break = " + (hyperEdge.getCanBreak() ? 1 : 0));
            }

            edgeNode.addAttribute(Attribute.X, getHyperNodeX(hyperEdge));
            edgeNode.addAttribute(Attribute.Y, getHyperNodeY(hyperEdge));

            hyperEdge.getVertices().forEach(vertex -> graph.addEdge(vertex.id + "-" + hyperEdge.id, vertex.id, hyperEdge.id));
        });
    }

    private double getHyperNodeX(HyperEdge hyperEdge) {
        return getHyperNodePosition(hyperEdge, vertex -> vertex.getGeom().getX());
    }

    private double getHyperNodeY(HyperEdge hyperEdge) {
        return getHyperNodePosition(hyperEdge, vertex -> vertex.getGeom().getY());
    }

    private double getHyperNodePosition(HyperEdge hyperEdge, ToIntFunction<Vertex> mapPosition) {
        return hyperEdge.getVertices()
                        .stream()
                        .mapToInt(mapPosition)
                        .average()
                        .orElse(0.0);
    }
}

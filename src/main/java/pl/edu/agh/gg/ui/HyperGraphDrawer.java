package pl.edu.agh.gg.ui;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.j2dviewer.J2DGraphRenderer;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.ui.graph.Attribute;
import pl.edu.agh.gg.ui.graph.HtmlClass;
import pl.edu.agh.gg.ui.graph.StylesheetProvider;

import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

public class HyperGraphDrawer {

    private HyperGraph<? extends Vertex, ? extends HyperEdge> hyperGraph;
    private SingleGraph graph;

    public HyperGraphDrawer(HyperGraph<?, ?> hyperGraph) {
        this.hyperGraph = hyperGraph;
        this.graph = new SingleGraph(hyperGraph.id, false, true);
    }

    public void draw() {
        System.setProperty(Attribute.RENDERER, J2DGraphRenderer.class.getName());

        drawVertices();
        drawHyperEdges();

        graph.setAttribute(Attribute.STYLESHEET, StylesheetProvider.STYLESHEET);
        graph.display(false);
    }

    private void drawVertices() {
        for (Vertex vertex : hyperGraph.getVertices()) {
            Node node = graph.addNode(vertex.id);
            node.addAttribute(Attribute.LABEL, vertex.getGeom() + ", " + vertex.getColor());
            node.addAttribute(Attribute.X, vertex.getGeom().getX());
            node.addAttribute(Attribute.Y, vertex.getGeom().getY());
        }
    }

    private void drawHyperEdges() {
        SpriteManager spriteManager = new SpriteManager(graph);
        for (HyperEdge<? extends Vertex> hyperEdge : hyperGraph.getEdges()) {
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

            for (Vertex vertex : hyperEdge.getVertices()) {
                Edge edge = graph.addEdge(vertex.id + "-" + hyperEdge.id, vertex.id, hyperEdge.id);
            }
        }
    }

    private double getHyperNodeX(HyperEdge<? extends Vertex> hyperEdge) {
        return getHyperNodePositionStream(hyperEdge, vertex -> vertex.getGeom().getX()).average().orElse(0.0);
    }

    private double getHyperNodeY(HyperEdge<? extends Vertex> hyperEdge) {
        return getHyperNodePositionStream(hyperEdge, vertex -> vertex.getGeom().getY()).average().orElse(0.0);
    }

    private IntStream getHyperNodePositionStream(HyperEdge<? extends Vertex> hyperEdge, ToIntFunction<Vertex> mapPositon) {
        return hyperEdge.getVertices()
                .stream()
                .mapToInt(mapPositon);
    }
}

package pl.edu.agh.gg.ui;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.j2dviewer.J2DGraphRenderer;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.Viewer;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.ui.graph.Attribute;
import pl.edu.agh.gg.ui.graph.HtmlClass;
import pl.edu.agh.gg.ui.graph.StylesheetProvider;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.*;
import java.util.IntSummaryStatistics;
import java.util.function.ToIntFunction;

import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.*;

public class HyperGraphDrawer {

    private static double DEFAULT_VIEW_PERCENT = 1.5;

    public static void draw(HyperGraph hyperGraph) {
        draw(hyperGraph, DEFAULT_VIEW_PERCENT);
    }

    public static void draw(HyperGraph hyperGraph, double viewPercent) {
        SingleGraph graph = new SingleGraph(hyperGraph.id, false, true);

        System.setProperty(Attribute.RENDERER, J2DGraphRenderer.class.getName());

        drawVertices(hyperGraph, graph);
        drawHyperEdges(hyperGraph, graph);

        graph.setAttribute(Attribute.STYLESHEET, StylesheetProvider.STYLESHEET);
        Viewer viewer = graph.display(false);

        Camera camera = viewer.getDefaultView().getCamera();
        camera.setViewPercent(viewPercent);

        Vertex maxXmaxY = VertexUtil.findMaxXMaxY(hyperGraph.getVertices()).get();
        Vertex minXminY = VertexUtil.findMinXMinY(hyperGraph.getVertices()).get();
        double xCenter = (minXminY.getX() + maxXmaxY.getX()) / 2.0;
        double yCenter = (minXminY.getY() + maxXmaxY.getY()) / 2.0;
        camera.setViewCenter(xCenter, yCenter, 0);
    }

    private static void drawVertices(HyperGraph hyperGraph, SingleGraph graph) {
        hyperGraph.getVertices().forEach(vertex -> {
            Node node = graph.addNode(vertex.id);
            node.addAttribute(Attribute.LABEL, vertex.getGeom() + ", " + vertex.getColor());
            node.addAttribute(Attribute.X, vertex.getGeom().getX());
            node.addAttribute(Attribute.Y, vertex.getGeom().getY());
        });
    }

    private static void drawHyperEdges(HyperGraph hyperGraph, SingleGraph graph) {
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

            if (hyperEdge.getType() == HyperEdgeType.FACE && hyperEdge.getVertices().size() == 1) {
                Vertex center = hyperEdge.getVertices().stream().findAny().get();
                HyperEdge edge = hyperGraph.getEdges().stream()
                        .filter(e -> e.getVertices().contains(center) && e.getType() == HyperEdgeType.INTERIOR)
                        .findFirst().get();
                HyperEdgeDirection dir = hyperEdge.getDir();
                double x = getHyperNodeX(hyperEdge);
                double y = getHyperNodeY(hyperEdge);
                double delta = edge.getSideLength() / 2.5;

                if (dir == UP) {
                    y += delta;
                } else if (dir == RIGHT) {
                    x += delta;
                } else if (dir == DOWN) {
                    y -= delta;
                } else if (dir == LEFT) {
                    x -= delta;
                }

                edgeNode.addAttribute(Attribute.X, x);
                edgeNode.addAttribute(Attribute.Y, y);
                edgeNode.addAttribute(Attribute.LABEL, hyperEdge.getType().label + hyperEdge.getDir().name());
            } else {
                edgeNode.addAttribute(Attribute.X, getHyperNodeX(hyperEdge));
                edgeNode.addAttribute(Attribute.Y, getHyperNodeY(hyperEdge));
            }

            hyperEdge.getVertices().forEach(vertex -> graph.addEdge(vertex.id + "-" + hyperEdge.id, vertex.id, hyperEdge.id));
        });
    }

    private static double getHyperNodeX(HyperEdge hyperEdge) {
        return getHyperNodePosition(hyperEdge, vertex -> vertex.getGeom().getX());
    }

    private static double getHyperNodeY(HyperEdge hyperEdge) {
        return getHyperNodePosition(hyperEdge, vertex -> vertex.getGeom().getY());
    }

    private static double getHyperNodePosition(HyperEdge hyperEdge, ToIntFunction<Vertex> mapPosition) {
        IntSummaryStatistics statistics =
                hyperEdge.getVertices()
                        .stream()
                        .mapToInt(mapPosition)
                        .summaryStatistics();

        return (statistics.getMin() + statistics.getMax()) / 2.0;
    }
}

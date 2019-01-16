package pl.edu.agh.gg.ui;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.j2dviewer.J2DGraphRenderer;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.Viewer;
import pl.edu.agh.gg.hypergraph.*;
import pl.edu.agh.gg.ui.graph.Attribute;
import pl.edu.agh.gg.ui.graph.HtmlClass;
import pl.edu.agh.gg.ui.graph.StylesheetProvider;
import pl.edu.agh.gg.util.VertexUtil;

import java.awt.*;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.DoubleStream;

import static pl.edu.agh.gg.hypergraph.HyperEdgeDirection.*;

public class HyperGraphDrawer {

    private static double DEFAULT_VIEW_PERCENT = 1.5;

    public static void draw(HyperGraph hyperGraph) {
        draw(hyperGraph, "", DEFAULT_VIEW_PERCENT);
    }

    public static void draw(HyperGraph hyperGraph, String title) {
        draw(hyperGraph, title, DEFAULT_VIEW_PERCENT);
    }

    public static void draw(HyperGraph hyperGraph, double viewPercent) {
        draw(hyperGraph, "", viewPercent);
    }

    public static void draw(HyperGraph hyperGraph, String title, double viewPercent) {
        SingleGraph graph = new SingleGraph(hyperGraph.getId(), false, true);

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

        Label titleLabel = new Label(title);
        viewer.getDefaultView().add(titleLabel);
    }

    private static void drawVertices(HyperGraph hyperGraph, SingleGraph graph) {
        hyperGraph.getVertices().forEach(vertex -> {
            Node node = graph.addNode(vertex.getId());
            node.addAttribute(Attribute.LABEL, vertex.getGeom() + ", " + vertex.getColor());
            node.addAttribute(Attribute.X, vertex.getGeom().getX());
            node.addAttribute(Attribute.Y, vertex.getGeom().getY());
        });
    }

    private static void drawHyperEdges(HyperGraph hyperGraph, SingleGraph graph) {
        SpriteManager spriteManager = new SpriteManager(graph);

        hyperGraph.getEdges().forEach(HyperGraphDrawer::setHyperNodePositions);

        hyperGraph.getEdges().forEach(hyperEdge -> {
            Node edgeNode = graph.addNode(hyperEdge.getId());
            edgeNode.addAttribute(Attribute.CLASS, HtmlClass.HYPER_EDGE);
            edgeNode.addAttribute(Attribute.LABEL, hyperEdge.getType().label);

            if (hyperEdge.getType() == HyperEdgeType.INTERIOR) {
                Sprite sprite = spriteManager.addSprite("sprite-" + hyperEdge.getId());
                sprite.attachToNode(hyperEdge.getId());
                sprite.addAttribute(Attribute.CLASS, HtmlClass.HYPER_EDGE);
                sprite.addAttribute(Attribute.LABEL, "break = " + (hyperEdge.getCanBreak() ? 1 : 0));
            }

            if (hyperEdge.getType() == HyperEdgeType.FACE && hyperEdge.getVertices().size() == 1) {
                Vertex center = hyperEdge.getVertices().stream().findAny().get();
                HyperEdge edge = hyperGraph.getEdges().stream()
                        .filter(e -> e.getVertices().contains(center) && e.getType() == HyperEdgeType.INTERIOR)
                        .findFirst().get();

                setFaceEdgeOffset(hyperGraph.getEdges(), hyperEdge);

                edgeNode.addAttribute(Attribute.LABEL, hyperEdge.getType().label + hyperEdge.getDir().name());
            }

            edgeNode.addAttribute(Attribute.X, hyperEdge.getNodeX());
            edgeNode.addAttribute(Attribute.Y, hyperEdge.getNodeY());

            hyperEdge.getVertices().forEach(vertex -> graph.addEdge(vertex.getId() + "-" + hyperEdge.getId(), vertex.getId(), hyperEdge.getId()));
        });
    }

    private static void setHyperNodePositions(HyperEdge hyperEdge) {
        setHyperNodeX(hyperEdge);
        setHyperNodeY(hyperEdge);
    }

    private static void setHyperNodeX(HyperEdge hyperEdge) {
        hyperEdge.setNodeX(getHyperNodePosition(hyperEdge, vertex -> vertex.getGeom().getX()));
    }

    private static void setHyperNodeY(HyperEdge hyperEdge) {
        hyperEdge.setNodeY(getHyperNodePosition(hyperEdge, vertex -> vertex.getGeom().getY()));
    }

    private static double getHyperNodePosition(HyperEdge hyperEdge, ToIntFunction<Vertex> mapPosition) {
        IntSummaryStatistics statistics =
                hyperEdge.getVertices()
                        .stream()
                        .mapToInt(mapPosition)
                        .summaryStatistics();

        return (statistics.getMin() + statistics.getMax()) / 2.0;
    }

    private static void setFaceEdgeOffset(List<HyperEdge> hyperEdges, HyperEdge faceEdge) {
        HyperEdgeDirection dir = faceEdge.getDir();

        double x = faceEdge.getNodeX();
        double y = faceEdge.getNodeY();

        if (dir == UP || dir == DOWN) {
            DoubleStream positionStream = hyperEdges.stream()
                    .filter(hyperEdge ->
                        Math.abs(hyperEdge.getNodeX() - x) <= 0.5 &&
                                ((dir == UP && hyperEdge.getNodeY() > y) || (dir == DOWN && hyperEdge.getNodeY() < y))
                    )
                    .mapToDouble(hyperEdge -> (hyperEdge.getNodeY() + y) / 2.0);

            OptionalDouble positionOptional = dir == UP ? positionStream.min() : positionStream.max();
            faceEdge.setNodeY(positionOptional.orElse(y));
        } else if (dir == LEFT || dir == RIGHT) {
            DoubleStream positionStream = hyperEdges.stream()
                    .filter(hyperEdge ->
                            Math.abs(hyperEdge.getNodeY() - y) <= 0.5 &&
                                    ((dir == LEFT && hyperEdge.getNodeX() < x) || (dir == RIGHT && hyperEdge.getNodeX() > x))
                    )
                    .mapToDouble(hyperEdge -> (hyperEdge.getNodeX() + x) / 2.0);
            OptionalDouble positionOptional = dir == LEFT ? positionStream.max() : positionStream.min();
            faceEdge.setNodeX(positionOptional.orElse(x));
        }
    }
}

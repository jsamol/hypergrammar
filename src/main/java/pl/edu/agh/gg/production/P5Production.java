package pl.edu.agh.gg.production;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.util.EdgeUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.edu.agh.gg.hypergraph.HyperEdgeType.INTERIOR;

public class P5Production implements Production {

    private HyperEdge hyperEdge;

    public P5Production(HyperEdge hyperEdge) {
        this.hyperEdge = hyperEdge;
    }

    @Override
    public void apply(HyperGraph graph) {
        if (hyperEdge.getType() != INTERIOR) {
            throw new IllegalStateException("edge should have type INTERIOR");
        }
        if (hyperEdge.getCanBreak()) {
            throw new IllegalStateException("canBreak should be false");
        }
        hyperEdge.setCanBreak(true);
        setCanBreakForAllPossibleEdges(graph, hyperEdge);
    }

    private void setCanBreakForAllPossibleEdges(HyperGraph graph, HyperEdge edge) {
        if (edge.getVertices().size() < 2) return;

        double sideLength = edge.getSideLength();
        Point center = edge.getCenter();

        List<HyperEdge> possibleEdges = graph.getEdges().stream().filter(e -> {
            if (e.getType() != INTERIOR) return false;
            Set<Vertex> commonVertices = e.findCommonVertices(edge);
            if (commonVertices.size() != 1) return false;
            double x = e.getSideLength();
            if (Math.abs(x - 2 * sideLength) > 1e5) return false;

            Vertex commonVertex = commonVertices.stream().findAny().get();

            long faceEdgesCount = EdgeUtil.findRelatedFaceEdges(graph, e, commonVertex).stream().filter(fe -> {
                List<Vertex> v = fe.getVertices();
                Vertex v1 = v.get(0);
                Vertex v2 = v.get(1);
                if (v1.getX() != v2.getX()) {
                    return v1.getX() < center.getX() && center.getX() < v2.getX() ||
                            v2.getX() < center.getX() && center.getX() < v1.getX();
                }
                if (v1.getY() != v2.getY()) {
                    return v1.getY() < center.getY() && center.getY() < v2.getY() ||
                            v2.getY() < center.getY() && center.getY() < v1.getY();
                }
                return false;
            }).count();

            return faceEdgesCount == 1;
        }).collect(Collectors.toList());

        possibleEdges.forEach(e -> {
            P6Production prod = new P6Production(edge, e);
            prod.apply(graph);
            setCanBreakForAllPossibleEdges(graph, e);
        });
    }

}

package pl.edu.agh.gg.production;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.util.EdgeUtil;

import java.util.HashSet;
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

        List<HyperEdge> possibleEdges = graph.getEdges().stream().filter(e -> {
            if (e.getType() != INTERIOR) return false;
            Set<Vertex> commonVertices = e.findCommonVertices(edge);
            if (commonVertices.size() != 1) return false;
            if (e.getSideLength() <= sideLength) return false;

            Vertex commonVertex = commonVertices.stream().findAny().get();

            long faceEdgesCount = EdgeUtil.findRelatedFaceEdges(graph, e, commonVertex).stream().filter(fe -> {
                Set<Vertex> edgeVertices = new HashSet<>(edge.getVertices());
                edgeVertices.remove(commonVertex);

                Set<Vertex> feVertices = new HashSet<>(fe.getVertices());
                feVertices.remove(commonVertex);
                Vertex feVertex = feVertices.stream().findAny().get();

                return edgeVertices.stream().allMatch(v -> v.getX() != feVertex.getX() && v.getY() != feVertex.getY());
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

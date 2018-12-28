package pl.edu.agh.gg.util;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;

import java.util.Set;
import java.util.stream.Collectors;

import static pl.edu.agh.gg.hypergraph.HyperEdgeType.FACE;

public class EdgeUtil {

    private EdgeUtil() {
        throw new UnsupportedOperationException();
    }

    public static Set<HyperEdge> findRelatedFaceEdges(HyperGraph graph, HyperEdge edge, Vertex vertex) {
        return graph.getEdges().stream().filter(e -> {
            if (e.getType() != FACE) return false;
            if (!e.getVertices().contains(vertex)) return false;
            return e.findCommonVertices(edge).size() == 2;
        }).collect(Collectors.toSet());
    }

}

package pl.edu.agh.gg.production;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.util.EdgeUtil;

import java.util.List;
import java.util.Set;

import static pl.edu.agh.gg.hypergraph.HyperEdgeType.INTERIOR;

public class P6Production implements Production {

    private HyperEdge small;
    private HyperEdge big;

    public P6Production(HyperEdge small, HyperEdge big) {
        this.small = small;
        this.big = big;
    }

    @Override
    public void apply(HyperGraph graph) {
        if (small.getType() != INTERIOR || big.getType() != INTERIOR) {
            throw new IllegalStateException("both edges should have type INTERIOR");
        }
        if (!small.getCanBreak()) {
            throw new IllegalStateException("canBreak for smaller edge should be true");
        }
        if (small.getVertices().size() < 2) {
            throw new IllegalStateException("smaller edge should contain more than 1 vertex");
        }
        Set<Vertex> commonVertices = big.findCommonVertices(small);
        if (commonVertices.size() != 1) {
            throw new IllegalStateException("edges should have one common vertex");
        }
        if (Math.abs(big.getSideLength() - 2 * small.getSideLength()) > 1e5) {
            throw new IllegalStateException("small should be smaller than big");
        }

        Vertex commonVertex = commonVertices.stream().findAny().get();
        Point center = small.getCenter();

        long faceEdgesCount = EdgeUtil.findRelatedFaceEdges(graph, big, commonVertex).stream().filter(fe -> {
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

        if (faceEdgesCount != 1) {
            throw new IllegalStateException("edges should be separated by face edge");
        }


        big.setCanBreak(true);
    }

}

package pl.edu.agh.gg.production;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;

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

        big.setCanBreak(true);
    }

}

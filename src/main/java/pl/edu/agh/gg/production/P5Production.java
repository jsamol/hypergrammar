package pl.edu.agh.gg.production;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;

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
    }

}

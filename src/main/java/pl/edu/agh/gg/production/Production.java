package pl.edu.agh.gg.production;

import pl.edu.agh.gg.hypergraph.HyperGraph;

public interface Production {
    void apply(HyperGraph graph);
}

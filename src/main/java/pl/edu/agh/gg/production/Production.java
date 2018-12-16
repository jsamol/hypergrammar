package pl.edu.agh.gg.production;

import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;

public interface Production {
    void apply(HyperGraph<Vertex, HyperEdge<Vertex>> graph);
}

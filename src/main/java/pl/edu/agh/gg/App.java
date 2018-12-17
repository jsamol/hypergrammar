package pl.edu.agh.gg;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.hypergraph.HyperEdge;
import pl.edu.agh.gg.hypergraph.HyperEdgeType;
import pl.edu.agh.gg.hypergraph.HyperGraph;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.ui.HyperGraphDrawer;

public class App {

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {

        // Test case
        HyperGraph graph = new HyperGraph();

        Vertex v1 = new Vertex(new Point(0, 0));
        Vertex v2 = new Vertex(new Point(10, 0));
        Vertex v3 = new Vertex(new Point(0, 10));
        Vertex v4 = new Vertex(new Point(10, 10));

        graph.addEdge(new HyperEdge(HyperEdgeType.INTERIOR, v1, v2, v3, v4));
        graph.addEdge(new HyperEdge(HyperEdgeType.BOUNDARY, v1, v2));
        graph.addEdge(new HyperEdge(HyperEdgeType.BOUNDARY, v1, v3));
        graph.addEdge(new HyperEdge(HyperEdgeType.BOUNDARY, v2, v4));
        graph.addEdge(new HyperEdge(HyperEdgeType.BOUNDARY, v3, v4));

        HyperGraphDrawer drawer = new HyperGraphDrawer(graph);
        drawer.draw();

    }
}

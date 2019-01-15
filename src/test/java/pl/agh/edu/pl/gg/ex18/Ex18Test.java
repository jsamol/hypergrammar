package pl.agh.edu.pl.gg.ex18;

import org.junit.Test;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.demo.ex18.Demo18;
import pl.edu.agh.gg.hypergraph.Vertex;

public class Ex18Test {

    @Test
    public void createRedGreenBlueHyperGraph() {
        new Demo18().createRedGreenBlueHyperGraph();
    }

    @Test
    public void createWhiteBlackHyperGraph() {
        new Demo18().createWhiteBlackHyperGraph();
    }

    @Test
    public void createBlueRedHyperGraph() {
        new Demo18().createBlueRedHyperGraph();
    }

    @Test
    public void createRedHyperGraph() {
        new Demo18().createRedHyperGraph();
    }
}

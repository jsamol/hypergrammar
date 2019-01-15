package pl.agh.edu.pl.gg.ui;

import org.junit.Test;
import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.ui.GraphBitmapApproximationDrawer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GraphBitmapApproximationDrawerTest {

    private static RgbColor RED = new RgbColor(255, 0, 0);

    @Test
    public void isDiagonalTest() {
        Set<Vertex> diagonal1 = new HashSet<>(Arrays.asList(new Vertex(new Point(100, 200), RED),
                new Vertex(new Point(200, 100), RED)));
        Set<Vertex> diagonal2 = new HashSet<>(Arrays.asList(new Vertex(new Point(100, 100), RED),
                new Vertex(new Point(200, 200), RED)));
        Set<Vertex> nonDiagonal1 = new HashSet<>(Arrays.asList(new Vertex(new Point(100, 100), RED),
                new Vertex(new Point(100, 200), RED)));
        Set<Vertex> nonDiagonal2 = new HashSet<>(Arrays.asList(new Vertex(new Point(100, 200), RED),
                new Vertex(new Point(200, 200), RED)));
        Set<Vertex> nonDiagonal3 = new HashSet<>(Arrays.asList(new Vertex(new Point(200, 200), RED),
                new Vertex(new Point(200, 100), RED)));
        Set<Vertex> nonDiagonal4 = new HashSet<>(Arrays.asList(new Vertex(new Point(100, 100), RED),
                new Vertex(new Point(200, 100), RED)));

        assertTrue(GraphBitmapApproximationDrawer.isDiagonal(diagonal1));
        assertTrue(GraphBitmapApproximationDrawer.isDiagonal(diagonal2));
        assertFalse(GraphBitmapApproximationDrawer.isDiagonal(nonDiagonal1));
        assertFalse(GraphBitmapApproximationDrawer.isDiagonal(nonDiagonal2));
        assertFalse(GraphBitmapApproximationDrawer.isDiagonal(nonDiagonal3));
        assertFalse(GraphBitmapApproximationDrawer.isDiagonal(nonDiagonal4));
    }
}

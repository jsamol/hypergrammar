package pl.edu.agh.gg.model.hypergraph;

import org.w3c.dom.css.RGBColor;

import java.awt.*;

public class Vertex {

    private static String ID_PREFIX = "vertex-";

    public String id = ID_PREFIX + String.valueOf(hashCode());

    private Point geom;
    private RGBColor color;

    public Vertex(Point geom) {
        this.geom = geom;
        this.color = new RgbColor(0, 0, 0);
    }

    public Vertex(Point geom, RgbColor color) {
        this.geom = geom;
        this.color = color;
    }
}

package pl.edu.agh.gg.hypergraph;

import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.ui.graph.Drawable;

import java.awt.image.BufferedImage;

public class Vertex extends Drawable {

    private Point geom;
    private RgbColor color;

    public Vertex(Point geom) {
        this.geom = geom;
        this.color = new RgbColor(0, 0, 0);
    }

    public Vertex(Point geom, RgbColor color) {
        this.geom = geom;
        this.color = color;
    }

    public Vertex(int x, int y) {
        this.geom = new Point(x, y);
        this.color = new RgbColor(0, 0, 0);
    }

    public Vertex(int x, int y, BufferedImage image) {
        this.geom = new Point(x, y);
        this.color = new RgbColor(image.getRGB(x, y));
    }

    public Point getGeom() {
        return geom;
    }

    public void setGeom(Point geom) {
        this.geom = geom;
    }

    public RgbColor getColor() {
        return color;
    }

    public void setColor(RgbColor color) {
        this.color = color;
    }

    public int getX() {
        return geom.getX();
    }

    public int getY() {
        return geom.getY();
    }
}

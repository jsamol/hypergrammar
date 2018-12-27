package pl.agh.edu.pl.gg.ui;

import org.junit.Test;
import pl.edu.agh.gg.data.Point;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.Vertex;
import pl.edu.agh.gg.ui.BitMapApproximationDrawer;
import pl.edu.agh.gg.util.BitmapUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static pl.edu.agh.gg.ui.BitMapApproximationDrawer.ZAD_12_OUT;

public class BitMapApproximationDrawerTest {

    private RgbColor red = new RgbColor(255, 0, 0);
    private RgbColor green = new RgbColor(0, 255, 0);
    private RgbColor white = new RgbColor(255, 255, 255);
    private RgbColor black = new RgbColor(0, 0, 0);
    private RgbColor blue = new RgbColor(0, 0, 255);

    @Test
    public void drawApproximateBitmapGreenAndRed() {
        List<Vertex> vertexList = Arrays.asList(new Vertex(new Point(0, 10), red), new Vertex(new Point(10, 10), red),
                new Vertex(new Point(0, 0), red), new Vertex(new Point(10, 0), green));

        BitMapApproximationDrawer.drawApproximateBitmap(vertexList, "greenRed");
        try {
            BufferedImage image = BitmapUtils.loadBitmapFromResource(ZAD_12_OUT + "/greenRed.bmp");
            assertEquals(image.getRGB(image.getMinX(), image.getMinY()), new Color(red.getR(), red.getG(), red.getB()).getRGB());
        } catch (IOException e) {
        }
    }

    @Test
    public void drawApproximateBitmapBlack() {
        List<Vertex> vertexList = Arrays.asList(new Vertex(new Point(0, 10), black), new Vertex(new Point(10, 10), black),
                new Vertex(new Point(0, 0), black), new Vertex(new Point(10, 0), black));

        BitMapApproximationDrawer.drawApproximateBitmap(vertexList, "black");
        try {
            BufferedImage image = BitmapUtils.loadBitmapFromResource(ZAD_12_OUT + "/black.bmp");
            assertEquals(image.getRGB(image.getMinX(), image.getMinY()), new Color(black.getR(), black.getG(), black.getB()).getRGB());
        } catch (IOException e) {
        }
    }

    @Test
    public void drawApproximateBitmapWhite() {
        List<Vertex> vertexList = Arrays.asList(new Vertex(new Point(0, 10), white), new Vertex(new Point(10, 10), white),
                new Vertex(new Point(0, 0), white), new Vertex(new Point(10, 0), white));

        BitMapApproximationDrawer.drawApproximateBitmap(vertexList, "white");
        try {
            BufferedImage image = BitmapUtils.loadBitmapFromResource(ZAD_12_OUT + "/white.bmp");
            assertEquals(image.getRGB(image.getMinX(), image.getMinY()), new Color(white.getR(), white.getG(), white.getB()).getRGB());
        } catch (IOException e) {
        }
    }

    @Test
    public void drawApproximateBitmapBlueWhite() {
        List<Vertex> vertexList = Arrays.asList(new Vertex(new Point(0, 100), blue), new Vertex(new Point(100, 100), blue),
                new Vertex(new Point(0, 0), white), new Vertex(new Point(100, 0), white));

        BitMapApproximationDrawer.drawApproximateBitmap(vertexList, "blueWhite");
        try {
            BufferedImage image = BitmapUtils.loadBitmapFromResource(ZAD_12_OUT +"/BlueWhite.bmp");
            assertEquals(image.getRGB(image.getMinX(), image.getMinY()), new Color(white.getR(), white.getG(), white.getB()).getRGB());
        } catch (IOException e) {
        }
    }
}

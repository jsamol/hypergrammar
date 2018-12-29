package pl.edu.agh.gg.ui;

import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.hypergraph.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BitMapApproximationDrawer {

    public static final String ZAD_12_OUT = "zad12";
    public static int[][] APPROX_R;
    public static int[][] APPROX_G;
    public static int[][] APPROX_B;

    public static void drawApproximateBitmap(List<Vertex> vertexList, String filename) {
        int x1 = vertexList.get(2).getGeom().getX();
        int y1 = vertexList.get(2).getGeom().getY();

        int x2 = vertexList.get(1).getGeom().getX();
        int y2 = vertexList.get(1).getGeom().getY();

        RgbColor rgb1 = vertexList.get(0).getColor();
        RgbColor rgb2 = vertexList.get(1).getColor();
        RgbColor rgb3 = vertexList.get(2).getColor();
        RgbColor rgb4 = vertexList.get(3).getColor();

        int xSize = x2 - x1 + 1;
        int ySize = y2 - y1 + 1;
        APPROX_R = new int[xSize][ySize];
        APPROX_G = new int[xSize][ySize];
        APPROX_B = new int[xSize][ySize];

        for (int px = x1; px <= x2; px++) {
            for (int py = y1; py <= y2; py++) {
                double xFactor = (double) (px - x1) / (double) (x2 - x1);
                double yFactor = (double) (py - y1) / (double) (y2 - y1);

                APPROX_R[px][py] += rgb1.getR() * (1 - xFactor) * yFactor;
                APPROX_G[px][py] += rgb1.getG() * (1 - xFactor) * yFactor;
                APPROX_B[px][py] += rgb1.getB() * (1 - xFactor) * yFactor;

                APPROX_R[px][py] += rgb2.getR() * xFactor * yFactor;
                APPROX_G[px][py] += rgb2.getG() * xFactor * yFactor;
                APPROX_B[px][py] += rgb2.getB() * xFactor * yFactor;

                APPROX_R[px][py] += rgb3.getR() * (1 - xFactor) * (1 - yFactor);
                APPROX_G[px][py] += rgb3.getG() * (1 - xFactor) * (1 - yFactor);
                APPROX_B[px][py] += rgb3.getB() * (1 - xFactor) * (1 - yFactor);

                APPROX_R[px][py] += rgb4.getR() * xFactor * (1 - yFactor);
                APPROX_G[px][py] += rgb4.getG() * xFactor * (1 - yFactor);
                APPROX_B[px][py] += rgb4.getB() * xFactor * (1 - yFactor);
            }
        }
        final BufferedImage result = new BufferedImage(x2 - x1 + 1, y2 - y1 + 1, BufferedImage.TYPE_INT_RGB);
        for (int px = x1; px <= x2; px++) {
            for (int py = y1; py <= y2; py++) {
                result.setRGB(px, py, new Color(APPROX_R[px][py], APPROX_G[px][py], APPROX_B[px][py]).getRGB());
            }
        }
        saveBMP(result, filename + ".bmp");
    }


    private static void saveBMP(final BufferedImage bi, final String name) {
        try {
            RenderedImage rendImage = bi;
            ImageIO.write(rendImage, "bmp",
                    new File(BitMapApproximationDrawer.class.getClassLoader().getResource(ZAD_12_OUT).getPath(), name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

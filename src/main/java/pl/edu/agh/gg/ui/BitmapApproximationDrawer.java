package pl.edu.agh.gg.ui;

import pl.edu.agh.gg.data.BitmapApproximationRGB;
import pl.edu.agh.gg.data.RgbColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BitmapApproximationDrawer {

    public static final String ZAD_12_OUT = "zad12";

    public static void drawBitmap(int x1, int y1, int x2, int y2, RgbColor rgb1, RgbColor rgb2, RgbColor rgb3,
                                  RgbColor rgb4, String filename) {
        int xSize = x2 - x1 + 1;
        int ySize = y2 - y1 + 1;
        int[][] APPROX_R = new int[xSize][ySize];
        int[][] APPROX_G = new int[xSize][ySize];
        int[][] APPROX_B = new int[xSize][ySize];
        BitmapApproximationRGB bitmapApproximationRGB = new BitmapApproximationRGB(APPROX_R, APPROX_G, APPROX_B);

        updateBitMapColors(x1, y1, x2, y2, rgb1, rgb2, rgb3, rgb4, bitmapApproximationRGB);
        saveImage(x1, y1, x2, y2, bitmapApproximationRGB, filename + ".bmp");
    }


    public static void updateBitMapColors(int x1, int y1, int x2, int y2, RgbColor rgb1, RgbColor rgb2,
                                          RgbColor rgb3, RgbColor rgb4, BitmapApproximationRGB bitmapApproximationRGB) {
        int[][] approx_R = bitmapApproximationRGB.getApprox_r();
        int[][] approx_G = bitmapApproximationRGB.getApprox_g();
        int[][] approx_B = bitmapApproximationRGB.getApprox_b();

        for (int px = x1 + 1; px < x2; px++) {
            for (int py = y1 + 1; py < y2; py++) {
                double xFactor = (double) (px - x1) / (double) (x2 - x1);
                double yFactor = (double) (py - y1) / (double) (y2 - y1);

                approx_R[px][py] += rgb1.getR() * (1 - xFactor) * yFactor;
                approx_G[px][py] += rgb1.getG() * (1 - xFactor) * yFactor;
                approx_B[px][py] += rgb1.getB() * (1 - xFactor) * yFactor;

                approx_R[px][py] += rgb2.getR() * xFactor * yFactor;
                approx_G[px][py] += rgb2.getG() * xFactor * yFactor;
                approx_B[px][py] += rgb2.getB() * xFactor * yFactor;

                approx_R[px][py] += rgb3.getR() * (1 - xFactor) * (1 - yFactor);
                approx_G[px][py] += rgb3.getG() * (1 - xFactor) * (1 - yFactor);
                approx_B[px][py] += rgb3.getB() * (1 - xFactor) * (1 - yFactor);

                approx_R[px][py] += rgb4.getR() * xFactor * (1 - yFactor);
                approx_G[px][py] += rgb4.getG() * xFactor * (1 - yFactor);
                approx_B[px][py] += rgb4.getB() * xFactor * (1 - yFactor);
            }
        }
    }

    public static void saveImage(int x1, int y1, int x2, int y2, BitmapApproximationRGB bitmapApproximationRGB,
                                 final String name) {
        int[][] approx_r = bitmapApproximationRGB.getApprox_r();
        int[][] approx_g = bitmapApproximationRGB.getApprox_g();
        int[][] approx_b = bitmapApproximationRGB.getApprox_b();

        final BufferedImage image = new BufferedImage(x2 - x1 + 1, y2 - y1 + 1, BufferedImage.TYPE_INT_RGB);

        for (int px = x1; px <= x2; px++) {
            for (int py = y1; py <= y2; py++) {
                image.setRGB(px, py, new Color(approx_r[px][py], approx_g[px][py], approx_b[px][py]).getRGB());
            }
        }

        try {
            ImageIO.write(image, "bmp",
                    new File(BitmapApproximationDrawer.class.getClassLoader().getResource(ZAD_12_OUT).getPath(), name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

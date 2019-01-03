package pl.agh.edu.pl.gg.ui;

import org.junit.Test;
import pl.edu.agh.gg.data.RgbColor;
import pl.edu.agh.gg.ui.BitmapApproximationDrawer;
import pl.edu.agh.gg.util.BitmapUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static pl.edu.agh.gg.ui.BitmapApproximationDrawer.ZAD_12_OUT;

public class BitmapApproximationDrawerTest {

    private RgbColor red = new RgbColor(255, 0, 0);
    private RgbColor green = new RgbColor(0, 255, 0);
    private RgbColor white = new RgbColor(255, 255, 255);
    private RgbColor black = new RgbColor(0, 0, 0);
    private RgbColor blue = new RgbColor(0, 0, 255);

    @Test
    public void drawApproximateBitmapGreenAndRed() {
        BitmapApproximationDrawer.drawBitmap(0, 0, 10, 10, red, red, red, green, "greenRed");

        try {
            BufferedImage image = BitmapUtils.loadBitmapFromResource(ZAD_12_OUT + "/greenRed.bmp");
            assertEquals(image.getRGB(image.getMinX(), image.getMinY()),
                    new Color(red.getR(), red.getG(), red.getB()).getRGB());
        } catch (IOException e) {
        }
    }

    @Test
    public void drawApproximateBitmapBlack() {
        BitmapApproximationDrawer.drawBitmap(0, 0, 10, 10, black, black, black, black, "black");

        try {
            BufferedImage image = BitmapUtils.loadBitmapFromResource(ZAD_12_OUT + "/black.bmp");
            assertEquals(image.getRGB(image.getMinX(), image.getMinY()),
                    new Color(black.getR(), black.getG(), black.getB()).getRGB());
        } catch (IOException e) {
        }
    }

    @Test
    public void drawApproximateBitmapWhite() {
        BitmapApproximationDrawer.drawBitmap(0, 0, 10, 10, white, white, white, white, "white");

        try {
            BufferedImage image = BitmapUtils.loadBitmapFromResource(ZAD_12_OUT + "/white.bmp");
            assertEquals(image.getRGB(image.getMinX(), image.getMinY()),
                    new Color(white.getR(), white.getG(), white.getB()).getRGB());
        } catch (IOException e) {
        }
    }

    @Test
    public void drawApproximateBitmapBlueWhite() {
        BitmapApproximationDrawer.drawBitmap(0, 0, 100, 200, blue, blue, white, white, "blueWhite");

        try {
            BufferedImage image = BitmapUtils.loadBitmapFromResource(ZAD_12_OUT + "/blueWhite.bmp");
            assertEquals(image.getRGB(image.getMinX(), image.getMinY()),
                    new Color(white.getR(), white.getG(), white.getB()).getRGB());
        } catch (IOException e) {
        }
    }
}

package pl.edu.agh.gg.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BitmapUtils {

    private BitmapUtils() {
        throw new UnsupportedOperationException();
    }

    public static BufferedImage loadBitmapFromResource(String filename) throws IOException {
        File file = new File(BitmapUtils.class.getClassLoader().getResource(filename).getFile());

        return ImageIO.read(file);
    }

}

package fit.g19202.baksheev.lab2;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Utils {
    public static BufferedImage copyBufferedImage(BufferedImage original) {
        var cm = original.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        var raster = original.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage templateBufferedImage(BufferedImage original) {
        return new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
    }
}

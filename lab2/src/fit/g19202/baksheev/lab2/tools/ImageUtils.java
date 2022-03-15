package fit.g19202.baksheev.lab2.tools;

import java.awt.image.BufferedImage;

public class ImageUtils {
    public static BufferedImage copyBufferedImage(BufferedImage original) {
        var cm = original.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        var raster = original.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage templateBufferedImage(BufferedImage original) {
        return new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
    }

    public static int colorTruncate(int value) {
        if (value > 255) {
            return 255;
        }
        return Math.max(value, 0);
    }

    public static int colorStep(int value, int stepSize) {
        return colorTruncate((int) (Math.round((double) value / stepSize) * stepSize));
    }

    public static int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    public static int getBlue(int rgb) {
        return (rgb >> 0) & 0xFF;
    }

    public static int composeColor(int r, int g, int b) {
        return ((0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                ((b & 0xFF) << 0);
    }
}

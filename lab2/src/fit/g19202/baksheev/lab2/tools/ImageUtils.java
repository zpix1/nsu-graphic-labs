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

    public static BufferedImage applyFilter(BufferedImage image, double[][] filter, double coef, double add) {
        var result = ImageUtils.templateBufferedImage(image);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                double rSum = 0;
                double gSum = 0;
                double bSum = 0;
                for (int dx = 0; dx < filter.length; dx++) {
                    for (int dy = 0; dy < filter.length; dy++) {
                        int nx = x + dx - filter.length / 2;
                        int ny = y + dy - filter.length / 2;
                        if ((nx >= 0 && nx < image.getWidth()) && (ny >= 0 && ny < image.getHeight())) {
                            var rgb = image.getRGB(nx, ny);
                            var r = ImageUtils.getRed(rgb);
                            var g = ImageUtils.getGreen(rgb);
                            var b = ImageUtils.getBlue(rgb);
                            rSum += r * filter[dx][dy];
                            gSum += g * filter[dx][dy];
                            bSum += b * filter[dx][dy];
                        }
                    }
                }
                var newRgb = ImageUtils.composeColor(
                        colorTruncate((int) (rSum * coef + add)),
                        colorTruncate((int) (gSum * coef + add)),
                        colorTruncate((int) (bSum * coef + add))
                );
                result.setRGB(x, y, newRgb);
            }
        }
        return result;
    }
}

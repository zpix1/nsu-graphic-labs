package fit.g19202.baksheev.lab2.tools.utilities;

import fit.g19202.baksheev.lab2.Utils;
import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.Tool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class OrderedDitheringTool extends Tool {
    private static final int matrixDim = 8;
    private static final int[][] matrix = {
            {0, 32, 8, 40, 2, 34, 10, 42},
            {48, 16, 56, 24, 50, 18, 58, 26},
            {12, 44, 4, 36, 14, 46, 6, 38},
            {60, 28, 52, 20, 62, 30, 54, 22},
            {3, 35, 11, 43, 1, 33, 9, 41},
            {51, 19, 59, 27, 49, 17, 57, 25},
            {15, 47, 7, 39, 13, 45, 5, 37},
            {63, 31, 55, 23, 61, 29, 53, 21}
    };

    @Override
    public String getName() {
        return "Ordered dithering";
    }

    @Override
    public File getIconPath() {
        return new File("open.gif");
    }

    @Override
    public String getMenuPath() {
        return "Filter/" + getName();
    }

    @Override
    public BufferedImage apply(Context context) {
        var result = Utils.templateBufferedImage(context.getOriginalImage());
        for (int x = 0; x < context.getOriginalImage().getWidth(); x++) {
            for (int y = 0; y < context.getOriginalImage().getHeight(); y++) {
                var threshold = matrix[x % matrixDim][y % matrixDim];
                var red = 0;
                var green = 0;
                var blue = 0;
                var pixel = new Color(context.getOriginalImage().getRGB(x, y));
                if ((float) pixel.getRed() / 256. * matrixDim * matrixDim > threshold)
                    red = 255;
                if ((float) pixel.getGreen() / 256. * matrixDim * matrixDim > threshold)
                    green = 255;
                if ((float) pixel.getBlue() / 256. * matrixDim * matrixDim > threshold)
                    blue = 255;
                result.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }
        return result;
    }
}

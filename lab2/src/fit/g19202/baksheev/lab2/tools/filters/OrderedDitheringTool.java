package fit.g19202.baksheev.lab2.tools.filters;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;
import fit.g19202.baksheev.lab2.tools.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class OrderedDitheringTool extends Tool {
    private static final int[][][] matrices = {
            null,
            null,
            {
                    {0, 2},
                    {3, 1}
            },
            {
                    {0, 8, 2, 10},
                    {12, 4, 14, 6},
                    {3, 11, 1, 9},
                    {15, 7, 13, 5}
            },
            {
                    {0, 32, 8, 40, 2, 34, 10, 42},
                    {48, 16, 56, 24, 50, 18, 58, 26},
                    {12, 44, 4, 36, 14, 46, 6, 38},
                    {60, 28, 52, 20, 62, 30, 54, 22},
                    {3, 35, 11, 43, 1, 33, 9, 41},
                    {51, 19, 59, 27, 49, 17, 57, 25},
                    {15, 47, 7, 39, 13, 45, 5, 37},
                    {63, 31, 55, 23, 61, 29, 53, 21}
            },
            {
                    {0, 192, 48, 240, 12, 204, 60, 252, 3, 195, 51, 243, 15, 207, 63, 255},
                    {128, 64, 176, 112, 140, 76, 188, 124, 131, 67, 179, 115, 143, 79, 191, 127},
                    {32, 224, 16, 208, 44, 236, 28, 220, 35, 227, 19, 211, 47, 239, 31, 223},
                    {160, 96, 144, 80, 172, 108, 156, 92, 163, 99, 147, 83, 175, 111, 159, 95},
                    {8, 200, 56, 248, 4, 196, 52, 244, 11, 203, 59, 251, 7, 199, 55, 247},
                    {136, 72, 184, 120, 132, 68, 180, 116, 139, 75, 187, 123, 135, 71, 183, 119},
                    {40, 232, 24, 216, 36, 228, 20, 212, 43, 235, 27, 219, 39, 231, 23, 215},
                    {168, 104, 152, 88, 164, 100, 148, 84, 171, 107, 155, 91, 167, 103, 151, 87},
                    {2, 194, 50, 242, 14, 206, 62, 254, 1, 193, 49, 241, 13, 205, 61, 253},
                    {130, 66, 178, 114, 142, 78, 190, 126, 129, 65, 177, 113, 141, 77, 189, 125},
                    {34, 226, 18, 210, 46, 238, 30, 222, 33, 225, 17, 209, 45, 237, 29, 221},
                    {162, 98, 146, 82, 174, 110, 158, 94, 161, 97, 145, 81, 173, 109, 157, 93},
                    {10, 202, 58, 250, 6, 198, 54, 246, 9, 201, 57, 249, 5, 197, 53, 245},
                    {138, 74, 186, 122, 134, 70, 182, 118, 137, 73, 185, 121, 133, 69, 181, 117},
                    {42, 234, 26, 218, 38, 230, 22, 214, 41, 233, 25, 217, 37, 229, 21, 213},
                    {170, 106, 154, 90, 166, 102, 150, 86, 169, 105, 153, 89, 165, 101, 149, 85}
            }
    };
    private int redColorSpaceSpread = 16;
    private int greenColorSpaceSpread = 16;
    private int blueColorSpaceSpread = 16;

    @Override
    public String getName() {
        return "Ordered dithering";
    }

    @Override
    public File getIconPath() {
        return new File("tools/dither.png");
    }

    @Override
    public String getMenuPath() {
        return "Filters/" + getName();
    }

    @Override
    public boolean showSettingsDialog(Context context) {
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        AtomicReference<Integer> tempRedColorSpaceSpread = new AtomicReference<>(redColorSpaceSpread);
        AtomicReference<Integer> tempGreenColorSpaceSpread = new AtomicReference<>(greenColorSpaceSpread);
        AtomicReference<Integer> tempBlueColorSpaceSpread = new AtomicReference<>(blueColorSpaceSpread);

        panel.add(new JLabel("Red spread factor"));
        panel.add(
                UIUtils.getSliderSpinnerPair(
                        tempRedColorSpaceSpread.get(),
                        2,
                        128,
                        16,
                        16,
                        UIUtils.getLabels(new int[]{2, 64, 128}),
                        tempRedColorSpaceSpread::set,
                        false
                )
        );

        panel.add(new JLabel("Green spread factor"));
        panel.add(
                UIUtils.getSliderSpinnerPair(
                        tempGreenColorSpaceSpread.get(),
                        2,
                        128,
                        16,
                        16,
                        UIUtils.getLabels(new int[]{2, 64, 128}),
                        tempGreenColorSpaceSpread::set,
                        false
                )
        );

        panel.add(new JLabel("Blue spread factor"));
        panel.add(
                UIUtils.getSliderSpinnerPair(
                        tempBlueColorSpaceSpread.get(),
                        2,
                        128,
                        16,
                        16,
                        UIUtils.getLabels(new int[]{2, 64, 128}),
                        tempBlueColorSpaceSpread::set,
                        false
                )
        );
        int result = JOptionPane.showOptionDialog(context.getMainFrame(), panel, "Configure " + getName(),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, new Object[]{"Apply", "Cancel"}, null);

        if (result == JOptionPane.YES_OPTION) {
            redColorSpaceSpread = tempRedColorSpaceSpread.get();
            greenColorSpaceSpread = tempGreenColorSpaceSpread.get();
            blueColorSpaceSpread = tempBlueColorSpaceSpread.get();
            return true;
        }

        return false;
    }

    private int[][] getMatrix(int colorSpaceSpread) {
        int[][] matrix = null;
        for (int[][] m : matrices) {
            if (m == null) {
                continue;
            }

            if (m.length * m.length >= (256 / colorSpaceSpread)) {
                matrix = m;
                break;
            }
        }
        if (matrix == null) {
            throw new RuntimeException("Can't find a good matrix");
        }
        return matrix;
    }

    private int getSteppedColor(int value, int[][] matrix, int x, int y, int spread) {
        var rv = 256 / (spread - 1);
        var matrixDim = matrix.length;
        var threshold = (int) (rv * (((double) matrix[x % matrixDim][y % matrixDim]) / (matrixDim * matrixDim) - 0.5));
        return ImageUtils.colorStep(value + threshold, rv);
    }

    @Override
    public BufferedImage apply(Context context) {
        var result = ImageUtils.templateBufferedImage(context.getOriginalImage());

        var redMatrix = getMatrix(redColorSpaceSpread);
        System.out.println("Chosen " + redMatrix.length + " matrix for red");
        var greenMatrix = getMatrix(greenColorSpaceSpread);
        System.out.println("Chosen " + greenMatrix.length + " matrix for green");
        var blueMatrix = getMatrix(blueColorSpaceSpread);
        System.out.println("Chosen " + blueMatrix.length + " matrix for blue");

        for (int x = 0; x < context.getOriginalImage().getWidth(); x++) {
            for (int y = 0; y < context.getOriginalImage().getHeight(); y++) {
                var rgb = context.getOriginalImage().getRGB(x, y);
                var r = ImageUtils.getRed(rgb);
                var g = ImageUtils.getGreen(rgb);
                var b = ImageUtils.getBlue(rgb);

                var red = getSteppedColor(r, redMatrix, x, y, redColorSpaceSpread);
                var green = getSteppedColor(g, greenMatrix, x, y, greenColorSpaceSpread);
                var blue = getSteppedColor(b, blueMatrix, x, y, blueColorSpaceSpread);

                var newRgb = ImageUtils.composeColor(red, green, blue);
                result.setRGB(x, y, newRgb);
            }
        }
        return result;
    }
}

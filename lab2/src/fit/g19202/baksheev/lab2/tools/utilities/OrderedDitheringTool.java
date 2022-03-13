package fit.g19202.baksheev.lab2.tools.utilities;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;
import fit.g19202.baksheev.lab2.tools.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

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
    private int colorSpaceSpread = 2;

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
    public boolean showSettingsDialog(Context context) {
        var panel = new JPanel();
        AtomicReference<Integer> tempColorSpaceSpread = new AtomicReference<>(colorSpaceSpread);
        panel.add(new JLabel("Spread factor"));
        panel.add(
                UIUtils.getSliderSpinnerPair(
                        tempColorSpaceSpread.get(),
                        2,
                        128,
                        16,
                        16,
                        UIUtils.getLabels(new int[]{2, 64, 128}),
                        tempColorSpaceSpread::set
                )
        );
        int result = JOptionPane.showOptionDialog(context.getMainFrame(), panel, "Configure " + getName(),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, new Object[]{"Apply", "Cancel"}, null);

        if (result == JOptionPane.YES_OPTION) {
            colorSpaceSpread = tempColorSpaceSpread.get();
            return true;
        }

        return false;
    }

    @Override
    public BufferedImage apply(Context context) {
        var result = ImageUtils.templateBufferedImage(context.getOriginalImage());
        var r = 255 / colorSpaceSpread;
        for (int x = 0; x < context.getOriginalImage().getWidth(); x++) {
            for (int y = 0; y < context.getOriginalImage().getHeight(); y++) {
                var pixel = new Color(context.getOriginalImage().getRGB(x, y));
                var threshold = (int) (r * ((double) matrix[x % matrixDim][y % matrixDim] / 64 - 0.5));
                var red = ImageUtils.colorStep(pixel.getRed() + threshold, r);
                var green = ImageUtils.colorStep(pixel.getGreen() + threshold, r);
                var blue = ImageUtils.colorStep(pixel.getBlue() + threshold, r);
                result.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }
        return result;
    }
}

package fit.g19202.baksheev.lab2.tools.filters;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;
import fit.g19202.baksheev.lab2.tools.UIUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class FSDithering extends Tool {
    private int redColorSpaceSpread = 16;
    private int greenColorSpaceSpread = 16;
    private int blueColorSpaceSpread = 16;

    @Override
    public String getName() {
        return "FS dithering";
    }

    @Override
    public File getIconPath() {
        return new File("tools/fs.png");
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

    private int getSteppedColor(int value, int[][] matrix, int x, int y, int spread) {
        var rv = 256 / (spread - 1);
        var matrixDim = matrix.length;
        var threshold = (int) (rv * (((double) matrix[x % matrixDim][y % matrixDim] + 1) / (matrixDim * matrixDim) - 0.5));
        return ImageUtils.colorStep(value + threshold, rv);
    }

    private void errorDiff(BufferedImage original, BufferedImage result, int x, int y, int rError, int gError, int bError, double k) {
        int rgb = original.getRGB(x, y);
        result.setRGB(x, y, ImageUtils.composeColor(
                ImageUtils.getRed(rgb) + ImageUtils.colorTruncate((int) (k * rError)),
                ImageUtils.getGreen(rgb) + ImageUtils.colorTruncate((int) (k * gError)),
                ImageUtils.getBlue(rgb) + ImageUtils.colorTruncate((int) (k * bError))
        ));
    }

    @Override
    public BufferedImage apply(Context context) {
        var result = ImageUtils.templateBufferedImage(context.getOriginalImage());
        var original = context.getOriginalImage();
        System.out.println(redColorSpaceSpread);
        System.out.println(ImageUtils.findClosestPaletteColor(10, redColorSpaceSpread));
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                var rgb = original.getRGB(x, y);
                var r = ImageUtils.getRed(rgb);
                var rNew = ImageUtils.findClosestPaletteColor(r, redColorSpaceSpread);
                var rError = r - rNew;
                var g = ImageUtils.getGreen(rgb);
                var gNew = ImageUtils.findClosestPaletteColor(g, greenColorSpaceSpread);
                var gError = g - gNew;
                var b = ImageUtils.getBlue(rgb);
                var bNew = ImageUtils.findClosestPaletteColor(b, blueColorSpaceSpread);
                var bError = b - bNew;

                result.setRGB(x, y, ImageUtils.composeColor(rNew, gNew, bNew));

                if (x + 1 < original.getWidth()) {
                    errorDiff(original, result, x + 1, y, rError, gError, bError, 7. / 16);
                }
                if (x - 1 >= 0 && y + 1 < original.getHeight()) {
                    errorDiff(original, result, x - 1, y + 1, rError, gError, bError, 3. / 16);
                }
                if (y + 1 < original.getHeight()) {
                    errorDiff(original, result, x, y + 1, rError, gError, bError, 5. / 16);
                }
                if (x + 1 < original.getWidth() && y + 1 < original.getHeight()) {
                    errorDiff(original, result, x + 1, y + 1, rError, gError, bError, 1. / 16);
                }
            }
        }
        return result;
    }
}

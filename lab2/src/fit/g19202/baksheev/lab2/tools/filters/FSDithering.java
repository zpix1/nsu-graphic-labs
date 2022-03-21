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
        int rgb = result.getRGB(x, y);
        result.setRGB(x, y, ImageUtils.composeColor(
                ImageUtils.colorTruncate(ImageUtils.getRed(rgb) + (int) (k * rError)),
                ImageUtils.colorTruncate(ImageUtils.getGreen(rgb) + (int) (k * gError)),
                ImageUtils.colorTruncate(ImageUtils.getBlue(rgb) + (int) (k * bError))
        ));
    }

    private int getSpread(int idx) {
        if (idx == 0) return redColorSpaceSpread;
        if (idx == 1) return greenColorSpaceSpread;
        if (idx == 2) return blueColorSpaceSpread;
        return -1;
    }

    @Override
    public BufferedImage apply(Context context) {
        var original = context.getOriginalImage();
        var img = ImageUtils.templateBufferedImage(context.getOriginalImage());
        var colors = new double[img.getWidth()][img.getHeight()][3];

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                var rgb = original.getRGB(x, y);
                var setRgb = 0;
                for (int i = 0; i < 3; i++) {
                    var bValue = ImageUtils.getColor(rgb, i);
                    var currentColor = bValue + colors[x][y][i];
                    var newColor = ImageUtils.findClosestPaletteColor((int) currentColor, getSpread(i));
                    setRgb = ImageUtils.setColor(setRgb, i, newColor);
                    var e = currentColor - newColor;
                    if (x + 1 < original.getWidth()) {
                        colors[x+1][y][i] += 7. * e / 16;
                    }
                    if (x - 1 >= 0 && y + 1 < original.getHeight()) {
                        colors[x-1][y+1][i] += 3. * e / 16;
                    }
                    if (y + 1 < original.getHeight()) {
                        colors[x][y+1][i] += 5. * e / 16;
                    }
                    if (x + 1 < original.getWidth() && y + 1 < original.getHeight()) {
                        colors[x+1][y+1][i] += 1. * e / 16;
                    }
                }
                img.setRGB(x, y, setRgb);
            }
        }
        return img;
    }
}

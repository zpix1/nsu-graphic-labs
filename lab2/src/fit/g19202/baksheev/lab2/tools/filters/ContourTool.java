package fit.g19202.baksheev.lab2.tools.filters;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;
import fit.g19202.baksheev.lab2.tools.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class ContourTool extends Tool {
    private static final String[] options = {"Sobel", "Roberts"};

    private int mode;
    private int threshold = 70;

    public static final double[][] sobelFilterX = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };
    public static final double[][] sobelFilterY = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
    };

    public static final double[][] robertsFilter = {
            {1, 0},
            {0, -1}
    };

    @Override
    public String getName() {
        return "Contour filter";
    }

    @Override
    public File getIconPath() {
        return new File("tools/contour.png");
    }

    @Override
    public String getMenuPath() {
        return "Filters/" + getName();
    }

    @Override
    public boolean showSettingsDialog(Context context) {
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        final int[] tempMode = {mode};

        panel.add(new JLabel("Mode"));
        var optionList = new JComboBox(options);
        optionList.setSelectedIndex(mode);
        optionList.addActionListener(e -> {
            var box = (JComboBox) e.getSource();
            var index = box.getSelectedIndex();
            tempMode[0] = index;
        });
        panel.add(optionList);


        AtomicReference<Integer> tempThreshold = new AtomicReference<>(threshold);
        panel.add(new JLabel("Threshold"));
        panel.add(
                UIUtils.getSliderSpinnerPair(
                        tempThreshold.get(),
                        0,
                        255,
                        16,
                        16,
                        UIUtils.getLabels(new int[]{2, 64, 128, 255}),
                        tempThreshold::set,
                        false
                )
        );

        int result = JOptionPane.showOptionDialog(context.getMainFrame(), panel, "Configure " + getName(),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, new Object[]{"Apply", "Cancel"}, null);

        if (result == JOptionPane.YES_OPTION) {
            mode = tempMode[0];
            threshold = tempThreshold.get();

            return true;
        }

        return false;
    }

    private int applyOperator(int value) {
        if (value > threshold) {
            return 255;
        }
        return 0;
    }

    @Override
    public BufferedImage apply(Context context) {
        var image = context.getOriginalImage();
        var result = ImageUtils.templateBufferedImage(image);
        if (mode == 0) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    double rSum1 = 0;
                    double gSum1 = 0;
                    double bSum1 = 0;
                    for (int dx = 0; dx < sobelFilterX.length; dx++) {
                        for (int dy = 0; dy < sobelFilterX.length; dy++) {
                            int nx = x + dx - sobelFilterX.length / 2;
                            int ny = y + dy - sobelFilterX.length / 2;
                            if ((nx >= 0 && nx < image.getWidth()) && (ny >= 0 && ny < image.getHeight())) {
                                var rgb = image.getRGB(nx, ny);
                                var r = ImageUtils.getRed(rgb);
                                var g = ImageUtils.getGreen(rgb);
                                var b = ImageUtils.getBlue(rgb);
                                rSum1 += r * sobelFilterX[dx][dy];
                                gSum1 += g * sobelFilterX[dx][dy];
                                bSum1 += b * sobelFilterX[dx][dy];
                            }
                        }
                    }
                    double rSum2 = 0;
                    double gSum2 = 0;
                    double bSum2 = 0;
                    for (int dx = 0; dx < sobelFilterY.length; dx++) {
                        for (int dy = 0; dy < sobelFilterY.length; dy++) {
                            int nx = x + dx - sobelFilterY.length / 2;
                            int ny = y + dy - sobelFilterY.length / 2;
                            if ((nx >= 0 && nx < image.getWidth()) && (ny >= 0 && ny < image.getHeight())) {
                                var rgb = image.getRGB(nx, ny);
                                var r = ImageUtils.getRed(rgb);
                                var g = ImageUtils.getGreen(rgb);
                                var b = ImageUtils.getBlue(rgb);
                                rSum2 += r * sobelFilterY[dx][dy];
                                gSum2 += g * sobelFilterY[dx][dy];
                                bSum2 += b * sobelFilterY[dx][dy];
                            }
                        }
                    }
                    var newRgb = ImageUtils.composeColor(
                            applyOperator((int) Math.sqrt(rSum1 * rSum1 + rSum2 * rSum2)),
                            applyOperator((int) Math.sqrt(gSum1 * gSum1 + gSum2 * gSum2)),
                            applyOperator((int) Math.sqrt(bSum1 * bSum1 + bSum2 * bSum2))
                    );
                    result.setRGB(x, y, newRgb);
                }
            }
        } else {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    double rSum1 = 0;
                    double gSum1 = 0;
                    double bSum1 = 0;
                    for (int dx = 0; dx < robertsFilter.length; dx++) {
                        for (int dy = 0; dy < robertsFilter.length; dy++) {
                            int nx = x + dx - robertsFilter.length / 2;
                            int ny = y + dy - robertsFilter.length / 2;
                            if ((nx >= 0 && nx < image.getWidth()) && (ny >= 0 && ny < image.getHeight())) {
                                var rgb = image.getRGB(nx, ny);
                                var r = ImageUtils.getRed(rgb);
                                var g = ImageUtils.getGreen(rgb);
                                var b = ImageUtils.getBlue(rgb);
                                rSum1 += r * robertsFilter[dx][dy];
                                gSum1 += g * robertsFilter[dx][dy];
                                bSum1 += b * robertsFilter[dx][dy];
                            }
                        }
                    }
                    var newRgb = ImageUtils.composeColor(
                            applyOperator((int) rSum1),
                            applyOperator((int) gSum1),
                            applyOperator((int) bSum1)
                    );
                    result.setRGB(x, y, newRgb);
                }
            }
        }
        return result;
    }
}

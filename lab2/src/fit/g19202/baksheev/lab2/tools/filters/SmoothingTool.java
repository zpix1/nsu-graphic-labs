package fit.g19202.baksheev.lab2.tools.filters;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;
import fit.g19202.baksheev.lab2.tools.UIUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class SmoothingTool extends Tool {
    private int windowSize = 3;

    private static final double[][][] matrices = {
            null,
            null,
            null,
            {
                    {1, 2, 1},
                    {2, 4, 2},
                    {1, 2, 1}
            },
            null,
            {
                    {1, 2, 3, 2, 1},
                    {2, 4, 5, 4, 2},
                    {3, 5, 6, 5, 3},
                    {2, 4, 5, 4, 2},
                    {1, 2, 3, 2, 1}
            },
            null
    };

    private static final Double[] coefs = {
            null,
            null,
            null,
            1. / 16,
            null,
            1. / 74
    };

    @Override
    public String getName() {
        return "Smooth filter";
    }

    @Override
    public File getIconPath() {
        return new File("tools/smooth.png");
    }

    @Override
    public String getMenuPath() {
        return "Filters/" + getName();
    }

    @Override
    public boolean showSettingsDialog(Context context) {
        var panel = new JPanel();
        AtomicReference<Integer> tempWindowSize = new AtomicReference<>(windowSize);

        panel.add(new JLabel("Window size"));
        panel.add(
                UIUtils.getSliderSpinnerPair(
                        tempWindowSize.get(),
                        3,
                        11,
                        2,
                        2,
                        UIUtils.getLabels(new int[]{3, 5, 7, 9, 11}),
                        tempWindowSize::set,
                        true
                )
        );

        boolean result = UIUtils.showDialog(context.getMainFrame(), panel, getName());

        if (result) {
            windowSize = tempWindowSize.get();
            return true;
        }

        return false;
    }

    @Override
    public BufferedImage apply(Context context) {
        if (windowSize < 7) {
            var matrix = matrices[windowSize];
            var coef = coefs[windowSize];
            return ImageUtils.applyFilter(context.getOriginalImage(), matrix, coef, 0);
        } else {
            var matrix = ImageUtils.generateMatrix(windowSize, 1);
            var coef = 1. / windowSize / windowSize;
            return ImageUtils.applyFilter(context.getOriginalImage(), matrix, coef, 0);
        }
    }
}

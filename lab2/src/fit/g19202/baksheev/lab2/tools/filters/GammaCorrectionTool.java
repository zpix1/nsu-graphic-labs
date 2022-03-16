package fit.g19202.baksheev.lab2.tools.filters;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;
import fit.g19202.baksheev.lab2.tools.UIUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class GammaCorrectionTool extends Tool {
    private double gamma = 1;

    @Override
    public String getName() {
        return "Gamma Correction";
    }

    @Override
    public File getIconPath() {
        return new File("tools/gammacor.png");
    }

    @Override
    public String getMenuPath() {
        return "Tools/" + getName();
    }

    @Override
    public boolean showSettingsDialog(Context context) {
        var panel = new JPanel();
        AtomicReference<Double> tempGamma = new AtomicReference<>(gamma);

        panel.add(new JLabel("Gamma"));
        panel.add(
                UIUtils.getSliderSpinnerPair(
                        tempGamma.get(),
                        0.1,
                        10,
                        0.1,
                        tempGamma::set
                )
        );

        boolean result = UIUtils.showDialog(context.getMainFrame(), panel, getName());

        if (result) {
            gamma = tempGamma.get();
            return true;
        }

        return false;
    }

    @Override
    public BufferedImage apply(Context context) {
        var result = ImageUtils.templateBufferedImage(context.getOriginalImage());
        for (int x = 0; x < context.getOriginalImage().getWidth(); x++) {
            for (int y = 0; y < context.getOriginalImage().getHeight(); y++) {
                var rgb = context.getOriginalImage().getRGB(x, y);
                var r = ImageUtils.getRed(rgb);
                var g = ImageUtils.getGreen(rgb);
                var b = ImageUtils.getBlue(rgb);
                int red = (int) (Math.pow((double) r / 255, gamma) * 255);
                int green = (int) (Math.pow((double) g / 255, gamma) * 255);
                int blue = (int) (Math.pow((double) b / 255, gamma) * 255);
                var newRgb = ImageUtils.composeColor(red, green, blue);
                result.setRGB(x, y, newRgb);
            }
        }
        return result;
    }
}

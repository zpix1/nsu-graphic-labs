package fit.g19202.baksheev.lab2.tools.utilities;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class NegativeFilter extends Tool {
    @Override
    public String getName() {
        return "Negative filter";
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
        var result = ImageUtils.templateBufferedImage(context.getOriginalImage());
        for (int x = 0; x < context.getOriginalImage().getWidth(); x++) {
            for (int y = 0; y < context.getOriginalImage().getHeight(); y++) {
                var rgb = context.getOriginalImage().getRGB(x, y);
                var r = ImageUtils.getRed(rgb);
                var g = ImageUtils.getGreen(rgb);
                var b = ImageUtils.getBlue(rgb);
                result.setRGB(x, y, ImageUtils.composeColor(255 - r, 255 - g, 255 - b));
            }
        }
        return result;
    }
}

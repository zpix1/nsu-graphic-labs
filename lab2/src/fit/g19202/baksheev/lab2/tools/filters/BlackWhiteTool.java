package fit.g19202.baksheev.lab2.tools.filters;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;

import java.awt.image.BufferedImage;
import java.io.File;

public class BlackWhiteTool extends Tool {
    @Override
    public String getName() {
        return "Black & White filter";
    }

    @Override
    public File getIconPath() {
        return new File("tools/bw.png");
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
                int Y = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                result.setRGB(x, y, ImageUtils.composeColor(Y, Y, Y));
            }
        }
        return result;
    }
}

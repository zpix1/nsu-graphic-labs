package fit.g19202.baksheev.lab2.tools.filters;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;

import java.awt.image.BufferedImage;
import java.io.File;

public class SepiaTool extends Tool {
    @Override
    public String getName() {
        return "Sepia Filter";
    }

    @Override
    public File getIconPath() {
        return new File("tools/sepia.png");
    }

    @Override
    public String getMenuPath() {
        return "Filters/" + getName();
    }

    @Override
    public BufferedImage apply(Context context) {
        var result = ImageUtils.templateBufferedImage(context.getOriginalImage());
        for (int x = 0; x < context.getOriginalImage().getWidth(); x++) {
            for (int y = 0; y < context.getOriginalImage().getHeight(); y++) {
                var rgb = context.getOriginalImage().getRGB(x, y);
                var R = ImageUtils.getRed(rgb);
                var G = ImageUtils.getGreen(rgb);
                var B = ImageUtils.getBlue(rgb);
                var newR = ImageUtils.colorTruncate((int) (0.393 * R + 0.769 * G + 0.189 * B));
                var newG = ImageUtils.colorTruncate((int) (0.349 * R + 0.686 * G + 0.168 * B));
                var newB = ImageUtils.colorTruncate((int) (0.272 * R + 0.534 * G + 0.131 * B));
                result.setRGB(x, y, ImageUtils.composeColor(newR, newG, newB));
            }
        }
        return result;
    }
}

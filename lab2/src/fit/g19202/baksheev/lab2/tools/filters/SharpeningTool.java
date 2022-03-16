package fit.g19202.baksheev.lab2.tools.filters;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;

import java.awt.image.BufferedImage;
import java.io.File;

public class SharpeningTool extends Tool {
    private final double[][] filter = {
            {0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}
    };

    @Override
    public String getName() {
        return "Sharpening Filter";
    }

    @Override
    public File getIconPath() {
        return new File("tools/sharpen.png");
    }

    @Override
    public String getMenuPath() {
        return "Filters/" + getName();
    }

    @Override
    public BufferedImage apply(Context context) {
        return ImageUtils.applyFilter(context.getOriginalImage(), filter, 1, 0);
    }
}

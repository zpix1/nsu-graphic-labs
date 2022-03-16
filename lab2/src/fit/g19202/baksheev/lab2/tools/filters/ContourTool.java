package fit.g19202.baksheev.lab2.tools.filters;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;

import java.awt.image.BufferedImage;
import java.io.File;

public class ContourTool extends Tool {
    public static final double[][] filter = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
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
    public BufferedImage apply(Context context) {
        return ImageUtils.applyFilter(context.getOriginalImage(), filter, 1. / 4, 0);
    }
}

package fit.g19202.baksheev.lab2.tools.utilities;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.Tool;

import java.awt.image.BufferedImage;
import java.io.File;

public class FitTool extends Tool {
    private boolean fit = false;

    @Override
    public String getName() {
        return "Fit";
    }

    @Override
    public File getIconPath() {
        return new File("tools/fit.png");
    }

    @Override
    public String getMenuPath() {
        return "Filter/" + getName();
    }

    @Override
    public BufferedImage apply(Context context) {
        fit = !fit;

        return null;
    }
}

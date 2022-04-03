package fit.g19202.baksheev.lab2.tools.utilities;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.Tool;

import java.awt.image.BufferedImage;
import java.io.File;

public class ExitTool extends Tool {
    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public File getIconPath() {
        return new File("exit.gif");
    }

    @Override
    public String getMenuPath() {
        return "File/Exit";
    }

    @Override
    public BufferedImage apply(Context context) {
        System.exit(0);
        return null;
    }

    @Override
    public String getTooltip() {
        return "Exit application";
    }
}

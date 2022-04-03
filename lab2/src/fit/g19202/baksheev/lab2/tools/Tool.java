package fit.g19202.baksheev.lab2.tools;

import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Tool {
    public abstract String getName();
    public abstract File getIconPath();
    public abstract String getMenuPath();

    public boolean showSettingsDialog(Context context) {
        return true;
    }

    public abstract BufferedImage apply(Context context);

    public String getTooltip() {
        return "Use " + getName().toLowerCase();
    }
}

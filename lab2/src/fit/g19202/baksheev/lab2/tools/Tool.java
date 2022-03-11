package fit.g19202.baksheev.lab2.tools;

import java.awt.image.BufferedImage;
import java.io.File;

public interface Tool {
    String getName();
    File getIconPath();
    String getMenuPath();

    void showSettingsDialog(Context context);
    BufferedImage apply(Context context);

    String getTooltip();
}

package fit.g19202.baksheev.lab5.tools;

import java.io.File;

public abstract class Tool {
    public abstract String getName();

    public abstract File getIconPath();

    public abstract String getMenuPath();

    public abstract void execute(Context context);

    public String getTooltip() {
        return "Use " + getName().toLowerCase();
    }
}

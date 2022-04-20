package fit.g19202.baksheev.lab5.tools.utilities;

import fit.g19202.baksheev.lab5.tools.Context;
import fit.g19202.baksheev.lab5.tools.Tool;

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
    public void execute(Context context) {
        System.exit(0);
    }

    @Override
    public String getTooltip() {
        return "Exit application";
    }
}

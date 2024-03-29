package fit.g19202.baksheev.lab5.tools.utilities;

import fit.g19202.baksheev.lab5.tools.Context;
import fit.g19202.baksheev.lab5.tools.Tool;

import java.io.File;

public class ResetTool extends Tool {
    @Override
    public String getName() {
        return "Reset viewport";
    }

    @Override
    public File getIconPath() {
        return new File("reset.png");
    }

    @Override
    public String getMenuPath() {
        return "Scene/Reset viewport";
    }

    @Override
    public void execute(Context context) {
        context.getScene().init();
    }
}

package fit.g19202.baksheev.lab5.tools.utilities;

import fit.g19202.baksheev.lab5.tools.Context;
import fit.g19202.baksheev.lab5.tools.Tool;

import java.io.File;

public class RenderTool extends Tool {
    @Override
    public String getName() {
        return "Render";
    }

    @Override
    public File getIconPath() {
        return new File("render.png");
    }

    @Override
    public String getMenuPath() {
        return "Scene/Render";
    }

    @Override
    public void execute(Context context) {
        context.getScene().showRenderDialog(context.getMainFrame());
    }
}

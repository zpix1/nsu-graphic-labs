package fit.g19202.baksheev.lab5.tools.utilities;

import fit.g19202.baksheev.lab5.tools.Context;
import fit.g19202.baksheev.lab5.tools.Tool;
import fit.g19202.baksheev.lab5.tools.scene.config.SceneConfig;

import java.io.File;

public class ResetTool extends Tool {
    private static final SceneConfig defaultParameters = SceneConfig.getEmptyInstance();

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
        context.getSceneParameters().setFov(defaultParameters.getFov());
        context.getSceneParameters().setThetaX(defaultParameters.getThetaX());
        context.getSceneParameters().setThetaY(defaultParameters.getThetaY());
        context.getSceneParameters().setThetaZ(defaultParameters.getThetaZ());
        context.getScene().updateParameters(context.getSceneParameters());
    }
}

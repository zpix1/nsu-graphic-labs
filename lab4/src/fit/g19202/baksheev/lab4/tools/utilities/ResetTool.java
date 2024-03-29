package fit.g19202.baksheev.lab4.tools.utilities;

import fit.g19202.baksheev.lab4.tools.Context;
import fit.g19202.baksheev.lab4.tools.Tool;
import fit.g19202.baksheev.lab4.tools.scene.SceneParameters;

import java.io.File;

public class ResetTool extends Tool {
    private static final SceneParameters defaultParameters = SceneParameters.getEmptyInstance();

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

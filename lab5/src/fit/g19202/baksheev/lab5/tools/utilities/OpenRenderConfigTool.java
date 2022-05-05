package fit.g19202.baksheev.lab5.tools.utilities;

import fit.g19202.baksheev.lab5.lib.UIUtils;
import fit.g19202.baksheev.lab5.tools.Context;
import fit.g19202.baksheev.lab5.tools.Tool;
import fit.g19202.baksheev.lab5.tools.scene.config.RenderConfig;
import fit.g19202.baksheev.lab5.tools.scene.config.SceneConfig;

import javax.swing.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static cg.FileUtils.getOpenFileName;

public class OpenRenderConfigTool extends Tool {
    @Override
    public String getName() {
        return "Open render config";
    }

    @Override
    public File getIconPath() {
        return new File("open.gif");
    }

    @Override
    public String getMenuPath() {
        return "File/Open Render Config";
    }

    @Override
    public void execute(Context context) {
        var renderConfigFile = getOpenFileName(context.getMainFrame(), new String[]{"render"}, "Render config file");
        if (renderConfigFile == null) {
            System.err.println("File not selected");
            return;
        }
        try {
            var sceneConfigString = Files.readString(renderConfigFile.getFile().toPath(), StandardCharsets.UTF_8);
            var renderConfig = new RenderConfig();
            renderConfig.loadConfigFromString(sceneConfigString);
            context.getScene().setRenderConfig(renderConfig);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(context.getMainFrame(), "Selected file " + renderConfigFile.getFile().getName() + " can not be opened.");
            ex.printStackTrace();
        }
    }

    @Override
    public String getTooltip() {
        return "Open .render file";
    }
}

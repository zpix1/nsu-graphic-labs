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

public class OpenSceneTool extends Tool {
    @Override
    public String getName() {
        return "Open Scene";
    }

    @Override
    public File getIconPath() {
        return new File("open.gif");
    }

    @Override
    public String getMenuPath() {
        return "File/Open Scene";
    }


    @Override
    public void execute(Context context) {
        var sceneConfigFile = getOpenFileName(context.getMainFrame(), new String[]{"scene"}, "Scene config file");
        if (sceneConfigFile == null) {
            System.err.println("File not selected");
            return;
        }
        try {
            var sceneConfigString = Files.readString(sceneConfigFile.getFile().toPath(), StandardCharsets.UTF_8);
            var sceneConfig = new SceneConfig();
            sceneConfig.loadConfigFromString(sceneConfigString);
            context.getScene().setSceneConfig(sceneConfig);

            var renderConfigFile = new File(UIUtils.removeSuffixIfExists(sceneConfigFile.getFile().getAbsolutePath(), sceneConfigFile.getExtension()) + "render");
            if (renderConfigFile.exists()) {
                System.out.println("Also loaded render config: " + renderConfigFile.getName());
                var renderConfigString = Files.readString(renderConfigFile.toPath(), StandardCharsets.UTF_8);
                var renderConfig = new RenderConfig();
                renderConfig.loadConfigFromString(renderConfigString);
                context.getScene().setRenderConfig(renderConfig);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(context.getMainFrame(), "Selected file " + sceneConfigFile.getFile().getName() + " can not be opened.");
            ex.printStackTrace();
        }
    }

    @Override
    public String getTooltip() {
        return "Open .scene file";
    }
}

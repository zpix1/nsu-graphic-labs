package fit.g19202.baksheev.lab5;

import cg.FileUtils;
import cg.MainFrame;
import fit.g19202.baksheev.lab5.lib.UIUtils;
import fit.g19202.baksheev.lab5.tools.Context;
import fit.g19202.baksheev.lab5.tools.Tool;
import fit.g19202.baksheev.lab5.tools.ToolManager;
import fit.g19202.baksheev.lab5.tools.scene.Scene;
import fit.g19202.baksheev.lab5.tools.scene.config.RenderConfig;
import fit.g19202.baksheev.lab5.tools.scene.config.SceneConfig;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * Main window class
 *
 * @author Ivan Baksheev
 */
public class InitMainWindow extends MainFrame {
    private final static String TITLE = "WireFrame";
    private final Context context;
    private final Scene scene;

    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super(640, 480, TITLE);

        var toolManager = ToolManager.getInstance();

        setMinimumSize(new Dimension(640, 480));

        scene = new Scene();
        add(scene);
        context = new Context(this, scene);

        try {
            var sceneConfigFile = new FileUtils.FileWithExtension(new File("./out/production/scene1.scene"), "scene");
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
            ex.printStackTrace();
        }

        try {
            for (var tool : toolManager.getToolList()) {
                Arrays.stream(tool.getMenuPath().split("/")).reduce((path, item) -> {
                    if (getMenuElement(path) == null)
                        addSubMenu(path, 0);
                    return path + "/" + item;
                });
                addToolMenu(tool);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addToolMenu(Tool tool) {
        addMenuItem(tool.getMenuPath(), tool.getTooltip(), 0, tool.getIconPath(), event -> applyTool(tool));
        addToolBarButton(tool.getMenuPath());
    }

    private void applyTool(Tool tool) {
        try {
            tool.execute(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Application main entry point
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InitMainWindow mainFrame = new InitMainWindow();
            mainFrame.setVisible(true);
        });
    }
}

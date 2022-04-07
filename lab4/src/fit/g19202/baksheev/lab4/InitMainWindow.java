package fit.g19202.baksheev.lab4;

import cg.MainFrame;
import fit.g19202.baksheev.lab4.tools.Context;
import fit.g19202.baksheev.lab4.tools.Tool;
import fit.g19202.baksheev.lab4.tools.ToolManager;
import fit.g19202.baksheev.lab4.tools.scene.Scene;
import fit.g19202.baksheev.lab4.tools.scene.SceneParameters;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Main window class
 *
 * @author Ivan Baksheev
 */
public class InitMainWindow extends MainFrame {
    private final static String TITLE = "WireFrame";
    private final Context ctx;
    private final Scene scene;

    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super(640, 480, TITLE);

        var toolManager = ToolManager.getInstance();

        setMinimumSize(new Dimension(640, 480));

        var sceneParameters = SceneParameters.getEmptyInstance();
        scene = new Scene(sceneParameters);
        ctx = new Context(this, scene, sceneParameters);

        add(scene);

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
            tool.execute(ctx);
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

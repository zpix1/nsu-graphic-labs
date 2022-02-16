package FIT_19202_Baksheev_Lab1;

import FIT_19202_Baksheev_Lab1.drawing.DrawContext;
import FIT_19202_Baksheev_Lab1.drawing.ToolManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nsu.cg.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Main window class
 *
 * @author Ivan Baksheev
 */
public class InitMainWindow extends MainFrame {
    private final ToolManager toolManager;
    private final DrawContext drawContext;
    private final ImagePanel imagePanel;

    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super(600, 400, "Lab 1 Paint");

        toolManager = new ToolManager();
        drawContext = new DrawContext(3, Color.BLACK);
        imagePanel = new ImagePanel(toolManager, drawContext);

        try {

            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "Exit.gif", event -> onExit());

            addCanvasMenu();

            addToolBarSeparator();

            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "About.gif", event -> onAbout());

            addToolBarButton("File/Exit");
            addToolBarButton("Help/About...");

            add(imagePanel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AllArgsConstructor
    @Getter
    static class NamedColor {
        String name;
        Color color;
    }

    private static final NamedColor[] colors = {
            new NamedColor("Black", new Color(0, 0, 0)),
            new NamedColor("White", new Color(255, 255, 255)),
            new NamedColor("Red", new Color(255, 0, 0)),
            new NamedColor("Yellow", new Color(255, 255, 0)),
            new NamedColor("Green", new Color(0, 255, 0)),
            new NamedColor("Cyan", new Color(0, 255, 255)),
            new NamedColor("Magenta", new Color(255, 0, 255))
    };

    private void addCanvasMenu() throws NoSuchMethodException {
        addSubMenu("Canvas", KeyEvent.VK_V);
        addMenuItem("Canvas/Clean", "Clean canvas", 0, "tools/clean.gif", event -> imagePanel.clean());
        addToolBarButton("Canvas/Clean");

        addColorsMenu();
        addToolBarSeparator();
        addToolsMenu();
    }

    private void addColorsMenu() throws NoSuchMethodException {
        addSubMenu("Canvas/Colors", 0);
        for (var color : colors) {
            var path = "Canvas/Colors/" + color.getName();
            addMenuItem(
                    path,
                    "Set " + color.getName().toLowerCase() + " as current color",
                    0,
                    "colors/" + color.getName().toLowerCase() + ".gif",
                    event -> drawContext.setColor(color.getColor())
            );
            addToolBarButton(path);
        }
    }

    private void addToolsMenu() {
        addSubMenu("Canvas/Tools", 0);
        for (var toolIdx = 0; toolIdx < toolManager.getAllTools().size(); toolIdx++) {
            var tool = toolManager.getAllTools().get(toolIdx);
            var path = "Canvas/Tools/" + tool.getName();
            int finalToolIdx = toolIdx;
            addMenuItem(
                    path,
                    "Use " + tool.getName().toLowerCase() + " as current tool",
                    0,
                    "tools/" + tool.getName() + ".gif",
                    event -> toolManager.setTool(finalToolIdx)
            );
            addToolBarButton(path);
        }
    }

    /**
     * Help/About... - shows program version and copyright information
     */
    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Lab1: Paint\nMade by Ivan Baksheev, 19202.\nFebruary 2022. ", "About Paint", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * File/Exit - exits application
     */
    public void onExit() {
        System.exit(0);
    }

    /**
     * Application main entry point
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible(true);
    }
}

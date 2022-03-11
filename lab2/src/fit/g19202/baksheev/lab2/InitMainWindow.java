package fit.g19202.baksheev.lab2;

import cg.MainFrame;
import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.Tool;
import fit.g19202.baksheev.lab2.tools.ToolManager;

import java.awt.*;
import java.util.Arrays;

/**
 * Main window class
 *
 * @author Ivan Baksheev
 */
public class InitMainWindow extends MainFrame {
    private final ImagePanel imagePanel;

    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super(640, 480, "Lab 2 Filter");

        setMinimumSize(new Dimension(640, 480));

        imagePanel = new ImagePanel();
        var toolManager = new ToolManager();

        try {
            for (var tool : toolManager.getToolList()) {
                Arrays.stream(tool.getMenuPath().split("/")).reduce((path, item) -> {
                    addSubMenu(path, 0);
                    return path + "/" + item;
                });
                addToolMenu(tool);
            }
            add(imagePanel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addToolMenu(Tool tool) {
        addMenuItem(tool.getMenuPath(), tool.getTooltip(), 0, tool.getIconPath(), event -> applyTool(tool));
        addToolBarButton(tool.getMenuPath());
    }

    private void applyTool(Tool tool) {
        imagePanel.setImage(tool.apply(new Context(imagePanel.getImage(), this)));
    }

//    private void selectButtonFromSet(int index, ButtonGroup group) {
////        does not work for some reason
////        group.clearSelection();
//        Collections.list(group.getElements()).forEach(button -> button.setSelected(false));
//        if (index != -1) {
//            Collections.list(group.getElements()).get(index).setSelected(true);
//        }
//    }
//
//    /**
//     * Help/About... - shows program version and copyright information
//     */
//    public void onAbout() {
//        JOptionPane.showMessageDialog(this,
//                """
//                        Lab2: Filter
//                        Main features:
//                        * !TODO: 123
//                        March 2022.
//                        Made by Ivan Baksheev, 19202.
//                        Swing, Java, IntelliJ IDEA, Mac OS X.
//                        \s""",
//                "About Paint", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    /**
//     * File/Exit - exits application
//     */
//    public void onExit() {
//        System.exit(0);
//    }

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

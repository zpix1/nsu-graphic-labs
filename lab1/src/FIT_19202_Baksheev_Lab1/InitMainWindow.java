package FIT_19202_Baksheev_Lab1;

import FIT_19202_Baksheev_Lab1.drawing.DrawContext;
import FIT_19202_Baksheev_Lab1.drawing.ToolManager;
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
        drawContext = new DrawContext(1, Color.BLACK);
        imagePanel = new ImagePanel(toolManager, drawContext);

        try {

            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "Exit.gif", "onExit");


            addToolsMenu();

            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "About.gif", "onAbout");

            addToolBarButton("File/Exit");
            addToolBarSeparator();
            addToolBarButton("Help/About...");

            add(imagePanel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addToolsMenu() throws NoSuchMethodException {
        addSubMenu("View", KeyEvent.VK_V);
        addMenuItem("View/Clean", "Clean canvas", 0, "clean");
        addToolBarButton("View/Clean");
    }

    public void clean() {
        imagePanel.clean();
    }

    /**
     * Help/About... - shows program version and copyright information
     */
    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Lab1: Paint\nMade by Ivan Baksheev, 19202,\nFebruary 2022. ", "About Paint", JOptionPane.INFORMATION_MESSAGE);
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

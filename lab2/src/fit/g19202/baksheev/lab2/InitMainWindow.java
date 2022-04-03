package fit.g19202.baksheev.lab2;

import cg.MainFrame;
import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.Tool;
import fit.g19202.baksheev.lab2.tools.ToolManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Main window class
 *
 * @author Ivan Baksheev
 */
public class InitMainWindow extends MainFrame {
    private final static String TITLE = "Filter";
    private final JImagePanel imagePanel;
    private BufferedImage originalImage;
    private BufferedImage currentImage;

    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super(640, 480, TITLE);

        setMinimumSize(new Dimension(640, 480));

//        imagePanel = new ImagePanel();
        var toolManager = ToolManager.getInstance();

        try {
            for (var tool : toolManager.getToolList()) {
                Arrays.stream(tool.getMenuPath().split("/")).reduce((path, item) -> {
                    if (getMenuElement(path) == null)
                        addSubMenu(path, 0);
                    return path + "/" + item;
                });
                addToolMenu(tool);
            }
            var sp = new JScrollPane();
            imagePanel = new JImagePanel(sp, this);
            imagePanel.realSize();
            add(sp);
            var ma = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    imagePanel.setImage(originalImage, false);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    imagePanel.setImage(currentImage, false);
                }
            };

            imagePanel.addMouseListener(ma);
//            imagePanel.addMouseMotionListener(ma);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addToolMenu(Tool tool) {
        addMenuItem(tool.getMenuPath(), tool.getTooltip(), 0, tool.getIconPath(), event -> applyTool(tool));
        addToolBarButton(tool.getMenuPath());
    }

    private void applyTool(Tool tool) {
        var context = new Context(originalImage, currentImage, this, imagePanel);

        if (!tool.showSettingsDialog(context)) {
            return;
        }

        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var newImage = tool.apply(context);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if (newImage == null) {
                return;
            }
            currentImage = newImage;
            if (tool.getName().equals("Open")) {
                originalImage = newImage;
                imagePanel.setImage(currentImage, false);
                imagePanel.realSize();
            } else {
                imagePanel.setImage(currentImage, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sync();
    }

    private void sync() {
        setTitle(TITLE + ": " + currentImage.getWidth() + "x" + currentImage.getHeight());
//        imagePanel.setAutoscrolls(true);
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

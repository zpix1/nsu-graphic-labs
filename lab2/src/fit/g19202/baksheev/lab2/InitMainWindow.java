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
    private final ImagePanel imagePanel;
    private BufferedImage originalImage;
    private BufferedImage currentImage;

    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super(640, 480, TITLE);

        setMinimumSize(new Dimension(640, 480));

        imagePanel = new ImagePanel();
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
            var sp = new JScrollPane(imagePanel);
            var ma = new MouseAdapter() {
                private Point origin;

                @Override
                public void mousePressed(MouseEvent e) {
                    origin = new Point(e.getPoint());
                    imagePanel.setImage(originalImage);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    imagePanel.setImage(currentImage);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (origin != null) {
                        JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, imagePanel);
                        if (viewPort != null) {
                            int deltaX = origin.x - e.getX();
                            int deltaY = origin.y - e.getY();

                            Rectangle view = viewPort.getViewRect();
                            view.x += deltaX;
                            view.y += deltaY;

                            imagePanel.scrollRectToVisible(view);
                        }
                    }
                }

            };

            imagePanel.addMouseListener(ma);
            imagePanel.addMouseMotionListener(ma);

            add(sp);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sync();
    }

    private void sync() {
        setTitle(TITLE + ": " + currentImage.getWidth() + "x" + currentImage.getHeight());
        imagePanel.setImage(currentImage);
        imagePanel.setAutoscrolls(true);
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

package fit.g19202.baksheev.lab1;

import fit.g19202.baksheev.lab1.drawing.DrawContext;
import fit.g19202.baksheev.lab1.drawing.Preset;
import fit.g19202.baksheev.lab1.drawing.SettingsDialog;
import fit.g19202.baksheev.lab1.drawing.ToolManager;
import ru.nsu.cg.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Main window class
 *
 * @author Ivan Baksheev
 */
public class InitMainWindow extends MainFrame {
    private final ToolManager toolManager;
    private final DrawContext drawContext;
    private final ImagePanel imagePanel;
    private final SettingsDialog dialog;

    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super(640, 480, "Lab 1 Paint");

        setMinimumSize(new Dimension(640, 480));

        toolManager = new ToolManager();
        drawContext = new DrawContext(3, Color.BLACK, 5, 100, 0);
        imagePanel = new ImagePanel(toolManager, drawContext);
        dialog = new SettingsDialog(this, drawContext);

        try {
            addFileMenu();

            addCanvasMenu();

            addToolBarSeparator();

            addSettingsMenu();

            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "About.gif", event -> onAbout());

            addToolBarButton("File/Exit");
            addToolBarButton("Help/About...");

            add(imagePanel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void addFileMenu() {
        addSubMenu("File", KeyEvent.VK_F);
        addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "Exit.gif", event -> onExit());
        addMenuItem("File/Save Image", "Save image as a file", 0, "save.gif", event -> {
            var file = getSaveFileName(new String[]{"png", "jpeg", "bmp", "gif"}, "Images");
            try {
                ImageIO.write(imagePanel.getImage(), file.getExtension(), file.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        addMenuItem("File/Open Image", "Open image from a file", 0, "open.gif", event -> {
            var file = getOpenFileName(new String[]{"png", "jpeg", "bmp", "gif"}, "Images");
            try {
                var image = ImageIO.read(file.getFile());
                imagePanel.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        addToolBarButton("File/Save Image");
        addToolBarButton("File/Open Image");
    }

    private void addCanvasMenu() {
        addSubMenu("Canvas", KeyEvent.VK_V);
        addMenuItem("Canvas/Clean", "Clean canvas", 0, "tools/clean.gif", event -> imagePanel.clean());
        addToolBarButton("Canvas/Clean");

        addColorsMenu();
        addToolBarSeparator();
        addToolsMenu();
    }

    private void selectButtonFromSet(int index, ButtonGroup group) {
//        does not work for some reason
//        group.clearSelection();
        Collections.list(group.getElements()).forEach(button -> button.setSelected(false));
        if (index != -1) {
            Collections.list(group.getElements()).get(index).setSelected(true);
        }
    }

    private void addColorsMenu() {
        addSubMenu("Canvas/Colors", 0);
        var radioButtonsGroup = new ButtonGroup();
        var toolbarButtonsGroup = new ButtonGroup();
        for (var colorIdx = 0; colorIdx < Preset.colors.length; colorIdx++) {
            var color = Preset.colors[colorIdx];
            var path = "Canvas/Colors/" + color.getName();
            int finalToolIdx = colorIdx;
            radioButtonsGroup.add(addMenuItem(
                    path,
                    "Set " + color.getName().toLowerCase() + " as current color",
                    0,
                    "colors/" + color.getName().toLowerCase() + ".gif",
                    event -> {
                        drawContext.setColor(color.getColor());
                        selectButtonFromSet(finalToolIdx, radioButtonsGroup);
                        selectButtonFromSet(finalToolIdx, toolbarButtonsGroup);
                    },
                    ButtonType.RADIO
            ));
            toolbarButtonsGroup.add(
                    addToolBarButton(path)
            );
        }

        addMenuItem(
                "Canvas/Colors/Color Chooser",
                "Select color from the palette",
                0,
                "colors/palette.gif",
                event -> {
                    drawContext.setColor(JColorChooser.showDialog(null, "Select a color", drawContext.getColor()));

                    var selectedColorIndex = Arrays.stream(Preset.colors).map(Preset.NamedColor::getColor)
                            .collect(Collectors.toList())
                            .indexOf(drawContext.getColor());

                    selectButtonFromSet(selectedColorIndex, radioButtonsGroup);
                    selectButtonFromSet(selectedColorIndex, toolbarButtonsGroup);
                }
        );

        addToolBarButton("Canvas/Colors/Color Chooser");

        selectButtonFromSet(0, radioButtonsGroup);
        selectButtonFromSet(0, toolbarButtonsGroup);
    }

    private void addToolsMenu() {
        addSubMenu("Canvas/Tools", 0);
        var radioButtonsGroup = new ButtonGroup();
        var toolbarButtonsGroup = new ButtonGroup();
        for (var toolIdx = 0; toolIdx < toolManager.getAllTools().size(); toolIdx++) {
            var tool = toolManager.getAllTools().get(toolIdx);
            var path = "Canvas/Tools/" + tool.getName();
            int finalToolIdx = toolIdx;
            radioButtonsGroup.add(addMenuItem(
                    path,
                    "Use " + tool.getName().toLowerCase() + " as current tool",
                    0,
                    "tools/" + tool.getName() + ".gif",
                    event -> {
                        toolManager.setTool(finalToolIdx);
                        selectButtonFromSet(finalToolIdx, radioButtonsGroup);
                        selectButtonFromSet(finalToolIdx, toolbarButtonsGroup);
                    },
                    ButtonType.RADIO
            ));
            toolbarButtonsGroup.add(addToolBarButton(path));
        }

        selectButtonFromSet(toolManager.getCurrentToolIndex(), radioButtonsGroup);
        selectButtonFromSet(toolManager.getCurrentToolIndex(), toolbarButtonsGroup);
    }

    private void addSettingsMenu() {
        addSubMenu("Settings", 0);
        addMenuItem(
                "Settings/All",
                "Settings",
                0,
                null,
                event -> dialog.showSettingsDialog()
        );
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

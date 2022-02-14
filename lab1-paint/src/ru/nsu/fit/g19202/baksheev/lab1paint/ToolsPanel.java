package ru.nsu.fit.g19202.baksheev.lab1paint;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class ToolsPanel extends JPanel {
    final private ToolManager toolManager;
    final private ImagePanel canvas;
    final private DrawContext context;

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

    public ToolsPanel(ToolManager toolManager, ImagePanel canvas, DrawContext context) {
        this.toolManager = toolManager;
        this.canvas = canvas;
        this.context = context;

        setLayout(new FlowLayout(FlowLayout.LEFT));
        for (var toolIdx = 0; toolIdx < toolManager.getAllTools().size(); toolIdx++) {
            var tool = toolManager.getAllTools().get(toolIdx);
            var toolBtn = new JButton(tool.getName());
            int finalToolIdx = toolIdx;
            toolBtn.setToolTipText("Use " + tool.getName().toLowerCase() + " tool");
            toolBtn.addActionListener(action -> toolManager.setTool(finalToolIdx));
            add(toolBtn);
        }

        var cleanButton = new JButton("Clean");
        cleanButton.setToolTipText("Clean whole screen with white color");
        cleanButton.addActionListener(action -> canvas.clean());
        add(cleanButton);

        for (var namedColor : colors) {
            var colorBtn = new JButton(namedColor.getName());
            colorBtn.setBackground(namedColor.getColor());
            colorBtn.setToolTipText(String.format("Set color to rgb(%d, %d, %d)", namedColor.getColor().getRed(), namedColor.getColor().getGreen(), namedColor.getColor().getBlue()));
            colorBtn.addActionListener(action -> context.setColor(namedColor.getColor()));
            add(colorBtn);
        }
    }
}

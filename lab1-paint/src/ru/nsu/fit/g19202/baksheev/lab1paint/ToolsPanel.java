package ru.nsu.fit.g19202.baksheev.lab1paint;

import javax.swing.*;
import java.awt.*;

public class ToolsPanel extends JPanel {
    private ToolManager toolManager;

    public ToolsPanel(ToolManager toolManager) {
        this.toolManager = toolManager;

        for (var toolIdx = 0; toolIdx < toolManager.getAllTools().size(); toolIdx++) {
            var toolBtn = new JButton(toolManager.getAllTools().get(toolIdx).getName());
            toolBtn.setVisible(true);
            add(toolBtn, BorderLayout.WEST);
//            toolManager.getAllTools()[toolIdx];
        }
    }


}

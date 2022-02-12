package ru.nsu.fit.g19202.baksheev.lab1paint;

import javax.swing.*;
import java.awt.*;

public class FrameWork extends JFrame {

    public static void main(String[] args) {
        new FrameWork();
    }

    FrameWork() {
        super("ICG test");
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(640, 480));
        setResizable(true);
        setLocation(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        var toolManager = new ToolManager();

        var toolsPanel = new ToolsPanel(toolManager);
        var canvas = new ImagePanel(toolManager);



        add(toolsPanel, BorderLayout.PAGE_START);
        add(canvas, BorderLayout.CENTER);

        JButton btn = new JButton("Clean");
        btn.addActionListener(ev -> canvas.clean());
        add(btn, BorderLayout.PAGE_END);

        pack();
        setVisible(true);
    }

}

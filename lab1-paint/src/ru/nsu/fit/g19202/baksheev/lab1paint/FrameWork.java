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

        ImagePanel p = new ImagePanel();
        add(p);

        JButton btn = new JButton("Clean");
        btn.addActionListener(ev -> p.clean());
        add(btn, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

}

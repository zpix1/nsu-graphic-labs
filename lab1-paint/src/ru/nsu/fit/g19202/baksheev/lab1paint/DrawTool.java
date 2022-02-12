package ru.nsu.fit.g19202.baksheev.lab1paint;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface DrawTool {
    void onClick(BufferedImage img, int x, int y, Color color);
    void onPress(BufferedImage img, int x, int y, Color color);
    void onRelease(BufferedImage img, int x, int y, int xd, int yd, Color color);
}

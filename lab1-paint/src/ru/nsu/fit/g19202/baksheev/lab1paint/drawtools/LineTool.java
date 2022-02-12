package ru.nsu.fit.g19202.baksheev.lab1paint.drawtools;

import ru.nsu.fit.g19202.baksheev.lab1paint.DrawTool;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.abs;

public class LineTool implements DrawTool {

    @Override
    public void onClick(BufferedImage img, int x, int y, Color color) {
        img.setRGB(x, y, color.getRGB());
    }

    @Override
    public void onPress(BufferedImage img, int x, int y, Color color) {

    }

    @Override
    public void onRelease(BufferedImage img, int x, int y, int xd, int yd, Color color) {
        if (x == xd && y == yd) {
            img.setRGB(x, y, color.getRGB());
        } else {
            brezenhem(img, xd, yd, x, y, color);
        }
    }

    private void brezenhem(BufferedImage img, int x0, int y0, int x1, int y1, Color color) {
        int A, B, sign;
        A = y1 - y0;
        B = x0 - x1;
        if (abs(A) > abs(B)) sign = 1;
        else sign = -1;
        int signa, signb;
        if (A < 0) signa = -1;
        else signa = 1;
        if (B < 0) signb = -1;
        else signb = 1;
        int f = 0;
        img.setRGB(x0, y0, color.getRGB());
        int x = x0, y = y0;
        if (sign == -1) {
            do {
                f += A * signa;
                if (f > 0) {
                    f -= B * signb;
                    y += signa;
                }
                x -= signb;
                img.setRGB(x, y, color.getRGB());
            } while (x != x1 || y != y1);
        } else {
            do {
                f += B * signb;
                if (f > 0) {
                    f -= A * signa;
                    x -= signb;
                }
                y += signa;
                img.setRGB(x, y, color.getRGB());
            } while (x != x1 || y != y1);
        }
    }
}

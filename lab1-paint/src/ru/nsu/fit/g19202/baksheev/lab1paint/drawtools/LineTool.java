package ru.nsu.fit.g19202.baksheev.lab1paint.drawtools;

import ru.nsu.fit.g19202.baksheev.lab1paint.DrawContext;
import ru.nsu.fit.g19202.baksheev.lab1paint.DrawTool;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.abs;

public class LineTool implements DrawTool {

    @Override
    public void onClick(BufferedImage img, int x, int y, DrawContext context) {
        img.setRGB(x, y, context.getColor().getRGB());
    }

    @Override
    public void onPress(BufferedImage img, int x, int y, DrawContext context) {

    }

    @Override
    public void onRelease(BufferedImage img, int x, int y, int xd, int yd, DrawContext context) {
        if (x == xd && y == yd) {
            img.setRGB(x, y, context.getColor().getRGB());
        } else if (context.getLineWidth() > 0) {
            bresenhams(img, xd, yd, x, y, context.getColor());
        } else {
            var graphics = img.createGraphics();
            graphics.setColor(context.getColor());
            graphics.setStroke(new BasicStroke(context.getLineWidth()));
            graphics.drawLine(x, y, xd, yd);
        }
    }

    private void bresenhams(BufferedImage img, int x0, int y0, int x1, int y1, Color color) {
        int dx = x0 - x1;
        int dy = y1 - y0;

        int sign;
        if (abs(dy) > abs(dx)) {
            sign = 1;
        } else {
            sign = -1;
        }

        int x_direction;
        if (dx < 0) {
            x_direction = -1;
        } else {
            x_direction = 1;
        }

        int y_direction;
        if (dy < 0) {
            y_direction = -1;
        } else {
            y_direction = 1;
        }

        img.setRGB(x0, y0, color.getRGB());

        int x = x0;
        int y = y0;
        if (sign == -1) {
            int err = -dx;
            for (int i = 0; i < abs(dx); i++) {
                x -= x_direction;
                err += 2 * abs(dy);
                if (err > 0) {
                    err -= 2 * abs(dx);
                    y += y_direction;
                }
                img.setRGB(x, y, color.getRGB());
            }
        } else {
            int err = -dy;
            for (int i = 0; i < abs(dy); i++) {
                y += y_direction;
                err += 2 * abs(dx);
                if (err > 0) {
                    err -= 2 * abs(dy);
                    x -= x_direction;
                }
                img.setRGB(x, y, color.getRGB());
            }
        }
    }
}

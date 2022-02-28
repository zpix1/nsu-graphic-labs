package fit.g19202.baksheev.lab1.drawing.drawtools;

import fit.g19202.baksheev.lab1.drawing.DrawContext;
import fit.g19202.baksheev.lab1.drawing.DrawTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StarTool implements DrawTool {
    @Override
    public String getName() {
        return "Star Stamp";
    }

    @Override
    public void onPress(BufferedImage img, int x, int y, DrawContext context) {
        var xPoints = new int[context.getAngleCount() * 2];
        var yPoints = new int[context.getAngleCount() * 2];
        for (var i = 0; i < context.getAngleCount(); i++) {
            var outerAngle = i * 2 * Math.PI / context.getAngleCount() + (context.getAngle() * Math.PI) / 180 + Math.PI;
            var innerAngle = (i + 0.5) * 2 * Math.PI / context.getAngleCount() + (context.getAngle() * Math.PI) / 180 + Math.PI;

            xPoints[2 * i] = (int) (Math.sin(outerAngle) * context.getRadius()) + x;
            yPoints[2 * i] = (int) (Math.cos(outerAngle) * context.getRadius()) + y;

            xPoints[2 * i + 1] = (int) (Math.sin(innerAngle) * context.getRadius() / 2) + x;
            yPoints[2 * i + 1] = (int) (Math.cos(innerAngle) * context.getRadius() / 2) + y;
        }
        var polygon = new Polygon(xPoints, yPoints, context.getAngleCount() * 2);
        var graphics = img.createGraphics();
        graphics.setColor(context.getColor());
        graphics.setStroke(new BasicStroke(context.getLineWidth()));
        graphics.drawPolygon(polygon);
    }

    @Override
    public void onClick(BufferedImage img, int x, int y, DrawContext context) {

    }

    @Override
    public void onRelease(BufferedImage img, int x, int y, int xd, int yd, DrawContext context) {

    }
}

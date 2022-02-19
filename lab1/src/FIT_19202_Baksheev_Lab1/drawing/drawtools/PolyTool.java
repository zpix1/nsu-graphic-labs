package FIT_19202_Baksheev_Lab1.drawing.drawtools;

import FIT_19202_Baksheev_Lab1.drawing.DrawContext;
import FIT_19202_Baksheev_Lab1.drawing.DrawTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PolyTool implements DrawTool {
    @Override
    public String getName() {
        return "Polygon Stamp";
    }

    @Override
    public void onPress(BufferedImage img, int x, int y, DrawContext context) {
        var xPoints = new int[context.getAngleCount()];
        var yPoints = new int[context.getAngleCount()];
        for (var i = 0; i < context.getAngleCount(); i++) {
            var outerAngle = i * 2 * Math.PI / context.getAngleCount() + (context.getAngle() * Math.PI) / 180 + Math.PI;

            xPoints[i] = (int) (Math.sin(outerAngle) * context.getRadius()) + x;
            yPoints[i] = (int) (Math.cos(outerAngle) * context.getRadius()) + y;
        }
        var polygon = new Polygon(xPoints, yPoints, context.getAngleCount());
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

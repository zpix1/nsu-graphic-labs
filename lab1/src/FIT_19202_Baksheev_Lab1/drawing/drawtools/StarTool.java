package FIT_19202_Baksheev_Lab1.drawing.drawtools;

import FIT_19202_Baksheev_Lab1.drawing.DrawContext;
import FIT_19202_Baksheev_Lab1.drawing.DrawTool;

import java.awt.image.BufferedImage;

public class StarTool implements DrawTool {
    @Override
    public String getName() {
        return "Star Generator";
    }

    @Override
    public void onClick(BufferedImage img, int x, int y, DrawContext context) {

    }

    @Override
    public void onPress(BufferedImage img, int x, int y, DrawContext context) {

    }

    @Override
    public void onRelease(BufferedImage img, int x, int y, int xd, int yd, DrawContext context) {

    }
}

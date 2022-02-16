package FIT_19202_Baksheev_Lab1.drawing;

import java.awt.image.BufferedImage;

public interface DrawTool {
    String getName();
    void onClick(BufferedImage img, int x, int y, DrawContext context);
    void onPress(BufferedImage img, int x, int y, DrawContext context);
    void onRelease(BufferedImage img, int x, int y, int xd, int yd, DrawContext context);
}

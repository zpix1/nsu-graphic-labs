package fit.g19202.baksheev.lab2.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, dimg.getWidth(), dimg.getHeight());
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}

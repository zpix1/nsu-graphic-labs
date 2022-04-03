package fit.g19202.baksheev.lab2.tools.shape;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.Tool;
import fit.g19202.baksheev.lab2.tools.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class RotateTool extends Tool {
    int angle = 0;

    @Override
    public String getName() {
        return "Rotate";
    }

    @Override
    public File getIconPath() {
        return new File("tools/rotate.png");
    }

    @Override
    public String getMenuPath() {
        return "Tools/" + getName();
    }

    @Override
    public boolean showSettingsDialog(Context context) {
        var panel = new JPanel();
        AtomicReference<Integer> tempAngle = new AtomicReference<>(angle);

        panel.add(new JLabel("Angle"));
        panel.add(
                UIUtils.getSliderSpinnerPair(
                        tempAngle.get(),
                        -180,
                        180,
                        60,
                        30,
                        UIUtils.getLabels(new int[]{-180, -120, -60, 0, 60, 120, 180}),
                        tempAngle::set,
                        false
                )
        );

        boolean result = UIUtils.showDialog(context.getMainFrame(), panel, getName());

        if (result) {
            angle = tempAngle.get();
            return true;
        }

        return false;
    }

    @Override
    public BufferedImage apply(Context context) {
        var image = context.getOriginalImage();
        var sin = Math.abs(Math.sin(Math.toRadians(angle)));
        var cos = Math.abs(Math.cos(Math.toRadians(angle)));
        var w = image.getWidth();
        var h = image.getHeight();
        var newW = (int) Math.floor(w * cos + h * sin);
        var newH = (int) Math.floor(h * cos + w * sin);
        System.out.println(newW + " " + newH + " " + w + " " + h);
        var result = new BufferedImage(newW, newH, image.getType());
        var g = result.createGraphics();
        g.translate((newW - w) / 2, (newH - h) / 2);
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }
}

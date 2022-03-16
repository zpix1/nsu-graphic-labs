package fit.g19202.baksheev.lab2.tools.shape;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;
import fit.g19202.baksheev.lab2.tools.UIUtils;

import javax.swing.*;
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
        int w = image.getWidth();
        int h = image.getHeight();
        var rotated = ImageUtils.templateBufferedImage(image);
        var graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), w / 2, h / 2);
        graphic.drawImage(image, null, 0, 0);
        graphic.dispose();
        return rotated;
    }
}

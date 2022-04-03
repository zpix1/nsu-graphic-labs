package fit.g19202.baksheev.lab2.tools.filters;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.ImageUtils;
import fit.g19202.baksheev.lab2.tools.Tool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class WatercolorTool extends Tool {
    private static final int WINDOW_SIZE = 5;
    @Override
    public String getName() {
        return "Watercolor filter";
    }

    @Override
    public File getIconPath() {
        return new File("tools/watercolor.png");
    }

    @Override
    public String getMenuPath() {
        return "Filters/" + getName();
    }

    @Override
    public BufferedImage apply(Context context) {
        var image = context.getOriginalImage();
        var result = ImageUtils.templateBufferedImage(image);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                var redArr = new ArrayList<Integer>();
                var greenArr = new ArrayList<Integer>();
                var blueArr = new ArrayList<Integer>();

                for (int dx = 0; dx < WINDOW_SIZE; dx++) {
                    for (int dy = 0; dy < WINDOW_SIZE; dy++) {
                        int nx = x + dx - WINDOW_SIZE / 2;
                        int ny = y + dy - WINDOW_SIZE / 2;
                        if ((nx >= 0 && nx < image.getWidth()) && (ny >= 0 && ny < image.getHeight())) {
                            var rgb = image.getRGB(nx, ny);
                            var r = ImageUtils.getRed(rgb);
                            var g = ImageUtils.getGreen(rgb);
                            var b = ImageUtils.getBlue(rgb);
                            redArr.add(r);
                            greenArr.add(g);
                            blueArr.add(b);
                        }
                    }
                }
                Collections.sort(redArr);
                Collections.sort(greenArr);
                Collections.sort(blueArr);
                var newRgb = ImageUtils.composeColor(
                        redArr.get(redArr.size() / 2),
                        greenArr.get(greenArr.size() / 2),
                        blueArr.get(blueArr.size() / 2)
                );
                result.setRGB(x, y, newRgb);
            }
        }
        result = ImageUtils.applyFilter(result, SharpeningTool.filter, 1, 0);
        return result;
    }
}

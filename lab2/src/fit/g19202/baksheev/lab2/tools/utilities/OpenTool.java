package fit.g19202.baksheev.lab2.tools.utilities;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.Tool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static cg.FileUtils.getOpenFileName;

public class OpenTool extends Tool {
    @Override
    public String getName() {
        return "Open";
    }

    @Override
    public File getIconPath() {
        return new File("open.gif");
    }

    @Override
    public String getMenuPath() {
        return "File/Open";
    }

    @Override
    public BufferedImage apply(Context context) {
        var file = getOpenFileName(context.getMainFrame(), new String[]{"png", "jpeg", "bmp", "gif"}, "Images");
        if (file == null) {
            System.out.println("File not selected");
            return context.getOriginalImage();
        }
        try {
            return ImageIO.read(file.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTooltip() {
        return "Open an image";
    }
}

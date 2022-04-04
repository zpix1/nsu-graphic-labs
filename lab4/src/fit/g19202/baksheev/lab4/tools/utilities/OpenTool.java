package fit.g19202.baksheev.lab4.tools.utilities;

import fit.g19202.baksheev.lab4.tools.Context;
import fit.g19202.baksheev.lab4.tools.Tool;

import java.io.File;

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
    public void execute(Context context) {
//        var file = getOpenFileName(context.getMainFrame(), new String[]{"png", "jpeg", "bmp", "gif"}, "Images");
//        if (file == null) {
//            System.out.println("File not selected");
//            return;
//        }
//        try {
//            return ImageIO.read(file.getFile());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public String getTooltip() {
        return "Open an image";
    }
}

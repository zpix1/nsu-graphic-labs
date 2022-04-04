package fit.g19202.baksheev.lab4.tools.utilities;

import fit.g19202.baksheev.lab4.tools.Context;
import fit.g19202.baksheev.lab4.tools.Tool;

import java.io.File;

public class SaveTool extends Tool {

    @Override
    public String getName() {
        return "Save";
    }

    @Override
    public File getIconPath() {
        return new File("save.gif");
    }

    @Override
    public String getMenuPath() {
        return "File/Save";
    }

    @Override
    public void execute(Context context) {
//        var file = getSaveFileName(context.getMainFrame(), new String[]{"png", "jpeg", "bmp", "gif"}, "Images");
//        if (file == null) {
//            System.out.println("File not selected");
//        } else {
//            try {
//                if (file.getExtension() != null) {
//                    ImageIO.write(context.getCurrentImage(), file.getExtension(), file.getFile());
//                } else {
//                    ImageIO.write(context.getCurrentImage(), "png", new File(file.getFile().getAbsolutePath() + ".png"));
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
    }
}

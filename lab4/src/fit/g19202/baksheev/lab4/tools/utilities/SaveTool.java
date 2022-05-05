package fit.g19202.baksheev.lab4.tools.utilities;

import cg.FileUtils;
import fit.g19202.baksheev.lab4.tools.Context;
import fit.g19202.baksheev.lab4.tools.Tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static cg.FileUtils.getSaveFileName;

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
        var file = getSaveFileName(context.getMainFrame(), new String[]{"wirescene"}, "Wireframe scene file");
        if (file == null) {
            System.out.println("File not selected");
        } else {
            if (file.getExtension() == null) {
                file = new FileUtils.FileWithExtension(new File(file.getFile().getAbsolutePath() + ".wirescene"), "wireframe");
            }
            try (var fos = new FileOutputStream(file.getFile().getAbsolutePath());
                 var oos = new ObjectOutputStream(fos)) {
                oos.writeObject(context.getSceneParameters());

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

package fit.g19202.baksheev.lab5.tools.utilities;

import cg.FileUtils;
import fit.g19202.baksheev.lab5.tools.Context;
import fit.g19202.baksheev.lab5.tools.Tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static cg.FileUtils.getSaveFileName;

public class SaveRenderConfigTool extends Tool {

    @Override
    public String getName() {
        return "Save render config";
    }

    @Override
    public File getIconPath() {
        return new File("save.gif");
    }

    @Override
    public String getMenuPath() {
        return "File/Save render config";
    }

    @Override
    public void execute(Context context) {
        var file = getSaveFileName(context.getMainFrame(), new String[]{".render"}, "Render config file");
        if (file == null) {
            System.out.println("File not selected");
        } else {
            if (file.getExtension() == null) {
                file = new FileUtils.FileWithExtension(new File(file.getFile().getAbsolutePath() + ".render"), "render");
            }
            try (var fos = new FileOutputStream(file.getFile().getAbsolutePath());
                 var os = new OutputStreamWriter(fos)) {
                os.write(context.getScene().getRenderConfig().getStringConfig());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

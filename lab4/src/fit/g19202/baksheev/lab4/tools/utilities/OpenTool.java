package fit.g19202.baksheev.lab4.tools.utilities;

import fit.g19202.baksheev.lab4.tools.Context;
import fit.g19202.baksheev.lab4.tools.Tool;
import fit.g19202.baksheev.lab4.tools.scene.SceneParameters;

import java.io.*;

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
    public void execute(Context context) {
        var file = getOpenFileName(context.getMainFrame(), new String[]{"wirescene"}, "Wireframe scene file");
        if (file == null) {
            System.out.println("File not selected");
            return;
        }
        try (var fos = new FileInputStream(file.getFile().getAbsolutePath());
             var ois = new ObjectInputStream(fos)) {
            var sceneParameters = (SceneParameters) ois.readObject();
            context.setSceneParameters(sceneParameters);
            context.getScene().updateParameters(sceneParameters);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getTooltip() {
        return "Open an image";
    }
}

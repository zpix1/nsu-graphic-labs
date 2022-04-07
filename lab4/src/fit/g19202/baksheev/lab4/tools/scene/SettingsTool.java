package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.tools.Context;
import fit.g19202.baksheev.lab4.tools.Tool;

import java.io.File;

public class SettingsTool extends Tool {
    SettingsFrame frame;

    @Override
    public String getName() {
        return "Edit scene parameters";
    }

    @Override
    public File getIconPath() {
        return new File("about.gif");
    }

    @Override
    public String getMenuPath() {
        return "Scene/Edit";
    }

    @Override
    public void execute(Context context) {
        frame = new SettingsFrame(context);
        frame.setVisible(true);
    }
}

package fit.g19202.baksheev.lab5.tools.utilities;

import fit.g19202.baksheev.lab5.tools.Context;
import fit.g19202.baksheev.lab5.tools.Tool;

import javax.swing.*;
import java.io.File;

public class AboutTool extends Tool {
    @Override
    public String getName() {
        return "About";
    }

    @Override
    public File getIconPath() {
        return new File("about.gif");
    }

    @Override
    public String getMenuPath() {
        return "About/" + getName();
    }

    @Override
    public void execute(Context context) {
        JOptionPane.showMessageDialog(context.getMainFrame(),
                """
                        Lab5: Raytracing
                        Main features:
                        * Load and save scenes
                        * Display scenes in wire view
                        * Render scenes
                        May 2022.
                        Made by Ivan Baksheev, 19202.
                        Swing, Java, IntelliJ IDEA, Mac OS X.
                        \s""",
                "About Raytracing", JOptionPane.INFORMATION_MESSAGE);
//        return ;
    }
}

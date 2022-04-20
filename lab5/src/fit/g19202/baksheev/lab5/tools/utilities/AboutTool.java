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
                        Lab4: Wireframe
                        Main features:
                        * Create scenes using b-splines
                        * Display them as rotation objects
                        * Save and load scenes
                        April 2022.
                        Made by Ivan Baksheev, 19202.
                        Swing, Java, IntelliJ IDEA, Mac OS X.
                        \s""",
                "About WireFrame", JOptionPane.INFORMATION_MESSAGE);
//        return ;
    }
}

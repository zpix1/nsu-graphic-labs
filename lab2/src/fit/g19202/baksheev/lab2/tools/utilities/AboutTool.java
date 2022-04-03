package fit.g19202.baksheev.lab2.tools.utilities;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.Tool;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class AboutTool extends Tool {
    @Override
    public String getName() {
        return "About";
    }

    @Override
    public File getIconPath() {
        return new File("About.gif");
    }

    @Override
    public String getMenuPath() {
        return "About/" + getName();
    }

    @Override
    public BufferedImage apply(Context context) {
        JOptionPane.showMessageDialog(context.getMainFrame(),
                """
                        Lab2: Filter
                        Main features:
                        * Open and save images
                        * Apply filters to images
                        * Rotate and scale images
                        March 2022.
                        Made by Ivan Baksheev, 19202.
                        Swing, Java, IntelliJ IDEA, Mac OS X.
                        \s""",
                "About Filter", JOptionPane.INFORMATION_MESSAGE);
        return null;
    }
}

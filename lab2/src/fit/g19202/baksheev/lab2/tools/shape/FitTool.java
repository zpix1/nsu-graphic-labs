package fit.g19202.baksheev.lab2.tools.shape;

import fit.g19202.baksheev.lab2.tools.Context;
import fit.g19202.baksheev.lab2.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FitTool extends Tool {
    private static final String[] options = {"None", "Bilinear", "Bicubic", "Nearest Neighbour"};

    private int fitModeIndex = 0;

    @Override
    public String getName() {
        return "Fit";
    }

    @Override
    public File getIconPath() {
        return new File("tools/fit.png");
    }

    @Override
    public String getMenuPath() {
        return "Tools/" + getName();
    }

    @Override
    public boolean showSettingsDialog(Context context) {
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        final int[] tempFitModeIndex = {fitModeIndex};

        panel.add(new JLabel("Scale"));
        var optionList = new JComboBox(options);
        optionList.setSelectedIndex(fitModeIndex);
        optionList.addActionListener(e -> {
            var box = (JComboBox) e.getSource();
            var index = box.getSelectedIndex();
            tempFitModeIndex[0] = index;
        });
        panel.add(optionList);

        int result = JOptionPane.showOptionDialog(context.getMainFrame(), panel, "Configure " + getName(),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, new Object[]{"Apply", "Cancel"}, null);

        if (result == JOptionPane.YES_OPTION) {
            fitModeIndex = tempFitModeIndex[0];

            return true;
        }

        return false;
    }

    @Override
    public BufferedImage apply(Context context) {
        switch (fitModeIndex) {
            case 0 -> context.getImagePanel().realSize();
            case 1 -> {
                context.getImagePanel().setHints(RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                context.getImagePanel().fitScreen();
            }
            case 2 -> {
                context.getImagePanel().setHints(RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                context.getImagePanel().fitScreen();
            }
            case 3 -> {
                context.getImagePanel().setHints(RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                context.getImagePanel().fitScreen();
            }
        }
        return null;
    }
}

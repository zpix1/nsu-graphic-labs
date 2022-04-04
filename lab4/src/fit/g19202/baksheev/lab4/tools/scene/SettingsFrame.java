package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.lib.Point2D;
import fit.g19202.baksheev.lab4.lib.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsFrame extends JFrame {

    public SettingsFrame() {
        super("Edit scene parameters");
        setMinimumSize(new Dimension(1400, 900));
        setResizable(false);

        init();
    }

    private void init() {
        var all = new JPanel(new GridLayout(2, 1));
        var points = new PointsPanel(List.of(new Point2D[]{new Point2D(0.1, 0.1)}));
        all.add(points);

        var controls = new JPanel(new GridLayout(4, 4));
        controls.add(new JLabel("Use left mouse button to add points and right mouse button to remove them"));
        all.add(controls);

        add(all);
    }
}

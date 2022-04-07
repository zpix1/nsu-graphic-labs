package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.lib.Matrix;
import fit.g19202.baksheev.lab4.lib.Point2D;
import fit.g19202.baksheev.lab4.lib.UIUtils;
import fit.g19202.baksheev.lab4.tools.Context;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsFrame extends JFrame {
    private final Context context;
    private PointsPanel pointsPanel;

    public SettingsFrame(Context context) {
        super("Edit scene parameters");
        setMinimumSize(new Dimension(1400, 900));
        setResizable(false);
        this.context = context;

        init();
    }

    private void onSubmit() {
        context.getSceneParameters().setPoints(pointsPanel.getScenePoints());
        context.getSceneParameters().setSplineBasePoints(pointsPanel.getBasePoints());
        context.getScene().updateParameters(context.getSceneParameters());
        setVisible(false);
    }

    private void init() {
        var all = new JPanel(new GridLayout(2, 1));
        pointsPanel = new PointsPanel(context.getSceneParameters());
        all.add(pointsPanel);

        var controls = new JPanel(new GridLayout(4, 4));
        controls.add(new JLabel("Use left mouse button to add points and right mouse button to remove them"));
        all.add(controls);

        controls.add(UIUtils.makeClickableButton("Submit", event -> this.onSubmit()));

        add(all);
    }
}

package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.lib.UIUtils;
import fit.g19202.baksheev.lab4.tools.Context;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JFrame {
    private final Context context;
    private PointsPanel pointsPanel;

    public SettingsFrame(Context context) {
        super("Edit scene parameters");
        setMinimumSize(new Dimension(640, 480));
        this.context = context;

        init();
    }

    private void apply() {
        context.getSceneParameters().setPoints(pointsPanel.getScenePoints());
        context.getSceneParameters().setSplineBasePoints(pointsPanel.getBasePoints());
        context.getScene().updateParameters(context.getSceneParameters());
    }

    private void submit() {
        apply();
        setVisible(false);
    }

    private void reset() {
        pointsPanel.reset();
    }

    private void init() {
        var sp = context.getSceneParameters();

        var all = new JPanel(new GridBagLayout());
        var c = new GridBagConstraints();

        pointsPanel = new PointsPanel(sp);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 5;
        all.add(pointsPanel, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0;
        c.weightx = 0;
        all.add(new JLabel("Use left mouse button to add points and right mouse button to remove them"), c);

        var controls = new JPanel(new GridLayout(1, 4, 0 ,0 ));

        controls.add(UIUtils.getIntegerInput("Rot angle count", sp.getAngleN(), 3, 100, 1, sp::setAngleN));
        controls.add(UIUtils.getIntegerInput("Spline points count", sp.getSplineN(), 3, 100, 1, sp::setSplineN));
        controls.add(UIUtils.getDoubleInput("Z Far", sp.getFar(), 0, 1000, 0.1, sp::setFar));
        controls.add(UIUtils.getDoubleInput("Z Near", sp.getNear(), 0, 1000, 0.1, sp::setNear));

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 0.5;
        c.weightx = 0;
        all.add(controls, c);


        var buttons = new JPanel(new GridLayout(1, 4, 0 ,0 ));
        buttons.add(UIUtils.makeClickableButton("Submit", event -> submit()));
        buttons.add(UIUtils.makeClickableButton("Apply", event -> apply()));
        buttons.add(UIUtils.makeClickableButton("Remove all points", event -> reset()));


        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 3;
        c.weighty = 0;
        c.weightx = 0;
        all.add(buttons, c);

        add(all);
    }
}

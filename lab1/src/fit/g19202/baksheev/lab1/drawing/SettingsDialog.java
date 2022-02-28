package fit.g19202.baksheev.lab1.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Consumer;

public class SettingsDialog extends JDialog {
    final DrawContext originalContext;
    final DrawContext context;

    public SettingsDialog(Frame parent, DrawContext originalContext) {
        super(parent, true);

        this.originalContext = originalContext;
        this.context = originalContext.toBuilder().build();

        setTitle("Settings");
        setSize(500, 350);
        setResizable(false);
        setLayout(new FlowLayout());

        add(new JLabel("Line width"));
        addSliderSpinnerPair(context.getLineWidth(), 1, 100, 10, 5, getLabels(new int[]{1, 50, 100}), context::setLineWidth);

        add(new JLabel("Star and polygon angle count"));
        addSliderSpinnerPair(context.getAngleCount(), 3, 16, 1, 1, getLabels(new int[]{3, 5, 7, 9, 11, 13, 15}), context::setAngleCount);

        add(new JLabel("Star and polygon radius"));
        addSliderSpinnerPair(context.getRadius(), 1, 100, 10, 5, getLabels(new int[]{1, 50, 100}), context::setRadius);

        add(new JLabel("Star and polygon rotation angle"));
        addSliderSpinnerPair(context.getAngle(), 0, 360, 60, 30, getLabels(new int[]{0, 60, 120, 180, 240, 300, 360}), context::setAngle);

        addButton("Apply", (event) -> applySettings());
        addButton("Cancel", (event) -> revert());
    }


    private void copyContext(DrawContext fromContext, DrawContext toContext) {
        toContext.setColor(fromContext.getColor());
        toContext.setAngle(fromContext.getAngle());
        toContext.setRadius(fromContext.getRadius());
        toContext.setLineWidth(fromContext.getLineWidth());
        toContext.setAngleCount(fromContext.getAngleCount());
    }

    private void applySettings() {
        copyContext(context, originalContext);
        setVisible(false);
    }

    private void revert() {
        copyContext(originalContext, context);
        setVisible(false);
    }

    private Dictionary<Integer, JLabel> getLabels(int[] ticks) {
        Dictionary<Integer, JLabel> labels = new Hashtable<>();
        for (var tick : ticks) {
            labels.put(tick, new JLabel(String.valueOf(tick)));
        }
        return labels;
    }

    private void addSliderSpinnerPair(int defaultValue, int min, int max, int majorTickSpacing, int minorTickSpacing, Dictionary<Integer, JLabel> labels, Consumer<Integer> onChange) {
        var spinner = new JSpinner(new SpinnerNumberModel(defaultValue, min, max, minorTickSpacing));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
        JFormattedTextField spinnerTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        spinnerTextField.setColumns(3);

        var slider = new JSlider(min, max);
        slider.setValue(defaultValue);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(majorTickSpacing);
        slider.setMinorTickSpacing(minorTickSpacing);
        if (labels != null) {
            slider.setLabelTable(labels);
            slider.setPaintLabels(true);
        }

        var line = new JPanel();

        slider.addChangeListener(event -> {
            var value = ((JSlider) event.getSource()).getValue();
            spinner.setValue(value);
            onChange.accept(value);
        });

        spinner.addChangeListener(event -> {
            var value = (int) ((JSpinner) event.getSource()).getValue();
            slider.setValue(value);
            onChange.accept(value);
        });

        line.add(spinner);
        line.add(slider);

        add(line);
    }

    private void addButton(String text, ActionListener action) {
        var button = new JButton(text);
        button.setVisible(true);
        button.addActionListener(action);
        add(button);
    }

    public void showSettingsDialog() {
        setVisible(true);
    }
}

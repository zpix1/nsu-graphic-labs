package fit.g19202.baksheev.lab2.tools;

import javax.swing.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Consumer;

public class UIUtils {
    public static JPanel getSliderSpinnerPair(int defaultValue, int min, int max, int majorTickSpacing, int minorTickSpacing, Dictionary<Integer, JLabel> labels, Consumer<Integer> onChange) {
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

        return line;
    }

    public static Dictionary<Integer, JLabel> getLabels(int[] ticks) {
        Dictionary<Integer, JLabel> labels = new Hashtable<>();
        for (var tick : ticks) {
            labels.put(tick, new JLabel(String.valueOf(tick)));
        }
        return labels;
    }
}

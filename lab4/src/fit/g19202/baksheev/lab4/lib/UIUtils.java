package fit.g19202.baksheev.lab4.lib;

import fit.g19202.baksheev.lab4.tools.scene.SceneParameters;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Consumer;

import static cg.FileUtils.getOpenFileName;

public class UIUtils {
    public static JPanel getSliderSpinnerPair(int defaultValue, int min, int max, int majorTickSpacing, int minorTickSpacing, Dictionary<Integer, JLabel> labels, Consumer<Integer> onChange, boolean snapToTicks) {
        var spinner = new JSpinner(new SpinnerNumberModel(defaultValue, min, max, minorTickSpacing));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
        JFormattedTextField spinnerTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        spinnerTextField.setColumns(3);

        var slider = new JSlider(min, max);
        slider.setValue(defaultValue);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(majorTickSpacing);
        slider.setMinorTickSpacing(minorTickSpacing);
        slider.setSnapToTicks(snapToTicks);

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

    public static JPanel getSliderSpinnerPair(double defaultValue, double min, double max, double minorTickSpacing, Consumer<Double> onChange) {
        var spinner = new JSpinner(new SpinnerNumberModel(defaultValue, min, max, minorTickSpacing));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#.#"));
        JFormattedTextField spinnerTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        spinnerTextField.setColumns(3);

        var line = new JPanel();

        spinner.addChangeListener(event -> {
            var value = (double) ((JSpinner) event.getSource()).getValue();
            onChange.accept(value);
        });

        line.add(spinner);

        return line;
    }

    public static JPanel getDoubleInput(String label, double defaultValue, double min, double max, double minorTickSpacing, Consumer<Double> onChange) {
        var result = new JPanel();
        result.add(new JLabel(label));
        result.add(getSliderSpinnerPair(defaultValue, min, max, minorTickSpacing, onChange));
        return result;
    }

    public static JPanel getIntegerInput(String label, int defaultValue, int min, int max, int minorTickSpacing, Consumer<Integer> onChange) {
        var result = new JPanel();
        result.add(new JLabel(label));

        var spinner = new JSpinner(new SpinnerNumberModel(defaultValue, min, max, minorTickSpacing));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
        JFormattedTextField spinnerTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        spinnerTextField.setColumns(3);
        spinner.addChangeListener(event -> {
            var value = (int) ((JSpinner) event.getSource()).getValue();
            onChange.accept(value);
        });

        result.add(spinner);
        return result;
    }

    public static Dictionary<Integer, JLabel> getLabels(int[] ticks) {
        Dictionary<Integer, JLabel> labels = new Hashtable<>();
        for (var tick : ticks) {
            labels.put(tick, new JLabel(String.valueOf(tick)));
        }
        return labels;
    }

    public static Dictionary<Double, JLabel> getLabels(double[] ticks) {
        Dictionary<Double, JLabel> labels = new Hashtable<>();
        for (var tick : ticks) {
            labels.put(tick, new JLabel(String.valueOf(tick)));
        }
        return labels;
    }

    public static boolean showDialog(JFrame parent, JPanel panel, String toolName) {
        return JOptionPane.showOptionDialog(parent, panel, "Configure " + toolName,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, new Object[]{"Apply", "Cancel"}, null) == JOptionPane.YES_OPTION;
    }

    public static JButton makeClickableButton(String label, ActionListener onClick) {
        var btn = new JButton(label);
        btn.addActionListener(onClick);
        return btn;
    }

    public static Object loadObject(File f) {
        try (var fos = new FileInputStream(f.getAbsolutePath());
             var ois = new ObjectInputStream(fos)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

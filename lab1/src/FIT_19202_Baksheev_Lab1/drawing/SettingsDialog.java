package FIT_19202_Baksheev_Lab1.drawing;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {

    public SettingsDialog(Frame parent, DrawContext context) {
        super(parent, true);
        setTitle("Settings");
        setSize(300, 300);
    }

    public void showSettingsDialog() {
        setVisible(true);
    }
}

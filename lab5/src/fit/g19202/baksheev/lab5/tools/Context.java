package fit.g19202.baksheev.lab5.tools;

import fit.g19202.baksheev.lab5.tools.scene.ui.Scene;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;

@AllArgsConstructor
public class Context {
    @Getter
    JFrame mainFrame;
    @Getter
    Scene scene;
}

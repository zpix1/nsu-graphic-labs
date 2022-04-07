package fit.g19202.baksheev.lab4.tools;

import fit.g19202.baksheev.lab4.tools.scene.Scene;
import fit.g19202.baksheev.lab4.tools.scene.SceneParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@AllArgsConstructor
public class Context {
    @Getter
    JFrame mainFrame;
    @Getter
    Scene scene;
    @Getter
    SceneParameters sceneParameters;
}

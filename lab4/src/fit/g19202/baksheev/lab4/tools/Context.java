package fit.g19202.baksheev.lab4.tools;

import fit.g19202.baksheev.lab4.SceneData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@AllArgsConstructor
public class Context {
    @Getter
    JFrame mainFrame;
    @Getter
    @Setter
    SceneData scene;
}

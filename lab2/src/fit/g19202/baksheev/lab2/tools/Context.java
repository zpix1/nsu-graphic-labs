package fit.g19202.baksheev.lab2.tools;

import fit.g19202.baksheev.lab2.ImagePanel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.swing.*;
import java.awt.image.BufferedImage;

@AllArgsConstructor
public class Context {
    @Getter
    BufferedImage originalImage;
    @Getter
    BufferedImage currentImage;
    @Getter
    JFrame mainFrame;
    @Getter
    ImagePanel imagePanel;
}

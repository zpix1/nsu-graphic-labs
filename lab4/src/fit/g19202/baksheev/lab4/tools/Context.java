package fit.g19202.baksheev.lab4.tools;

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
}

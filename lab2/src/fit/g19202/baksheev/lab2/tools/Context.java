package fit.g19202.baksheev.lab2.tools;

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
    JFrame mainFrame;
}

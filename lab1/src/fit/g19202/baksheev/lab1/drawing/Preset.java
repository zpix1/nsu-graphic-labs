package fit.g19202.baksheev.lab1.drawing;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

public class Preset {

    @AllArgsConstructor
    @Getter
    public static class NamedColor {
        String name;
        Color color;
    }

    public static final NamedColor[] colors = {
            new NamedColor("Black", new Color(0, 0, 0)),
            new NamedColor("White", new Color(255, 255, 255)),
            new NamedColor("Red", new Color(255, 0, 0)),
            new NamedColor("Yellow", new Color(255, 255, 0)),
            new NamedColor("Green", new Color(0, 255, 0)),
            new NamedColor("Cyan", new Color(0, 255, 255)),
            new NamedColor("Magenta", new Color(255, 0, 255))
    };
}

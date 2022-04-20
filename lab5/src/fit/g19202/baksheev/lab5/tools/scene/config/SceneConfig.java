package fit.g19202.baksheev.lab5.tools.scene.config;

import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.lib.datas.SceneShape;
import fit.g19202.baksheev.lab5.lib.datas.shapes.SceneBox;
import fit.g19202.baksheev.lab5.lib.datas.shapes.SceneQuadrangle;
import fit.g19202.baksheev.lab5.lib.datas.shapes.SceneSphere;
import fit.g19202.baksheev.lab5.lib.datas.shapes.SceneTriangle;
import fit.g19202.baksheev.lab5.lib.txt.ConfigScanner;
import fit.g19202.baksheev.lab5.lib.txt.ConfigSerializable;
import fit.g19202.baksheev.lab5.lib.txt.ConfigUtils;
import lombok.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class SceneConfig implements ConfigSerializable {
    // рассеянный свет в пространстве RGB в диапазоне 0..255
    private Color A;
    // число точечных источников в сцене
    // далее идёт NL строк, каждая из которых описывает точечный источник света
    private int NL;
    // LX, LY, LZ – положение источника,
    // LR, LG, LB – цвет источника в пространстве RGB в диапазоне 0..255
    private Color[] lColors;
    private Vec4[] lPositions;
    private List<SceneShape> shapes;

    @Override
    public void loadConfigFromString(String input) {
        var configScanner = new ConfigScanner(input);
        var scanner = configScanner.getRawScanner();
        A = configScanner.readColor();
        NL = scanner.nextInt();
        lColors = new Color[NL];
        lPositions = new Vec4[NL];
        for (int i = 0; i < NL; i++) {
            lPositions[i] = configScanner.readPoint();
            lColors[i] = configScanner.readColor();
        }

        shapes = new ArrayList<>();

        while (scanner.hasNext()) {
            var sectionHeader = configScanner.readSectionHeader();
            switch (sectionHeader) {
                case "SPHERE" -> {
                    var shape = new SceneSphere();
                    shape.setCenter(configScanner.readPoint());
                    shape.setRadius(scanner.nextDouble());
                    shape.setOpticalParameters(configScanner.readOpticalParameters());
                    shapes.add(shape);
                }
                case "BOX" -> {
                    var shape = new SceneBox();
                    shape.setMinPoint(configScanner.readPoint());
                    shape.setMaxPoint(configScanner.readPoint());
                    shape.setOpticalParameters(configScanner.readOpticalParameters());
                    shapes.add(shape);
                }
                case "TRIANGLE" -> {
                    var shape = new SceneTriangle();
                    shape.setP1(configScanner.readPoint());
                    shape.setP2(configScanner.readPoint());
                    shape.setP3(configScanner.readPoint());
                    shape.setOpticalParameters(configScanner.readOpticalParameters());
                    shapes.add(shape);
                }
                case "QUADRANGLE" -> {
                    var shape = new SceneQuadrangle();
                    shape.setP1(configScanner.readPoint());
                    shape.setP2(configScanner.readPoint());
                    shape.setP3(configScanner.readPoint());
                    shape.setP4(configScanner.readPoint());
                    shape.setOpticalParameters(configScanner.readOpticalParameters());
                    shapes.add(shape);
                }
                default -> throw new RuntimeException("Failed to parse " + sectionHeader + " as section header");
            }
        }
    }

    @Override
    public String getStringConfig() {
        throw new RuntimeException();
    }
}

package fit.g19202.baksheev.lab5.tools.scene.config;

import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.lib.txt.ConfigScanner;
import fit.g19202.baksheev.lab5.lib.txt.ConfigSerializable;
import fit.g19202.baksheev.lab5.lib.txt.ConfigUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.Scanner;

@NoArgsConstructor
@Setter
@Getter
public class RenderConfig implements ConfigSerializable {
    // цвет фона в формате 0..255
    private Color B;

    // значение гаммы
    private double GAMMA;

    // глубина трассировки
    private double DEPTH;

    // rough – грубое, normal – среднее, fine – высокое
    public enum Quality {
        Rough, Normal, Fine
    }

    private Quality QUALITY;

    // позиция и характеристики камеры
    // точка камеры
    private Vec4 eye;
    // точка наблюдения (это центр бокса для начальной установки камеры)
    private Vec4 view;
    // вектор вверх, может быть не перпендикулярен вектору (EYE, VIEW)
    private Vec4 up;
    // ближняя и дальняя граница видимости камеры
    private double ZN, ZF;
    // ширина и высота матрицы камеры (через неё пускаются лучи),
    // матрица расположение на расстояние ZN от точки EYE по направлению на точку VIEW
    private double SW, SH;

    @Override
    public void loadConfigFromString(String input) {
        var configScanner = new ConfigScanner(input);
        var scanner = configScanner.getRawScanner();
        B = configScanner.readColor();
        GAMMA = scanner.nextDouble();
        DEPTH = scanner.nextDouble();
        var qualityStr = scanner.next();
        QUALITY = switch (qualityStr) {
            case "rough" -> Quality.Rough;
            case "normal" -> Quality.Normal;
            case "fine" -> Quality.Fine;
            default -> throw new RuntimeException("Failed to parse " + qualityStr + " as QUALITY");
        };
        eye = configScanner.readPoint();
        view = configScanner.readPoint();
        up = configScanner.readPoint();
        ZN = scanner.nextDouble();
        ZF = scanner.nextDouble();
        SW = scanner.nextDouble();
        SH = scanner.nextDouble();
    }

    @Override
    public String getStringConfig() {
        return null;
    }
}

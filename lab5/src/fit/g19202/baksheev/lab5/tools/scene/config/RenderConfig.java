package fit.g19202.baksheev.lab5.tools.scene.config;

import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.lib.txt.ConfigScanner;
import fit.g19202.baksheev.lab5.lib.txt.ConfigSerializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RenderConfig implements ConfigSerializable {
    // цвет фона в формате 0..255
    private Vec4 backgroundColor;

    // значение гаммы
    private double Gamma;

    // глубина трассировки
    private double Depth;

    // rough – грубое, normal – среднее, fine – высокое
    public enum Quality {
        Rough, Normal, Fine
    }

    private Quality Quality;

    // позиция и характеристики камеры
    // точка камеры
    private Vec4 cameraPosition;
    // точка наблюдения (это центр бокса для начальной установки камеры)
    private Vec4 viewPosition;
    // вектор вверх, может быть не перпендикулярен вектору (EYE, VIEW)
    private Vec4 upVector;
    // ближняя и дальняя граница видимости камеры
    private double ZNear, ZFar;
    // ширина и высота матрицы камеры (через неё пускаются лучи),
    // матрица расположение на расстояние ZN от точки EYE по направлению на точку VIEW
    private double SWidth, SHeight;

    @Override
    public void loadConfigFromString(String input) {
        var configScanner = new ConfigScanner(input);
        var scanner = configScanner.getRawScanner();
        backgroundColor = configScanner.readColor();
        Gamma = scanner.nextDouble();
        Depth = scanner.nextDouble();
        var qualityStr = scanner.next();
        Quality = switch (qualityStr) {
            case "rough" -> Quality.Rough;
            case "normal" -> Quality.Normal;
            case "fine" -> Quality.Fine;
            default -> throw new RuntimeException("Failed to parse " + qualityStr + " as QUALITY");
        };
        cameraPosition = configScanner.readPoint();
        viewPosition = configScanner.readPoint();
        upVector = configScanner.readPoint();
        ZNear = scanner.nextDouble();
        ZFar = scanner.nextDouble();
        SWidth = scanner.nextDouble();
        SHeight = scanner.nextDouble();
    }

    @Override
    public String getStringConfig() {
        return null;
    }
}

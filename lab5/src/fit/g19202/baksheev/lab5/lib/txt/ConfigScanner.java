package fit.g19202.baksheev.lab5.lib.txt;

import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.lib.datas.OpticalParameters;

import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConfigScanner {
    private final Scanner scanner;

    public ConfigScanner(String input) {
        var preprocessed = ConfigUtils.removeComments(input);
        scanner = new Scanner(preprocessed).useLocale(Locale.US);
        scanner.useDelimiter(Pattern.compile("\\s+"));
    }

    public Scanner getRawScanner() {
        return scanner;
    }

    public Vec4 readColor() {
        int r = scanner.nextInt();
        int g = scanner.nextInt();
        int b = scanner.nextInt();
        return new Vec4(r / 255., g / 255., b / 255.);
    }

    public Vec4 readPoint() {
        var x = scanner.nextDouble();
        var y = scanner.nextDouble();
        var z = scanner.nextDouble();
        return new Vec4(x, y, z);
    }

    public OpticalParameters readOpticalParameters() {
        var Kd = readPoint();
        var Ks = readPoint();
        var Power = scanner.nextDouble();
        return new OpticalParameters(Kd, Ks, Power);
    }

    public String readSectionHeader() {
        return scanner.next();
    }
}

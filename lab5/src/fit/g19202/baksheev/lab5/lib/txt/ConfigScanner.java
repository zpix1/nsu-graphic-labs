package fit.g19202.baksheev.lab5.lib.txt;

import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.lib.datas.OpticalParameters;

import java.awt.*;
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

    public Color readColor() {
        int r = scanner.nextInt();
        int g = scanner.nextInt();
        int b = scanner.nextInt();
        return new Color(r, g, b);
    }

    public Vec4 readPoint() {
        var x = scanner.nextDouble();
        var y = scanner.nextDouble();
        var z = scanner.nextDouble();
        return new Vec4(x, y, z);
    }

    public OpticalParameters readOpticalParameters() {
        var KDr = scanner.nextDouble();
        var KDg = scanner.nextDouble();
        var KDb = scanner.nextDouble();
        var KSr = scanner.nextDouble();
        var KSg = scanner.nextDouble();
        var KSb = scanner.nextDouble();
        var Power = scanner.nextDouble();
        return new OpticalParameters(KDr, KDg, KDb, KSr, KSg, KSb, Power);
    }

    public String readSectionHeader() {
        return scanner.next();
    }
}

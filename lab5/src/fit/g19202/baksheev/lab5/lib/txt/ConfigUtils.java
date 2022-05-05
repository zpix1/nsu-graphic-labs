package fit.g19202.baksheev.lab5.lib.txt;

public class ConfigUtils {
    public static String removeComments(String config) {
        return config.replaceAll("//.+\n", " ");
    }
}

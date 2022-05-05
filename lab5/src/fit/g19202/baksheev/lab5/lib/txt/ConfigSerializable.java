package fit.g19202.baksheev.lab5.lib.txt;

public interface ConfigSerializable {
    void loadConfigFromString(String input);

    String getStringConfig();
}

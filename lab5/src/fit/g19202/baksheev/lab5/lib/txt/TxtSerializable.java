package fit.g19202.baksheev.lab5.lib.txt;

import java.io.InputStream;
import java.io.OutputStream;

public interface TxtSerializable {
    void loadFromStream(InputStream is);

    void saveToStream(OutputStream os);
}

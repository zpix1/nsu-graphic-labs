package fit.g19202.baksheev.lab5.lib.datas;

import fit.g19202.baksheev.lab5.lib.Tri;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public abstract class SceneShape {
    @AllArgsConstructor
    @Getter
    public static class Inter {
        double t1;
        double t2;
    }

    @Getter
    @Setter
    private OpticalParameters opticalParameters;

    public abstract Tri[] getTriangles();
}

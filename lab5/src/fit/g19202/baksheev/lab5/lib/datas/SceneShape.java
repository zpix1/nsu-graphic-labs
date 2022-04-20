package fit.g19202.baksheev.lab5.lib.datas;

import fit.g19202.baksheev.lab5.lib.Tri;
import lombok.Getter;
import lombok.Setter;

public abstract class SceneShape {
    @Getter
    @Setter
    private OpticalParameters opticalParameters;

    public abstract Tri[] getTriangles();
}

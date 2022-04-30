package fit.g19202.baksheev.lab5.lib.datas;

import fit.g19202.baksheev.lab5.lib.Tri;
import fit.g19202.baksheev.lab5.lib.Vec4;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public abstract class SceneShape {
    @AllArgsConstructor
    @Getter
    public static class Inter {
        private Vec4 from;
        private Vec4 ray;
        private Vec4 normal;
        private Vec4 pHit;
        private double distance;
    }

    @Getter
    @Setter
    private OpticalParameters opticalParameters;

    public abstract Tri[] getTriangles();

    public abstract Inter intersect(Vec4 from, Vec4 ray);
}

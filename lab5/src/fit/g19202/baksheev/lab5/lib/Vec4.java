package fit.g19202.baksheev.lab5.lib;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Vec4 {
    @Getter
    private double x, y, z, w;

    public Vec4(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }
}

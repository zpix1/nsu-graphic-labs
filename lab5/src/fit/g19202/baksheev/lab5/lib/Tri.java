package fit.g19202.baksheev.lab5.lib;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
public class Tri {
    private Vec4 p1;
    private Vec4 p2;
    private Vec4 p3;

    public Vec4 normal() {
        var line1 = p2.sub(p1);
        var line2 = p3.sub(p1);
        return line1.cross(line2).normalized();
    }

    public Tri applyMatrix(Matrix A) {
        return new Tri(
                A.times(p1),
                A.times(p2),
                A.times(p3)
        );
    }

    public Vec4 getCenter() {
        var line1 = p2.sub(p1);
        var line2 = p3.sub(p1);
        return p1.add(line1.add(line2).times(1./3));
    }
}

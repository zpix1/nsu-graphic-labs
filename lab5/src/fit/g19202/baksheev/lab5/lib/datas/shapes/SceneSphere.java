package fit.g19202.baksheev.lab5.lib.datas.shapes;

import fit.g19202.baksheev.lab5.lib.Tri;
import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.lib.datas.SceneShape;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SceneSphere extends SceneShape {
    private double radius;
    private Vec4 center;

    private static Vec4 getPoint(double phi, double theta, double radius) {
        var x = radius * Math.sin(phi) * Math.cos(theta);
        var y = radius * Math.sin(phi) * Math.sin(theta);
        var z = radius * Math.cos(phi);
        return new Vec4(x, y, z);
    }

    @Override
    public Tri[] getTriangles() {
        var result = new ArrayList<Tri>();
        var to = 10;
        for (double phiCounter = 0; phiCounter < to; phiCounter++) {
            for (double thetaCounter = 0; thetaCounter < to; thetaCounter++) {
                var p1 = getPoint((phiCounter / to) * 2 * Math.PI, (thetaCounter / to) * 2 * Math.PI, radius);
                var p2 = getPoint(((phiCounter + 1) % to / to) * 2 * Math.PI, ((thetaCounter + 1) % to / to) * 2 * Math.PI, radius);
                p1 = p1.add(center);
                p2 = p2.add(center);
                result.add(new Tri(p1, p2, p1));
            }
        }
        return result.toArray(new Tri[0]);

//        return new Tri[]{
//                new Tri(center, center.add(new Vec4(0, 0, radius)), center.add(new Vec4(0, radius, 0))),
//                new Tri(center, center.add(new Vec4(0, 0, radius)), center.add(new Vec4(0, -radius, 0))),
//                new Tri(center, center.add(new Vec4(0, 0, radius)), center.add(new Vec4(radius, 0, 0))),
//                new Tri(center, center.add(new Vec4(0, 0, radius)), center.add(new Vec4(-radius, 0, 0))),
//
//                new Tri(center, center.add(new Vec4(0, 0, -radius)), center.add(new Vec4(0, radius, 0))),
//                new Tri(center, center.add(new Vec4(0, 0, -radius)), center.add(new Vec4(0, -radius, 0))),
//                new Tri(center, center.add(new Vec4(0, 0, -radius)), center.add(new Vec4(radius, 0, 0))),
//                new Tri(center, center.add(new Vec4(0, 0, -radius)), center.add(new Vec4(-radius, 0, 0))),
//
//                new Tri(center, center.add(new Vec4(radius, 0, 0)), center.add(new Vec4(0, radius, 0))),
//                new Tri(center, center.add(new Vec4(0, radius, 0)), center.add(new Vec4(-radius, 0, 0))),
//                new Tri(center, center.add(new Vec4(-radius, 0, 0)), center.add(new Vec4(0, -radius, 0))),
//                new Tri(center, center.add(new Vec4(0, -radius, 0)), center.add(new Vec4(radius, 0, 0))),
//        };
    }
}

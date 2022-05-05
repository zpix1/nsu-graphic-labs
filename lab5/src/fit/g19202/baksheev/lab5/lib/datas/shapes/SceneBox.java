package fit.g19202.baksheev.lab5.lib.datas.shapes;

import fit.g19202.baksheev.lab5.lib.Tri;
import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.lib.datas.SceneShape;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SceneBox extends SceneShape {
    private Vec4 minPoint;
    private Vec4 maxPoint;

    @Override
    public Tri[] getTriangles() {
        Vec4
                p000 = minPoint,
                p010 = new Vec4(minPoint.getX(), maxPoint.getY(), minPoint.getZ()),
                p100 = new Vec4(maxPoint.getX(), minPoint.getY(), minPoint.getZ()),
                p110 = new Vec4(maxPoint.getX(), maxPoint.getY(), minPoint.getZ()),

                p001 = new Vec4(minPoint.getX(), minPoint.getY(), maxPoint.getZ()),
                p011 = new Vec4(minPoint.getX(), maxPoint.getY(), maxPoint.getZ()),
                p101 = new Vec4(maxPoint.getX(), minPoint.getY(), maxPoint.getZ()),
                p111 = maxPoint;

        return new Tri[]{
                new Tri(p000, p010, p110),
                new Tri(p000, p110, p100),

// EAST
                new Tri(p100, p110, p111),
                new Tri(p100, p111, p101),

// NORTH
                new Tri(p101, p111, p011),
                new Tri(p101, p011, p001),

// WEST
                new Tri(p001, p011, p010),
                new Tri(p001, p010, p000),

// TOP
                new Tri(p010, p011, p111),
                new Tri(p010, p111, p110),

// BOTTOM
                new Tri(p101, p001, p000),
                new Tri(p101, p000, p100),
        };
    }
//
//    @Override
//    public Inter intersect(Vec4 from, Vec4 ray) {
//        var tNear = -Double.MAX_VALUE;
//        var tFar = Double.MAX_VALUE;
//        Vec4 normal = null;
//
//        for (int i = 0; i < 3; i++) {
//            if (ray.getI(i) == 0) {
//                if (from.getI(i) < minPoint.getI(i) || from.getI(i) > maxPoint.getI(i)) {
//                    return null;
//                }
//            } else {
//                var t1 = (minPoint.getI(i) - from.getI(i)) / ray.getI(i);
//                var t2 = (maxPoint.getI(i) - from.getI(i)) / ray.getI(i);
//                t1 = Math.min(t1, t2);
//                t2 = Math.max(t1, t2);
//                if (t1 > tNear) {
//                    normal = Vec4.getE(i);
//                    tNear = t1;
//                }
//                if (t2 < tFar) {
//                    tFar = t2;
//                }
//                if (tNear > tFar) {
//                    return null;
//                }
//                if (tFar < 0) {
//                    return null;
//                }
//            }
//        }
//
//        var pHit = from.add(ray.mul(tNear));
//
//        return new Inter(
//                from,
//                ray,
//                normal,
//                pHit,
//                1
//        );
//    }
}

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

    public Inter intersect(Vec4 from, Vec4 ray) {
        var tris = getTriangles();
        Inter intersection = null;
        var dist = Double.MAX_VALUE;
        for (var tri: tris) {
            var currentIntersection = triangleInter(from, ray, tri.getP1(), tri.getP2(), tri.getP3());
            if (currentIntersection != null && currentIntersection.getDistance() < dist) {
                dist = currentIntersection.getDistance();
                intersection = currentIntersection;
            }
        }
        return intersection;
    }

    private Inter triangleInter(Vec4 from, Vec4 ray, Vec4 p1, Vec4 p2, Vec4 p3) {
        var A = p2.sub(p1);
        var B = p3.sub(p1);
        var N = A.cross(B).normalized();
        var D = -p1.dot(N);

        var vd = N.dot(ray);
        if (Math.abs(vd) < 0.01) {
            return null;
        }
        var v0 = N.dot(from) + D;
        double t = -v0 / vd;
        if (t < 0) {
            return null;
        }

        var pHit = from.add(ray.mul(t));

        var e0 = p2.sub(p1);
        var e1 = p3.sub(p2);
        var e2 = p1.sub(p3);

        var C0 = pHit.sub(p1);
        var C1 = pHit.sub(p2);
        var C2 = pHit.sub(p3);

        if (N.dot(e0.cross(C0)) >= 0 && N.dot(e1.cross(C1)) >= 0 && N.dot(e2.cross(C2)) >= 0) {
            return new Inter(
                    from,
                    ray,
                    N,
                    pHit,
                    t
            );
        }

        return null;
    }
}

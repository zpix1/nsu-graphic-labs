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
public class SceneTriangle extends SceneShape {
    private Vec4 p1;
    private Vec4 p2;
    private Vec4 p3;

    @Override
    public Tri[] getTriangles() {
        return new Tri[]{
                new Tri(p1, p2, p3)
        };
    }

    @Override
    public Inter intersect(Vec4 from, Vec4 ray) {
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

        if (N.dot(e0.cross(C0)) > 0 && N.dot(e1.cross(C1)) > 0 && N.dot(e2.cross(C2)) > 0) {
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

    public static void main(String[] args) {
        var i = new SceneTriangle();
        i.setP1(new Vec4(1, 0, 0));
        i.setP2(new Vec4(0, 1, 0));
        i.setP3(new Vec4(0, 0, 1));
        i.intersect(null, null);
    }
}

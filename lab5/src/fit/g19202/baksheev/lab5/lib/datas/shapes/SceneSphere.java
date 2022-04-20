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
public class SceneSphere extends SceneShape {
    private double radius;
    private Vec4 center;

    @Override
    public Tri[] getTriangles() {
        return new Tri[]{
                new Tri(center, center.add(new Vec4(0, 0, radius)), center.add(new Vec4(0, radius, 0))),
                new Tri(center, center.add(new Vec4(0, 0, radius)), center.add(new Vec4(0, -radius, 0))),
                new Tri(center, center.add(new Vec4(0, 0, radius)), center.add(new Vec4(radius, 0, 0))),
                new Tri(center, center.add(new Vec4(0, 0, radius)), center.add(new Vec4(-radius, 0, 0))),

                new Tri(center, center.add(new Vec4(0, 0, -radius)), center.add(new Vec4(0, radius, 0))),
                new Tri(center, center.add(new Vec4(0, 0, -radius)), center.add(new Vec4(0, -radius, 0))),
                new Tri(center, center.add(new Vec4(0, 0, -radius)), center.add(new Vec4(radius, 0, 0))),
                new Tri(center, center.add(new Vec4(0, 0, -radius)), center.add(new Vec4(-radius, 0, 0))),

                new Tri(center, center.add(new Vec4(radius, 0, 0)), center.add(new Vec4(0, radius, 0))),
                new Tri(center, center.add(new Vec4(0, radius, 0)), center.add(new Vec4(-radius, 0, 0))),
                new Tri(center, center.add(new Vec4(-radius, 0, 0)), center.add(new Vec4(0, -radius, 0))),
                new Tri(center, center.add(new Vec4(0, -radius, 0)), center.add(new Vec4(radius, 0, 0))),
        };
    }
}

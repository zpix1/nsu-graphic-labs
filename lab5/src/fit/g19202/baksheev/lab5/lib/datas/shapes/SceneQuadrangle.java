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
public class SceneQuadrangle extends SceneShape {
    private Vec4 p1;
    private Vec4 p2;
    private Vec4 p3;
    private Vec4 p4;

    @Override
    public Tri[] getTriangles() {
        return new Tri[]{
                new Tri(p1, p2, p3),
                new Tri(p3, p4, p1)
        };
    }

    @Override
    public Inter intersect(Vec4 from, Vec4 ray) {
        throw new RuntimeException();
    }
}

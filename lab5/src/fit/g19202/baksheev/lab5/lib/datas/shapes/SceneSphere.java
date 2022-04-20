package fit.g19202.baksheev.lab5.lib.datas.shapes;

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
}

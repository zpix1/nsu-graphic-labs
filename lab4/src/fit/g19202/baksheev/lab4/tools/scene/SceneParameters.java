package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.lib.Matrix;
import fit.g19202.baksheev.lab4.lib.Point2D;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SceneParameters implements Serializable {
    private List<Point2D> splineBasePoints;
    private List<Matrix[]> points;
    private double fov;
    private double far;
    private double near;
    private int angleN;
    private int splineN;
    private double thetaX;
    private double thetaY;
    private double thetaZ;

    public static SceneParameters getEmptyInstance() {
        return new SceneParameters(
                new ArrayList<>(),
                new ArrayList<>(),
                60.0 * (Math.PI / 180.0),
                10,
                0.1,
                10,
                3,
                0,
                0,
                0
        );
    }
}

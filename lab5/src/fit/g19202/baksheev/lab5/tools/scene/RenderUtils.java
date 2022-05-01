package fit.g19202.baksheev.lab5.tools.scene;

import fit.g19202.baksheev.lab5.lib.Matrix;
import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.tools.scene.config.RenderConfig;

public class RenderUtils {
    public static Vec4 getLookDir(RenderConfig renderConfig) {
        var camera = renderConfig.getCameraPosition();
        return camera.sub(renderConfig.getViewPosition()).normalized();
    }

    public static Matrix getPointAtMatrix(RenderConfig renderConfig) {
        var camera = renderConfig.getCameraPosition();
        var up = renderConfig.getUpVector();
        var lookDir = camera.sub(renderConfig.getViewPosition()).normalized();
        var target = camera.add(lookDir);
        return makePointAtMatrix(camera, target, up);
    }

    public static Matrix makePointAtMatrix(Vec4 cam, Vec4 target, Vec4 up) {
        var forward = target.sub(cam).normalized();

        var a = forward.mul(up.dot(forward));
        var newUp = up.sub(a).normalized();
        var right = newUp.cross(forward).normalized();

        return new Matrix(new double[][]{
                right.getData(0),
                newUp.getData(0),
                forward.getData(0),
                cam.getData(1.)
        });
    }

    public static Matrix makeProjectionMatrix(double fovDeg, double ar, double near, double far) {
        double fovRad = 1. / Math.tan(fovDeg * 0.5 / 180 * Math.PI);
        return new Matrix(new double[][]{
                {ar * fovRad, 0, 0, 0},
                {0, fovRad, 0, 0},
                {0, 0, far / (far - near), 0},
                {0, 0, -far * near / (far - near), 0}
        }).transpose();
    }

    public static Matrix getProjectionMatrix(int width, int height, RenderConfig renderConfig) {
        return makeProjectionMatrix(90., height * 1. / width, renderConfig.getZNear(), renderConfig.getZFar());
    }

    public static Matrix makeTranslationMatrix(Vec4 how) {
        return new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                how.getData(1)
        });
    }

    public static Matrix makeRotationMatrix(double thetaX, double thetaY, double thetaZ) {
        var rotZ = new Matrix(new double[][]{
                {Math.cos(thetaZ), Math.sin(thetaZ), 0, 0},
                {-Math.sin(thetaZ), Math.cos(thetaZ), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });

        var rotY = new Matrix(new double[][]{
                {Math.cos(thetaY), 0, Math.sin(thetaY), 0},
                {0, 1, 0, 0},
                {-Math.sin(thetaY), 0, Math.cos(thetaY), 0},
                {0, 0, 0, 1},
        });

        var rotX = new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(thetaX), Math.sin(thetaX), 0},
                {0, -Math.sin(thetaX), Math.cos(thetaX), 0},
                {0, 0, 0, 1},
        });

        return rotX.times(rotY).times(rotZ);
    }
}

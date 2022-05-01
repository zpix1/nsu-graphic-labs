package fit.g19202.baksheev.lab5.tools.scene;

import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.lib.datas.SceneShape;
import fit.g19202.baksheev.lab5.tools.scene.config.RenderConfig;
import fit.g19202.baksheev.lab5.tools.scene.config.SceneConfig;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class RayTracer {
    @Setter
    private SceneConfig sceneConfig;
    @Setter
    private RenderConfig renderConfig;

    private int toDo = 0;
    private AtomicInteger done = new AtomicInteger(0);

    public double getProgress() {
        return done.get() * 1. / toDo;
    }

    public BufferedImage render(int width, int height) {
        var service = Executors.newFixedThreadPool(30);
        var tasks = new ArrayList<Future>();
        toDo = width * height;

        var pixels = new Vec4[width][height];
        var maxColor = new AtomicReference<>(0.);

        var cameraMatrix = RenderUtils.getPointAtMatrix(renderConfig);
        var angle = Math.tan(Math.PI * 0.5 * renderConfig.getFovDeg() / 180.);
        var ar = width * 1. / height;
        var far = renderConfig.getZFar();
        var near = renderConfig.getZNear();
        var removeFar = far * near / (far - near);

        for (int x = 0; x < width; x++) {
            int finalX = x;
            tasks.add(service.submit(() -> {
                for (int y = 0; y < height; y++) {
                    var xx = (finalX * 1. / width - 0.5) * angle * ar * removeFar;
                    var yy = (y * 1. / height - 0.5) * angle * removeFar;
                    var ray =
                            cameraMatrix.times(
                                            new Vec4(xx, yy, -1, 1)
                                    )
                                    .sub(renderConfig.getCameraPosition()).normalized();
                    var color = traceRay(renderConfig.getCameraPosition(), ray, 0);
                    maxColor.getAndUpdate(v -> Math.max(v, Math.max(Math.max(color.getX(), color.getY()), color.getY())));
                    pixels[finalX][y] = color;
                    done.incrementAndGet();
                }
            }));
        }

        for (var f: tasks) {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }
        }

        var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                var r = Math.pow(pixels[x][y].getX() / maxColor.get(), renderConfig.getGamma());
                var g = Math.pow(pixels[x][y].getY() / maxColor.get(), renderConfig.getGamma());
                var b = Math.pow(pixels[x][y].getZ() / maxColor.get(), renderConfig.getGamma());
                var c = new Vec4(r, g, b);
                img.setRGB(x, y, c.toColor().getRGB());
            }
        }

        return img;
    }

    private Vec4 traceRay(Vec4 from, Vec4 ray, int depth) {
        var minDistance = Double.MAX_VALUE;
        SceneShape shape = null;
        SceneShape.Inter intersection = null;

        for (var intersectingShape : sceneConfig.getShapes()) {
            var in = intersectingShape.intersect(from, ray);
            if (in != null) {
                if (in.getDistance() < minDistance && Math.abs(in.getDistance()) > 0.00001) {
                    minDistance = in.getDistance();
                    shape = intersectingShape;
                    intersection = in;
                }
            }
        }

        if (shape == null) {
            return renderConfig.getBackgroundColor();
        }

        var reflectionColor = new Vec4(0, 0, 0);
        var pHit = intersection.getPHit();
        Vec4 reflectionDir = new Vec4(0, 0, 0);
        if (depth < renderConfig.getDepth()) {
            var N = intersection.getNormal();
            var L = ray.mul(-1);

            reflectionDir = N.mul(2 * N.dot(L)).sub(L).normalized();
            reflectionColor = traceRay(
                    pHit,
                    reflectionDir,
                    depth + 1
            );
        }

        var A = sceneConfig.getAmbientColor();
        var Kd = shape.getOpticalParameters().getKd();
        var Ks = shape.getOpticalParameters().getKs();
        var power = shape.getOpticalParameters().getPower();

        var lightsColor = new Vec4(0, 0, 0);
        for (var i = 0; i < sceneConfig.getLColors().length; i++) {
            var lightColor = sceneConfig.getLColors()[i];
            var lightPosition = sceneConfig.getLPositions()[i];
            var lightDirection = pHit.sub(lightPosition).normalized();
            var notInShadow = true;
            var lightDistance = lightPosition.dist2(pHit);

            for (var shadingShape : sceneConfig.getShapes()) {
                var in = shadingShape.intersect(lightPosition, lightDirection);
                if (in == null) {
                    continue;
                }
                var d = lightPosition.dist2(in.getPHit());
                if (d - lightDistance > -0.001) {
                    continue;
                }
                notInShadow = false;
                break;
            }

            if (notInShadow) {
                var V = ray;
                var N = intersection.getNormal();
                var L = lightPosition.sub(pHit).normalized();
                var R = reflectionDir.normalized();

                var Ij = lightColor;

                var Id = Kd.mul(N.dot(L));
                var Is = Ks.mul(Math.pow(R.dot(V), power));
                var I0 = Id;

                var fAttL = 1. / (1. + lightDistance);

                lightsColor = lightsColor.add(I0.mul(fAttL).vdot(Ij));
            }
        }

        double fAttR = 1;
        if (depth > 0) {
            var distToRef = pHit.dist2(from);
            fAttR = 1 / (1 + distToRef);
        }

        return A.vdot(Kd).add(reflectionColor).add(lightsColor).mul(fAttR);
    }
}

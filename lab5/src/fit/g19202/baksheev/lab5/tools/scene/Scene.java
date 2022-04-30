package fit.g19202.baksheev.lab5.tools.scene;

import fit.g19202.baksheev.lab5.lib.Matrix;
import fit.g19202.baksheev.lab5.lib.Tri;
import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.lib.datas.SceneShape;
import fit.g19202.baksheev.lab5.lib.datas.shapes.SceneSphere;
import fit.g19202.baksheev.lab5.tools.scene.config.RenderConfig;
import fit.g19202.baksheev.lab5.tools.scene.config.SceneConfig;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Scene extends JPanel {
    private SceneConfig sceneConfig;
    private RenderConfig renderConfig;
    private double thetaX, thetaY, thetaZ;
    private final double speed = 0.05f;
    private final double fovDeg = 90;
    private Vec4 lookDir = new Vec4(0, 0, 0);
    private BufferedImage renderedImage;

    private double yawAngle;

    public Scene() {
        var mouseAdapter = new MouseInputAdapter() {
            private int startedX;
            private int startedY;

            private double savedThetaX;
            private double savedThetaY;
            private double savedThetaZ;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                requestFocusInWindow();
                startedX = e.getX();
                startedY = e.getY();
                savedThetaX = thetaX;
                savedThetaY = thetaY;
                savedThetaZ = thetaZ;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    thetaZ = savedThetaZ + (startedX - e.getX()) * 0.03;
                    thetaY = savedThetaY + (startedY - e.getY()) * -0.03;
                }
                repaint();
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
        addKeyListener(getKeyAdapter());
    }

    public void setSceneConfig(SceneConfig config) {
        this.sceneConfig = config;
        repaint();
    }

    public void setRenderConfig(RenderConfig config) {
        this.renderConfig = config;
        repaint();
    }

    private void drawLine(Graphics2D g2, Vec4 a, Vec4 b, int width, int height) {
        var x1 = (int) ((a.getX() + 1.0) * width / 2.);
        var y1 = (int) ((a.getY() + 1.0) * height / 2.);
        var x2 = (int) ((b.getX() + 1.0) * width / 2.);
        var y2 = (int) ((b.getY() + 1.0) * height / 2.);
        g2.drawLine(
                x1, y1,
                x2, y2
        );
    }

    private void fillTri(Graphics2D g2, Tri tri, Color c, int width, int height) {
        var x1 = (int) ((tri.getP1().getX() + 1.0) * width / 2.);
        var y1 = (int) ((tri.getP1().getY() + 1.0) * height / 2.);
        var x2 = (int) ((tri.getP2().getX() + 1.0) * width / 2.);
        var y2 = (int) ((tri.getP2().getY() + 1.0) * height / 2.);
        var x3 = (int) ((tri.getP3().getX() + 1.0) * width / 2.);
        var y3 = (int) ((tri.getP3().getY() + 1.0) * height / 2.);
        g2.setColor(c);
        g2.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    private void drawTri(Graphics2D g2, Tri tri, Color c, int width, int height) {
        var x1 = (int) ((tri.getP1().getX() + 0.5) * width);
        var y1 = (int) ((tri.getP1().getY() + 0.5) * height);
        var x2 = (int) ((tri.getP2().getX() + 0.5) * width);
        var y2 = (int) ((tri.getP2().getY() + 0.5) * height);
        var x3 = (int) ((tri.getP3().getX() + 0.5) * width);
        var y3 = (int) ((tri.getP3().getY() + 0.5) * height);
        g2.setColor(c);
        g2.drawPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    private Matrix makeYRotationMatrix(double theta) {
        return new Matrix(new double[][]{
                {Math.cos(theta), 0, Math.sin(theta), 0},
                {0, 1, 0, 0},
                {-Math.sin(theta), 0, Math.cos(theta), 0},
                {0, 0, 0, 1},
        });
    }

    private Matrix getRotMatrix(double thetaX, double thetaY, double thetaZ) {
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

    private Matrix makeTranslationMatrix(Vec4 how) {
        return new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                how.getData(1)
        });
    }

    private void drawFigure(Graphics2D g2) {
        var width = getWidth();
        var height = getHeight();

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.WHITE);

        var projectionMatrix = getProjectionMatrix(width, height);
        var translationMatrix = makeTranslationMatrix(new Vec4(0, 0, 0));
        var rotationMatrix = getRotMatrix(thetaX, thetaY, thetaZ);
        var worldMatrix = translationMatrix;

        var cameraMatrix = getPointAtMatrix();
        var viewMatrix = cameraMatrix.inverse();

        var all = worldMatrix.times(viewMatrix).times(projectionMatrix);

        for (var shape : sceneConfig.getShapes()) {
            for (var sceneTri : shape.getTriangles()) {
                var projectTri = sceneTri.applyMatrix(all);
                projectTri = new Tri(
                        projectTri.getP1().wize(),
                        projectTri.getP2().wize(),
                        projectTri.getP3().wize()
                );
                drawTri(g2, projectTri, Color.WHITE, width, height);
            }
        }


        for (var i = 0; i < sceneConfig.getLColors().length; i++) {
            var shape = new SceneSphere();
            shape.setCenter(sceneConfig.getLPositions()[i]);
            shape.setRadius(0.001);
            System.out.println(sceneConfig.getLColors()[i].toColor());
            projectAndDrawShape(g2, all, shape, sceneConfig.getLColors()[i].toColor(), width, height);
        }

        for (var shape : sceneConfig.getShapes()) {
            projectAndDrawShape(g2, all, shape, Color.WHITE, width, height);
        }
    }

    private void projectAndDrawShape(Graphics2D g2, Matrix all, SceneShape shape, Color color, int width, int height) {
        for (var sceneTri : shape.getTriangles()) {
            var projectTri = sceneTri.applyMatrix(all);
            projectTri = new Tri(
                    projectTri.getP1().wize(),
                    projectTri.getP2().wize(),
                    projectTri.getP3().wize()
            );
            drawTri(g2, projectTri, color, width, height);
        }
    }

    private Matrix getPointAtMatrix() {
        var camera = renderConfig.getCameraPosition();
        var up = renderConfig.getUpVector();
        lookDir = makeYRotationMatrix(yawAngle).times(new Vec4(0, 0, 1));
        var target = camera.add(lookDir);
        return makePointAtMatrix(camera, target, up);
    }

    private Matrix getProjectionMatrix(int width, int height) {
        return makeProjectionMatrix(fovDeg, height * 1. / width, renderConfig.getZNear(), renderConfig.getZFar()).transpose();
    }

    private Matrix makeProjectionMatrix(double fovDeg, double ar, double near, double far) {
        double fovRad = 1. / Math.tan(fovDeg * 0.5 / 180 * Math.PI);
        return new Matrix(new double[][]{
                {ar * fovRad, 0, 0, 0},
                {0, fovRad, 0, 0},
                {0, 0, far / (far - near), 0},
                {0, 0, -far * near / (far - near), 0}
        });
    }

    private Matrix makePointAtMatrix(Vec4 cam, Vec4 target, Vec4 up) {
        var forward = target.sub(cam).normalized();

        var a = forward.mul(up.dot(forward));
        var newUp = up.sub(a).normalized();
        var right = newUp.cross(forward).normalized();

        return new Matrix(new double[][]{
                right.getData(0),
                up.getData(0),
                forward.getData(0),
                renderConfig.getCameraPosition().getData(1.)
        });
    }

    private void drawParams(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        var j = 1;
        var step = 20;
        g2.drawString(String.format("Theta X: %.0f°", thetaX), 5, j++ * step);
        g2.drawString(String.format("Theta Y: %.0f°", thetaY), 5, j++ * step);
        g2.drawString(String.format("Theta Z: %.0f°", thetaZ), 5, j++ * step);
        g2.drawString(String.format("Yaw: %.0f°", yawAngle * 180 / Math.PI), 5, j++ * step);
        g2.drawString(String.format("FOV: %.0f°", fovDeg), 5, j++ * step);
        var view = renderConfig.getViewPosition();
        g2.drawString(String.format("View at: %.2f %.2f %.2f", view.getX(), view.getY(), view.getZ()), 5, j++ * step);
        var cam = renderConfig.getCameraPosition();
        g2.drawString(String.format("Cam at: %.2f %.2f %.2f", cam.getX(), cam.getY(), cam.getZ()), 5, j++ * step);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sceneConfig == null || renderConfig == null) {
            g.drawString("Please load both scene and render config", getWidth() / 2 - 110, getHeight() / 2);
            return;
        }
        if (renderedImage != null) {
            renderedImage = render();
            g.drawImage(renderedImage, 0, 0, renderedImage.getWidth(), renderedImage.getHeight(), null);
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        drawFigure(g2);
        drawParams(g2);
    }

    public KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    if (renderedImage != null) {
                        renderedImage = null;
                    } else {
                        renderedImage = render();
                    }
                    repaint();
                    return;
                }
                Vec4 v = new Vec4(0, 0, 0);
                if (e.getKeyChar() == KeyEvent.VK_UP || e.getExtendedKeyCode() == 0x26) {
                    v = v.add(new Vec4(0, -8 * speed, 0));
                }
                if (e.getKeyChar() == KeyEvent.VK_DOWN || e.getExtendedKeyCode() == 0x28) {
                    v = v.add(new Vec4(0, 8 * speed, 0));
                }
                if (e.getKeyChar() == KeyEvent.VK_RIGHT || e.getExtendedKeyCode() == 0x27) {
                    v = v.add(new Vec4(8 * speed, 0, 0));
                }
                if (e.getKeyChar() == KeyEvent.VK_LEFT || e.getExtendedKeyCode() == 0x25) {
                    v = v.add(new Vec4(-8 * speed, 0, 0));
                }
                Vec4 forward = lookDir.mul(.3);
                if (e.getKeyChar() == KeyEvent.VK_W || e.getExtendedKeyCode() == 87) {
                    renderConfig.setCameraPosition(renderConfig.getCameraPosition().sub(forward));
                }
                if (e.getKeyChar() == KeyEvent.VK_S || e.getExtendedKeyCode() == 83) {
                    renderConfig.setCameraPosition(renderConfig.getCameraPosition().add(forward));
                }
                if (e.getKeyChar() == KeyEvent.VK_A || e.getExtendedKeyCode() == 65) {
                    yawAngle -= 0.02 * lookDir.abs();
                }
                if (e.getKeyChar() == KeyEvent.VK_D || e.getExtendedKeyCode() == 68) {
                    yawAngle += 0.02 * lookDir.abs();
                }

                renderConfig.setCameraPosition(renderConfig.getCameraPosition().add(v));
                repaint();
            }
        };
    }

    public void showRenderWindow() {
        renderedImage = render();
        repaint();
    }

    public void hideRenderWindow() {
        renderedImage = null;
        repaint();
    }

    public BufferedImage render() {
        int width = getWidth();
        int height = getHeight();

        var pixels = new Vec4[width][height];
        var maxColor = 0.;

        var cameraMatrix = getPointAtMatrix();
        var angle = Math.tan(Math.PI * 0.5 * fovDeg / 180.);
        var ar = width * 1. / height;
        var far = renderConfig.getZFar();
        var near = renderConfig.getZNear();
        var removeFar = far * near / (far - near);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                var xx = (x * 1. / width - 0.5) * angle * ar * removeFar;
                var yy = (y * 1. / height - 0.5) * angle * removeFar;
                var ray =
                        cameraMatrix.times(
                                        new Vec4(xx, yy, 0, 0)
                                                .add(new Vec4(0, 0, -1))
                                )
                                .sub(renderConfig.getCameraPosition()).normalized();
                var color = traceRay(renderConfig.getCameraPosition(), ray, 0);
                maxColor = Math.max(maxColor, Math.max(Math.max(color.getX(), color.getY()), color.getY()));
                pixels[x][y] = color;
            }
        }
        var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                var r = Math.pow(pixels[x][y].getX() / maxColor, renderConfig.getGamma());
                var g = Math.pow(pixels[x][y].getY() / maxColor, renderConfig.getGamma());
                var b = Math.pow(pixels[x][y].getZ() / maxColor, renderConfig.getGamma());
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
                if (in.getDistance() < minDistance) {
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
//                if (shadingShape == shape) {
//                    continue;
//                }
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
                var L = lightPosition.sub(pHit);
                var R = reflectionDir;

                var Ij = lightColor;

                var Id = Kd.mul(N.dot(L));
                var Is = Ks.mul(Math.pow(R.dot(V), power));
                var I0 = Id.add(Is);

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

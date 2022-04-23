package fit.g19202.baksheev.lab5.tools.scene;

import fit.g19202.baksheev.lab5.lib.Matrix;
import fit.g19202.baksheev.lab5.lib.Tri;
import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.tools.scene.config.RenderConfig;
import fit.g19202.baksheev.lab5.tools.scene.config.SceneConfig;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Scene extends JPanel {
    private SceneConfig sceneConfig;
    private RenderConfig renderConfig;
    private double thetaX, thetaY, thetaZ;
    private final double speed = 0.05f;
    private final double fov = 1.5;
    private Vec4 lookDir = new Vec4(0, 0, 0);

    private double yawAngle;
    private double thetaAngle;

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

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                if (e.isControlDown()) {
                    var delta = renderConfig.getView().sub(renderConfig.getEye()).normalized();
                    renderConfig.setEye(renderConfig.getEye().add(delta.mul(0.1 * e.getPreciseWheelRotation())));
                } else {
                    renderConfig.setZN(renderConfig.getZN() + e.getPreciseWheelRotation() * 0.01);
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
        var x1 = (int) ((tri.getP1().getX() + 1.0) * width / 2.);
        var y1 = (int) ((tri.getP1().getY() + 1.0) * height / 2.);
        var x2 = (int) ((tri.getP2().getX() + 1.0) * width / 2.);
        var y2 = (int) ((tri.getP2().getY() + 1.0) * height / 2.);
        var x3 = (int) ((tri.getP3().getX() + 1.0) * width / 2.);
        var y3 = (int) ((tri.getP3().getY() + 1.0) * height / 2.);
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

//    private Matrix getRotMatrix() {
//        var rotZ = new Matrix(new double[][]{
//                {Math.cos(thetaZ), Math.sin(thetaZ), 0, 0},
//                {-Math.sin(thetaZ), Math.cos(thetaZ), 0, 0},
//                {0, 0, 1, 0},
//                {0, 0, 0, 1},
//        });
//
//        var rotX = new Matrix(new double[][]{
//                {1, 0, 0, 0},
//                {0, Math.cos(thetaX), Math.sin(thetaX), 0},
//                {0, -Math.sin(thetaX), Math.cos(thetaX), 0},
//                {0, 0, 0, 1},
//        });
//
//
//        return rotX.times(rotY).times(rotZ);
//    }

    private Matrix getProjectionMatrix() {
        var width = getWidth();
        var height = getHeight();
        var a = height * 1. / width;
        var f = 1.0 / Math.tan(fov / 2);
        var far = 1.;
        var near = 0.1;
        var q = far / (far - near);
        return new Matrix(new double[][]{
                {a * f, 0, 0, 0},
                {0, f, 0, 0},
                {0, 0, q, 1},
                {0, 0, -near * q, 0}
        });
    }

    private Matrix getCameraMatrix() {
        var forward = renderConfig.getView()
                .sub(renderConfig.getEye())
                .normalized();
        var a = forward.mul(
                renderConfig.getUp().dot(forward)
        );
        var up = renderConfig.getUp().sub(a).normalized();
        var right = up.cross(forward).normalized();

        return new Matrix(new double[][]{
                right.getData(0),
                up.getData(0),
                forward.getData(0),
                renderConfig.getEye().getData(1.)
        });
//        return new Matrix(new double[][]{
//                right.getData(0),
//                up.getData(0),
//                forward.getData(0),
//                new double[]{0, 0, 0, 1}
//        }).times(new Matrix(
//                new double[][]{
//                        {1, 0, 0, -eye.getX()},
//                        {0, 1, 0, -eye.getY()},
//                        {0, 0, 1, -eye.getZ()},
//                        {0, 0, 0, 1},
//                }
//        ));
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

        var projectionMatrix = makeProjectionMatrix(90., height * 1. / width, 0.1, 1000.);

        var translationMatrix = makeTranslationMatrix(new Vec4(0, 0, 0));
        var worldMatrix = translationMatrix;

        var camera = renderConfig.getEye();
        var up = new Vec4(0, 1, 0);
        var target = new Vec4(0, 0, 1);
        var cameraRotationMatrix = makeYRotationMatrix(yawAngle);
        lookDir = cameraRotationMatrix.times(target);
        target = camera.add(lookDir);
        var cameraMatrix = makePointAtMatrix(camera, target, up);
        var viewMatrix = cameraMatrix.inverse();
//        viewMatrix.show();
        for (var shape : sceneConfig.getShapes()) {
            for (var sceneTri : shape.getTriangles()) {
                var worldTri = sceneTri.applyMatrix(worldMatrix);
//                System.out.println(worldTri);
                var viewTri = worldTri.applyMatrix(viewMatrix);
                var projectTri = viewTri.applyMatrix(projectionMatrix);
//                System.out.println(projectTri);
                drawTri(g2, projectTri, Color.WHITE, width, height);
//                break;
            }
        }
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
                renderConfig.getEye().getData(1.)
        });
    }

    private void drawParams(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawString(String.format("Theta X: %.0f°", thetaX), 5, 20);
        g2.drawString(String.format("Theta Y: %.0f°", thetaY), 5, 40);
        g2.drawString(String.format("Theta Z: %.0f°", thetaZ), 5, 60);
        g2.drawString(String.format("FOV: %.0f° (%.2f)", fov * 180 / Math.PI, fov), 5, 80);
        var view = renderConfig.getView();
        g2.drawString(String.format("View at: %.2f %.2f %.2f", view.getX(), view.getY(), view.getZ()), 5, 100);
        var cam = renderConfig.getEye();
        g2.drawString(String.format("Cam at: %.2f %.2f %.2f", cam.getX(), cam.getY(), cam.getZ()), 5, 120);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sceneConfig == null || renderConfig == null) {
            g.drawString("Please load both scene and render config", getWidth() / 2 - 110, getHeight() / 2);
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
                Vec4 v = new Vec4(0, 0, 0);
                if (e.getKeyChar() == KeyEvent.VK_UP || e.getExtendedKeyCode() == 0x26) {
                    v = v.add(new Vec4(0, -8 * speed, 0));
                }
                if (e.getKeyChar() == KeyEvent.VK_DOWN || e.getExtendedKeyCode() == 0x28) {
                    v = v.add(new Vec4(0, +8 * speed, 0));
                }
                if (e.getKeyChar() == KeyEvent.VK_RIGHT || e.getExtendedKeyCode() == 0x27) {
                    v = v.add(new Vec4(8 * speed, 0, 0));
                }
                if (e.getKeyChar() == KeyEvent.VK_LEFT || e.getExtendedKeyCode() == 0x25) {
                    v = v.add(new Vec4(-8 * speed, 0, 0));
                }
                Vec4 forward = lookDir.mul(.08f);
                if (e.getKeyChar() == KeyEvent.VK_W || e.getExtendedKeyCode() == 87) {
                    renderConfig.setEye(renderConfig.getEye().add(forward));
                }
                if (e.getKeyChar() == KeyEvent.VK_S || e.getExtendedKeyCode() == 83) {
                    renderConfig.setEye(renderConfig.getEye().sub(forward));
                }
                if (e.getKeyChar() == KeyEvent.VK_A || e.getExtendedKeyCode() == 65) {
                    yawAngle -= 0.2;
                }
                if (e.getKeyChar() == KeyEvent.VK_D || e.getExtendedKeyCode() == 68) {
                    yawAngle += 0.2;
                }

                renderConfig.setEye(renderConfig.getEye().add(v));
                repaint();
            }
        };
    }

}

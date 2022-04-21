package fit.g19202.baksheev.lab5.tools.scene;

import fit.g19202.baksheev.lab5.lib.Matrix;
import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.tools.scene.config.RenderConfig;
import fit.g19202.baksheev.lab5.tools.scene.config.SceneConfig;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

public class Scene extends JPanel {
    private SceneConfig sceneConfig;
    private RenderConfig renderConfig;
    private double thetaX, thetaY, thetaZ;
    private final double speed = 0.5;

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
                    renderConfig.setEye(renderConfig.getEye().add(delta.times(0.1 * e.getPreciseWheelRotation())));
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

    private Matrix getRotMatrix() {
        var rotZ = new Matrix(new double[][]{
                {Math.cos(thetaZ), Math.sin(thetaZ), 0, 0},
                {-Math.sin(thetaZ), Math.cos(thetaZ), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });

        var rotX = new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(thetaX), Math.sin(thetaX), 0},
                {0, -Math.sin(thetaX), Math.cos(thetaX), 0},
                {0, 0, 0, 1},
        });

        var rotY = new Matrix(new double[][]{
                {Math.cos(thetaY), 0, Math.sin(thetaY), 0},
                {0, 1, 0, 0},
                {-Math.sin(thetaY), 0, Math.cos(thetaY), 0},
                {0, 0, 0, 1},
        });


        return rotX.times(rotY).times(rotZ);
    }

    private Matrix getProjectionMatrix() {
        var width = getWidth();
        var height = getHeight();
        var a = height * 1. / width;
        var f = 1.0 / Math.tan(1.5 / 2);
        var far = 10.;
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
        var eye = renderConfig.getEye();
        var forward = eye.sub(renderConfig.getView()).normalized();
        var up = renderConfig.getUp();
        var right = up.cross(forward);


        return new Matrix(new double[][]{
                right.getData(0),
                up.getData(0),
                forward.getData(0),
                eye.getData(1)
        });
    }

    private void drawFigure(Graphics2D g2) {
        var width = getWidth();
        var height = getHeight();

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.WHITE);

        var cameraMatrix = getCameraMatrix();
        var rotMatrix = getRotMatrix();
        var clipMatrix = getProjectionMatrix();
        var apply = clipMatrix.times(cameraMatrix).times(rotMatrix);

        for (var shape : sceneConfig.getShapes()) {
            for (var tri : shape.getTriangles()) {
                var a = new Vec4(apply.times(new Matrix(tri.getP1().getData()).transpose()));
                var b = new Vec4(apply.times(new Matrix(tri.getP2().getData()).transpose()));
                var c = new Vec4(apply.times(new Matrix(tri.getP3().getData()).transpose()));
                drawLine(g2, a, b, width, height);
                drawLine(g2, b, c, width, height);
                drawLine(g2, c, a, width, height);
            }
        }
    }

    private void drawParams(Graphics2D g2) {
//        g2.drawString(String.format("Theta X: %.0f°", sceneConfig.getDegThetaX()), 5, 20);
//        g2.drawString(String.format("Theta Y: %.0f°", sceneConfig.getDegThetaY()), 5, 40);
//        g2.drawString(String.format("Theta Z: %.0f°", sceneConfig.getDegThetaZ()), 5, 60);
//        g2.drawString(String.format("FOV: %.0f° (%.2f)", sceneConfig.getDegFov(), sceneConfig.getFov()), 5, 80);
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
                if (e.getKeyChar() == KeyEvent.VK_UP || e.getExtendedKeyCode() == 0x26) {
                    renderConfig.setEye(renderConfig.getEye().add(new Vec4(0, 0, speed)));
                    repaint();
                } else if (e.getKeyChar() == KeyEvent.VK_DOWN || e.getExtendedKeyCode() == 0x28) {
                    renderConfig.setEye(renderConfig.getEye().add(new Vec4(0, 0, -speed)));
                    repaint();
                } else if (e.getKeyChar() == KeyEvent.VK_RIGHT || e.getExtendedKeyCode() == 0x27) {
                    renderConfig.setEye(renderConfig.getEye().add(new Vec4(0, speed, 0)));
                    repaint();
                } else if (e.getKeyChar() == KeyEvent.VK_LEFT || e.getExtendedKeyCode() == 0x25) {
                    renderConfig.setEye(renderConfig.getEye().add(new Vec4(0, -speed, 0)));
                    repaint();
                }
            }
        };
    }

}

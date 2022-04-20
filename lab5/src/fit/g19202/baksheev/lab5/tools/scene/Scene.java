package fit.g19202.baksheev.lab5.tools.scene;

import fit.g19202.baksheev.lab5.lib.Matrix;
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
    double thetaX, thetaY, thetaZ;

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
                    System.out.println(renderConfig.getEye());
                } else {
                    renderConfig.setZN(renderConfig.getZN() + e.getPreciseWheelRotation() * 0.01);
                }
                repaint();
            }
        };

        var keyboardAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_UP) {
                    renderConfig.setEye(renderConfig.getEye().add(new Vec4(0, 0, 0.1)));
                    repaint();
                } else if (e.getKeyChar() == KeyEvent.VK_DOWN) {
                    renderConfig.setEye(renderConfig.getEye().add(new Vec4(0, 0, -0.1)));
                    repaint();
                }
            }
        };

        addKeyListener(keyboardAdapter);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
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

    private void drawFigure(Graphics2D g2) {
        var width = getWidth();
        var height = getHeight();

        var a = height * 1. / width;
        var f = 1.0;
        var far = 10.;//renderConfig.getZF();
        var near = 1.;//renderConfig.getZN();
        var q = far / (far - near);
        var clipMatrix = new Matrix(new double[][]{
                {a * f, 0, 0, 0},
                {0, f, 0, 0},
                {0, 0, q, 1},
                {0, 0, -near * q, 0}
        });

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

        var eye = renderConfig.getEye();
        var forward = eye.sub(renderConfig.getView()).normalized();
        var up = renderConfig.getUp();
        var right = up.cross(forward);

        var cameraMatrix = new Matrix(new double[][]{
                right.getData(0),
                up.getData(0),
                forward.getData(0),
                eye.getData(1)
        }).transpose();
        cameraMatrix.show();

        var rot = rotX.times(rotY).times(rotZ);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.WHITE);

        for (var shape : sceneConfig.getShapes()) {
            for (var tri : shape.getTriangles()) {
                var A = tri.getP1().times(cameraMatrix).times(clipMatrix);
                var B = tri.getP2().times(cameraMatrix).times(clipMatrix);
                var C = tri.getP3().times(cameraMatrix).times(clipMatrix);
                System.out.println(tri.getP3().times(cameraMatrix));
//                System.out.println("Withotu clip");
//                System.out.println(tri.getP3().times(cameraMatrix).wize());
                drawLine(g2, A, B, width, height);
                drawLine(g2, B, C, width, height);
                drawLine(g2, C, A, width, height);
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
}

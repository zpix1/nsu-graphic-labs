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

import static fit.g19202.baksheev.lab5.tools.scene.RenderUtils.*;

public class Scene extends JPanel {
    private SceneConfig sceneConfig;
    private RenderConfig renderConfig;
    private final double speed = 0.05f;
    private Vec4 lookDir = new Vec4(0, 0, 0);
    private boolean isRayTracingModeEnabled = false;
    private final RayTracer rayTracer;
    private double thetaX, thetaY, thetaZ;

    public Scene() {
        var keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    isRayTracingModeEnabled = !isRayTracingModeEnabled;
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

                renderConfig.setCameraPosition(renderConfig.getCameraPosition().add(v));
                repaint();
            }
        };
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
        addKeyListener(keyAdapter);

        rayTracer = new RayTracer();
    }

    public void setSceneConfig(SceneConfig config) {
        this.sceneConfig = config;
        this.rayTracer.setSceneConfig(config);
        repaint();
    }

    public void setRenderConfig(RenderConfig config) {
        this.renderConfig = config;
        this.rayTracer.setRenderConfig(config);
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

    private void drawFigure(Graphics2D g2) {
        var width = getWidth();
        var height = getHeight();

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.WHITE);

        var projectionMatrix = getProjectionMatrix(width, height, renderConfig);
//        var rotationMatrix = getR(thetaX, thetaY, thetaZ);

        var cameraMatrix = getPointAtMatrix(renderConfig);
        var viewMatrix = cameraMatrix.inverse();

        var all = viewMatrix.times(projectionMatrix);

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

    private void drawParams(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        var j = 1;
        var step = 20;
//        g2.drawString(String.format("Theta X: %.0f°", thetaX), 5, j++ * step);
//        g2.drawString(String.format("Theta Y: %.0f°", thetaY), 5, j++ * step);
//        g2.drawString(String.format("Theta Z: %.0f°", thetaZ), 5, j++ * step);
//        g2.drawString(String.format("Yaw: %.0f°", yawAngle * 180 / Math.PI), 5, j++ * step);
        g2.drawString(String.format("FOV: %.0f°", renderConfig.getFovDeg()), 5, j++ * step);
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
        if (isRayTracingModeEnabled) {
            var renderedImage = rayTracer.render(getWidth(), getHeight());
            g.drawImage(renderedImage, 0, 0, renderedImage.getWidth(), renderedImage.getHeight(), null);
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        drawFigure(g2);
        drawParams(g2);
    }
}

package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.lib.Matrix;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Scene extends JPanel {
    private SceneParameters sceneParameters;

    public Scene(SceneParameters parameters) {
        updateParameters(parameters);
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
                savedThetaX = sceneParameters.getThetaX();
                savedThetaY = sceneParameters.getThetaY();
                savedThetaZ = sceneParameters.getThetaZ();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    sceneParameters.setThetaZ(savedThetaZ + (startedX - e.getX()) * 0.03);
                    sceneParameters.setThetaX(savedThetaX + (startedY - e.getY()) * 0.03);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    sceneParameters.setThetaY(savedThetaZ + (startedX - e.getX()) * 0.03);
                    sceneParameters.setThetaX(savedThetaX + (startedY - e.getY()) * 0.03);
                }
                repaint();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                sceneParameters.setFov(sceneParameters.getFov() + e.getPreciseWheelRotation() * 0.01);
                repaint();
            }
        };

        var keyboardAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_UP) {
                    sceneParameters.setThetaX(sceneParameters.getThetaX() + 0.03);
                    repaint();
                } else if (e.getKeyChar() == KeyEvent.VK_DOWN) {
                    sceneParameters.setThetaX(sceneParameters.getThetaX() - 0.03);
                    repaint();
                }
            }
        };

        addKeyListener(keyboardAdapter);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    public void updateParameters(SceneParameters parameters) {
        this.sceneParameters = parameters;
        repaint();
    }

    private void drawLine(Graphics2D g2, Matrix A, Matrix B, int width, int height) {
        var x1 = (int) ((A.get(0, 0) + 1.0) * width / 2.);
        var y1 = (int) ((A.get(0, 1) + 1.0) * height / 2.);
        var x2 = (int) ((B.get(0, 0) + 1.0) * width / 2.);
        var y2 = (int) ((B.get(0, 1) + 1.0) * height / 2.);
        g2.drawLine(
                x1, y1,
                x2, y2
        );
    }

    private void drawFigure(Graphics2D g2) {
        var width = getWidth();
        var height = getHeight();

        var a = height * 1. / width;
        var f = 1.0 / Math.tan(sceneParameters.getFov() / 2);
        var far = sceneParameters.getFar();
        var near = sceneParameters.getNear();
        var q = far / (far - near);
        var clipMatrix = new Matrix(new double[][]{
                {a * f, 0, 0, 0},
                {0, f, 0, 0},
                {0, 0, q, 1},
                {0, 0, -near * q, 0}
        });

        var thetaX = sceneParameters.getThetaX();
        var thetaY = sceneParameters.getThetaY();
        var thetaZ = sceneParameters.getThetaZ();

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

        var rot = rotX.times(rotY).times(rotZ);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.WHITE);

        for (var line : sceneParameters.getPoints()) {
            var A = line[0].times(rot).times(clipMatrix);
            var B = line[1].times(rot).times(clipMatrix);

            drawLine(g2, A, B, width, height);
        }
    }

    private void drawParams(Graphics2D g2) {
        g2.drawString(String.format("Theta X: %.0f°", sceneParameters.getDegThetaX()), 5, 20);
        g2.drawString(String.format("Theta Y: %.0f°", sceneParameters.getDegThetaY()), 5, 40);
        g2.drawString(String.format("Theta Z: %.0f°", sceneParameters.getDegThetaZ()), 5, 60);
        g2.drawString(String.format("FOV: %.0f° (%.2f)", sceneParameters.getDegFov(), sceneParameters.getFov()), 5, 80);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawFigure(g2);
        drawParams(g2);
    }
}

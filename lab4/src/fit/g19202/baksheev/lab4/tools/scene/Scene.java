package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.lib.Matrix;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Scene extends JPanel {
    private SceneParameters sceneParameters;

    private double thetaZ = 0;
    private double thetaX = 0;

    public Scene(SceneParameters parameters) {
        updateParameters(parameters);
        var adapter = new MouseInputAdapter() {
            private int startedX;
            private int startedY;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                startedX = e.getX();
                startedY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                thetaZ = (startedX - e.getX()) * 0.03;
                thetaX = (startedY - e.getY()) * 0.03;
                repaint();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                sceneParameters.setFov(sceneParameters.getFov() + e.getPreciseWheelRotation() * 0.01);
                repaint();
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        addMouseWheelListener(adapter);
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        var width = getWidth();
        var height = getHeight();

        var a = width / height;
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

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.WHITE);

        for (var line : sceneParameters.getPoints()) {
            var A = line[0].times(rotZ).times(rotX).times(clipMatrix);
            var B = line[1].times(rotZ).times(rotX).times(clipMatrix);

            drawLine(g2, A, B, width, height);
        }
    }
}

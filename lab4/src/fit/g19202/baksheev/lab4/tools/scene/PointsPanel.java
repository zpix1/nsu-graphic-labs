package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.lib.Point2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PointsPanel extends JPanel {

    final Color AXIS_COLOR = Color.GRAY;
    final Color BACKGROUND_COLOR = Color.BLACK;
    final Color POINT_COLOR = Color.RED;
    final int POINT_RADIUS = 10;

    private static class MutablePoint extends Point2D {
        public MutablePoint(double x, double y) {
            super(x, y);
        }

        void setX(double x) {
            this.x = x;
        }

        void setY(double y) {
            this.y = y;
        }
    }


    private final List<MutablePoint> points;

    public PointsPanel(List<Point2D> initialPoints) {
        this.points = new ArrayList<>();
        for (var point : initialPoints) {
            points.add(new MutablePoint(point.getX(), point.getY()));
        }
        setBackground(BACKGROUND_COLOR);
    }

    public void addPoint() {
        points.add(new MutablePoint(0.5, 0.5));
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawAxes(g2);
        drawPoints(g2);
    }

    private void drawAxes(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
        g2.setColor(AXIS_COLOR);
        g2.drawLine(0, height / 2, width, height / 2);
        g2.drawLine(width / 2, 0, width / 2, height);
    }

    private void drawPoints(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();

        g2.setColor(POINT_COLOR);
        for (var point : points) {
            g2.drawOval(
                    (int) (point.getX() * width),
                    (int) (point.getY() * height),
                    POINT_RADIUS,
                    POINT_RADIUS
            );
        }
    }
}

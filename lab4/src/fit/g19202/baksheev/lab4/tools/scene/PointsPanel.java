package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.lib.Point2D;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PointsPanel extends JPanel {
    final Color AXIS_COLOR = Color.GRAY;
    final Color BACKGROUND_COLOR = Color.BLACK;
    final Color POINT_COLOR = Color.RED;
    final int POINT_RADIUS = 10;


    private final class MouseInputListener extends MouseInputAdapter {
        private PointsPanel.MovablePoint2D currentPoint = null;
        private boolean removeMode = false;

        @Override
        public void mousePressed(MouseEvent e) {
            currentPoint = findPoint(e.getX(), e.getY());

            if (removeMode && currentPoint != null) {
                removePoint(currentPoint);
                currentPoint = null;
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            currentPoint = null;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (currentPoint != null) {
                currentPoint.setImageX(e.getX());
                currentPoint.setImageY(e.getY());
                repaint();
            }
        }
    }

    MouseInputListener listener;

    private class MovablePoint2D extends Point2D {
        MovablePoint2D(double x, double y) {
            super(x, y);
        }

        void setX(double x) {
            this.x = x;
        }

        void setY(double y) {
            this.y = y;
        }

        void setImageX(int imageX) {
            x = 1. * imageX / getWidth();
        }

        void setImageY(int imageY) {
            y = 1. * imageY / getHeight();
        }

        int getImageX() {
            return (int) (x * getWidth());
        }

        int getImageY() {
            return (int) (y * getHeight());
        }
    }

    private final List<MovablePoint2D> points;

    public PointsPanel(List<Point2D> initialPoints) {
        this.points = new ArrayList<>();
        for (var point : initialPoints) {
            points.add(new MovablePoint2D(point.getX(), point.getY()));
        }
        setBackground(BACKGROUND_COLOR);
        listener = new MouseInputListener();

        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    public void toggleRemoveMode() {
        if (listener.removeMode) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            listener.removeMode = false;
        } else {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            listener.removeMode = true;
        }
    }

    public void addPoint() {
        points.add(new MovablePoint2D(0.5, 0.5));
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
        g2.setColor(POINT_COLOR);
        for (var point : points) {
            g2.drawOval(
                    point.getImageX() - POINT_RADIUS / 2,
                    point.getImageY() - POINT_RADIUS / 2,
                    POINT_RADIUS,
                    POINT_RADIUS
            );
        }
    }

    private MovablePoint2D findPoint(int imageX, int imageY) {
        for (var point : points) {
            if ((Math.abs(point.getImageX() - imageX) < POINT_RADIUS) &&
                    (Math.abs(point.getImageY() - imageY) < POINT_RADIUS)) {
                return point;
            }
        }
        return null;
    }

    private void removePoint(MovablePoint2D point) {
        points.remove(point);
    }
}

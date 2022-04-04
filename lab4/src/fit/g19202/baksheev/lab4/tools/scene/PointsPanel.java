package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.lib.BSpline;
import fit.g19202.baksheev.lab4.lib.Point2D;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static fit.g19202.baksheev.lab4.tools.Config.*;

public class PointsPanel extends JPanel {
    private final class PointDragListener extends MouseInputAdapter {
        private PointsPanel.MovablePoint2D currentPoint = null;

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                currentPoint = findPoint(e.getX(), e.getY());
                if (currentPoint == null) {
                    addPoint(e.getX(), e.getY());
                    repaint();
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                var toRemovePoint = findPoint(e.getX(), e.getY());
                if (toRemovePoint != null) {
                    removePoint(toRemovePoint);
                    repaint();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            currentPoint = null;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && currentPoint != null) {
                currentPoint.setImageX(e.getX());
                currentPoint.setImageY(e.getY());
                repaint();
            }
        }
    }

    PointDragListener listener;

    private class MovablePoint2D extends Point2D {
        MovablePoint2D(double x, double y) {
            super(x, y);
        }

        MovablePoint2D(int imageX, int imageY) {
            super(0, 0);
            setImageX(imageX);
            setImageY(imageY);
        }

        void setX(double x) {
            this.x = x;
        }

        void setY(double y) {
            this.y = y;
        }

        void setImageX(int imageX) {
            x = (imageX - getWidth() / 2.) / getWidth();
        }

        void setImageY(int imageY) {
            y = (imageY - getHeight() / 2.) / getHeight();
        }

        int getImageX() {
            return (int) ((x + 0.5) * getWidth());
        }

        int getImageY() {
            return (int) ((y + 0.5) * getHeight());
        }
    }

    private final List<MovablePoint2D> points;

    public PointsPanel(List<Point2D> initialPoints) {
        this.points = new ArrayList<>();
        for (var point : initialPoints) {
            points.add(new MovablePoint2D(point.getX(), point.getY()));
        }
        setBackground(BACKGROUND_COLOR);
        listener = new PointDragListener();

        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    public void addPoint() {
        points.add(new MovablePoint2D(0.5, 0.5));
        repaint();
    }

    public void addPoint(int imageX, int imageY) {
        points.add(new MovablePoint2D(imageX, imageY));
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawAxes(g2);
        drawPoints(g2);
        drawSpline(g2);
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
        MovablePoint2D prev = null;
        for (var point : points) {
            g2.drawOval(
                    point.getImageX() - POINT_RADIUS / 2,
                    point.getImageY() - POINT_RADIUS / 2,
                    POINT_RADIUS,
                    POINT_RADIUS
            );
            if (prev != null) {
                g2.drawLine(
                        point.getImageX(),
                        point.getImageY(),
                        prev.getImageX(),
                        prev.getImageY()
                );
            }
            prev = point;
        }
    }

    private void drawSpline(Graphics2D g2) {
        var spline = new BSpline(points.toArray(new Point2D[0]));
        var splinePoints = spline.getSplinePoints(10);
        if (splinePoints == null) {
            return;
        }
        var width = getWidth();
        var height = getHeight();
        g2.setColor(SPLINE_COLOR);
        for (int i = 1; i < splinePoints.length; i++) {
//            System.out.println("from x");
//            System.out.println((int) (width * (splinePoints[i - 1].getX() + 0.5)));
//            System.out.println("to x");
//            System.out.println((int) (width * (splinePoints[i].getX() + 0.5)));
            g2.drawLine(
                    (int) (width * (splinePoints[i - 1].getX() + 0.5)),
                    (int) (height * (splinePoints[i - 1].getY() + 0.5)),
                    (int) (width * (splinePoints[i].getX() + 0.5)),
                    (int) (height * (splinePoints[i].getY() + 0.5))
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

package fit.g19202.baksheev.lab4.tools.scene;

import fit.g19202.baksheev.lab4.lib.BSpline;
import fit.g19202.baksheev.lab4.lib.Matrix;
import fit.g19202.baksheev.lab4.lib.Point2D;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fit.g19202.baksheev.lab4.tools.Config.*;

public class PointsPanel extends JPanel {
    private final class PointDragListener extends MouseInputAdapter {
        private PointsPanel.MovablePoint2D currentPoint = null;
        private int pressedX = 0;
        private int pressedY = 0;
        private int savedOffsetX = 0;
        private int savedOffsetY = 0;


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
                } else {
                    pressedX = e.getX();
                    pressedY = e.getY();
                    savedOffsetX = offsetX;
                    savedOffsetY = offsetY;
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
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                offsetX = savedOffsetX + e.getX() - pressedX;
                offsetY = savedOffsetY + e.getY() - pressedY;
                repaint();
            }
        }
    }

    PointDragListener listener;

    private int offsetX = 0;
    private int offsetY = 0;

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
            x = (imageX - offsetX - getWidth() / 2.) / getWidth();
        }

        void setImageY(int imageY) {
            y = (imageY - offsetY - getHeight() / 2.) / getHeight();
        }

        int getImageX() {
            return (int) ((x + 0.5) * getWidth()) + offsetX;
        }

        int getImageY() {
            return (int) ((y + 0.5) * getHeight()) + offsetY;
        }

        double distanceTo(MovablePoint2D p) {
            return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
        }

        double imageDistanceTo(MovablePoint2D p) {
            return Math.sqrt((getImageX() - p.getImageX()) * (getImageX() - p.getImageX()) + (getImageY() - p.getImageY()) * (getImageY() - p.getImageY()));
        }
    }

    private final List<MovablePoint2D> points;
    private final SceneParameters sceneParameters;

    public PointsPanel(SceneParameters sceneParameters) {
        this.sceneParameters = sceneParameters;

        this.points = new ArrayList<>();
        for (var point : sceneParameters.getSplineBasePoints()) {
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
        var newPoint = new MovablePoint2D(imageX, imageY);
        if (points.isEmpty()) {
            points.add(newPoint);
        } else {
            var pFirst = points.get(0);
            var pLast = points.get(points.size() - 1);
            if (newPoint.distanceTo(pFirst) < newPoint.distanceTo(pLast)) {
                points.add(0, newPoint);
            } else {
                points.add(newPoint);
            }
        }
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
        g2.drawLine(0, height / 2 + offsetY, width , height / 2 + offsetY);
        g2.drawLine(width / 2 + offsetX, 0, width / 2 + offsetX, height);
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
        var splinePoints = spline.getSplinePoints(sceneParameters.getSplineN());
        if (splinePoints == null) {
            return;
        }
        var width = getWidth();
        var height = getHeight();
        g2.setColor(SPLINE_COLOR);
        for (int i = 1; i < splinePoints.length; i++) {
            g2.drawLine(
                    (int) (width * (splinePoints[i - 1].getX() + 0.5)) + offsetX,
                    (int) (height * (splinePoints[i - 1].getY() + 0.5)) + offsetY,
                    (int) (width * (splinePoints[i].getX() + 0.5)) + offsetX,
                    (int) (height * (splinePoints[i].getY() + 0.5)) + offsetY
            );
        }
    }

    private MovablePoint2D findPoint(int imageX, int imageY) {
        var click = new MovablePoint2D(imageX, imageY);
        for (var point : points) {
            if (point.imageDistanceTo(click) <= POINT_RADIUS) {
                return point;
            }
        }
        return null;
    }

    private void removePoint(MovablePoint2D point) {
        points.remove(point);
    }

    public List<Point2D> getSplinePoints() {
        var spline = new BSpline(points.toArray(new Point2D[0]));
        var splinePoints = spline.getSplinePoints(sceneParameters.getSplineN());
        if (splinePoints == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(splinePoints);
    }

    public List<Point2D> getBasePoints() {
        var res = new ArrayList<Point2D>();
        for (var point : points) {
            res.add(new Point2D(point.getX(), point.getY()));
        }
        return res;
    }

    public List<Matrix[]> getScenePoints() {
        var points2d = getSplinePoints();
        var vertices = new ArrayList<Matrix[]>();
        var angleN = sceneParameters.getAngleN() * sceneParameters.getVirtualAngleN();
        var a = getWidth() * 1. / getHeight();
        var psStep = sceneParameters.getSplineN();
        for (int j = 0; j < angleN; j++) {
            for (int i = 0; i < points2d.size(); i += psStep) {
                var p = points2d.get(i);
                var Fiv = p.getX();
                var Fuv = p.getY();
                vertices.add(new Matrix[]{
                        new Matrix(new double[]{
                                Fiv * Math.cos(j * 2 * Math.PI / angleN) * a,
                                Fiv * Math.sin(j * 2 * Math.PI / angleN) * a,
                                Fuv,
                                1
                        }),
                        new Matrix(new double[]{
                                Fiv * Math.cos((j + 1) % angleN * 2 * Math.PI / angleN) * a,
                                Fiv * Math.sin((j + 1) % angleN * 2 * Math.PI / angleN) * a,
                                Fuv,
                                1
                        }),
                });

                if (i + psStep >= points2d.size() - 1 && i != points2d.size() - 1) {
                    i = points2d.size() - 1 - psStep;
                }
            }
        }

        var normalAngleN = sceneParameters.getAngleN();

        for (int i = 1; i < points2d.size(); i++) {
            for (int j = 0; j < normalAngleN; j++) {
                var p1 = points2d.get(i);
                var p2 = points2d.get(i - 1);

                vertices.add(new Matrix[]{
                        new Matrix(new double[]{
                                p1.getX() * Math.cos(j * 2 * Math.PI / normalAngleN) * a,
                                p1.getX() * Math.sin(j * 2 * Math.PI / normalAngleN) * a,
                                p1.getY(),
                                1
                        }),
                        new Matrix(new double[]{
                                p2.getX() * Math.cos(j * 2 * Math.PI / normalAngleN) * a,
                                p2.getX() * Math.sin(j * 2 * Math.PI / normalAngleN) * a,
                                p2.getY(),
                                1
                        }),
                });
            }
        }
        return vertices;
    }

    public void reset() {
        this.points.clear();
        repaint();
    }
}

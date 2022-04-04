package fit.g19202.baksheev.lab4.lib;

public class BSpline {
    private static final Matrix M_s = new Matrix(new double[][]{
            {-1. / 6, 3. / 6, -3. / 6, 1. / 6},
            {3. / 6, -6. / 6, 3. / 6, 0. / 6},
            {-3. / 6, 0. / 6, 3. / 6, 0. / 6},
            {1. / 6, 4. / 6, 1. / 6, 0. / 6}
    });
    private final Point2D[] points;

    public BSpline(Point2D[] points) {
        this.points = points;
    }

    public Point2D[] getSplinePoints(int N) {
        if (points.length < 4) {
            return null;
        }
        var xPoints = new double[4];
        var yPoints = new double[4];
        int upto = points.length - 3;
        var totalPoints = new Point2D[(N + 1) * upto];
        for (int i = 1; i < upto + 1; i++) {
            for (int cnt = 0; cnt < 4; cnt++) {
                xPoints[cnt] = points[i + cnt - 1].getX();
                yPoints[cnt] = points[i + cnt - 1].getY();
            }
            var G_su = new Matrix(xPoints).transpose();
            var G_sv = new Matrix(yPoints).transpose();
            for (var j = 0; j <= N; j++) {
                var t = j * 1. / N;
                var T = new Matrix(new double[]{t * t * t, t * t, t, 1});
                var uPoints = T
                        .times(M_s)
                        .times(G_su);
                var vPoints = T.times(M_s).times(G_sv);
                totalPoints[(i - 1) * (N + 1) + j] = new Point2D(
                        uPoints.get(0, 0),
                        vPoints.get(0, 0)
                );
            }
        }
//        System.out.println("start");
//        for (var point : totalPoints) {
//            System.out.println(point.toString());
//        }
        return totalPoints;
    }
}

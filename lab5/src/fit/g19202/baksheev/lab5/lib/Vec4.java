package fit.g19202.baksheev.lab5.lib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Vec4 {
    @Getter
    private double x, y, z, w;

    public Vec4(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }

    // do not add w
    public Vec4 add(Vec4 another) {
        return new Vec4(x + another.x, y + another.y, z + another.z, w);
    }

    // do not sub w
    public Vec4 sub(Vec4 another) {
        return new Vec4(x - another.x, y - another.y, z - another.z, w);
    }

    public Vec4 times(Matrix A) {
        var res = new Matrix(new double[]{x, y, z, w}).times(A);
        return new Vec4(res.get(0, 0), res.get(0, 1), res.get(0, 2), res.get(0, 3));
    }

    public Vec4 times(double k) {
        return new Vec4(x * k, y * k, z * k, w);
    }

    public double abs() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vec4 normalized() {
        return times(1 / abs());
    }

    public Vec4 wize() {
        if (w == 0.) {
            w = 1;
        }
        return new Vec4(x / w, y / w, z / w, 1.);
    }

    public double[] getData(double w) {
        return new double[]{x, y, z, w};
    }

    public Vec4 cross(Vec4 another) {
        var newX = this.y * another.z - this.z * another.y;
        var newY = this.z * another.x - this.x * another.z;
        var newZ = this.x * another.y - this.y * another.x;

        return new Vec4(newX, newY, newZ);
    }
}

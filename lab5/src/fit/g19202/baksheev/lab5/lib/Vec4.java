package fit.g19202.baksheev.lab5.lib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.awt.*;

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

    public Vec4(Matrix A) {
        this.x = A.get(0, 0);
        this.y = A.get(1, 0);
        this.z = A.get(2, 0);
        this.w = A.get(3, 0);
    }

    // do not add w
    public Vec4 add(Vec4 another) {
        return new Vec4(x + another.x, y + another.y, z + another.z);
    }

    // do not sub w
    public Vec4 sub(Vec4 another) {
        return new Vec4(x - another.x, y - another.y, z - another.z);
    }

    public Vec4 mul(double k) {
        return new Vec4(x * k, y * k, z * k);
    }

    public double abs() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vec4 normalized() {
        return mul(1 / abs());
    }

    public double[] getData(double tw) {
        return new double[]{x, y, z, tw};
    }

    public double[] getData() {
        return new double[]{x, y, z, w};
    }

    public Vec4 cross(Vec4 another) {
        var newX = this.y * another.z - this.z * another.y;
        var newY = this.z * another.x - this.x * another.z;
        var newZ = this.x * another.y - this.y * another.x;

        return new Vec4(newX, newY, newZ);
    }

    public double dot(Vec4 another) {
        return this.x * another.x +
                this.y * another.y +
                this.z * another.z;
    }

    public Vec4 vdot(Vec4 another) {
        return new Vec4(this.x * another.x,
                this.y * another.y,
                this.z * another.z);
    }

    public Vec4 wize() {
        if (w == 0) return this;
        return new Vec4(
                this.x / w,
                this.y / w,
                this.z / w,
                1.
        );
    }

    private static int ctrim(int c) {
        if (c > 255) {
            return 255;
        }
        return Math.max(c, 0);
    }

    public Color toColor() {
        return new Color(
                ctrim((int) (this.x * 255)),
                ctrim((int) (this.y * 255)),
                ctrim((int) (this.z * 255))
        );
    }

    public double dist2(Vec4 b) {
        return (x - b.x) * (x - b.x) +
                (y - b.y) * (y - b.y) +
                (z - b.z) * (z - b.z);
    }

    public double getI(int i) {
        if (i == 0) return x;
        if (i == 1) return y;
        if (i == 2) return z;
        throw new RuntimeException("invalid index");
    }

    public static Vec4 getE(int i) {
        if (i == 0) return new Vec4(1, 0, 0);
        if (i == 1) return new Vec4(0, 1, 0);
        if (i == 2) return new Vec4(0, 0, 1);
        throw new RuntimeException("invalid index");
    }
}

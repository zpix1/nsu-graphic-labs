package fit.g19202.baksheev.lab4.lib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@AllArgsConstructor
public class Point2D implements Serializable {
    @Getter
    protected double x;
    @Getter
    protected double y;
}

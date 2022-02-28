package fit.g19202.baksheev.lab1.drawing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder=true)
public class DrawContext {
    private int lineWidth;
    private Color color;
    private int angleCount;
    private int radius;
    private int angle;
}

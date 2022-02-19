package FIT_19202_Baksheev_Lab1.drawing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
@Data
public class DrawContext {
    private int lineWidth;
    private Color color;
    private int angleCount;
    private int radius;
    private int angle;
}

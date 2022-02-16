package FIT_19202_Baksheev_Lab1.drawing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@AllArgsConstructor
public class DrawContext {
    @Getter
    @Setter
    private int lineWidth;

    @Getter
    @Setter
    private Color color;
}

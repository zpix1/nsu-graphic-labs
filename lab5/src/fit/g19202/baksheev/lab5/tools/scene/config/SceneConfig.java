package fit.g19202.baksheev.lab5.tools.scene.config;

import fit.g19202.baksheev.lab5.lib.Vec4;
import fit.g19202.baksheev.lab5.lib.txt.TxtSerializable;
import fit.g19202.baksheev.lab5.tools.scene.config.datas.SceneShape;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.InputStream;
import java.io.OutputStream;

@AllArgsConstructor
@Getter
@Setter
public class SceneConfig implements TxtSerializable {
    // рассеянный свет в пространстве RGB в диапазоне 0..255
    private double Ar, Ag, Ab;
    // число точечных источников в сцене
    // далее идёт NL строк, каждая из которых описывает точечный источник света
    private int NL;
    // LX, LY, LZ – положение источника,
    // LR, LG, LB – цвет источника в пространстве RGB в диапазоне 0..255
    private Color[] lColors;
    private Vec4[] lPositions;
    private SceneShape[] shapes;

    @Override
    public void loadFromStream(InputStream is) {

    }

    @Override
    public void saveToStream(OutputStream os) {

    }
}

package fit.g19202.baksheev.lab5.lib.datas;

import fit.g19202.baksheev.lab5.lib.Vec4;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OpticalParameters {
    private Vec4 Kd;
    private Vec4 Ks;
    private double Power;
}

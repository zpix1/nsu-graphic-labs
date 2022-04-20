package fit.g19202.baksheev.lab5.tools.scene.config.datas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OpticalParameters {
    private double KDr, KDg, KDb, KSr, KSg, KSb, Power;
}

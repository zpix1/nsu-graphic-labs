package fit.g19202.baksheev.lab2.tools;

import fit.g19202.baksheev.lab2.tools.filters.*;
import fit.g19202.baksheev.lab2.tools.shape.FitTool;
import fit.g19202.baksheev.lab2.tools.shape.RotateTool;
import fit.g19202.baksheev.lab2.tools.utilities.AboutTool;
import fit.g19202.baksheev.lab2.tools.utilities.ExitTool;
import fit.g19202.baksheev.lab2.tools.utilities.OpenTool;
import fit.g19202.baksheev.lab2.tools.utilities.SaveTool;
import lombok.Getter;

import java.util.List;

public class ToolManager {
    @Getter
    private final List<Tool> toolList;

    public static class ToolManagerHolder {
        public static final ToolManager HOLDER_INSTANCE = new ToolManager();
    }

    public static ToolManager getInstance() {
        return ToolManagerHolder.HOLDER_INSTANCE;
    }

    private ToolManager() {
        toolList = List.of(
                new OpenTool(),
                new SaveTool(),
                new ExitTool(),
                new FitTool(),
                new ContourTool(),
                new NegativeFilter(),
                new BlackWhiteTool(),
                new OrderedDitheringTool(),
                new FSDithering(),
                new GammaCorrectionTool(),
                new SharpeningTool(),
                new EmbossingTool(),
                new WatercolorTool(),
                new RotateTool(),
                new SmoothingTool(),
                new AboutTool()
        );
    }
}

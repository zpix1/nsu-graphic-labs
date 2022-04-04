package fit.g19202.baksheev.lab4.tools;

import fit.g19202.baksheev.lab4.tools.utilities.*;
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
                new AboutTool()
        );
    }
}

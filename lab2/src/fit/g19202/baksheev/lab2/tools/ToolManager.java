package fit.g19202.baksheev.lab2.tools;

import fit.g19202.baksheev.lab2.tools.utilities.ImageLoaderTool;
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

    public ToolManager() {
        toolList = List.of(new ImageLoaderTool[]{
                new ImageLoaderTool()
        });
    }
}

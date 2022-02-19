package FIT_19202_Baksheev_Lab1.drawing;

import FIT_19202_Baksheev_Lab1.drawing.drawtools.FillTool;
import FIT_19202_Baksheev_Lab1.drawing.drawtools.LineTool;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ToolManager {
    private final List<DrawTool> toolList;

    @Getter
    private int currentToolIndex = 0;

    public ToolManager() {
        toolList = new ArrayList<>();
        toolList.add(new LineTool());
        toolList.add(new FillTool());
    }

    public void setTool(int index) {
        currentToolIndex = index;
    }

    public void nextTool() {
        System.out.println("Next tool");
        currentToolIndex = (currentToolIndex + 1) % toolList.size();
    }

    public DrawTool getCurrentTool() {
        return toolList.get(currentToolIndex);
    }

    public List<DrawTool> getAllTools() {
        return toolList;
    }
}
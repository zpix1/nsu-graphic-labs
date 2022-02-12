package ru.nsu.fit.g19202.baksheev.lab1paint;

import lombok.Getter;
import ru.nsu.fit.g19202.baksheev.lab1paint.drawtools.LineTool;

import java.util.ArrayList;
import java.util.List;

public class ToolManager {
    private final List<DrawTool> toolList;

    private int currentToolIndex = 0;

    @Getter
    private DrawContext context;

    public ToolManager() {
        toolList = new ArrayList<>();
        toolList.add(new LineTool());
    }

    public void nextTool() {
        System.out.println("Next tool");
        currentToolIndex = (currentToolIndex + 1) % toolList.size();
    }

    public DrawTool getCurrentTool() {
        return toolList.get(currentToolIndex);
    }
}

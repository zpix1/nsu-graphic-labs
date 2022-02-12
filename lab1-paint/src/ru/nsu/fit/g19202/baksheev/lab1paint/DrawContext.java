package ru.nsu.fit.g19202.baksheev.lab1paint;

import lombok.AllArgsConstructor;
import lombok.Data;
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

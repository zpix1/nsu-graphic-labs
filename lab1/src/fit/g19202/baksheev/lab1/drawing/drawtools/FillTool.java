package fit.g19202.baksheev.lab1.drawing.drawtools;

import fit.g19202.baksheev.lab1.drawing.DrawContext;
import fit.g19202.baksheev.lab1.drawing.DrawTool;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.util.Stack;

public class FillTool implements DrawTool {
    @Data
    private static class Span {
        public int y;
        public int leftBound;
        public int rightBound;
    }


    @Override
    public String getName() {
        return "Fill";
    }

    @Override
    public void onClick(BufferedImage img, int x, int y, DrawContext context) {
    }

    @Override
    public void onPress(BufferedImage img, int x, int y, DrawContext context) {
        spanFilling(img, x, y, img.getRGB(x, y), context.getColor().getRGB());
    }

    @Override
    public void onRelease(BufferedImage img, int x, int y, int xd, int yd, DrawContext context) {

    }

    private Span getSpanAtPoint(BufferedImage img, int x, int y, int rgbColor) {
        var span = new Span();
        span.y = y;
        span.rightBound = span.leftBound = x;
        while (span.rightBound < img.getWidth()
                && img.getRGB(span.rightBound, y) == rgbColor) {
            span.rightBound++;
        }
        while (span.leftBound >= 0
                && img.getRGB(span.leftBound, y) == rgbColor) {
            span.leftBound--;
        }
        span.leftBound++;
        return span;
    }

    private void fillSpan(BufferedImage img, Span span, int rgbColor) {
        for (int i = span.leftBound; i < span.rightBound; i++) {
            img.setRGB(i, span.y, rgbColor);
        }
    }

    private boolean isOk(BufferedImage img, int x, int y, int color) {
        if (!((x >= 0 && x < img.getWidth()) && (y >= 0 && y < img.getHeight()))) {
            return false;
        }
        return img.getRGB(x, y) == color;
    }

    private void spanFilling(BufferedImage img, int seed_x, int seed_y, int fillFrom, int fillTo) {
        if (fillTo == fillFrom) {
            return;
        }
        var stack = new Stack<Span>();
        var startingSpan = getSpanAtPoint(img, seed_x, seed_y, fillFrom);
        stack.add(startingSpan);
        fillSpan(img, startingSpan, fillTo);
        System.out.println("starting span" + startingSpan);
        while (!stack.empty()) {
            System.out.println("Stack size " + stack.size());
            System.out.println("Top span y=" + stack.peek().y + " " + stack.peek().rightBound + " " + stack.peek().leftBound);
            var span = stack.pop();
            for (int i = span.leftBound; i < span.rightBound; i++) {
                if (isOk(img, i, span.y + 1, fillFrom)) {
                    var newSpan = getSpanAtPoint(img, i, span.y + 1, fillFrom);
                    fillSpan(img, newSpan, fillTo);
                    stack.add(newSpan);
                }
            }
            for (int i = span.leftBound; i < span.rightBound; i++) {
                if (isOk(img, i, span.y - 1, fillFrom)) {
                    var newSpan = getSpanAtPoint(img, i, span.y - 1, fillFrom);
                    fillSpan(img, newSpan, fillTo);
                    stack.add(newSpan);
                }
            }
        }
    }
}

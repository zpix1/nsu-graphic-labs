package ru.nsu.fit.g19202.baksheev.lab1paint;

import ru.nsu.fit.g19202.baksheev.lab1paint.drawtools.LineTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel implements MouseListener {
    private int x, y, xd = -1, yd = -1;
    private BufferedImage img;
    private DrawTool currentTool = new LineTool();
    private Color currentColor = Color.BLACK;
    private int height = 1000;
    private int width = 1000;

    static class ResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            e.getComponent().setSize(new Dimension(e.getComponent().getWidth(), e.getComponent().getHeight()));
            super.componentResized(e);
        }
    }

//    @Override
//    public void setSize(Dimension d) {
//        img = Utils.resize(img, d.width, d.height);
//        super.setSize(d);
//    }

    public ImagePanel() {
        addMouseListener(this);
//        addComponentListener(new ResizeListener());
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        clean();

//        addMouseMotionListener(new MouseAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                if (xd >= 0) {
//                    Graphics g = getGraphics();
//                    g.setColor(Color.blue);
//                    g.drawLine(xd, yd, e.getX(), e.getY());
//                }
//
//                xd = e.getX();
//                yd = e.getY();
//            }
//        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintComponent issued");
        g.drawImage(img, 0, 0, width, height, null);
//        if (draw) {
//            Graphics2D g2 = (Graphics2D) g;
//            g2.setColor(Color.RED);
//            g2.setStroke(new BasicStroke(4));
//            g2.drawLine(0, 0, getWidth() - 1, getHeight() - 1);
//
//
//            // Работа с изображением (BufferedImage)
//            /*
//             * Получить цвет пикселя:
//             * int color = img.getRGB(x, y);
//             * Задать цвет пикселя:
//             * img.setRGB(x, y, Color.GREEN.getRGB());
//             * Подготовка изображения должна проводиться в другом методе, который будет вызывать repaint();
//             *
//             * В paintComponent() в итоге просто отрисовывается обработанное изображение:
//             * g2.drawImage(img, x, y, getWidth(), getHeight(), null);
//             */
//        }
    }

    public void clean() {
        Graphics2D graphics = img.createGraphics();
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0, img.getWidth(), img.getHeight());

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent ev) {
        System.out.println("Mouse clicked: (" + x + ", " + y + ")");
        currentTool.onClick(img, x, y, currentColor);

        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent ev) {
    }

    @Override
    public void mouseExited(MouseEvent ev) {
    }

    @Override
    public void mousePressed(MouseEvent ev) {
        xd = x = ev.getX();
        yd = y = ev.getY();
        System.out.println("Mouse pressed: (" + x + ", " + y + ")");
        currentTool.onPress(img, x, y, currentColor);

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent ev) {
        x = ev.getX();
        y = ev.getY();
        System.out.println("Mouse released: (" + x + ", " + y + ") from (" + xd + ", " + yd + ")");
        currentTool.onRelease(img, x, y, xd, yd, currentColor);
        xd = -1;
        yd = -1;

        repaint();
    }
}

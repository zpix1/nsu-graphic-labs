package FIT_19202_Baksheev_Lab1;

import FIT_19202_Baksheev_Lab1.drawing.DrawContext;
import FIT_19202_Baksheev_Lab1.drawing.ToolManager;
import FIT_19202_Baksheev_Lab1.drawing.Utils;

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
    private int height = 1000;
    private int width = 1000;
    private final ToolManager toolManager;
    private final DrawContext context;

    static class ResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            e.getComponent().setSize(new Dimension(e.getComponent().getWidth(), e.getComponent().getHeight()));
            super.componentResized(e);
        }
    }

    @Override
    public void setSize(Dimension d) {
        if (d.width > width && d.height > height) {
            width = d.width;
            height = d.height;
        } else if (d.width > width) {
            width = d.width;
        } else if (d.height > height) {
            height = d.height;
        } else {
            return;
        }
        img = Utils.resize(img, width, height);
        super.setSize(d);
    }

    public ImagePanel(ToolManager toolManager, DrawContext context) {
        this.toolManager = toolManager;
        this.context = context;

        addMouseListener(this);
        addComponentListener(new ResizeListener());
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
        if (ev.getButton() == 3) {
            toolManager.nextTool();
            return;
        }

        System.out.println("Mouse clicked: (" + x + ", " + y + ")");
        toolManager.getCurrentTool().onClick(img, x, y, context);

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
        toolManager.getCurrentTool().onPress(img, x, y, context);

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent ev) {
        x = ev.getX();
        y = ev.getY();
        System.out.println("Mouse released: (" + x + ", " + y + ") from (" + xd + ", " + yd + ")");
        toolManager.getCurrentTool().onRelease(img, x, y, xd, yd, context);
        xd = -1;
        yd = -1;

        repaint();
    }
}

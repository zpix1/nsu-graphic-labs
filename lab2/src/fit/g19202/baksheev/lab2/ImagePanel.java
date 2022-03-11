package fit.g19202.baksheev.lab2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel implements MouseListener {
    private BufferedImage img;

    static class ResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            e.getComponent().setSize(new Dimension(e.getComponent().getWidth(), e.getComponent().getHeight()));
            super.componentResized(e);
        }
    }

    public BufferedImage getImage() {
        return img;
    }

    public void setImage(BufferedImage img) {
        this.img = img;
        repaint();
    }

    public ImagePanel() {
        addMouseListener(this);
        addComponentListener(new ResizeListener());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        } else {
            g.drawRect(0, 0, 100, 100);
        }
    }

    @Override
    public void mouseClicked(MouseEvent ev) {
    }

    @Override
    public void mouseEntered(MouseEvent ev) {
    }

    @Override
    public void mouseExited(MouseEvent ev) {
    }

    @Override
    public void mousePressed(MouseEvent ev) {
    }

    @Override
    public void mouseReleased(MouseEvent ev) {
    }
}

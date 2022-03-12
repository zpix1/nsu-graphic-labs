package fit.g19202.baksheev.lab2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel implements MouseListener {
    private BufferedImage img;
    private static final int borderWidth = 5;

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
        revalidate();
    }

    public ImagePanel() {
        addMouseListener(this);
        addComponentListener(new ResizeListener());
        setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(getBackground().getRGB()), borderWidth),
                        BorderFactory.createDashedBorder(Color.BLACK, 1, 1)
                )
        );
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            g.drawImage(img, borderWidth + 1, borderWidth + 1, img.getWidth() + borderWidth + 1, img.getHeight() + borderWidth + 1, null);
            setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
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

package fit.g19202.baksheev.lab5.tools.scene;

import cg.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDialog extends MainFrame {
    private final BufferedImage image;

    class ImagePanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }

    public ImageDialog(BufferedImage image) {
        super(640, (int) (640 * image.getHeight() * 1. / image.getWidth()) + 40, "Result");
        this.image = image;
        addSubMenu("File", 0);
        addMenuItem("File/Save", "Save", 0, new File("save.gif"), event -> {
            var file = getSaveFileName(new String[]{"png", "jpeg", "bmp", "gif"}, "Images");
            if (file == null) {
                System.out.println("File not selected");
            } else {
                try {
                    if (file.getExtension() != null) {
                        ImageIO.write(image, file.getExtension(), file.getFile());
                    } else {
                        ImageIO.write(image, "png", new File(file.getFile().getAbsolutePath() + ".png"));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        addToolBarButton("File/Save");
        add(new ImagePanel());
        setVisible(true);
    }
}

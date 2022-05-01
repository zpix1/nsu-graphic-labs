package fit.g19202.baksheev.lab5.tools.scene.ui;

import fit.g19202.baksheev.lab5.lib.UIUtils;
import fit.g19202.baksheev.lab5.tools.scene.RayTracer;
import fit.g19202.baksheev.lab5.tools.scene.config.RenderConfig;
import fit.g19202.baksheev.lab5.tools.scene.config.SceneConfig;

import javax.swing.*;
import java.awt.*;

public class RenderDialog extends JFrame {
    public RenderDialog(RenderConfig renderConfig, SceneConfig sceneConfig) {
        super("Render");
        setMinimumSize(new Dimension(200, 300));
        setResizable(false);
        setLayout(new GridLayout(5, 1));
        add(UIUtils.getDoubleInput("Gamma value", renderConfig.getGamma(), 0.1, 10, 0.1, renderConfig::setGamma));
        add(UIUtils.getIntegerInput("Depth value", renderConfig.getDepth(), 0, 3, 1, renderConfig::setDepth));
        add(UIUtils.getIntegerInput("Width", renderConfig.getSWidth(), 10, 4000, 1, renderConfig::setSWidth));
        add(UIUtils.getIntegerInput("Height", renderConfig.getSHeight(), 10, 4000, 1, renderConfig::setSHeight));
        var runButton = new JButton("Run");
        runButton.addActionListener(event -> {
            runButton.setEnabled(false);
            var rayTracer = new RayTracer();
            rayTracer.setRenderConfig(renderConfig);
            rayTracer.setSceneConfig(sceneConfig);
            final var progressTracker = new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(50);
                        if (!Double.isNaN(rayTracer.getProgress())) {
                            runButton.setText(String.format("Running (%.2f%% done)", rayTracer.getProgress() * 100));
                        }
                    }
                } catch (InterruptedException e) {
                    return;
                }
            });
            new Thread(() -> {
                runButton.setEnabled(false);
                progressTracker.start();
                var image = rayTracer.render(renderConfig.getSWidth(), renderConfig.getSHeight());
                new ImageDialog(image);
                progressTracker.interrupt();
                runButton.setText(String.format("Done!", rayTracer.getProgress() * 100));
            }).start();
        });
        add(runButton);
        setVisible(true);
    }
}

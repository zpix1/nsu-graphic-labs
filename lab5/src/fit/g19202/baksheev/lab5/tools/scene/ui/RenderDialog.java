package fit.g19202.baksheev.lab5.tools.scene.ui;

import fit.g19202.baksheev.lab5.lib.UIUtils;
import fit.g19202.baksheev.lab5.tools.scene.RayTracer;
import fit.g19202.baksheev.lab5.tools.scene.config.RenderConfig;
import fit.g19202.baksheev.lab5.tools.scene.config.SceneConfig;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RenderDialog extends JFrame {
    private AtomicInteger poolSize;

    public RenderDialog(RenderConfig renderConfig, SceneConfig sceneConfig) {
        super("Render");
        setMinimumSize(new Dimension(200, 300));
        setResizable(false);
        setLayout(new GridLayout(6, 1));
        poolSize = new AtomicInteger(5);
        add(UIUtils.getDoubleInput("Gamma value", renderConfig.getGamma(), 0.1, 10, 0.1, renderConfig::setGamma));
        add(UIUtils.getIntegerInput("Depth value", renderConfig.getDepth(), 0, 10, 1, renderConfig::setDepth));
        add(UIUtils.getIntegerInput("Width", renderConfig.getSWidth(), 10, 40000, 1, renderConfig::setSWidth));
        add(UIUtils.getIntegerInput("Height", renderConfig.getSHeight(), 10, 40000, 1, renderConfig::setSHeight));
        add(UIUtils.getIntegerInput("Pool Size", poolSize.get(), 1, 50, 1, poolSize::getAndSet));
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
                var start = System.nanoTime();
                var image = rayTracer.render(renderConfig.getSWidth(), renderConfig.getSHeight(), poolSize.get());
                var end = System.nanoTime();
                progressTracker.interrupt();
                var sImg = renderConfig.getSHeight() * renderConfig.getSHeight();
                var totalMs = (end - start) / 1e6;
                runButton.setText("Done! Run again?");
                runButton.setEnabled(true);
                var title = String.format("Result %dx%d in %.0fms (%.0f p/ms, %d threads)", image.getWidth(), image.getHeight(), totalMs, sImg / totalMs, poolSize.get());
                new ImageDialog(image, title);
            }).start();
        });
        add(runButton);
        setVisible(true);
    }
}

package cg;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

public class FileUtils {
    private static File dataDirectory = null;

    @AllArgsConstructor
    @Getter
    public static class FileWithExtension {
        private File file;
        private String extension;
    }

    /**
     * Returns File pointing to Data directory of current project. If Data directory is not found, returns project directory.
     *
     * @return File object.
     */
    public static File getDataDirectory() {
        if (dataDirectory == null) {
            try {
                String path = URLDecoder.decode(MainFrame.class.getProtectionDomain().getCodeSource().getLocation().getFile(), Charset.defaultCharset().toString());
                dataDirectory = new File(path).getParentFile();
            } catch (UnsupportedEncodingException e) {
                dataDirectory = new File(".");
            }
            if (dataDirectory == null || !dataDirectory.exists()) dataDirectory = new File(".");
            for (File f : dataDirectory.listFiles()) {
                if (f.isDirectory() && f.getName().endsWith("_Data")) {
                    dataDirectory = f;
                    break;
                }
            }
        }
        return dataDirectory;
    }

    public static String getExtension(String filename) {
        var parts = filename.split("\\.");
        if (parts.length == 1) {
            return null;
        }
        return parts[parts.length - 1];
    }

    /**
     * Prompts user for file name to save and returns it
     *
     * @param parent      - parent frame for file selection dialog
     * @param extension   - preferred file extension (example: "txt")
     * @param description - description of specified file type (example: "Text files")
     * @return File specified by user or null if user canceled operation
     * @see MainFrame.getOpenFileName
     */
    public static FileWithExtension getSaveFileName(JFrame parent, String[] extensions, String description) {
        JFileChooser fileChooser = new JFileChooser();
        FileFilter filter = new ExtensionFileFilter(extensions, description);
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setCurrentDirectory(getDataDirectory());
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            return new FileWithExtension(f, getExtension(f.getName()));
        }
        return null;
    }

    /**
     * Prompts user for file name to open and returns it
     *
     * @param parent      - parent frame for file selection dialog
     * @param extension   - preferred file extension (example: "txt")
     * @param description - description of specified file type (example: "Text files")
     * @return File specified by user or null if user canceled operation
     * @see MainFrame.getSaveFileName
     */
    public static FileWithExtension getOpenFileName(JFrame parent, String[] extensions, String description) {
        JFileChooser fileChooser = new JFileChooser();
        FileFilter filter = new ExtensionFileFilter(extensions, description);
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setCurrentDirectory(getDataDirectory());
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            return new FileWithExtension(f, getExtension(f.getName()));
        }
        return null;
    }
}

package cg;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * File filter which leaves only directories and files with specific extension
 *
 * @author Tagir F. Valeev
 */
public class ExtensionFileFilter extends FileFilter {
    String[] extensions;
    String description;

    /**
     * Constructs filter
     *
     * @param extensions   - extensions (without point), for example, "txt"
     * @param description - file type description, for example, "Text files"
     */
    public ExtensionFileFilter(String[] extensions, String description) {
        this.extensions = extensions;
        this.description = description;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) return true;
        for (var extension : extensions) {
            if (f.getName().toLowerCase().endsWith("." + extension.toLowerCase()))
                return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return description + " (*." + String.join(", ", extensions) + ")";
    }
}
package cg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.InvalidParameterException;

/**
 * MainFrame - main application frame
 * Application should subclass it to create its own implementation
 *
 * @author Tagir F. Valeev
 */
public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JMenuBar menuBar;
    protected JToolBar toolBar;

    public enum ButtonType {
        BASIC,
        RADIO
    }

    /**
     * Default constructor which sets up L&F and creates tool-bar and menu-bar
     */
    public MainFrame() {
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (Exception e) {
//        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        toolBar = new JToolBar("Main toolbar");
        toolBar.setRollover(true);
        add(toolBar, BorderLayout.PAGE_START);
    }

    /**
     * Constructor where you can specify window size and title
     *
     * @param x     - horizontal size of newly created window
     * @param y     - vertical size of newly created window
     * @param title - window title
     */
    public MainFrame(int x, int y, String title) {
        this();
        setSize(x, y);
        setLocationByPlatform(true);
        setTitle(title);
    }

    public JMenuItem createMenuItem(String title, String tooltip, int mnemonic, File icon, ActionListener actionMethod) throws SecurityException {
        return createMenuItem(title, tooltip, mnemonic, icon, actionMethod, ButtonType.BASIC);
    }

    /**
     * Shortcut method to create menu item
     * Note that you have to insert it into proper place by yourself
     *
     * @param title        - menu item title
     * @param tooltip      - floating tooltip describing menu item
     * @param mnemonic     - mnemonic key to activate item via keyboard
     * @param icon         - file name containing icon (must be located in 'resources' subpackage relative to your implementation of MainFrame), can be null
     * @param actionMethod - String containing method name which will be called when menu item is activated (method should not take any parameters)
     * @return created menu item
     * @throws NoSuchMethodException - when actionMethod method not found
     * @throws SecurityException     - when actionMethod method is inaccessible
     */
    public JMenuItem createMenuItem(String title, String tooltip, int mnemonic, File icon, ActionListener actionMethod, ButtonType type) throws SecurityException {
        JMenuItem item = switch (type) {
            case BASIC -> new JMenuItem(title);
            case RADIO -> new JRadioButtonMenuItem(title);
        };
        item.setMnemonic(mnemonic);
        item.setToolTipText(tooltip);
        System.out.println(new ImageIcon(getClass().getResource("/" + icon), title));
        if (icon != null)
            item.setIcon(new ImageIcon(getClass().getResource("/" + icon), title));
        item.addActionListener(actionMethod);
        return item;
    }

    /**
     * Shortcut method to create menu item (without icon)
     * Note that you have to insert it into proper place by yourself
     *
     * @param title        - menu item title
     * @param tooltip      - floating tooltip describing menu item
     * @param mnemonic     - mnemonic key to activate item via keyboard
     * @param actionMethod - String containing method name which will be called when menu item is activated (method should not take any parameters)
     * @return created menu item
     * @throws SecurityException     - when actionMethod method is inaccessible
     */
    public JMenuItem createMenuItem(String title, String tooltip, int mnemonic, ActionListener actionMethod) throws SecurityException {
        return createMenuItem(title, tooltip, mnemonic, null, actionMethod);
    }

    /**
     * Creates submenu and returns it
     *
     * @param title    - submenu title
     * @param mnemonic - mnemonic key to activate submenu via keyboard
     * @return created submenu
     */
    public JMenu createSubMenu(String title, int mnemonic) {
        JMenu menu = new JMenu(title);
        menu.setMnemonic(mnemonic);
        return menu;
    }

    /**
     * Creates submenu and inserts it to the specified location
     *
     * @param title    - submenu title with full path (just submenu title for top-level submenus)
     *                 example: "File/New" - will create submenu "New" under menu "File" (provided that menu "File" was previously created)
     * @param mnemonic - mnemonic key to activate submenu via keyboard
     */
    public void addSubMenu(String title, int mnemonic) {
        MenuElement element = getParentMenuElement(title);
        if (element == null)
            throw new InvalidParameterException("Menu path not found: " + title);
        JMenu subMenu = createSubMenu(getMenuPathName(title), mnemonic);
        if (element instanceof JMenuBar)
            ((JMenuBar) element).add(subMenu);
        else if (element instanceof JMenu)
            ((JMenu) element).add(subMenu);
        else if (element instanceof JPopupMenu)
            ((JPopupMenu) element).add(subMenu);
        else
            throw new InvalidParameterException("Invalid menu path: " + title);
    }

    public JMenuItem addMenuItem(String title, String tooltip, int mnemonic, File icon, ActionListener actionMethod) throws SecurityException {
        return addMenuItem(title, tooltip, mnemonic, icon, actionMethod, ButtonType.BASIC);
    }
    /**
     * Creates menu item and adds it to the specified menu location
     *
     * @param title        - menu item title with full path
     * @param tooltip      - floating tooltip describing menu item
     * @param mnemonic     - mnemonic key to activate item via keyboard
     * @param icon         - file name containing icon (must be located in 'resources' subpackage relative to your implementation of MainFrame), can be null
     * @param actionMethod - String containing method name which will be called when menu item is activated (method should not take any parameters)
     * @throws NoSuchMethodException     - when actionMethod method not found
     * @throws SecurityException         - when actionMethod method is inaccessible
     * @throws InvalidParameterException - when specified menu location not found
     */
    public JMenuItem addMenuItem(String title, String tooltip, int mnemonic, File icon, ActionListener actionMethod, ButtonType type) throws SecurityException {
        MenuElement element = getParentMenuElement(title);
        if (element == null)
            throw new InvalidParameterException("Menu path not found: " + title);
        JMenuItem item = createMenuItem(getMenuPathName(title), tooltip, mnemonic, icon, actionMethod, type);
        if (element instanceof JMenu)
            ((JMenu) element).add(item);
        else if (element instanceof JPopupMenu)
            ((JPopupMenu) element).add(item);
        else
            throw new InvalidParameterException("Invalid menu path: " + title);
        return item;
    }

    /**
     * Creates menu item (without icon) and adds it to the specified menu location
     *
     * @param title        - menu item title with full path
     * @param tooltip      - floating tooltip describing menu item
     * @param mnemonic     - mnemonic key to activate item via keyboard
     * @param actionMethod - String containing method name which will be called when menu item is activated (method should not take any parameters)
     * @throws NoSuchMethodException     - when actionMethod method not found
     * @throws SecurityException         - when actionMethod method is inaccessible
     * @throws InvalidParameterException - when specified menu location not found
     */
    public void addMenuItem(String title, String tooltip, int mnemonic, ActionListener actionMethod) throws SecurityException {
        addMenuItem(title, tooltip, mnemonic, null, actionMethod);
    }

    /**
     * Adds menu separator in specified menu location
     *
     * @param title - menu location
     * @throws InvalidParameterException - when specified menu location not found
     */
    public void addMenuSeparator(String title) {
        MenuElement element = getMenuElement(title);
        if (element == null)
            throw new InvalidParameterException("Menu path not found: " + title);
        if (element instanceof JMenu)
            ((JMenu) element).addSeparator();
        else if (element instanceof JPopupMenu)
            ((JPopupMenu) element).addSeparator();
        else
            throw new InvalidParameterException("Invalid menu path: " + title);
    }

    private String getMenuPathName(String menuPath) {
        int pos = menuPath.lastIndexOf('/');
        if (pos > 0)
            return menuPath.substring(pos + 1);
        else
            return menuPath;
    }

    /**
     * Looks for menu element by menu path ignoring last path component
     *
     * @param menuPath - '/'-separated path to menu item (example: "Help/About...")
     * @return found menu item or null if no such item found
     */
    private MenuElement getParentMenuElement(String menuPath) {
        int pos = menuPath.lastIndexOf('/');
        if (pos > 0)
            return getMenuElement(menuPath.substring(0, pos));
        else
            return menuBar;
    }

    /**
     * Looks for menu element by menu path
     *
     * @param menuPath - '/'-separated path to menu item (example: "Help/About...")
     * @return found menu item or null if no such item found
     */
    public MenuElement getMenuElement(String menuPath) {
        MenuElement element = menuBar;
        for (String pathElement : menuPath.split("/")) {
            MenuElement newElement = null;
            for (MenuElement subElement : element.getSubElements()) {
                if ((subElement instanceof JMenu && ((JMenu) subElement).getText().equals(pathElement))
                        || (subElement instanceof JMenuItem && ((JMenuItem) subElement).getText().equals(pathElement))) {
                    if (subElement.getSubElements().length == 1 && subElement.getSubElements()[0] instanceof JPopupMenu)
                        newElement = subElement.getSubElements()[0];
                    else
                        newElement = subElement;
                    break;
                }
            }
            if (newElement == null) return null;
            element = newElement;
        }
        return element;
    }

    /**
     * Creates toolbar button which will behave exactly like specified menuitem
     *
     * @param item - menuitem to create toolbar button from
     * @return created toolbar button
     */
    public JButton createToolBarButton(JMenuItem item) {
        JButton button = new JButton(item.getIcon());
        for (ActionListener listener : item.getActionListeners())
            button.addActionListener(listener);
        button.setToolTipText(item.getToolTipText());
        return button;
    }

    /**
     * Creates toolbar button which will behave exactly like specified menuitem
     *
     * @param menuPath - path to menu item to create toolbar button from
     * @return created toolbar button
     * @see MainFrame.getMenuItem
     */
    public JButton createToolBarButton(String menuPath) {
        JMenuItem item = (JMenuItem) getMenuElement(menuPath);
        if (item == null)
            throw new InvalidParameterException("Menu path not found: " + menuPath);
        return createToolBarButton(item);
    }

    /**
     * Creates toolbar button which will behave exactly like specified menuitem and adds it to the toolbar
     *
     * @param menuPath - path to menu item to create toolbar button from
     */
    public JButton addToolBarButton(String menuPath) {
        var btn = createToolBarButton(menuPath);
        toolBar.add(btn);
        return btn;
    }

    /**
     * Adds separator to the toolbar
     */
    public void addToolBarSeparator() {
        toolBar.addSeparator();
    }

    /**
     * Prompts user for file name to save and returns it
     *
     * @param extension   - preferred file extension (example: "txt")
     * @param description - description of specified file type (example: "Text files")
     * @return File specified by user or null if user canceled operation
     * @see MainFrame.getOpenFileName
     */
    public FileUtils.FileWithExtension getSaveFileName(String[] extensions, String description) {
        return FileUtils.getSaveFileName(this, extensions, description);
    }

    /**
     * Prompts user for file name to open and returns it
     *
     * @param extension   - preferred file extension (example: "txt")
     * @param description - description of specified file type (example: "Text files")
     * @return File specified by user or null if user canceled operation
     * @see MainFrame.getSaveFileName
     */
    public FileUtils.FileWithExtension getOpenFileName(String[] extension, String description) {
        return FileUtils.getOpenFileName(this, extension, description);
    }
}

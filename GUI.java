import javax.swing.*;
import java.awt.*;
// GUI Courtesy of Alex Ross
public class GUI {

    private final String appName = "Page Rank Calculator";

    GUI() {
        // Attempt to set the look and feel of menuBars for OS X clients. Some exceptions will be thrown if it is initialized on a windows client and the program will fall back to the default look and feel.
        if (System.getProperty("os.name").startsWith("Mac")) {
            try {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", appName);
                System.setProperty("apple.awt.application.name", appName);
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                // Do Nothing - Fall back to default look and feel.
            }
    }
}

    // Show the file chooser.
    String showFileChooser(String title) {
        String directory;

        // Construct a JFrame to hold our file chooser window. The host operating system dialog will be used.
        JFrame fcParent = new JFrame();
        fcParent.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Initialize the file dialog, which uses the system open and close file window.
        FileDialog fd = new FileDialog(fcParent, title, FileDialog.LOAD);
        fd.setDirectory(System.getProperty("."));
        fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
        fd.setVisible(true);
        String filename = fd.getFile();

        // Only execute if we got the directory we are asking for.
        if (filename != null) {
            directory = fd.getDirectory().concat(filename);
            return directory;
        }

        // Dispose of the window when we are done.
        fcParent.dispose();

        return null;
    }
}

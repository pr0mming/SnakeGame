
package org.snake;

import org.snake.scenes.MenuScene;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The scenes I represent as JPanels, just create a class with all the elements
 * and the JFrame will do its job, it is more flexible than opening and closing JFrames.
 * This main class manages the scenes, and it works with an instance, which is passed as parameter
 * in each constructor of the scenes and from that instance it is called to the method
 * that runs the scenes (instance objects of certain classes)
 */

public class App extends JFrame {

    private JPanel rootPanel;
    private static App app;

    private App() {
        super("Snake");
        createApp();
    }

    public static App getInstance() {
        if (app == null) {
            app = new App();
        }

        return app;
    }

    private void createApp() {
        double[] screen = calculateScreen();
        setPreferredSize(new Dimension((int) screen[0], (int) screen[1]));

        rootPanel = new JPanel();
        rootPanel.setBackground(Color.white);
        rootPanel.setPreferredSize(getPreferredSize());
        rootPanel.setFocusable(true);

        getContentPane().add(rootPanel);

        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png"));
        setIconImage(icon);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocation((int) screen[0] / 2, (int) screen[1] / 4);
        setResizable(false);
        importFont();

        pack();
    }

    private void importFont() {
        try {
            var fontList = List.of("/font/Sugarpunch.otf", "/font/AldotheApache.ttf");

            for (String fontPath : fontList) {
                var fontResource = this.getClass().getResource(fontPath);

                if (fontResource == null) {
                    throw new Exception();
                }

                Font font = Font.createFont(Font.TRUETYPE_FONT, fontResource.openStream());
                GraphicsEnvironment ga = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ga.registerFont(font);
            }

        } catch (Exception e) {
            System.out.println("Oops ... an error has occurred in the importation of typography.");
        }
    }

    public void runScene(JPanel scene) {
        destroyScene();

        rootPanel.setBackground(scene.getBackground());
        rootPanel.add(scene);
        rootPane.revalidate();
        rootPanel.repaint();
    }

    private void destroyScene() {
        rootPanel.removeAll();
    }

    private double[] calculateScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screen.getWidth() * 0.5483;
        double height = screen.getHeight() * 0.727;

        return new double[]{width, height};
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> getInstance().runScene(new MenuScene()));
    }
}

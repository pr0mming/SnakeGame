
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/*
    The scenes I represent as JPanels, just create a class with all the elements 
    and the JFrame will do its job, it is more flexible than opening and closing JFrames.

    This main class manages the scenes, and it works with an instance, which is passed as parameter 
    in each constructor of the scenes and from that instance it is called to the method 
    that runs the scenes (instanciar objects of certain classes)
*/

public class App extends JFrame{
    
    private JPanel rootPanel;
    
    public App() {
        super("Snake");
        createApp();
    }
    
    private void createApp() {
        
        double[] screen = calculateScreen();
        setPreferredSize(new Dimension((int) screen[0], (int) screen[1]));
        
        rootPanel = new JPanel();
        rootPanel.setBackground(Color.white);
        rootPanel.setPreferredSize(getPreferredSize());
        rootPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(rootPanel);
                
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png"));
        setIconImage(icon);
        runScene(new MenuScene(this));
        
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        
        pack();
    }
    
    public void runScene(JPanel scene) {
        destroyScene();
        
        rootPanel.add(scene);
        rootPane.validate();
        rootPanel.repaint();
    }
     
    private void destroyScene() {
        rootPanel.removeAll();
        rootPanel.revalidate();
        rootPanel.repaint();
    }
    
    private double[] calculateScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screen.getWidth() * 0.5483;
        double height = screen.getHeight() * 0.727;
        
        return new double[]{width, height};
    }
    
    public static void main(String[] args) {
        new App();
    }
}

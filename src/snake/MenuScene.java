
package snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author pr0mming
 * 
 * SnakeProject is a project with the purpose to 
 * fully exploit tools Java (Swing and AWT) specifically ... 
 * If you think you can help me improve this project it would be great
 * 
 * GitHub: https://github.com/pr0mming
 */

public class MenuScene extends JFrame {
    
    private JFrame windowMenu;
    private Font font;
    
    public MenuScene() {
        createWindow();
    }
    
    private void createWindow() {
        windowMenu = new JFrame("Snake");                    
        double[] screen = calculateScreen();
        windowMenu.setPreferredSize(new Dimension((int) screen[0], (int) screen[1]));
        
        JPanel panelLogo = new JPanel();
        panelLogo.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelLogo.setBackground(Color.white);
        
        JLabel snakeLogo = new JLabel();
        snakeLogo.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resources/snakeLogo.png")).getImage().getScaledInstance((int) (screen[0] * 0.65), (int) (screen[1] * 0.65), Image.SCALE_DEFAULT)));
        snakeLogo.setHorizontalAlignment(SwingConstants.CENTER);
        panelLogo.add(snakeLogo);
        
        JPanel panelText = new JPanel();
        panelText.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelText.setBackground(Color.white);
        
        JLabel text = new JLabel("<html><body><center>It may be quite simple but it takes some time.<br> This application is intended to amuse and demonstrate <br> the use of Java Swing & AWT <br>recreating games as simple as Snake</center></body></html>");
        boolean importFont = typographyImport();
        text.setFont(new Font((importFont)?"SugarpunchDEMO":"Consolas", font.PLAIN, 23));
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setBackground(Color.white);
        panelText.add(text);
        
        JPanel panelButtons = new JPanel();
        panelButtons.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelButtons.setBackground(Color.white);
        
        JButton buttonStart = new JButton("START");      
        buttonStart.setFont(new Font((importFont)?"SugarpunchDEMO":"Consolas", font.PLAIN, 26));
        buttonStart.setPreferredSize(new Dimension(250, 60));
        buttonStart.setBackground(Color.white);
        buttonStart.setBorder(new BordeRadio(10));
        buttonStart.setFocusable(true);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowMenu.dispose();
                new GameScene();
            }
        });
        panelButtons.add(buttonStart);
        
        //Yes, you will see soon that will make this button if I can ...
        JButton buttonSpecial = new JButton("BREAK THIS!");
        buttonSpecial.setFont(new Font((importFont)?"SugarpunchDEMO":"Consolas", font.PLAIN, 26));
        buttonSpecial.setPreferredSize(new Dimension(250, 60));
        buttonSpecial.setBackground(Color.white);
        buttonSpecial.setBorder(new BordeRadio(10));
        buttonSpecial.setFocusable(true);
        panelButtons.add(buttonSpecial);
        
        windowMenu.getContentPane().add(panelLogo, BorderLayout.NORTH);
        windowMenu.getContentPane().add(panelText);
        windowMenu.getContentPane().add(panelButtons, BorderLayout.SOUTH);
        
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png"));
        windowMenu.setIconImage(icon);
        windowMenu.setFocusable(true);
        windowMenu.setResizable(false);
        windowMenu.setVisible(true);
        windowMenu.setLocationRelativeTo(null);
        windowMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowMenu.pack();
    }
    
    private double[] calculateScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screen.getWidth() * 0.5483;
        double height = screen.getHeight() * 0.727;
        
        return new double[]{width, height};
    }
    
    private boolean typographyImport() {
        try {
            font = font.createFont(font.TRUETYPE_FONT, this.getClass().getResource("/resources/SugarpunchDEMO.otf").openStream());
            GraphicsEnvironment ga = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ga.registerFont(font);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Oops ... an error has occurred in the importation of typography. It will try to pick another ;)", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuScene();
            }
        });
    }
    
    
}

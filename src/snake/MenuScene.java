
package snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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

public class MenuScene extends JPanel {
    
    private Font font;
    private App app;
    
    public MenuScene(App app) {
        this.app = app;
        createScene();
    }
    
    private void createScene() {
        JPanel rootPanel = new JPanel();    
        
        rootPanel.setPreferredSize(this.app.getPreferredSize());
        rootPanel.setBackground(Color.white);
        
        JPanel panelLogo = new JPanel();
        panelLogo.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelLogo.setBackground(Color.white);
        
        JLabel snakeLogo = new JLabel();
        snakeLogo.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resources/snakeLogo.png")).getImage().getScaledInstance((int) (this.app.getPreferredSize().width * 0.63), (int) (this.app.getPreferredSize().height * 0.625), Image.SCALE_DEFAULT)));
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
                app.runScene(new GameScene(app));
            }
        });
        buttonStart.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonStart.setBackground(Color.black);
                buttonStart.setForeground(Color.white);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonStart.setBackground(Color.white);
                buttonStart.setForeground(Color.black);
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
        buttonSpecial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        buttonSpecial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        buttonSpecial.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonSpecial.setBackground(Color.black);
                buttonSpecial.setForeground(Color.white);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonSpecial.setBackground(Color.white);
                buttonSpecial.setForeground(Color.black);
            }
        });
        panelButtons.add(buttonSpecial);
        
        rootPanel.add(panelLogo, BorderLayout.NORTH);
        rootPanel.add(panelText);
        rootPanel.add(panelButtons, BorderLayout.SOUTH);
        
        setBackground(rootPanel.getBackground());
        add(rootPanel);
        setVisible(true);
    }
    
    private boolean typographyImport() {
        try {
            font = font.createFont(font.TRUETYPE_FONT, this.getClass().getResource("/resources/SugarpunchDEMO.otf").openStream());
            GraphicsEnvironment ga = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ga.registerFont(font);
            return true;
        } catch (Exception e) {
            System.out.println("Oops ... an error has occurred in the importation of typography. It will try to pick another ;)");
            return false;
        }
    }
    
}


package snake;

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
    
    public MenuScene() {
        createScene();
    }
    
    private void createScene() {
        JPanel rootPanel = new JPanel();    
        
        rootPanel.setPreferredSize(App.getInstance().getPreferredSize());
        rootPanel.setBackground(Color.white);
        
        JPanel panelLogo = new JPanel();
        panelLogo.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelLogo.setBackground(Color.white);
        
        JLabel snakeLogo = new JLabel();
        snakeLogo.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resources/snakeLogo.png")).getImage().getScaledInstance((int) (App.getInstance().getPreferredSize().width * 0.63), (int) (App.getInstance().getPreferredSize().height * 0.625), Image.SCALE_DEFAULT)));
        snakeLogo.setHorizontalAlignment(SwingConstants.CENTER);
        panelLogo.add(snakeLogo);
        
        JPanel panelText = new JPanel();
        panelText.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelText.setBackground(Color.white);
        
        JLabel text = new JLabel("<html><body><center>It may be quite simple but it takes some time.<br> This application is intended to amuse and demonstrate <br> the use of Java Swing & AWT <br>recreating games as simple as Snake</center></body></html>");
        boolean importFont = importFont();
        text.setFont(new Font((importFont)?"SugarpunchDEMO":"Consolas", font.PLAIN, (int) (rootPanel.getPreferredSize().height * 0.03)));
        text.setBackground(Color.white);
        panelText.add(text);
        
        JPanel panelButtons = new JPanel();
        panelButtons.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelButtons.setBackground(Color.white);
        
        JButton buttonStart = new JButton("START");      
        buttonStart.setFont(new Font((importFont)?"SugarpunchDEMO":"Consolas", font.PLAIN, (int) (rootPanel.getPreferredSize().height * 0.036)));
        buttonStart.setPreferredSize(new Dimension((int) (rootPanel.getPreferredSize().width * 0.5), (int) (rootPanel.getPreferredSize().height * 0.06)));
        buttonStart.setBackground(Color.white);
        buttonStart.setBorder(new BordeRadio(10));
        buttonStart.setFocusable(true);
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.getInstance().runScene(new GameScene());  
            }
        };
        
        buttonStart.addActionListener(action);
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
        
        rootPanel.add(panelLogo);
        rootPanel.add(panelText);
        rootPanel.add(panelButtons);
        
        setBackground(rootPanel.getBackground());
        add(rootPanel);
        setVisible(true);
    }
    
    private boolean importFont() {
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

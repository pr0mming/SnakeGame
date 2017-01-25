
package snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;                      
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
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

public class GameScene extends JPanel {

    private JPanel panelGame;
    private JLabel [][] matrix;
    private JLabel score, len;
    private ClientPlay play;
    private Color background;
    private Scheduler motion;
    private Font font;
    private KeyboardFocusManager keyboardFocus;
    private Control keyEventDispatcher;
    
    public GameScene() {
        createScene();
        
        motion = new Scheduler(this);
        play = new ClientPlay(this);
        keyEventDispatcher = new Control(this);
        keyboardFocus = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        
        addKeyboardFocus();
    }         
    
    private void createScene() {
        //Panels
        int x = 40, y = 60;
        
        background = Color.black;
        panelGame = new JPanel(new GridLayout(x, y, 0, 0));
        
        Dimension size = App.getInstance().getPreferredSize();
        panelGame.setPreferredSize(new Dimension((int) (size.width * 0.99), (int) (size.height * 0.79)));
        panelGame.setBackground(Color.white);
        panelGame.setBorder(BorderFactory.createLineBorder(background, 5));
        
        JPanel panelStatistics = new JPanel(new GridLayout(2, 2, 10, 10));
        panelStatistics.setPreferredSize(new Dimension((int) (size.width), (int) (size.height * 0.14)));
        panelStatistics.setBorder(new EmptyBorder(2, 25, 25, 25));
        panelStatistics.setBackground(background);
        
        //Buttons
        matrix = new JLabel[x][y];
        boolean importFont = importFont();
        
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {                            
                matrix[i][j] = new JLabel();               
                matrix[i][j].setBackground(background);
                matrix[i][j].setOpaque(true);
                matrix[i][j].setSize(new Dimension(panelGame.getPreferredSize().width / y, panelGame.getPreferredSize().height / x));
                panelGame.add(matrix[i][j]);
            }
        
        int sizeFont = (int) (panelGame.getPreferredSize().height * 0.044);
        
        score = new JLabel();
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setFont(new Font((importFont)?"Aldo the Apache":"Consolas", font.PLAIN, sizeFont));
        score.setForeground(Color.white);
        updateScore(0);
        
        len = new JLabel();
        len.setHorizontalAlignment(SwingConstants.CENTER);
        len.setFont(new Font((importFont)?"Aldo the Apache":"Consolas", font.PLAIN, sizeFont));
        len.setForeground(Color.white);
        
        JButton buttonMenu = new JButton("MENU");  
        buttonMenu.setHorizontalAlignment(SwingConstants.CENTER);
        buttonMenu.setFont(new Font((importFont)?"Aldo the Apache":"Consolas", font.PLAIN, sizeFont));
        buttonMenu.setPreferredSize(new Dimension(panelGame.getPreferredSize().width / 2, (int) (panelGame.getPreferredSize().height * 0.09)));
        buttonMenu.setBorder(new BordeRadio(10));
        buttonMenu.setForeground(Color.white);
        buttonMenu.setBackground(background);
        buttonMenu.setFocusable(true);
        buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScene(new MenuScene());
            }
        });
        
        buttonMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonMenu.setBackground(Color.white);
                buttonMenu.setForeground(background);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonMenu.setBackground(background);
                buttonMenu.setForeground(Color.white);
            }
        });  
        
        JButton buttonRestart = new JButton("RESTART");   
        buttonRestart.setHorizontalAlignment(SwingConstants.CENTER);
        buttonRestart.setFont(new Font((importFont)?"Aldo the Apache":"Consolas", font.PLAIN, sizeFont));
        buttonRestart.setPreferredSize(new Dimension(panelGame.getPreferredSize().width / 2, (int) (panelGame.getPreferredSize().height * 0.09)));
        buttonRestart.setBorder(new BordeRadio(10));
        buttonRestart.setForeground(Color.white);
        buttonRestart.setBackground(background);
        buttonRestart.setFocusable(true);
        buttonRestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play.restartGame();
            }
        });   
        
        buttonRestart.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonRestart.setBackground(Color.white);
                buttonRestart.setForeground(background);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonRestart.setBackground(background);
                buttonRestart.setForeground(Color.white);
            }
        });      
        
        panelStatistics.add(score);
        panelStatistics.add(len);
        panelStatistics.add(buttonMenu);
        panelStatistics.add(buttonRestart);
        
        //Root
        JPanel rootPanel = new JPanel();             
        rootPanel.setPreferredSize(App.getInstance().getPreferredSize());
        rootPanel.add(panelGame);
        rootPanel.add(panelStatistics, BorderLayout.SOUTH);
        rootPanel.setBackground(background);
        rootPanel.setVisible(true);
        
        add(rootPanel);
        setBackground(background);
        setVisible(true);
    }
    
    /*
        Here I am not completely convinced if the memory is successfully released with this method. 
        Practically if there are active event listeners or timers, the GC will not choose them, 
        I have to resort to manually deactivating them and equaling them to null. Otherwise 
        this scene would still exist in memory and you could still "play blindly"
    */
    public void changeScene(JPanel scene) {
        removeKeyFocus();
        motion.stopAllTimers();
        
        motion = null;
        play = null;
        keyboardFocus = null;
        keyEventDispatcher = null;
        
        App.getInstance().runScene(scene);
    }
    
    private boolean importFont() {
        try {
            font = font.createFont(font.TRUETYPE_FONT, this.getClass().getResource("/resources/AldotheApache.ttf").openStream());
            GraphicsEnvironment ga = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ga.registerFont(font);
            
            return true;
        } catch (Exception e) {
            System.out.println("Oops ... an error has occurred in the importation of typography. It will try to pick another ;)");
            
            return false;
        }
    } 
    
    public void updateScore(int p) {
        score.setText("SCORE: "+p);
    }
    
    public void updateLenght(int l) {
        len.setText("LENGTH: "+l);
    }
    
    public void addKeyboardFocus() {
        this.keyboardFocus.addKeyEventDispatcher(keyEventDispatcher);
    }
    
    protected void removeKeyFocus() {
        this.keyboardFocus.removeKeyEventDispatcher(keyEventDispatcher);
    }
    
    public void changeColorPanel() {
        panelGame.setBackground((panelGame.getBackground() == Color.white) ? Color.black : Color.white);
    }
    
    public void restoreColorPanel() {
        panelGame.setBackground(Color.white);
    }
    
    public Scheduler getScheduler() {
        return this.motion;
    }
    
    public ClientPlay getPlay() {
        return this.play;
    }
    
    public Color getBackgroundGame() {
        return this.background;
    }
    
    public JLabel[][] getMatrix() {
        return matrix;
    }
}
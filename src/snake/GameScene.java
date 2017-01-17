
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
import javax.swing.*;
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

    private App app;
    private JPanel rootPanel, panelGame, panelStatistics;
    private JLabel [][] matrix;
    private JButton buttonRestart, buttonMenu;
    private JLabel score, len;
    private int x, y;
    private ClientPlay play;
    private Color background;
    private Time motion;
    private Font font;
    private KeyboardFocusManager keyboardFocus;
    private Events keyEventDispatcher;
    
    public GameScene(App app) {
        this.app = app;
        createPanels();
        createButtons();
        createRootPanel(); 
        motion = new Time(this);
        play = new ClientPlay(this.matrix, this);
        keyEventDispatcher = new Events(this);
        keyboardFocus = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        
        addKeyboardFocus();
    }   
    
    private boolean typographyImport() {
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

    private void createButtons() {
        matrix = new JLabel[x][y];
        boolean importFont = typographyImport();
        
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {                            
                matrix[i][j] = new JLabel();               
                matrix[i][j].setBackground(background);
                matrix[i][j].setOpaque(true);
                matrix[i][j].setPreferredSize(new Dimension(3, 3));               
                panelGame.add(matrix[i][j]);
            }
        
        score = new JLabel();
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setFont(new Font((importFont)?"Aldo the Apache":"Consolas", font.PLAIN, 26));
        score.setForeground(Color.white);
        updateScore(0);
        
        len = new JLabel();
        len.setHorizontalAlignment(SwingConstants.CENTER);
        len.setFont(new Font((importFont)?"Aldo the Apache":"Consolas", font.PLAIN, 26));
        len.setForeground(Color.white);
        
        buttonMenu = new JButton("MENU");  
        buttonMenu.setHorizontalAlignment(SwingConstants.CENTER);
        buttonMenu.setFont(new Font((importFont)?"Aldo the Apache":"Consolas", font.PLAIN, 26));
        buttonMenu.setPreferredSize(new Dimension(120, 40));
        buttonMenu.setBorder(new BordeRadio(10));
        buttonMenu.setForeground(Color.white);
        buttonMenu.setBackground(background);
        buttonMenu.setFocusable(true);
        buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.runScene(new MenuScene(app));
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
        
        buttonRestart = new JButton("RESTART");   
        buttonRestart.setHorizontalAlignment(SwingConstants.CENTER);
        buttonRestart.setFont(new Font((importFont)?"Aldo the Apache":"Consolas", font.PLAIN, 26));
        buttonRestart.setPreferredSize(new Dimension(120, 40));
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
    }       
    
    private void createPanels() {
        x = 40; y = 60;
        background = Color.black;
        panelGame = new JPanel(new GridLayout(x, y, 0, 0));
        
        Dimension size = this.app.getPreferredSize();
        panelGame.setPreferredSize(new Dimension((int) (size.width), (int) (size.height * 0.80)));
        panelGame.setBackground(Color.white);
        panelGame.setBorder(BorderFactory.createLineBorder(background, 5));
        
        panelStatistics = new JPanel(new GridLayout(2, 2, 10, 10));
        panelStatistics.setPreferredSize(new Dimension((int) (size.width), (int) (size.height * 0.14)));
        panelStatistics.setBorder(new EmptyBorder(2, 25, 25, 25));
        panelStatistics.setBackground(background);
    }
    
    private void createRootPanel() {
        rootPanel = new JPanel();                   
        rootPanel.setBackground(background);
        rootPanel.setPreferredSize(this.app.getPreferredSize());
        rootPanel.add(panelGame);
        rootPanel.add(panelStatistics, BorderLayout.SOUTH);
        rootPanel.setVisible(true);
        
        add(rootPanel);
        setVisible(true);
    }
    
    public void destroyScene() {
        this.app.runScene(new MenuScene(app));
    }
    
    public void updateScore(int p) {
        score.setText("SCORE: "+p);
    }
    
    public void updateLenght(int l) {
        len.setText("LENGHT: "+l);
    }
    
    public void addKeyboardFocus() {
        this.keyboardFocus.addKeyEventDispatcher(keyEventDispatcher);
    }
    
    protected void removeKeyFocus() {
        this.keyboardFocus.removeKeyEventDispatcher(keyEventDispatcher);
    }
    
    public Time getTime() {
        return this.motion;
    }
    
    public JPanel getPanelGame() {
        return this.panelGame;
    }
    
    public ClientPlay getPlay() {
        return this.play;
    }
    
    public Color getBackgroundGame() {
        return this.background;
    }
    
}
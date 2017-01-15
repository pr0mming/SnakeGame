
package snake;

import java.awt.*;
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

public class SnakeGame extends JFrame {

    private JFrame windowGame;
    private JPanel panelGame;
    private JPanel panelStatistics;
    private JLabel [][] matrix;
    private JButton buttonRestart;
    private JLabel score, len, timeBonus;
    private int x, y;
    private Gameplay play;
    private Color background;
    private Time motion;
    private Font font;
    private KeyboardFocusManager keyboardFocus;
    private Events keyEventDispatcher;
    
    public SnakeGame() {               
        createPanels();
        createButtons();
        createWindow(); 
        play = new Gameplay(matrix, this);
        keyEventDispatcher = new Events(this);
        keyboardFocus = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        motion = new Time(this);
        addKeyboardFocus();
    }   
    
    private boolean typographyImport() {
        try {
            font = font.createFont(font.TRUETYPE_FONT, this.getClass().getResource("/resources/AldotheApache.ttf").openStream());
            GraphicsEnvironment ga = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ga.registerFont(font);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Oops ... an error has occurred in the importation of typography. It will try to pick another ;)", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void createButtons() {
        matrix = new JLabel[x][y];
        boolean importFont;
        importFont = typographyImport();
        
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
        timeBonus = new JLabel();
        timeBonus.setHorizontalAlignment(SwingConstants.CENTER);
        timeBonus.setFont(new Font((importFont)?"Aldo the Apache":"Consolas", font.PLAIN, 26));
        timeBonus.setForeground(Color.white);
        updateTimeBonus(0);
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
                buttonRestart.setForeground(Color.black);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonRestart.setBackground(background);
                buttonRestart.setForeground(Color.white);
            }
        });       
        panelStatistics.add(score);
        panelStatistics.add(len);
        panelStatistics.add(timeBonus);
        panelStatistics.add(buttonRestart);
    }       
    
    private void createPanels() {
        x= 40; y= 40;
        background = Color.black;
        panelGame = new JPanel(new GridLayout(x, y, 0, 0));
        panelGame.setPreferredSize(new Dimension(500, 700));
        panelGame.setBackground(Color.white);
        panelGame.setBorder(BorderFactory.createLineBorder(background, 5));
        panelGame.setVisible(true);        
        panelStatistics = new JPanel(new GridLayout(2, 2, 10, 10));
        panelStatistics.setPreferredSize(new Dimension(500, 120));
        panelStatistics.setBorder(new EmptyBorder(10, 25, 25, 25));
        panelStatistics.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        panelStatistics.setBackground(background);
    }
    
    private void createWindow() {
        windowGame = new JFrame("Snake");                   
        windowGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowGame.setPreferredSize(new Dimension(700, 900));
        windowGame.getContentPane().add(panelGame);
        windowGame.getContentPane().add(panelStatistics, BorderLayout.SOUTH);
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png"));
        windowGame.setIconImage(icon);
        windowGame.setFocusable(true);
        windowGame.setResizable(false);
        windowGame.setVisible(true);
        windowGame.setLocationRelativeTo(null);
        windowGame.pack();
    }
    
    public void updateScore(int p) {
        score.setText("SCORE: "+p);
    }
    
    public void updateLenght(int l) {
        len.setText("LENGHT: "+l);
    }
    
    public void updateTimeBonus(int t) {
        timeBonus.setText("BONUS: "+t);
    }
    
    public Time getTime() {
        return this.motion;
    }
    
    public JPanel getPanelGame() {
        return this.panelGame;
    }
    
    public Gameplay getPlay() {
        return this.play;
    }
    
    public Color getBackgroundGame() {
        return this.background;
    }
    
    public void addKeyboardFocus() {
        this.keyboardFocus.addKeyEventDispatcher(keyEventDispatcher);
    }
    
    public void removeKeyFocus() {
        this.keyboardFocus.removeKeyEventDispatcher(keyEventDispatcher);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SnakeGame();
            }
        });
    }
    
}

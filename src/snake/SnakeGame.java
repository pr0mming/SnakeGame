
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

    private JFrame WindowGame;
    private static JPanel Panel;
    private JPanel SecondaryPanel;
    private JLabel [][] Matriz_Label;
    private Image Icon;
    private JButton Restart;
    private static JLabel Points, Lenght, Time_Bonus;
    private int x, y;
    private static Gameplay Play;
    private static Color Background;
    private static Time Motion;
    private boolean Import_font;
    private Font Font;
    
    public SnakeGame() {               
        Content();
        Button();
        Window(); 
        Play= new Gameplay(Matriz_Label);
        KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        Motion = new Time();
        m.addKeyEventDispatcher(new Events());
        Motion.StartMotion((Play.getPositionCondition())?0:1); 
    }   
    
    private boolean TypographyImport() {
        try {
            Font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResource("/resources/AldotheApache.ttf").openStream());
            GraphicsEnvironment ga = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ga.registerFont(Font);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Oops ... an error has occurred in the importation of typography. It will try to pick another ;)", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void Button() {
        Matriz_Label = new JLabel[x][y];
        Import_font = TypographyImport();
        for (int i = 0; i < Matriz_Label.length; i++)
            for (int j = 0; j < Matriz_Label[i].length; j++) {                            
                Matriz_Label[i][j] = new JLabel();               
                Matriz_Label[i][j].setBackground(Background);
                Matriz_Label[i][j].setOpaque(true);
                Matriz_Label[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                Matriz_Label[i][j].setPreferredSize(new Dimension(3, 3));               
                Panel.add(Matriz_Label[i][j]);
            }
        
        Points = new JLabel();
        Points.setHorizontalAlignment(SwingConstants.CENTER);
        Points.setFont(new Font((Import_font)?"Aldo the Apache":"Consolas", Font.PLAIN, 26));
        Points.setForeground(Color.white);
        UpdatePoints(0);
        Lenght = new JLabel();
        Lenght.setHorizontalAlignment(SwingConstants.CENTER);
        Lenght.setFont(new Font((Import_font)?"Aldo the Apache":"Consolas", Font.PLAIN, 26));
        Lenght.setForeground(Color.white);
        Time_Bonus = new JLabel();
        Time_Bonus.setHorizontalAlignment(SwingConstants.CENTER);
        Time_Bonus.setFont(new Font((Import_font)?"Aldo the Apache":"Consolas", Font.PLAIN, 26));
        Time_Bonus.setForeground(Color.white);
        UpdateTimeBonus(0);
        Restart = new JButton("RESTART");   
        Restart.setHorizontalAlignment(SwingConstants.CENTER);
        Restart.setFont(new Font((Import_font)?"Aldo the Apache":"Consolas", Font.PLAIN, 26));
        Restart.setPreferredSize(new Dimension(120, 40));
        Restart.setBorder(new BordeRadio(10));
        Restart.setForeground(Color.white);
        Restart.setBackground(Background);
        Restart.setFocusable(true);
        Restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.Play.RestartEnvironment(true);
            }
        });   
        Restart.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Restart.setBackground(Color.white);
                Restart.setForeground(Color.black);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Restart.setBackground(Background);
                Restart.setForeground(Color.white);
            }
        });       
        SecondaryPanel.add(Points);
        SecondaryPanel.add(Lenght);
        SecondaryPanel.add(Time_Bonus);
        SecondaryPanel.add(Restart);
    }       
    
    private void Content() {
        x= 40; y= 40;
        Background = Color.black;
        Panel = new JPanel(new GridLayout(x, y, 0, 0));
        Panel.setPreferredSize(new Dimension(500, 700));
        Panel.setBackground(Color.white);
        Panel.setBorder(BorderFactory.createLineBorder(Background, 5));
        Panel.setVisible(true);        
        SecondaryPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        SecondaryPanel.setPreferredSize(new Dimension(500, 120));
        SecondaryPanel.setBorder(new EmptyBorder(10, 25, 25, 25));
        SecondaryPanel.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        SecondaryPanel.setBackground(Background);
    }
    
    private void Window() {
        WindowGame = new JFrame("Snake");                   
        WindowGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WindowGame.setPreferredSize(new Dimension(700, 900));
        WindowGame.getContentPane().add(Panel);
        WindowGame.getContentPane().add(SecondaryPanel, BorderLayout.SOUTH);
        Icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png"));
        WindowGame.setIconImage(Icon);
        WindowGame.setFocusable(true);
        WindowGame.setResizable(false);
        WindowGame.setVisible(true);
        WindowGame.setLocationRelativeTo(null);
        WindowGame.pack();
    }
    
    public static void UpdatePoints(int p) {
        Points.setText(p+" POINTS");
    }
    
    public static void UpdateLenght(int l) {
        Lenght.setText("LENGHT: "+l);
    }
    
    public static void UpdateTimeBonus(int t) {
        Time_Bonus.setText("BONUS: "+t);
    }
    
    public static Time getTime() {
        return Motion;
    }
    
    public static JPanel getPanel() {
        return Panel;
    }
    
    public static Gameplay getPlay() {
        return Play;
    }
    
    public static Color getBackgroundGame() {
        return Background;
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

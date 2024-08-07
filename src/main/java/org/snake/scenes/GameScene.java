
package org.snake.scenes;

import org.snake.*;
import org.snake.core.ControlKeysManager;
import org.snake.core.GameManager;
import org.snake.core.Scheduler;
import org.snake.ui.BorderRadio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * This class represents the game scene,
 * basically it creates the matrix where the snake will be traveling
 * and also manage some button events
 */
public class GameScene extends JPanel {

    private JPanel panelGame;
    private JLabel[][] matrix;
    private JLabel score, len;
    private GameManager gameManager;
    private Color background;
    private Scheduler motion;
    private KeyboardFocusManager keyboardFocus;
    private ControlKeysManager controlKeysManager;

    public GameScene() {
        createScene();

        motion = new Scheduler(this);
        gameManager = new GameManager(this);
        controlKeysManager = new ControlKeysManager(this);
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
        panelStatistics.setPreferredSize(new Dimension(size.width, (int) (size.height * 0.14)));
        panelStatistics.setBorder(new EmptyBorder(2, 25, 25, 25));
        panelStatistics.setBackground(background);

        //Buttons
        matrix = new JLabel[x][y];

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
        score.setFont(new Font("Aldo the Apache", Font.PLAIN, sizeFont));
        score.setForeground(Color.white);
        updateScore(0);

        len = new JLabel();
        len.setHorizontalAlignment(SwingConstants.CENTER);
        len.setFont(new Font("Aldo the Apache", Font.PLAIN, sizeFont));
        len.setForeground(Color.white);

        JButton buttonMenu = new JButton("MENU");
        buttonMenu.setHorizontalAlignment(SwingConstants.CENTER);
        buttonMenu.setFont(new Font("Aldo the Apache", Font.PLAIN, sizeFont));
        buttonMenu.setPreferredSize(new Dimension(panelGame.getPreferredSize().width / 2, (int) (panelGame.getPreferredSize().height * 0.09)));
        buttonMenu.setBorder(new BorderRadio(10));
        buttonMenu.setForeground(Color.white);
        buttonMenu.setBackground(background);
        buttonMenu.setFocusable(true);
        buttonMenu.addActionListener(e -> changeScene(new MenuScene()));

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
        buttonRestart.setFont(new Font("Aldo the Apache", Font.PLAIN, sizeFont));
        buttonRestart.setPreferredSize(new Dimension(panelGame.getPreferredSize().width / 2, (int) (panelGame.getPreferredSize().height * 0.09)));
        buttonRestart.setBorder(new BorderRadio(10));
        buttonRestart.setForeground(Color.white);
        buttonRestart.setBackground(background);
        buttonRestart.setFocusable(true);
        buttonRestart.addActionListener(e -> gameManager.restartGame());

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
        I have to resort to manually deactivating them and equaling them to null. Otherwise,
        this scene would still exist in memory, and you could still "play blindly"
    */
    public void changeScene(JPanel scene) {
        removeKeyFocus();
        motion.stopAllTimers();

        motion = null;
        gameManager = null;
        keyboardFocus = null;
        controlKeysManager = null;

        App.getInstance().runScene(scene);
    }

    public void updateScore(int p) {
        score.setText("SCORE: " + p);
    }

    public void updateLength(int l) {
        len.setText("LENGTH: " + l);
    }

    public void addKeyboardFocus() {
        this.keyboardFocus.addKeyEventDispatcher(controlKeysManager);
    }

    public void removeKeyFocus() {
        this.keyboardFocus.removeKeyEventDispatcher(controlKeysManager);
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

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public Color getBackgroundGame() {
        return this.background;
    }

    public JLabel[][] getMatrix() {
        return matrix;
    }
}
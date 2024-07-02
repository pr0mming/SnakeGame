
package org.snake.scenes;

import org.snake.App;
import org.snake.ui.BorderRadio;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * This class represents the main menu of the game
 */

public class MenuScene extends JPanel {

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

        setLogo(panelLogo);

        JPanel panelText = new JPanel();
        panelText.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelText.setBackground(Color.white);

        JLabel text = new JLabel("<html><body><center><br> This application is intended to amuse and demonstrate <br> the use of Java Swing & AWT <br>recreating games as simple as Snake</center></body></html>");
        text.setFont(new Font("SugarpunchDEMO", Font.PLAIN, (int) (rootPanel.getPreferredSize().height * 0.03)));
        text.setBackground(Color.white);
        panelText.add(text);

        JPanel panelButtons = new JPanel();
        panelButtons.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelButtons.setBackground(Color.white);

        JButton buttonStart = new JButton("START");
        buttonStart.setFont(new Font("SugarpunchDEMO", Font.PLAIN, (int) (rootPanel.getPreferredSize().height * 0.036)));
        buttonStart.setPreferredSize(new Dimension((int) (rootPanel.getPreferredSize().width * 0.5), (int) (rootPanel.getPreferredSize().height * 0.06)));
        buttonStart.setBackground(Color.white);
        buttonStart.setBorder(new BorderRadio(10));
        buttonStart.setFocusable(true);
        ActionListener action = e -> App.getInstance().runScene(new GameScene());

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

    private void setLogo(JPanel panelLogo) {
        JLabel snakeLogo = new JLabel();

        var imageResource = getClass().getResource("/images/logo.png");

        if (imageResource != null) {
            snakeLogo.setIcon(new ImageIcon(new ImageIcon(imageResource).getImage().getScaledInstance((int) (App.getInstance().getPreferredSize().width * 0.63), (int) (App.getInstance().getPreferredSize().height * 0.625), Image.SCALE_DEFAULT)));
            snakeLogo.setHorizontalAlignment(SwingConstants.CENTER);
            panelLogo.add(snakeLogo);
        }
    }

}

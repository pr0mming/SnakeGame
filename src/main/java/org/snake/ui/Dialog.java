
package org.snake.ui;

import org.snake.App;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class represents a customizable dialog to show any message
 */
public class Dialog extends JDialog {

    private final ActionListener[] actions;
    private final String message;

    public Dialog(ActionListener[] actions, String title, String message) {
        super(App.getInstance(), title, true);
        this.actions = actions;
        this.message = message;
        createDialog();
    }

    private void createDialog() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(100, 300));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setBackground(Color.black);

        JLabel label = new JLabel(message);
        label.setFont(new Font("SugarpunchDEMO", Font.PLAIN, 20));
        label.setForeground(Color.white);
        panel.add(label);

        String[] msgButtons = {"Try again", "Back to menu"};

        for (int i = 0; i < msgButtons.length; i++) {
            JButton button = new JButton(msgButtons[i]);
            button.setFont(new Font("SugarpunchDEMO", Font.PLAIN, 20));
            button.setPreferredSize(new Dimension(label.getPreferredSize().width / 2, 50));
            button.setBorder(new BorderRadio(10));
            button.setForeground(Color.white);
            button.setBackground(Color.black);
            button.setFocusable(true);
            button.addActionListener(actions[i]);
            button.addActionListener(e -> dispose());
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.white);
                    button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.black);
                    button.setForeground(Color.white);
                }
            });

            panel.add(button);
        }

        getContentPane().add(panel);
        getContentPane().setBackground(Color.black);

        Dimension size = App.getInstance().getPreferredSize();
        setSize((int) (size.width * 0.40), (int) (size.height * 0.18));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();

    }

}

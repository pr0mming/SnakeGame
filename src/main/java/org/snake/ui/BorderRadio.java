
package org.snake.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

/**
 * This class is a boilerplate taken from Internet
 * to see the rounded edges of the button "Restart" and others
 */
public class BorderRadio implements Border {

    private final int radio;

    public BorderRadio(int radio) {
        this.radio = radio;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radio + 1, this.radio + 1, this.radio + 2, this.radio);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radio, radio);
    }

}


package snake;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

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

public class BordeRadio implements Border {
    
    /*
        If you see the rounded edges of the button "Restart" this class does!
    */
    private final int radio;

    BordeRadio(int radio) {
        this.radio = radio;
    }
   
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radio+1, this.radio+1, this.radio+2, this.radio);
    }
    
    @Override
    public boolean isBorderOpaque() {
        return true;
    }
   
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radio, radio);
    }
    
}

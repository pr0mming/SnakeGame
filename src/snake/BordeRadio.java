
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

    private final int Radio;

    BordeRadio(int radio) {
        this.Radio = radio;
    }
   
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.Radio+1, this.Radio+1, this.Radio+2, this.Radio);
    }
    
    @Override
    public boolean isBorderOpaque() {
        return true;
    }
   
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, Radio, Radio);
    }
    
}


package snake;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

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

public class Events implements KeyEventDispatcher {    
    /*
        This class is responsible for the controls ...
    */
    private GameScene instanceGame;
    
    public Events(GameScene instanceGame) {
        super();
        this.instanceGame = instanceGame;
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getID() == KeyEvent.KEY_PRESSED) {
            if(e.getKeyCode() == KeyEvent.VK_LEFT) {   
                if (!instanceGame.getPlay().getDirection().equals("Right")) {
                    instanceGame.getPlay().moveSnake("Left");
                    instanceGame.getTime().actuate(0, this.instanceGame.getTime().getAmountTimers());
                }
            } else
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {         
                    if (!this.instanceGame.getPlay().getDirection().equals("Left")) {
                        this.instanceGame.getPlay().moveSnake("Right");
                        this.instanceGame.getTime().actuate(1, this.instanceGame.getTime().getAmountTimers());
                    }
                } else 
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        if (!this.instanceGame.getPlay().getDirection().equals("Down")) {
                            this.instanceGame.getPlay().moveSnake("Up");
                            this.instanceGame.getTime().actuate(2, this.instanceGame.getTime().getAmountTimers());
                        }
                    } else
                        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                            if(!this.instanceGame.getPlay().getDirection().equals("Up")) {
                                this.instanceGame.getPlay().moveSnake("Down");
                                this.instanceGame.getTime().actuate(3, this.instanceGame.getTime().getAmountTimers());
                            }
                        }       
            this.instanceGame.getTime().valueSpeed(this.instanceGame.getPlay().getScore());
            this.instanceGame.getTime().startBonus();
        }
        return false;
    }
    
}

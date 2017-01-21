
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

public class Control implements KeyEventDispatcher {    
    /*
        This class is responsible for the controls ...
    */
    private GameScene instanceGame;
    
    public Control(GameScene instanceGame) {
        this.instanceGame = instanceGame;
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        
        if(e.getID() == KeyEvent.KEY_PRESSED) {
            String direction = instanceGame.getPlay().getDirection();
            
            this.instanceGame.getScheduler().valueSpeed(this.instanceGame.getPlay().getScore());
            this.instanceGame.getScheduler().startBonus();
            
            if(e.getKeyCode() == KeyEvent.VK_LEFT) {   
                if (!direction.equals("Right")) {
                    instanceGame.getScheduler().actuate(0);
                    instanceGame.getPlay().moveSnake("Left");
                }
            } else
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {         
                    if (!direction.equals("Left")) {
                        this.instanceGame.getScheduler().actuate(1);
                        this.instanceGame.getPlay().moveSnake("Right");
                    }
                } else 
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        if (!direction.equals("Down")) {
                            this.instanceGame.getScheduler().actuate(2);
                            this.instanceGame.getPlay().moveSnake("Up");
                        }
                    } else
                        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                            if(!direction.equals("Up")) {
                                this.instanceGame.getScheduler().actuate(3);
                                this.instanceGame.getPlay().moveSnake("Down");
                            }
                        }     
        }
        
        return false;
    }
    
}

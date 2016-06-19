
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
    
    private int Period;
            
    public Events() {
        Period = 100;
    }
    
    private void ValueSpeed(int x) {
        if(x  >= Period) {
            SnakeGame.getTime().IncreaseSpeed();
            Period+=x;
        }
    }
    
    private void Actuate(int e, int l) {
        for (int i = 0; i < l; i++) {
            if(i == e) {
                SnakeGame.getTime().StartMotion(i);
            } else {
                SnakeGame.getTime().StopMotion(i);
            }
        }
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getID() == KeyEvent.KEY_PRESSED) {
            if(e.getKeyCode() == KeyEvent.VK_LEFT) {   
                if (!SnakeGame.getPlay().getDirection().equals("Right")) {
                    SnakeGame.getPlay().MovingSnake("Left");
                    Actuate(0, SnakeGame.getTime().AmountTimers());
                }
            } else
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {         
                    if (!SnakeGame.getPlay().getDirection().equals("Left")) {
                        SnakeGame.getPlay().MovingSnake("Right");
                        Actuate(1, SnakeGame.getTime().AmountTimers());
                    }
                } else 
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        if (!SnakeGame.getPlay().getDirection().equals("Down")) {
                            SnakeGame.getPlay().MovingSnake("Up");
                            Actuate(2, SnakeGame.getTime().AmountTimers());
                        }
                    } else
                        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                            if(!SnakeGame.getPlay().getDirection().equals("Up")) {
                                SnakeGame.getPlay().MovingSnake("Down");
                                Actuate(3, SnakeGame.getTime().AmountTimers());
                            }
                        }       
            ValueSpeed(SnakeGame.getPlay().getPoints());
            SnakeGame.getTime().StartLEDEffect();
        }
        return false;
    }
    
}

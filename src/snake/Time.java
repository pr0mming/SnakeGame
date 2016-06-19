
package snake;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

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
public class Time {
    
    private static int Delay;
    private static ActionListener[] Action = {
        new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                SnakeGame.getPlay().MovingSnake("Left");              
            }
        },
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SnakeGame.getPlay().MovingSnake("Right");
            }
        },
        new ActionListener() {
            public void actionPerformed(ActionEvent i) {
                SnakeGame.getPlay().MovingSnake("Up");
            }
        },
        new ActionListener() {
            public void actionPerformed(ActionEvent o) {
                SnakeGame.getPlay().MovingSnake("Down");
            }
        }
    };
    
    private Timer Led_effect;
    private static Timer[] Time_motion;
    private boolean Status_ledeffect;
    private boolean[] Status_time_motion;
    
    public Time() {
        Delay = 500;
        Time_motion = new Timer[Action.length];
        Status_time_motion = new boolean[Time_motion.length];
        Status_ledeffect = false;
        LocateTimes();
        LEDEffect();
    }
    
    private void LEDEffect() {
        Led_effect = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SnakeGame.getPanel().setBackground((SnakeGame.getPanel().getBackground() == Color.white)?Color.black:Color.white);
                if (SnakeGame.getPlay().getCurrentColor() != SnakeGame.getPlay().getSpecialColor()) {
                    SnakeGame.getPanel().setBackground(Color.white);
                    Led_effect.stop();
                    Status_ledeffect = false;
                }
            }
        });
    }
    
    public void StartLEDEffect() {
        if (SnakeGame.getPlay().getCurrentColor() == SnakeGame.getPlay().getSpecialColor() && !Status_ledeffect) {
            Status_ledeffect = true;
            Led_effect.start();      
        }
    }
    
    private void LocateTimes() {
        for (int i = 0; i < Time_motion.length; i++) {
            Time_motion[i] = new Timer(Delay, Action[i]);
            Status_time_motion[i] = false;
        }
    }
    
    public void StartMotion(int t) {
        if (!Status_time_motion[t]) {
            Time_motion[t].start();
            Status_time_motion[t] = true;
        }
    }
    
    public void StopMotion(int t) {
        if (Status_time_motion[t]) {
            Time_motion[t].stop();
            Status_time_motion[t] = false;
        }
    }
    
    public int AmountTimers() {
        return Time_motion.length;
    }
    
    public void IncreaseSpeed() {
        for (int i = 0; i < Time_motion.length; i++) 
            Time_motion[i].setDelay(Time_motion[i].getDelay()-80);       
    }
    
    public static void RestoreSpeed() {
        for (int i = 0; i < Time_motion.length; i++) 
            Time_motion[i].setDelay(Delay);
    }
    
    public static void StopTime() {
        for (int i = 0; i < Time_motion.length; i++) 
            Time_motion[i].stop();      
    }
}

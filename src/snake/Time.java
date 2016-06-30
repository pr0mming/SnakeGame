
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
    private ActionListener[] Action = {
        new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                SnakeGame.getPlay().MovingSnake("Left");  
                ValueSpeed(SnakeGame.getPlay().getPoints());
                SnakeGame.getTime().StartLEDEffect();
            }
        },
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SnakeGame.getPlay().MovingSnake("Right");
                ValueSpeed(SnakeGame.getPlay().getPoints());
                SnakeGame.getTime().StartLEDEffect();
            }
        },
        new ActionListener() {
            public void actionPerformed(ActionEvent i) {
                SnakeGame.getPlay().MovingSnake("Up");
                ValueSpeed(SnakeGame.getPlay().getPoints());
                SnakeGame.getTime().StartLEDEffect();
            }
        },
        new ActionListener() {
            public void actionPerformed(ActionEvent o) {
                SnakeGame.getPlay().MovingSnake("Down");
                ValueSpeed(SnakeGame.getPlay().getPoints());
                SnakeGame.getTime().StartLEDEffect();
            }
        }
    };
    
    private Timer Led_effect;
    private static Timer[] Time_motion;
    private boolean Status_ledeffect;
    private boolean[] Status_time_motion;
    private int Time_bonus, Goal;
    
    public Time() {
        Delay = 500;
        Goal = 100;
        Time_motion = new Timer[Action.length];
        Status_time_motion = new boolean[Time_motion.length];      
        Status_ledeffect = false;
        Time_bonus = (900 * 5); //A second can be approximated to 900 milliseconds, multiplied by five, are 13500 milliseconds (5 seconds)
        LocateTimes();
        LEDEffect();
    }
    /*
        If you get to the 100 points the speed of the snake increases 
        and add another 100 to the next goal!
    */
    public void ValueSpeed(int x) {
        if(x  >= Goal) {
            IncreaseSpeed();
            Goal+=x;
        }
    }
    /*
        Time runs enable you chose and others hold ...
    */
    public void Actuate(int e, int l) {
        for (int i = 0; i < l; i++) {
            if(i == e) {
                StartMotion(i);
            } else {
                StopMotion(i);
            }
        }
    }
    /*
        This method defines a timer that makes the frame flashes white, 
        updated every 100 milliseconds the time remaining to complete the bonus and 
        when it is right to block special color or variable reaches zero this timer 
        is stopped and restarted certain variables ...
    */
    private void LEDEffect() {
        Led_effect = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SnakeGame.UpdateTimeBonus(Time_bonus);
                Time_bonus-=Led_effect.getDelay();             
                SnakeGame.getPanel().setBackground((SnakeGame.getPanel().getBackground() == Color.white)?Color.black:Color.white);
                if (SnakeGame.getPlay().getCurrentColor() != SnakeGame.getPlay().getSpecialColor() || Time_bonus <= 0) {
                    SnakeGame.UpdateTimeBonus(Time_bonus);
                    Time_bonus = (900 * 5);
                    SnakeGame.getPlay().DeleteBonus();
                    SnakeGame.getPanel().setBackground(Color.white);
                    Led_effect.stop();
                    Status_ledeffect = false;
                }
            }
        });
    }
    // Activates the timer bonus
    public void StartLEDEffect() {
        if (SnakeGame.getPlay().getCurrentColor() == SnakeGame.getPlay().getSpecialColor() && !Status_ledeffect) {
            Status_ledeffect = true;
            Led_effect.start();      
        }
    }
    // Defines the movement times, it is responsible for moving the snake "automatically"
    private void LocateTimes() {
        for (int i = 0; i < Time_motion.length; i++) {
            Time_motion[i] = new Timer(Delay, Action[i]);
            Status_time_motion[i] = false;
        }
    }
    // If possible start the timer indicated
    public void StartMotion(int t) {
        if (!Status_time_motion[t]) {
            Time_motion[t].start();
            Status_time_motion[t] = true;
        }
    }
    // If possible stops the timer indicated
    public void StopMotion(int t) {
        if (Status_time_motion[t]) {
            Time_motion[t].stop();
            Status_time_motion[t] = false;
        }
    }
    
    public int AmountTimers() {
        return Time_motion.length;
    }
    // Increases speed in all directions
    public void IncreaseSpeed() {
        for (int i = 0; i < Time_motion.length; i++) 
            Time_motion[i].setDelay(Time_motion[i].getDelay()-80);       
    }
    // The delay returns to normal in all movements
    public void RestoreSpeed() {
        for (int i = 0; i < Time_motion.length; i++) 
            Time_motion[i].setDelay(Delay);
    }
    // If possible stop all movements
    public void StopTime() {
        for (int i = 0; i < Time_motion.length; i++) 
            if (Status_time_motion[i]) {
                Time_motion[i].stop();
                Status_time_motion[i] = false;
            }
    }
}

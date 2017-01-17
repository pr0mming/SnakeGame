
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
    
    private int delay;
    private GameScene instanceGame;
    
    private ActionListener[] actions = {
        new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instanceGame.getPlay().moveSnake("Left");  
                valueSpeed(instanceGame.getPlay().getScore());
                instanceGame.getTime().startBonus();
            }
        },
        new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instanceGame.getPlay().moveSnake("Right");
                valueSpeed(instanceGame.getPlay().getScore());
                instanceGame.getTime().startBonus();
            }
        },
        new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instanceGame.getPlay().moveSnake("Up");
                valueSpeed(instanceGame.getPlay().getScore());
                instanceGame.getTime().startBonus();
            }
        },
        new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instanceGame.getPlay().moveSnake("Down");
                valueSpeed(instanceGame.getPlay().getScore());
                instanceGame.getTime().startBonus();
            }
        }
    };
    
    private Timer timerBonus;
    private Timer[] timerMotion;
    private int timeBonus, goal;
    
    public Time(GameScene instanceGame) {
        this.instanceGame = instanceGame;
        delay = 500;
        goal = 100;
        timerMotion = new Timer[actions.length];
        timeBonus = (900 * 5); //A second can be approximated to 900 milliseconds, multiplied by five, are 13500 milliseconds (5 seconds)
        createTimers();
        createBonus();
        startMotion(0);
    }
    /*
        If you get to the 100 points the speed of the snake increases 
        and add another 100 to the next goal!
    */
    public void valueSpeed(int x) {
        if(x  >= goal) {
            increaseSpeed();
            goal+=x;
        }
    }
    /*
        Time runs enable you chose and others hold ...
    */
    public void actuate(int e) {
        for (int i = 0; i < timerMotion.length; i++) {
            if(i == e) 
                startMotion(i);
            else 
                stopMotion(i);    
        }
    }
    /*
        This method defines a timer that makes the frame flashes white, 
        updated every 100 milliseconds the time remaining to complete the bonus and 
        when it is right to block special color or variable reaches zero this timer 
        is stopped and restarted certain variables ...
    */
    private void createBonus() {
        timerBonus = new Timer(90, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeBonus -= 100;             
                instanceGame.getPanelGame().setBackground((instanceGame.getPanelGame().getBackground() == Color.white)?Color.black:Color.white);
                if ((!instanceGame.getPlay().getBonus()) || timeBonus <= 0) {
                    instanceGame.getPlay().setBonus(false);
                    timeBonus = (900 * 5);
                    instanceGame.getPlay().deleteBonus();
                    instanceGame.getPanelGame().setBackground(Color.white);
                    timerBonus.stop();                   
                }
            }
        });
    }
    // Activates the timer bonus
    public void startBonus() {
        if (instanceGame.getPlay().getBonus() && !timerBonus.isRunning()) 
            timerBonus.start();      
    }
    // Defines the movement times, it is responsible for moving the snake "automatically"
    private void createTimers() {
        for (int i = 0; i < timerMotion.length; i++) 
            timerMotion[i] = new Timer(delay, actions[i]);
    }
    // If possible start the timer indicated
    public void startMotion(int t) {
        if (!timerMotion[t].isRunning()) 
            timerMotion[t].start();      
    }
    // If possible stops the timer indicated
    public void stopMotion(int t) {
        if (timerMotion[t].isRunning()) 
            timerMotion[t].stop();      
    }
    
    public int getAmountTimers() {
        return this.timerMotion.length;
    }
    // Increases speed in all directions
    public void increaseSpeed() {
        for (int i = 0; i < timerMotion.length; i++) 
            timerMotion[i].setDelay(timerMotion[i].getDelay()-80);       
    }
    // The delay returns to normal in all movements
    public void restoreSpeed() {
        for (int i = 0; i < timerMotion.length; i++) 
            timerMotion[i].setDelay(delay);
    }
    
    public int getSpeed() {
        return timerMotion[0].getDelay();
    }
    // If possible stop all movements
    public void stopTime() {
        for (int i = 0; i < timerMotion.length; i++) 
            if (timerMotion[i].isRunning()) 
                timerMotion[i].stop();           
    }
}

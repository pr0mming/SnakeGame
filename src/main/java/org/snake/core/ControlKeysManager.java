
package org.snake.core;

import org.snake.scenes.GameScene;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

/**
 * This class represents the logic to move the snake
 */

public class ControlKeysManager implements KeyEventDispatcher {
    /*
        This class is responsible for the controls ...
    */
    private final GameScene instanceGame;

    public ControlKeysManager(GameScene instanceGame) {
        this.instanceGame = instanceGame;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        if (e.getID() == KeyEvent.KEY_PRESSED) {
            String direction = instanceGame.getGameManager().getDirection();

            this.instanceGame.getScheduler().valueSpeed(this.instanceGame.getGameManager().getScore());
            this.instanceGame.getScheduler().startBonus();

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (!direction.equals("Right")) {
                    instanceGame.getScheduler().actuate(0);
                    instanceGame.getGameManager().moveSnake("Left");
                }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (!direction.equals("Left")) {
                    this.instanceGame.getScheduler().actuate(1);
                    this.instanceGame.getGameManager().moveSnake("Right");
                }
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (!direction.equals("Down")) {
                    this.instanceGame.getScheduler().actuate(2);
                    this.instanceGame.getGameManager().moveSnake("Up");
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (!direction.equals("Up")) {
                    this.instanceGame.getScheduler().actuate(3);
                    this.instanceGame.getGameManager().moveSnake("Down");
                }
            }
        }

        return false;
    }

}

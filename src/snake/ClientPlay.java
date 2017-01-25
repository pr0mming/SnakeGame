
package snake;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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

public class ClientPlay {

    private GameScene instanceGame;
    private JLabel[][] matrix;
    private int snakeLen, len, score, xFood, yFood, foodInMemory;
    private ArrayList<String> snake, foodInBonus;
    private Color snakeColor;
    private String direction;
    private boolean bonus, lose, win;
    private Random rnd;
    
    public ClientPlay(GameScene instanceGame) {
        this.rnd = new Random();
        this.instanceGame = instanceGame;
        this.matrix = instanceGame.getMatrix();
        startGame();
    }
    
    private void startGame() {
        this.snakeLen = 4;
        this.foodInMemory = 0;
        this.score = 0;
        this.foodInBonus = new ArrayList<>();
        this.snake = new ArrayList<>();
        this.snakeColor = Color.white;
        this.bonus = false;
        this.lose = false;
        this.win = false;
        
        createSnake();
        createFood();
        
    }
    
    /*    
        This method allows you to see if the coordinates given are block food 
        if it is not then could belong to a block bonus: 
        If the special color (bonus) the effect is activated and the snake will double,
        also generate new food.
    */
    public void eat(int x, int y) {    
        if (xFood == x && yFood == y) {
            matrix[x][y].setIcon(null);
            foodInMemory+=2;
            incrementScore(10);
            createFood();
        } else
            if (foodInBonus.contains(x+","+y)) {
                matrix[x][y].setIcon(null);
                foodInMemory+=1;
                incrementScore(10);
                foodInBonus.remove(x+","+y);
                
                if (foodInBonus.isEmpty()) bonus = false;
            }
        
        len = snake.size();
        instanceGame.updateLenght(len);
        youWin();
    }
    
    //  Calculates and updates the score
    public void incrementScore(int p) {
        score+=p;
        instanceGame.updateScore(score);
    } 
    
    /*
        lim takes the random value and according to this value meals that rely 
        on special color is generated, the coordinates are stored in an array 
        to know exactly where they are and want to delete them at once ...
    */
    public void createBonus(int lim) {
        bonus = true;
        
        while (lim > 0) {
            int x = (rnd.nextInt(matrix.length - 0) + 0);
            int y = (rnd.nextInt(matrix[0].length - 0) + 0);
            
            if (x != xFood && y != yFood && !foodInBonus.contains(x+","+y) && !snake.contains(x+","+y)) {
                foodInBonus.add(x+","+y);
                matrix[x][y].setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resources/food.png")).getImage().getScaledInstance(matrix[x][y].getSize().width, matrix[x][y].getSize().height, Image.SCALE_SMOOTH)));
                lim--;
            }
        }
    }
    
    /*        
        This method is very important. 
        Can generate a block of food once a block of food disappears. 
        the coordinates of the block in two variables is stored, if the special color 
        is chosen (bonus) will be generated n amount of new food, all randomly ...
    */
    public void createFood() {
        int xRnd = rnd.nextInt(matrix.length - 0) + 0;
        int yRnd = rnd.nextInt(matrix[0].length - 0) + 0;
        
        if(snake.contains(xRnd+","+yRnd) || foodInBonus.contains(xRnd+","+yRnd)) {
            createFood();
        } else {
            xFood = xRnd;
            yFood = yRnd;
            
            int b = (rnd.nextInt(10 - 1) + 1);
            
            if (b % 3 == 0 && !bonus) {
                createBonus(rnd.nextInt(4 - 2) + 2);
            } else 
                matrix[xFood][yFood].setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resources/food.png")).getImage().getScaledInstance(matrix[xFood][yFood].getSize().width, matrix[xFood][yFood].getSize().height, Image.SCALE_REPLICATE)));             
        }
    }
    
    /*
        This method generates a snake randomly selecting a row and a column. 
        Then calculates how many there are left and right blocks, 
        if the condition is true then I'll move right snake and put the 
        opposite direction (left) but would be a reverse case ...
    */
    public void createSnake() {  
        int x = (matrix.length / 2); int y = (matrix[0].length / 2);
              
        if (snakeLen > (matrix[0].length - 1) / 2) snakeLen = 4;
            
        for (int i = 0, j = y; i <= snakeLen; i++, j++) {
            matrix[x][j].setBackground(snakeColor);
            snake.add(x+","+j);
        }      
    
        direction = "Left";
        len = snake.size();
        instanceGame.updateLenght(len);
    } 
    
    /*
        This method mobilizes the snake, when a block previous to 
        this it moves into position and does this each, 
        all at the reference part of the first block, this is done recursively moves
    */
    public void reposition(int x, int y, int index) {
        int[] array = {x, y};
        
        if(index >= snake.size()) {
            if (foodInMemory > 0) {
                matrix[x][y].setBackground(snakeColor);
                snake.add(x+","+y);
                foodInMemory--;
            } else
                matrix[x][y].setBackground(instanceGame.getBackgroundGame());            
        } else {
            String[] coords = snake.get(index).split(",");
            snake.set(index, x+","+y);
            matrix[x][y].setBackground(snakeColor);
            
            reposition(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]), index+=1);
        }
    }
    
    /*
        This method gives direction to the snake, the snake further 
        determines whether "ate herself" and if the next block exists
    */
    public void moveSnake(String key) {
        String[] coords = snake.get(0).split(",");
        
        if (key.equals("Right") && !direction.equals("Left") && Integer.valueOf(coords[1]) < matrix[0].length - 1) {
            if (!snake.contains(coords[0] + "," + (Integer.valueOf(coords[1]) + 1))) {
                reposition(Integer.valueOf(coords[0]), (Integer.valueOf(coords[1]) + 1), 0);
                eat(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]) + 1);
                direction = key;
            } else {
                youLose();
            }
        } else {
            if (key.equals("Left") && !direction.equals("Right") && Integer.valueOf(coords[1]) > 0) {
                if (!snake.contains(coords[0] + "," + (Integer.valueOf(coords[1]) - 1))) {
                    reposition(Integer.valueOf(coords[0]), (Integer.valueOf(coords[1]) - 1), 0);
                    eat(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]) - 1);
                    direction = key;
                } else {
                    youLose();
                }
            } else {
                if (key.equals("Up") && !direction.equals("Down") && Integer.valueOf(coords[0]) > 0) {
                    if (!snake.contains((Integer.valueOf(coords[0]) - 1) + "," + coords[1])) {
                        reposition((Integer.valueOf(coords[0]) - 1), Integer.valueOf(coords[1]), 0);
                        eat(Integer.valueOf(coords[0]) - 1, Integer.valueOf(coords[1]));
                        direction = key;
                    } else {
                        youLose();
                    }
                } else {
                    if (key.equals("Down") && !direction.equals("Up") && Integer.valueOf(coords[0]) < matrix.length - 1) {
                        if (!snake.contains((Integer.valueOf(coords[0]) + 1) + "," + coords[1])) {
                            reposition((Integer.valueOf(coords[0]) + 1), Integer.valueOf(coords[1]), 0);
                            eat(Integer.valueOf(coords[0]) + 1, Integer.valueOf(coords[1]));
                            direction = key;
                        } else {
                            youLose();
                        }
                    } else {
                        youLose();
                    }
                }
            }
        }
    }
    
    /*        
        This method receives an ArrayList, 
        covers half of its length and takes the first two elements to locate 
        and "restore" its color and edge finally removes ...
    */
    
    public void deleteArray(ArrayList<String> x) {
        
        for(String element : x) {
            String[] coords = element.split(",");
            matrix[Integer.valueOf(coords[0])][Integer.valueOf(coords[1])].setBackground(instanceGame.getBackground());
            matrix[Integer.valueOf(coords[0])][Integer.valueOf(coords[1])].setIcon(null);
        }
        
        x.removeAll(x);
    }
    
    /*
        This method restarts the game, restore points, stops and restore times, 
        regenerates the snake, etc ...
    */
    
    public void restartGame() {                  
        instanceGame.getScheduler().restoreSpeed();
        deleteArray(snake);
        deleteArray(foodInBonus);
        matrix[xFood][yFood].setIcon(null);
        
        startGame();       
        instanceGame.updateScore(score);
    }
    
    /*
        It determines if the snake has the length of at 
        least the product of the rows and columns least two ...
    */
    
    public void youWin() {
        if (!win && len >= ((matrix.length * matrix[0].length) * 0.25)) {
            
            win = true;
            instanceGame.getScheduler().stopAllTimers();
            instanceGame.removeKeyFocus();
            instanceGame.restoreColorPanel();
            
            ActionListener[] actions = new ActionListener[]{
                new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            restartGame();
                            instanceGame.addKeyboardFocus();
                        }
                },

                new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            instanceGame.changeScene(new MenuScene());
                        }
                }
            };

            new Dialog(actions, "You Win", "It looks like you have won");
        }
    }
    
    
    public void youLose() {
        if (!lose) {
            /*
                The boolean is an aid to avoid the appearance of multiple JoptionPane in case of playing "wildly"
            */
            lose = true;
            instanceGame.getScheduler().stopAllTimers();
            instanceGame.removeKeyFocus();
            instanceGame.restoreColorPanel();
            
            ActionListener[] actions = new ActionListener[]{
                new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            restartGame();
                            instanceGame.addKeyboardFocus();
                        }
                },

                new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            instanceGame.changeScene(new MenuScene());
                        }
                }
            };

            new Dialog(actions, "You Lose", "You lost, but you can try again");
        }
    }
    
    /*
        When time is running out bonus, 
        bonus blocks are removed and generates a new block of food
    */
    
    public void deleteBonus() {
        deleteArray(foodInBonus);
        matrix[xFood][yFood].setIcon(null);
        createFood();
    }
    
    // -- Down here are just getters --
    
    public Color getSnakeColor() {
        return this.snakeColor;
    }
    
    public int getXFood() {
        return this.xFood;
    }
  
    public int getYFood() {
        return this.yFood;
    }
    
    public JLabel[][] getMatrix() {
        return this.matrix;
    }
    
    public boolean getBonus() {
        return this.bonus;
    }
    
    public void setBonus(boolean e) {
        this.bonus = e;
    }
    
    public String getDirection() {
        return this.direction;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public int getLen() {
        return this.len;
    }
    
    public ArrayList<String> getFoodInBonus() {
        return this.foodInBonus;
    }
    
}

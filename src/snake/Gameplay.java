
package snake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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

public class Gameplay {
    
    private SnakeGame instanceGame;
    private JLabel[][] matrix;
    private int snakeLen, xFood, yFood, len, score;
    private ArrayList<Integer> snake;
    private ArrayList<Integer> foodInBonus;
    private Color snakeColor;
    private Color[] foodColor = {Color.yellow, Color.blue, Color.red};
    private String direction;
    private boolean bonus, lose;
    
    public Gameplay(JLabel[][] matrix, SnakeGame instanceGame) {
        this.instanceGame = instanceGame;
        this.matrix = matrix;
        startGame();
    }
    
    public void startGame() {
        this.bonus = false;
        this.lose = false;
        this.snakeLen = 4;
        this.foodInBonus = new ArrayList<>(Arrays.asList());
        this.snake = new ArrayList<>(Arrays.asList());
        this.snakeColor = Color.white;
        this.score = 0;
        generateSnake();
        food();
    }
    /*    
        This method allows you to see if the coordinates given are block food 
        if it is not then could belong to a block bonus: 
        If the special color (bonus) the effect is activated and the snake will double,
        also generate new food.
    */
    private void eat(int x, int y) {
        if(x == xFood && y == yFood) {          
            if(matrix[x][y].getBackground() == foodColor[2]) {
                bonus = false;
                duplicateSnake(4);
                generateScore(20);
            } else {
                duplicateSnake(2);
                generateScore(10);
            }
            
            food();
        }
        
        len = snake.size()/2;
        this.instanceGame.updateLenght(len);
        youWin();
    }
    /*
        This method allows the snake can grow depending on how much. 
        For example: If I want to stretch to 3 blocks then 3 x 2 = 6 and x = 6, 
        the same if I want to stretch 2 blocks then 2 x 2 = 4 and x = 4. so on ...   
    */
    private void duplicateSnake(int x) {
        int n = x;
        while(n > 0) {
            snake.add((((snake.get(snake.size()-n)) <= 0)?(snake.get(snake.size()-n))+1:(snake.get(snake.size()-n))-1));
            n--;
        }
    }
    //  Calculates and updates the score
    private void generateScore(int p) {
        score+=p;
        this.instanceGame.updateScore(score);
    }    
    /*
        lim takes the random value and according to this value meals that rely 
        on special color is generated, the coordinates are stored in an array 
        to know exactly where they are and want to delete them at once ...
    */
    private void generateBonus(int lim) {
        while (lim > 0) {
            foodInBonus.add((int) Math.floor(Math.random()*matrix[0].length));
            foodInBonus.add((int) Math.floor(Math.random()*matrix.length));
            matrix[foodInBonus.get(foodInBonus.size()-2)][foodInBonus.get(foodInBonus.size()-1)].setBorder(BorderFactory.createLineBorder(getCurrentColor()));
            lim--;
        }
    }
    /*        
        This method is very important. 
        Can generate a block of food once a block of food disappears. 
        the coordinates of the block in two variables is stored, if the special color 
        is chosen (bonus) will be generated n amount of new food, all randomly ...
    */
    private void food() {
        xFood = (int) Math.floor(Math.random()*matrix.length);
        yFood = (int) Math.floor(Math.random()*matrix[0].length);
        
        if(matrix[xFood][yFood].getBackground() == snakeColor) {
            food();
        } else {
            Color c = foodColor[(int) Math.floor(Math.random()*foodColor.length)];
            matrix[xFood][yFood].setBackground(c);
            if (c == foodColor[2]) {
                bonus = true;
                generateBonus((int) Math.floor(Math.random()*(3-1)+1));
            }
        }
    }
    /*
        This method generates a snake randomly selecting a row and a column. 
        Then calculates how many there are left and right blocks, 
        if the condition is true then I'll move right snake and put the 
        opposite direction (left) but would be a reverse case ...
    */
    private void generateSnake() {  
        int x = 15;
        int y = 30;
              
        for (int i = 0, j = y; i <= snakeLen; i++, j++) {
            matrix[x][j].setBackground(snakeColor);
            snake.add(x);
            snake.add(j);
        }      
    
        direction = "Left";
        len = snake.size()/2;
        instanceGame.updateLenght(len);
    }     
    /*
        This method mobilizes the snake, when a block previous to 
        this it moves into position and does this each, 
        all at the reference part of the first block, this is done recursively moves
    */
    private void reposition(int x, int y, int index) {
        int[] array = {x, y};
        
        if(index >= snake.size()) {
            matrix[x][y].setBackground(this.instanceGame.getBackgroundGame());            
        } else {
            for (int i = 0, j = index; i < array.length; i++, j++) {
                int tmp = snake.get(j);
                snake.set(j, array[i]);
                array[i] = tmp;
            }
            int tmp = index+1;
            matrix[snake.get(index)][snake.get(tmp)].setBackground(snakeColor);           
            reposition(array[0], array[1], index+=2);
        }
    }
    /*
        This method gives direction to the snake, the snake further 
        determines whether "ate herself" and if the next block exists
    */
    public void moveSnake(String tecla) {
            if(tecla.equals("Right") && !direction.equals("Left") && snake.get(1) < matrix[0].length-1) {
                if(matrix[snake.get(0)][(snake.get(1)+1)].getBackground() != snakeColor) {     
                    reposition(snake.get(0), (snake.get(1)+1), 0);
                    eat(snake.get(0), snake.get(1));
                    direction = tecla;
                } else {
                    youLose();
                }
            } else
                if(tecla.equals("Left") && !direction.equals("Right") && snake.get(1) > 0) {
                    if(matrix[snake.get(0)][(snake.get(1)-1)].getBackground() != snakeColor) {
                        reposition(snake.get(0), (snake.get(1)-1), 0);
                        eat(snake.get(0), snake.get(1));
                        direction = tecla;
                    } else {
                        youLose();
                    }
                } else
                    if(tecla.equals("Up") && !direction.equals("Down") && snake.get(0) > 0) {
                        if(matrix[(snake.get(0)-1)][snake.get(1)].getBackground() != snakeColor) {
                            reposition((snake.get(0)-1), snake.get(1), 0);
                            eat(snake.get(0), snake.get(1));
                            direction = tecla;
                        } else {
                            youLose();
                        }
                    } else
                        if(tecla.equals("Down") && !direction.equals("Up") && snake.get(0) < matrix[0].length-1) {
                            if(matrix[snake.get(0)+1][snake.get(1)].getBackground() != snakeColor) {
                                reposition((snake.get(0)+1), snake.get(1), 0);
                                eat(snake.get(0), snake.get(1));
                                direction = tecla;
                            } else {
                                youLose();
                            }
                        } else {
                            youLose();
                        }
        
    }
    /*        
        This method receives an ArrayList, 
        covers half of its length and takes the first two elements to locate 
        and "restore" its color and edge finally removes ...
    */
    private void deleteArray(ArrayList<Integer> x) {
        int n = x.size()/2;
        while(n > 0) {
            matrix[x.get(0)][x.get(1)].setBorder(BorderFactory.createLineBorder(Color.black));
            matrix[x.get(0)][x.get(1)].setBackground(this.instanceGame.getBackgroundGame());       
            x.remove(1);
            x.remove(0);
            n--;
        }
    }
    /*
        This method restarts the game, restore points, stops and restore times, 
        regenerates the snake, etc ...
    */
    public void restartGame() {                  
        this.instanceGame.getTime().stopTime();
        this.instanceGame.getTime().restoreSpeed();
        deleteArray(snake);
        deleteArray(foodInBonus);
        matrix[xFood][yFood].setBackground(this.instanceGame.getBackgroundGame());    
        
        startGame();       
        this.instanceGame.updateScore(score);
    }
    /*
        It determines if the snake has the length of at 
        least the product of the rows and columns least two ...
    */
    private void youWin() {
        if (len >= ((matrix[0].length * matrix.length)-2)) {
            JOptionPane.showMessageDialog(null, "LOL... you must be crazy to want to see this message", "Impossible", JOptionPane.INFORMATION_MESSAGE);
            restartGame();
        }
    }
    
    private void youLose() {
        if (!lose) {
            /*
                The boolean is an aid to avoid the appearance of multiple JoptionPane in case of playing "wildly"
            */
            this.lose = true;
            this.instanceGame.removeKeyFocus();

            Object[] options = { "Try again", "Back to menu" };

            int result = JOptionPane.showOptionDialog(null, "You have lost", "Sorry",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, 0);
            if (result == JOptionPane.YES_OPTION){
                restartGame();
                this.instanceGame.addKeyboardFocus();
            } else {

            }
        }
    }
    /*
        When time is running out bonus, 
        bonus blocks are removed and generates a new block of food
    */
    public void deleteBonus() {
        deleteArray(foodInBonus);
        matrix[xFood][yFood].setBackground(this.instanceGame.getBackgroundGame());
        food();
    }
    // -- Down here are just getters --
    public Color getsnakeColor() {
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
    
    public boolean getEffect() {
        return this.bonus;
    }
    
    public void setBonus(boolean e) {
        this.bonus = e;
    }
    
    public String getDirection() {
        return this.direction;
    }
    
    public Color getCurrentColor() {
        return this.matrix[xFood][yFood].getBackground();
    }
    
    public Color getSpecialColor() {
        return this.foodColor[2];
    }
    
    public int getScore() {
        return this.score;
    }
    
    public int getLen() {
        return this.len;
    }
    
    public ArrayList<Integer> getFoodInBonus() {
        return this.foodInBonus;
    }
    
}

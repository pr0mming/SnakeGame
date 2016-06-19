
package snake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

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
    
    private JLabel[][] Matriz;
    private int Length_snake, x_food, y_food, Lenght, Points;
    private ArrayList<Integer> Snake;
    private ArrayList<Integer> IndexBonus;
    private Color SnakeColor;
    private Color[] FoodColor = {Color.yellow, Color.blue, Color.red};
    private String Direction;
    private boolean Effect;
    
    public Gameplay(JLabel[][] Matriz) {
        this.Matriz = Matriz;
        Effect = false;
        Length_snake = 4;
        IndexBonus = new ArrayList<>(Arrays.asList());
        Snake = new ArrayList<>(Arrays.asList());
        SnakeColor = Color.white;
        Points = 0;
        GenerateSnake();
        Lenght = Snake.size()/2;
        Food();
    }
    
    private void Eat(int x, int y) {
        int[] c = {x, y};
        
        if(x == x_food && y == y_food) {          
            if(Matriz[x][y].getBackground() == FoodColor[2]) {
                Effect = false;
                DuplicateSnake(4);
                GeneratePoints(20);
            } else {
                DuplicateSnake(2);
                GeneratePoints(10);
            }
            Food();
        } else 
            if (((LineBorder) Matriz[x][y].getBorder()).getLineColor() == FoodColor[2]) {
                Matriz[x][y].setBorder(BorderFactory.createLineBorder(Color.black));
                DuplicateSnake(2);
                GeneratePoints(5);
            }    
        Lenght = Snake.size()/2;
        SnakeGame.UpdateLenght(Lenght);
        YouWin();
    }
    
    private void DuplicateSnake(int x) {
        int n = x;
        while(n > 0) {
            Snake.add((((Snake.get(Snake.size()-n)) <= 0)?(Snake.get(Snake.size()-n))+1:(Snake.get(Snake.size()-n))-1));
            n--;
        }
    }
    
    private void GeneratePoints(int p) {
        Points+=p;
        SnakeGame.UpdatePoints(Points);
    }
    
    private void GenerateBonus(int lim) {
        while (lim > 0) {
            IndexBonus.add((int) Math.floor(Math.random()*Matriz[0].length));
            IndexBonus.add((int) Math.floor(Math.random()*Matriz.length));
            Matriz[IndexBonus.get(IndexBonus.size()-2)][IndexBonus.get(IndexBonus.size()-1)].setBorder(BorderFactory.createLineBorder(getCurrentColor()));
            lim--;
        }
    }
    
    private void Food() {
        x_food = (int) Math.floor(Math.random()*Matriz.length);
        y_food = (int) Math.floor(Math.random()*Matriz[0].length);
        if(Matriz[x_food][y_food].getBackground() == SnakeColor) {
            Food();
        } else {
            Color c = FoodColor[(int) Math.floor(Math.random()*FoodColor.length)];
            Matriz[x_food][y_food].setBackground(c);
            if (c == FoodColor[2]) {
                Effect = true;
                GenerateBonus((int) Math.floor(Math.random()*(3-1)+1));
            }
        }
    }
    
    private void GenerateSnake() {        
        int x = 15; int y = 20;
        
        for (int i = 0, j = y; i <= Length_snake; i++, j++) {
            Matriz[x][j].setBackground(SnakeColor);
            Snake.add(x);
            Snake.add(j);
        }
        Direction = "Left";
        Lenght = Snake.size()/2;
        SnakeGame.UpdateLenght(Lenght);
    }     
    
    private void Reposition(int x, int y, int index) {
        int[] array = {x, y};
        if(index >= Snake.size()) {
            Matriz[x][y].setBackground(SnakeGame.getBackgroundGame());            
        } else {
            for (int i = 0, j = index; i < array.length; i++, j++) {
                int tmp = Snake.get(j);
                Snake.set(j, array[i]);
                array[i] = tmp;
            }
            int tmp = index+1;
            Eat(x, y);
            Matriz[Snake.get(index)][Snake.get(tmp)].setBackground(SnakeColor);           
            Reposition(array[0], array[1], index+=2);
        }
    }
    
    public void MovingSnake(String tecla) {
            if(tecla.equals("Right") && !Direction.equals("Left") && Snake.get(1) < Matriz[0].length-1) {
                if(Matriz[Snake.get(0)][(Snake.get(1)+1)].getBackground() != SnakeColor) {     
                    Reposition(Snake.get(0), (Snake.get(1)+1), 0);
                    Direction = tecla;
                } else {
                    RestartEnvironment(false);
                }
            } else
                if(tecla.equals("Left") && !Direction.equals("Right") && Snake.get(1) > 0) {
                    if(Matriz[Snake.get(0)][(Snake.get(1)-1)].getBackground() != SnakeColor) {
                        Reposition(Snake.get(0), (Snake.get(1)-1), 0);
                        Direction = tecla;
                    } else {
                        RestartEnvironment(false);
                    }
                } else
                    if(tecla.equals("Up") && !Direction.equals("Down") && Snake.get(0) > 0) {
                        if(Matriz[(Snake.get(0)-1)][Snake.get(1)].getBackground() != SnakeColor) {
                            Reposition((Snake.get(0)-1), Snake.get(1), 0);
                            Direction = tecla;
                        } else {
                            RestartEnvironment(false);
                        }
                    } else
                        if(tecla.equals("Down") && !Direction.equals("Up") && Snake.get(0) < Matriz[0].length-1) {
                            if(Matriz[Snake.get(0)+1][Snake.get(1)].getBackground() != SnakeColor) {
                                Reposition((Snake.get(0)+1), Snake.get(1), 0);
                                Direction = tecla;
                            } else {
                                RestartEnvironment(false);
                            }
                        } else {
                            RestartEnvironment(false);
                        }
        
    }
    
    private void DeleteArray(ArrayList<Integer> x) {
        int n = x.size()/2;
        while(n > 0) {
            Matriz[x.get(0)][x.get(1)].setBackground(SnakeGame.getBackgroundGame());
            Matriz[x.get(0)][x.get(1)].setBorder(BorderFactory.createLineBorder(Color.black));
            x.remove(1);
            x.remove(0);
            n--;
        }
    }
    
    public void RestartEnvironment(boolean r) {                  
        Time.StopTime();
        Time.RestoreSpeed();
        DeleteArray(Snake);
        DeleteArray(IndexBonus);
        GenerateSnake();
        Matriz[x_food][y_food].setBackground(SnakeGame.getBackgroundGame());
        Food();
        Points = 0;
        SnakeGame.UpdatePoints(Points);
        JOptionPane.showMessageDialog(null, (!r)?"Sorry ... You've lost but starts a new game!":"It starts a new game :D", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void YouWin() {
        if (Lenght >= ((Matriz[0].length * Matriz.length)-2)) {
            JOptionPane.showMessageDialog(null, "LOL... you must be crazy to want to see this message", "Impossible", JOptionPane.INFORMATION_MESSAGE);
            RestartEnvironment(true);
        }
    }
    
    public Color getSnakeColor() {
        return SnakeColor;
    }
    
    public int getXFood() {
        return x_food;
    }
    
    public int getYFood() {
        return y_food;
    }
    
    public JLabel[][] getMatriz() {
        return Matriz;
    }
    
    public boolean getEffect() {
        return Effect;
    }
    
    public void setEffect(boolean e) {
        this.Effect = e;
    }
    
    public String getDirection() {
        return Direction;
    }
    
    public Color getCurrentColor() {
        return Matriz[x_food][y_food].getBackground();
    }
    
    public Color getSpecialColor() {
        return FoodColor[2];
    }
    
    public int getPoints() {
        return Points;
    }
    
    public int getLenght() {
        return Lenght;
    }
}

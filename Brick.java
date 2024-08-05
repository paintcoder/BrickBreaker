import java.awt.*;
import javax.imageio.*;
import java.io.*;
public class Brick{
    private int height;
    private int width;
    private int x;
    private int y;
    private Color color;
    private boolean visible;
    private Image imgWeakBrick;
    private static int score; // keeps track of score
    public Brick(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.visible = true;
        try{
            imgWeakBrick =ImageIO.read(new File("Images/weakBrick.png"));
        }catch (IOException e){}
    }
    
    public int getX(){return x;}
    public int getY(){return y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public boolean getVisible(){return visible;}
    public Color getColor(){return color;}
    public static int getScore(){return score;}
    
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public void setWidth(int width){this.width = width;}
    public void setHeight(int height){this.height = height;}
    public void setVisible(boolean visible){this.visible = visible;}
    public void setColor(Color color){this.color = color;}
    public static void setScore(int s){score = s;}
    public static void addToScore(int s){score +=s;}
    
    public void draw(Graphics g){
        g.drawImage(imgWeakBrick, x, y, null);
        //g.setColor(color);
        // g.fillRect(x, y, width, height);
    }
    public void hit(){
        visible = false;
        score+= 5;
    }
    
    public String getAbility(){
        return "0";
    }
}

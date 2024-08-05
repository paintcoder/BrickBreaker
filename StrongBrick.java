import java.awt.*;
import javax.imageio.*;
import java.io.*;
public class StrongBrick extends Brick{
    private int health;
    private Image imgBrick1;
    private Image imgBrick2;
    private Image imgBrick3;
    
    public StrongBrick(int x, int y, int width, int height, Color color, int health){
        super(x,y,width,height,color);
        this.health = health;
        try{
            imgBrick1 =ImageIO.read(new File("Images/brick1.png"));
            imgBrick2 =ImageIO.read(new File("Images/brick2.png"));
            imgBrick3 =ImageIO.read(new File("Images/brick3.png"));
        }catch (IOException e){}
    }//standard is 3 health at most

    public int getHealth(){return health;}
    
    public void setHealth(int health){this.health = health;}
    
    public void draw(Graphics g){
        if(health==2){
            g.drawImage(imgBrick1, getX(), getY(), null);
        }else if (health ==1){
            g.drawImage(imgBrick2, getX(), getY(), null);
        }else if (health ==0){
            g.drawImage(imgBrick3, getX(), getY(), null);
        }
        
    }
    public void hit(){
        if(health<1){
            setVisible(false);
            Brick.addToScore(15);
        }
        else{
            health--;
            Brick.addToScore(3);}
    }
}

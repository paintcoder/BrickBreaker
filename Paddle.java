import java.awt.*;
public class Paddle extends Brick
{
    private int speed;
    private int fSize;
    
    public Paddle(int x, int y, int width, int height, Color color, int sp){
        super(x,y, width, height, color);
        speed = sp; 
        fSize = 700;
    }
    
    public int getSpeed(){return speed;}
    public void setSpeed(int sp){speed = sp;}
    
    public void moveRight(){
        if(getX()+getWidth() +speed >= fSize-5)
            setX(fSize-getWidth()-5);
        else setX(getX()+speed);
    }
    public void moveLeft(){
        if(getX()-speed <= 5)
            setX(5);
        else setX(getX()-speed);
    }
    public void moveUp(){
        if(getY()-speed <= 5)
            setY(5);
        else setY(getY()-speed);
    }
    public void moveDown(){
        if(getY()+speed +getHeight() >= fSize-5)
            setY(fSize-5-getHeight());
        else setY(getY()+speed);
    }
    public void draw(Graphics g){
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
    public void hit(){
        setVisible(true); //paddle never disappears
    }
}

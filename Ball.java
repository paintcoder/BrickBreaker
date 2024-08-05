import java.awt.*;
public class Ball{
    //instance variables
    private int x;// top left x val
    private int y;
    private int size;//diameter
    private int dx;
    private int dy;
    private Color color;
    private int fSize;
    private Image img;
    private int countdown;
    
    public Ball(int x, int y, int size, int dx, int dy, Color color){
        this.x = x;
        this.y = y;
        this.size = size;
        this.dx = dx;
        this.dy= dy;
        this.color = color;
        fSize = 700;
    }
    public Ball(){
        this.x = 0;
        this.y = 0;
        this.size = 100;
        this.dx = 0;
        this.dy= 0;
        this.color = Color.red;
        fSize = 700;
    }
    
    public int getX(){return x;}
    public int getY(){return y;}
    public int getDx(){return dx;}
    public int getDy(){return dy;}
    public int getSize(){return size;}
    public Color getColor(){return color;}
    public int getCountdown(){return countdown;}
    
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public void setDx(int dx){this.dx = dx;}
    public void setDy(int dy){this.dy = dy;}
    public void setSize(int size){this.size = size;}
    public void setColor(Color color){this.color = color;}
    public void setCountdown(int cd){this.countdown =cd;}
    
    public void move(Graphics g){
        x+= dx;
        //check collision with the left and right walls
        if(x<5){//left wall
            x=5;
            dx=-dx;
        }else if (x + size > fSize-5){
            x = fSize-5 - size;
            dx= -dx;
        }
        y+= dy;
        // check collision with top wall
        if(y<5){
            y=5;
            dy=-dy;
        }else if (y + size > fSize-5){ //check collision with bottom
            y = fSize-5 - size;
            dy= 0;
            dx=0;
        }
        
        draw(g);
    }
    
    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }
    
    public void collidesWith(Brick b){
        Rectangle ball = new Rectangle(x, y, size, size);
        Rectangle brick = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());
        
        if(ball.intersects(brick)){
            b.hit();
            if(y< b.getY() || y + size > b.getY() + b.getHeight()){
                // dy *= -1;
                if(y+size<b.getY() + b.getHeight()/2){
                    dy=-Math.abs(dy); 
                }else{dy=Math.abs(dy);}
            }
            else if(x< b.getX() || x + size > b.getX() + b.getWidth()){
                // dx *= -1;
                if(x+size<b.getX() + b.getWidth()/2){
                    dx=-Math.abs(dx); 
                }else{dx=Math.abs(dx);}
            }
            
        }
    
    }
}

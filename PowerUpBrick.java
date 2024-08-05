import java.awt.*;
import javax.imageio.*;
import java.io.*;
public class PowerUpBrick extends Brick{
    private PowerUp power;
    private Image imgCat;
    private String ability;// randomly chosen
    public PowerUpBrick(int x, int y, int w, int h, Color c){
        super(x,y, w, h, c); // this.visible = true; is in super
        
        int random = (int)(Math.random()*2) +1;
        if(random == 1){
            ability = "wings";}
        else{
        ability = "wider";}
        
        try{
            imgCat =ImageIO.read(new File("Images/"+ ability+"Brick"+((int)(Math.random()*2)+1)+".png"));
        }catch (IOException e){}
    }
    
    public void draw(Graphics g){
        //g.setColor(Color.red);
        //g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.drawImage(imgCat, getX(), getY(), null);
    }
    public String getAbility(){
        return ability;
    }
    public void hit(){
        setVisible(false);
        Brick.addToScore(3);
    }
}

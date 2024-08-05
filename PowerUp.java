import java.awt.*;
import javax.imageio.*;
import java.io.*;
public class PowerUp{
    private int countdown;
    //miliseconds remaining
    private Image image;// transparent bg overlayed image over color bar
    //bar's colour
    private Color color;
    private String ability;
    private static int wingsActive=0;
    private static int widerAmount=0;
    
    public PowerUp(int cd, Color c, String a){
        countdown = cd;
        color =c;
        
        ability = a;
        if (ability.equals("wings")){
            wingsActive++;
            color = new Color(0,0 ,255);
        }
        if (ability.equals("wider")){
            widerAmount+= 20;
            color = new Color(255,255 ,0);
        }
        
        try{
            image =ImageIO.read(new File("Images/"+ ability+".png"));
        }catch (IOException e){}
    }
    
    public void draw(Graphics g,int x){
        g.setColor(color);
        g.fillRect((x*55) +20, 700-20-(countdown/10), 50, (countdown/10));
        g.drawImage(image, (x*55) +20, 700-20-50, null);
    }
    
    public void tick(){
        countdown--;
        if(countdown==1 && ability.equals("wings")){
            wingsActive--;
        }else if(countdown==1 && ability.equals("wider")){
            widerAmount-= 20;
        }
    }
    
    public int getCountdown(){
        return countdown;
    }
    public String getAbility(){
        return ability;
    }
    public boolean isActive(){
        return countdown>0;
    }
    public static boolean wingsIsActive(){
        return wingsActive>0;
    }
    public static boolean widerIsActive(){
        return widerAmount>0;
    }
    public static int getWiderAmount(){
        return widerAmount;
    }

}

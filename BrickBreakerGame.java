import java.io.*;
import javax.imageio.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
public class BrickBreakerGame extends JPanel implements KeyListener, ActionListener{
    private Ball ball;
    private Timer timer;
    private boolean play;
    private ArrayList<Brick> bricks;
    private Paddle paddle;
    private Image menuBg;
    private Image screen5Img;
    private Image screen6Img;
    private int screen;
    private int level;
    private int fSize;//frame size
    private boolean[] keysDown;// L, R, U, D
    
    private ArrayList<PowerUp> powerUps;
    private int endScreenCD;// countdown for end screen
    // -1 = not in use, +10 = in use, 0 = done
    
    private boolean transLevel;// whether or not it is transitioning betwixt levels
    
    public BrickBreakerGame(){
        ball = new Ball(250,500, 20, 8,-3, Color.white);
        bricks = new ArrayList<Brick>();
        powerUps = new ArrayList<PowerUp>();
        fSize = 700;
        screen = 0;
        /* screen 0 = title
         * 1= normal 
         * 2= slowed down ball
         * 3= randomized bricks spawn wherever w/ power ups
         * 4= level by level
         * 5= win screen
         * 6= lose screen
        */
        level =0; 
        keysDown = new boolean[4];
        
        paddle = new Paddle(225, 560, 120, 30, Color.red, 7);
        //paddle = new Paddle(225, 560, 120, 30, Color.red, 10);
        addKeyListener(this);
        
        timer = new Timer(20,this);// every ten millisecond
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer.start();
        play = false;
        endScreenCD = -1;
        
        try{menuBg = ImageIO.read(new File("Images/screen0.jpg"));
        screen5Img = ImageIO.read(new File("Images/screen5.png"));
        screen6Img = ImageIO.read(new File("Images/screen6.png"));}
        catch(IOException e){}
    }
    
    
    public void paint(Graphics g){
        if(screen ==0){
            g.drawImage(menuBg, 0, 0, null);
        }
        else if(screen >=1 && screen <=4){//if not at end or menu screen
            g.setColor(Color.blue);
            g.fillRect(0,0, fSize,fSize);
            g.setColor(new Color(207, 226, 243));
            g.fillRect(5,5, fSize-10,fSize-5);
            if(ball.getCountdown() > 0){//didn't end up using this for anything
                ball.setCountdown(ball.getCountdown()-1);
            }
            ball.move(g);
            ball.collidesWith(paddle);
            //draw brick and check collision-----------------------------
            boolean bricksExist = false;
            for(int i = 0; i< bricks.size(); i++){//draws powerup icons and deletes expired ones
                if(bricks.get(i).getVisible()){
                    bricks.get(i).draw(g);
                    ball.collidesWith(bricks.get(i));
                    bricksExist = true;
                }else{
                    if(bricks.get(i) instanceof PowerUpBrick){
                        String ab = bricks.get(i).getAbility();
                        powerUps.add(new PowerUp((int)(Math.random()*200 +500), Color.blue, ab));
                    }
                    bricks.remove(i); i--;
                }
            }
            if(!bricksExist){//what to do when there's no more bricks
                if(screen ==4 && level<=3){
                    if(transLevel ==false){
                        level++;
                        transLevel =true;
                    }else if(ball.getY()>=500){
                            transLevel = false;
                            generateBricks();
                    }
                }
                else if ((screen == 4 && level>3) || (screen >=1 && screen <=3)){
                    screen=5;
                    endScreenCD = 100;
                }
                else{
                generateBricks();}
            }
            //draw PowerUp icons---------------------
            for(int i = 0; i< powerUps.size(); i++){//draws powerup icons and deletes expired ones
                if(powerUps.get(i).isActive()){
                    powerUps.get(i).draw(g, i);
                }else{
                    powerUps.remove(i); i--;
                }
            }
            paddle.draw(g);
            //draw score
            g.setColor(new Color(100, 0, 0));
             g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
             g.drawString("score " + Brick.getScore(),15,45);
        }
        if(screen ==4){
             //draw level
             g.setColor(new Color(100, 0, 0));
             g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
             g.drawString("Level " + level,15,90);
        }
        
        
        if((screen==5 || screen ==6) && endScreenCD>0){//does the flashing yellow red text before the end screen
            g.setColor(new Color(100, 0, 0));
            g.fillRect(270,350,170,70);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            if((endScreenCD/10) % 2 ==0){g.setColor(new Color(255,255,255));}
            else{g.setColor(new Color(255,0,0));}
            if(screen ==5){
            g.drawString("You Won!",270,400);}else
            g.drawString("You Lost!",270,400);}
        if(screen ==5 && endScreenCD <1){// win screen
            g.drawImage(screen5Img, 0, 0, null);
        }if(screen ==6 && endScreenCD <1){//lose screen
            g.drawImage(screen6Img, 0, 0, null);
        }
    }
    
    public void actionPerformed(ActionEvent e){
        repaint();
        int origWidth = paddle.getWidth();
        paddle.setWidth(120+ PowerUp.getWiderAmount());
        int dw = paddle.getWidth()-origWidth;// change in width
        paddle.setX(paddle.getX() - dw/2);// offsets x val as required
        if(screen!=0){
            if(keysDown[0]== true ) //Left Right Up Down
                {paddle.moveLeft();}
            else if (keysDown[1]== true)
                {paddle.moveRight();}
            if(keysDown[2]== true)
            {paddle.moveUp();}
            else if (keysDown[3]== true)
            {paddle.moveDown();}
        }
        for(int i = 0; i< powerUps.size(); i++){
            if(powerUps.get(i).isActive()){
                powerUps.get(i).tick();}
        }
        
        //check ball's collision with bottom
        if (ball.getY() + ball.getSize() > fSize-6){
            screen =6;
            endScreenCD = 100;
            ball.setY(500);//reset ball. ball's dx dy is changed in ball class
            while(bricks.size()!= 0){
                bricks.remove(0);// ensures no brick overlap
            }
        }
        if(endScreenCD >=-10){//increments the endscreen countdown
            endScreenCD--;
        }
    }
    
    public void generateBricks(){
        //likelyhood of..
        //{normal, strong, powerup}
        int[] randomizer = {1, 1, 0};
        if(screen ==3){
            randomizer= new int[]{2, 2, 2};
        } else if(screen ==4){//levels
            if(level ==1){
                randomizer= new int[]{1, 0, 0};
            }if(level ==2){
                randomizer= new int[] {1, 1, 0};
            }if(level ==3){
                randomizer= new int[]{0, 2, 1};
            }
        }
         for(int row = 0; row <4; row ++){//row<4
            for(int col = 0; col<6; col++){//col<6
                int grx = 10+(114*col);// "gr" for grid
                int gry = 100+ row*60;
                int grw = 655/6;// 109
                int grh = 50;
                int random = (int)(Math.random()*(randomizer[1]+randomizer[2]+randomizer[0])) +1;
                if(random<=randomizer[0]){
                bricks.add(new Brick( grx ,gry ,grw, grh, Color.blue));}
                else if(random <=randomizer[1]+randomizer[0]){
                bricks.add(new StrongBrick(grx , gry ,grw, grh, Color.blue,2));}
                else if(random <=randomizer[1]+randomizer[2]+randomizer[0]){
                bricks.add(new PowerUpBrick(grx , gry ,grw, grh, Color.blue));}//powerup is randomly chosen in its constructor
            }
        }
    }
    
    public void resetGame(int sc, int dy, int dx){
        screen = sc;
        ball.setDy(dy);//sets dy dx
        ball.setDx(dx);
        ball.setY(500);
        paddle.setY(560);//resets paddle's y if it was tampered w/ w/ powerup
        generateBricks();
        //purges powerups
        while(powerUps.size()>0){
            powerUps.remove(0);
        }
    }

    public void keyPressed(KeyEvent e){
        if(screen ==0){
            Brick.setScore(0);
            if(e.getKeyCode() == KeyEvent.VK_1){
                resetGame(1, -3, 8);
            }
            else if (e.getKeyCode() == KeyEvent.VK_2){
                resetGame(2, -3, 2);// slower dx than the rest
            }
            else if(e.getKeyCode() == KeyEvent.VK_3){
                resetGame(3, -3, 8);
            }
            else if (e.getKeyCode() == KeyEvent.VK_4){
                level =1;//sets the level, needs to happen before generating bricks
                resetGame(4, -3, 8);
            }
        }else if(screen >=1 && screen <= 4){
            //the keysDown array tracks which keys are currently pressed to allow for smoother paddle movement
            if(e.getKeyCode() == KeyEvent.VK_LEFT){keysDown[0] = true;}
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT){keysDown[1] = true;}
            if(PowerUp.wingsIsActive()){
            if(e.getKeyCode() == KeyEvent.VK_UP){keysDown[2] = true;}
            else if (e.getKeyCode() == KeyEvent.VK_DOWN){keysDown[3] = true;}}
        }
    }
    public void keyTyped(KeyEvent e){
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_LEFT){ keysDown[0] = false;}
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT){keysDown[1] = false;}
        if(e.getKeyCode() == KeyEvent.VK_UP){keysDown[2] = false;}
        else if (e.getKeyCode() == KeyEvent.VK_DOWN){keysDown[3] = false;}
        if((screen==5 || screen ==6) && endScreenCD < -10){
            screen =0;
        }// putting it here means that someone holding a key still sees the lose screen
    }
}

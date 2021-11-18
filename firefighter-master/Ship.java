package firefighter.Game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public class Ship extends Sprite {
   
    private final Image image;
  

    public Ship(int x, int y, int speed) {
        super(x, y, speed);
        this.image = new ImageIcon(getClass().getResource("/firefighter/Game/asset/image/ship1.png")).getImage();

    }

    @Override
    protected void draw(Graphics2D g2D) {
        g2D.drawImage(this.image, getX(),getY(),null);
    }
    
    
    void shoot() {
        
    }

    void moveleft() {
        if(getX()<0){
            return;
        }
        incSpeed();
        setX(getX()-getSpeed());
    }

    void moveright() {
       if(getX()>GAME_WIDTH-45){
            return;
        }
       incSpeed();
        setX(getX()+getSpeed());
    }
    
    private void incSpeed(){
        if(getSpeed() < AUTO_SPEED){
            setSpeed(getSpeed() + 15);
        }
        
    }

    void resetSpeed() {
        setSpeed(0);
    }
    
    public Rectangle getBound(){
       return new Rectangle(getX(),getY(),this.image.getWidth(null),this.image.getHeight(null));
   }
    
}

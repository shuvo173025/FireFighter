package firefighter.Game;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.util.Random;


public class Enemy extends Sprite {

    private final Image image;

    public Enemy(int x, int y, int speed) {
        super(x, y, speed);
         this.image = new ImageIcon(getClass().getResource("/firefighter/Game/asset/image/enemy.png")).getImage();
         
    }

    @Override
    protected void draw(Graphics2D g2D) {
        
       g2D.drawImage(this.image, getX(), getY(), null); 
    }

   public void update() {
        setX(getX() - getSpeed());
   }
   
   public Rectangle getBound(){
       return new Rectangle(getX(),getY(),this.image.getWidth(null),this.image.getHeight(null));
   }
    
}

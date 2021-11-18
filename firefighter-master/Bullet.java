package firefighter.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public class Bullet extends Sprite {

    private final Image image;

    public Bullet(int x, int y, int speed) {
        super(x, y, speed);
        this.image = new ImageIcon(getClass().getResource("/firefighter/Game/asset/image/bullet.png")).getImage();
    }

    @Override
    protected void draw(Graphics2D g2D) {
       g2D.drawImage(this.image, getX()+17, getY(), null);
    }

   public void update() {
        setY(getY() - getSpeed());
    }
   
   public Rectangle getBound(){
       return new Rectangle(getX(),getY(),this.image.getWidth(null),this.image.getHeight(null));
   }
    
}

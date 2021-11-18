package firefighter.Game;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;


public class BackGround extends Sprite {
    
    private final Image image;

    public BackGround(int x, int y, int speed) {
        super(x, y, speed);
        this.image = new ImageIcon(getClass().getResource("/firefighter/Game/asset/image/back.png")).getImage();
    }

    @Override
    protected void draw(Graphics2D g2D) {
        g2D.drawImage(this.image, getX(), getY(),null);
    }
    
}

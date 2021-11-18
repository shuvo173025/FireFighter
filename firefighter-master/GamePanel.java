package firefighter.Game;
import firefighter.Music;
import firefighter.musics;
import firefighter.startmenu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.layout.Background;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;


public class GamePanel extends Canvas implements Runnable{
    private Thread gameThread;
    private final Sprite background = new BackGround(0, 0, 0);
    private final Ship ship = new Ship((GAME_WIDTH/2)-20,GAME_HEIGHT-55 , 0);
    private boolean isRunning;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ArrayList<Enemy> enemys = new ArrayList<>();
    private final ArrayList<Bomb> bombs = new ArrayList<>();
    private final int enemyPosition[];
    private int currentPosition;
    private int life = 5;
    private int score;
    public static long nextSecond = System.currentTimeMillis() + 1000;
    public static int frameInLastSecond = 0;
    public static int frameInCurrentSecond = 0;
    public JFrame gameframe;

    musics music= new musics();
    
    public void musicplay()
    {
        try{
         File musicpath = new File("C:\\Users\\Shuvo Patowari\\Documents\\NetBeansProjects\\FireFighter\\src\\firefighter\\background.wav");
         AudioInputStream audioInput= AudioSystem.getAudioInputStream(musicpath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e)
        {
            
        }
    }
    
    
    
    public GamePanel(JFrame gameFrames){
        this.enemyPosition = new int[]{100, 180, 240, 324};
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        gameframe= gameFrames;
        musicplay();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if(gameThread == null){
            gameThread = new Thread(GamePanel.this);
        }
        gameThread.start();
    }
    
    @Override
     protected void onKeyUp(KeyEvent event){
         ship.resetSpeed();
     }
     
     @Override
     protected  void onKeyPressed(KeyEvent event){
         if(event.getKeyCode()==KeyEvent.VK_SPACE){
            if(System.currentTimeMillis()%3==0){
             bullets.add(new Bullet(ship.getX(),ship.getY(), getBulletSpeed()));
             ship.shoot();
            // music.bgmusic(true);
            }
         }
         if(event.getKeyCode() == KeyEvent.VK_F1 ){
              if(System.currentTimeMillis()%3==0){
             bullets.add(new Bullet(ship.getX()-15,ship.getY(), getBulletSpeed()));
             bullets.add(new Bullet(ship.getX(),ship.getY(), getBulletSpeed()));
             bullets.add(new Bullet(ship.getX()+15,ship.getY(), getBulletSpeed()));
             ship.shoot();
              }
         }
         if(event.getKeyCode()==KeyEvent.VK_LEFT){
             ship.moveleft();
         }
         if(event.getKeyCode()==KeyEvent.VK_RIGHT){
             ship.moveright();
         }
       
     }
     
     @Override
     protected  void onDraw(Graphics2D g2D){
         background.draw(g2D);
         ship.draw(g2D);
         
         for(Enemy enemy: enemys){
             enemy.draw(g2D);
         }
         
             for(Bullet bullet: bullets){
                 bullet.draw(g2D);
             }
             
              for(Bomb bomb: bombs){
                 bomb.draw(g2D);
             }
              g2D.setColor(Color.white);
              g2D.drawString("LIFE :"+life, GAME_WIDTH - 100, 40);
              g2D.drawString("SCORE :"+score, GAME_WIDTH - 100, 20);
              g2D.setColor(Color.BLACK);
              g2D.drawString("FPS :"+frameInLastSecond,10,30);
     }

    @Override
    public void run() {
        init();
        while(isRunning){
            long startTime = System.currentTimeMillis();
            updateGame();
            renderGame();
            long endTime = (System.currentTimeMillis()-startTime);
             long waitTime = ((MILISECONDS/FPS)- endTime/MILISECONDS);
             try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
            }  
        }
    }

    private void init() {
        isRunning = true;
    }

    private void updateGame() {
        
        //enemy
        if(enemys.size() < 5){
            enemys.add(new Enemy(GAME_WIDTH, getEnemyYPosition(), getRandomSpeed()));
            currentPosition++;
        }
        
        for(int i=0; i < enemys.size(); i++){
            Enemy enemy = enemys.get(i);
            enemy.update();
             for(int j=0; j <bullets.size();j++){
            Bullet bullet = bullets.get(j);
            if(enemy.getBound().intersects(bullet.getBound())){
                enemys.remove(enemy);
                bullets.remove(bullet);
                score++;
            }
        }
            if(enemy.getX() == new Random().nextInt(500)||enemy.getX() == new Random().nextInt(250)){
                bombs.add(new Bomb(enemy.getX()+40,enemy.getY()+20, getBombSpeed()));
            }
            if(enemy.getX() < -90){
                enemys.remove(enemy);
            }
        }
        
        for(int i=0; i <bullets.size();i++){
            Bullet bullet = bullets.get(i);
            bullet.update();
            if(bullet.getY() < 0){
                bullets.remove(bullet);
            }
        }
        
         for(int i=0; i <bombs.size();i++){
            Bomb bomb = bombs.get(i);
            bomb.update();
            if(ship.getBound().intersects(bomb.getBound())){
                life--;
                if(life > 0){
                    bombs.remove(bomb);
                    //life = 5;
                }
                else if(life == 0){
                    isRunning =false;
                    gameframe.dispose();
                    //musicplay();
                   // music.bgmusic(false);
                    startmenu r= new startmenu(score,"score");
                    r.setSize(450,650);
                    r.setVisible(true);
                    r.setLocationRelativeTo(null);
                    r.setResizable(false);
                    r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
                    
                }       
            }
            if(bomb.getY() < 0){
                bombs.remove(bombs);
            }
        }
         
         long currentTime = System.currentTimeMillis();
         if(currentTime > nextSecond){
             nextSecond += 1000;
             frameInLastSecond = frameInCurrentSecond;
             frameInCurrentSecond = 0;
         }
         frameInCurrentSecond++;
        
    }

    private void renderGame() {
        repaint();
    }

    private int getBulletSpeed() {
        return 10;
    }

    private int getEnemyYPosition() {
        if(currentPosition >= enemyPosition.length){
            currentPosition = 0;
        }
        return enemyPosition[currentPosition];
    }

    private int getRandomSpeed() {
        return new Random().nextInt(5) + 1;
    }

    private int getBombSpeed() {
        return 7;
    }
    
    
    
 

     
}

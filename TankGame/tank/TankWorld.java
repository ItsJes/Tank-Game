package tank;

import java.awt.*;
import java.awt.image.*;
import java.net.URL;
import java.util.*;
import bullet.TankBullet;
import game.*;
import ui.*;
import modifiers.*;
import motion.MotionController;

public class TankWorld
  extends GameWorld
{ 
   private static final long serialVersionUID = 1L;
   private Thread thread;
   private static final TankWorld game = new TankWorld();
   public static final GameClock clock = new GameClock();
   GameMenu menu;
   public TankLevel level;
   public static HashMap<String, Image> sprites = GameWorld.sprites;
   private BufferedImage bimg;
   private BufferedImage player1view;
   private BufferedImage player2view;
   int score = 0;
   int life = 4;
   Random generator = new Random();
   int sizeX;
   int sizeY;
   Point mapSize;
   private ArrayList<Bullet> bullets;
   private ArrayList<Tank> players;
   private ArrayList<InterfaceObject> ui;
   boolean gameOver;
   boolean gameWon;
   boolean gameFinished;
   ImageObserver observer; 
    
   private TankWorld()
   { 
      setFocusable(true);
      this.background = new ArrayList();
      this.players = new ArrayList();
      this.ui = new ArrayList();
      this.bullets = new ArrayList();
   }
   
   public static TankWorld getInstance()
   {
      return game;
   } 
   
   public void init()
   {
      setBackground(Color.white);
      loadSprites();
      Point speed = GameWorld.getSpeed();
      this.gameOver = false;
      this.observer = this;
      this.level = new TankLevel("/Resources/level.txt");
      this.level.addObserver(this);
      this.mapSize = new Point(this.level.w * 32, this.level.h * 32);
      GameWorld.setSpeed(new Point(0, 0));
      addBackground(new BackgroundObject[] { new Background(this.mapSize.x, this.mapSize.y, speed, 
            (Image)sprites.get("background")) });
      this.level.load();
      clock.addObserver(this.level);
   }
    
   protected void loadSprites()
   {
      sprites.put("background", getSprite("/Resources/Background.jpg"));
      sprites.put("wall1", getSprite("/Resources/Wall1.png"));
      sprites.put("wall2", getSprite("/Resources/Wall2.png"));
      sprites.put("bullet", getSprite("/Resources/bullet.png"));
      sprites.put("Explosion_small", getSprite("/Resources/Explosion_small.png"));
      sprites.put("Explosion_large", getSprite("/Resources/Explosion_large.png"));
      sprites.put("player1", getSprite("/Resources/Tank1.png"));
      sprites.put("player2", getSprite("/Resources/Tank2.png"));
   }
    
   public Image getSprite(String name)
   {
      URL url = TankWorld.class.getResource(name);
      Image img = Toolkit.getDefaultToolkit().getImage(url);
      try
      {
         MediaTracker tracker = new MediaTracker(this);
         tracker.addImage(img, 0);
         tracker.waitForID(0);
      }
      catch (Exception e) {}
      return img;
   }
   
   public int getFrameNumber()
   {
      return clock.getFrame();
   }
   
   public int getTime()
   {
      return clock.getTime();
   }
   
   public void removeClockObserver(Observer theObject)
   {
      clock.deleteObserver(theObject);
   }
   
   public ListIterator<BackgroundObject> getBackgroundObjects()
   {
      return this.background.listIterator();
   }
   
   public ListIterator<Tank> getPlayers()
   {
      return this.players.listIterator();
   }
   
   public ListIterator<Bullet> getBullets()
   {
      return this.bullets.listIterator();
   }

   public void setDimensions(int w, int h)
   {
      this.sizeX = w;
      this.sizeY = h;
   }
   
   public void addBullet(Bullet... newObjects)
   {
      Bullet[] arrayOfBullet;
      int j = (arrayOfBullet = newObjects).length;
      for (int i = 0; i < j; i++) 
      {
         Bullet bullet = arrayOfBullet[i];
         this.bullets.add(bullet);
      }
   }
     
   public void addPlayer(Tank... newObjects)
   {
      Tank[] arrayOfPlayerShip;
      int j = (arrayOfPlayerShip = newObjects).length;
      for (int i = 0; i < j; i++)
      {
        Tank player = arrayOfPlayerShip[i];;
        this.ui.add(new InfoBar(player, Integer.toString(this.players.size())));
        this.players.add(player);
      }
   }
    
   public void addBackground(BackgroundObject... newObjects)
   {
      BackgroundObject[] arrayOfBackgroundObject;
      int j = (arrayOfBackgroundObject = newObjects).length;
      for (int i = 0; i < j; i++)
      {
        BackgroundObject object = arrayOfBackgroundObject[i];
        this.background.add(object);
      }
   }
   public void addClockObserver(Observer theObject)
   {
      clock.addObserver(theObject);
   }
   
   public void drawFrame(int w, int h, Graphics2D g2)
   {
      PlayerShip p1 = (PlayerShip)this.players.get(0);
      PlayerShip p2 = (PlayerShip)this.players.get(1);
      Rectangle p1Loc = p1.getLocation();
      Rectangle p2Loc = p2.getLocation();
      ListIterator<?> iterator = getBackgroundObjects();
      while (iterator.hasNext())
      { 
         BackgroundObject obj = (BackgroundObject)iterator.next();
         obj.update(w, h);
         obj.draw(g2, this);
         if (((obj instanceof BigExplosion)) || ((obj instanceof SmallExplosion)) || (!obj.show)) 
          iterator.remove();
         
     // if (this.menu.isWaiting())
       // this.menu.draw(g2, w, h);
        
      else
      {
         ListIterator<Tank> players = getPlayers();
         
         while ((players.hasNext()) && (obj.show))
         {
            PlayerShip player = players.next();
            if (obj.collision(player))
            {
               Rectangle playerLocation = player.getLocation();
               Rectangle location = obj.getLocation();
               
               if (playerLocation.x < location.x) 
                  player.move(-2, 0);
              
               if (playerLocation.y < location.y) 
                  player.move(0, -2);
              
               if (playerLocation.x > location.x) 
                  player.move(2, 0);
              
               if (playerLocation.y > location.y) 
                  player.move(0, 2);
            }
         }
      }
   }   
      if (!this.gameFinished) 
      {
         ListIterator<Bullet> bullets = getBullets();
         while (bullets.hasNext())
         {
            TankBullet bullet = (TankBullet)bullets.next();
            if ((bullet.getX() > w) || (bullet.getY() > h))
               bullets.remove();
            
            else
            {
               iterator = getBackgroundObjects();
               
               while (iterator.hasNext())
               {
                  GameObject other = (GameObject)iterator.next();
                  
                  if ((other.show) && (other.collision(bullet)))
                  {
                     bullets.remove();
                     addBackground(new BackgroundObject[] { new SmallExplosion(bullet.getLocationPoint())});
                     break;
                  }
               }
            }
            bullet.draw(g2, this); 
         }
         
         iterator = getPlayers();
         
         while (iterator.hasNext())
         {
            PlayerShip player = (PlayerShip)iterator.next();
            if (player.isDead())
               this.gameOver = true;
            else
            {
               bullets = getBullets();
               
               while (bullets.hasNext())
               {
                  TankBullet bullet = (TankBullet)bullets.next();
                  
                  if ((bullet.getOwner() != player) && (bullet.collision(player)) && (player.respawnCounter <= 0))
                  {
                     player.damage(bullet.getStrength());
                     bullet.getOwner().incrementScore(bullet.getStrength());
                
                     addBackground(new BackgroundObject[] { new SmallExplosion(bullet.getLocationPoint()) });
                     bullets.remove();
                  }
               }
            }
         }

         if (p1.collision(p2))
         {  
            if (p1Loc.x < p2Loc.x) 
               p1.move(-1, 0);
            
            if (p1Loc.y < p2Loc.y) 
               p1.move(0, -1);
            
            if (p1Loc.x > p2Loc.x) 
               p1.move(1, 0);
            
            if (p1Loc.y > p2Loc.y) 
               p1.move(0, 1);
          
         }
         if (p2.collision(p1))
         {
            if (p2Loc.x < p1Loc.x) 
               p2.move(-1, 0);
            
            if (p2Loc.y < p1Loc.y) 
               p2.move(0, -1);
            
            if (p2Loc.x > p1Loc.x) 
               p2.move(1, 0);
            
            if (p2Loc.y > p1Loc.y) 
            p2.move(0, 1);
          
         }
         p1.draw(g2, this);
         p2.draw(g2, this);
        
         p1.update(w, h);
         p2.update(w, h);
      }
      else
      {
         g2.setColor(Color.WHITE);
         g2.setFont(new Font("Calibri", 0, 24));
         
         if (p1Loc.y < 100) 
            p1Loc.y = 200;
         
         g2.drawString("Game Over!", p1Loc.x, p1Loc.y);
         g2.drawString("Score", p1Loc.x - 100, p1Loc.y - 100);
         int i = 1;
         for (PlayerShip player : this.players)
         {
            g2.drawString(player.getName() + ": " + 
              Integer.toString(player.getScore()), p1Loc.x - 100, p1Loc.y - 100 + i * 25);
            i++;
         }
      }
   }
   public Graphics2D createGraphics2D(int w, int h)
   {
      Graphics2D g2 = null;
      if ((this.bimg == null) || (this.bimg.getWidth() != w) || (this.bimg.getHeight() != h)) 
         this.bimg = ((BufferedImage)createImage(w, h));
     
     g2 = this.bimg.createGraphics();
     g2.setBackground(getBackground());
     g2.setRenderingHint(RenderingHints.KEY_RENDERING, 
           RenderingHints.VALUE_RENDER_QUALITY);
     
     g2.clearRect(0, 0, w, h);
     return g2;
   }
    
   public void paint(Graphics g)
   {
     if (this.players.size() != 0) 
       clock.tick();
     
     Dimension windowSize = getSize();
     Graphics2D g2 = createGraphics2D(this.mapSize.x, this.mapSize.y);
     drawFrame(this.mapSize.x, this.mapSize.y, g2);
     g2.dispose();
      
     int p1x = ((PlayerShip)this.players.get(0)).getX() - windowSize.width / 4 > 0 ? ((PlayerShip)this.players.get(0)).getX() - windowSize.width / 4 : 0;
     int p1y = ((PlayerShip)this.players.get(0)).getY() - windowSize.height / 2 > 0 ? ((PlayerShip)this.players.get(0)).getY() - windowSize.height / 2 : 0;
     if (p1x > this.mapSize.x - windowSize.width / 2) {
       p1x = this.mapSize.x - windowSize.width / 2;
     }
     if (p1y > this.mapSize.y - windowSize.height) {
       p1y = this.mapSize.y - windowSize.height;
     } 
     int p2x = ((PlayerShip)this.players.get(1)).getX() - windowSize.width / 4 > 0 ? ((PlayerShip)this.players.get(1)).getX() - windowSize.width / 4 : 0;
     int p2y = ((PlayerShip)this.players.get(1)).getY() - windowSize.height / 2 > 0 ? ((PlayerShip)this.players.get(1)).getY() - windowSize.height / 2 : 0;
     if (p2x > this.mapSize.x - windowSize.width / 2) {
       p2x = this.mapSize.x - windowSize.width / 2;
     }
     if (p2y > this.mapSize.y - windowSize.height) {
       p2y = this.mapSize.y - windowSize.height;
     }
     this.player1view = this.bimg.getSubimage(p1x, p1y, windowSize.width / 2, windowSize.height);
     this.player2view = this.bimg.getSubimage(p2x, p2y, windowSize.width / 2, windowSize.height);
     g.drawImage(this.player1view, 0, 0, this);
     g.drawImage(this.player2view, windowSize.width / 2, 0, this);
     g.drawImage(this.bimg, windowSize.width / 2 - 75, 800, 150, 150, this.observer);
     g.drawRect(windowSize.width / 2 - 1, 0, 1, windowSize.height);
     g.drawRect(windowSize.width / 2 - 76, 799, 151, 151);
     
     ListIterator<InterfaceObject> objects = this.ui.listIterator();
     int offset = 0;
     while (objects.hasNext())
     {
       InterfaceObject object = (InterfaceObject)objects.next();
       object.draw(g, offset, windowSize.height);
       offset += 975;
     }
   }
   
   public void start()
   {
     this.thread = new Thread(this);
     this.thread.setPriority(1);
     this.thread.start();
   }
   
   public void run()
   {
     Thread me = Thread.currentThread();
     while (this.thread == me)
     {
       requestFocusInWindow();
       repaint();
       try
       {
         Thread.sleep(23L);
       }
       catch (InterruptedException e)
       {
         break;
       }
     }
   }
   
   public void endGame(boolean win)
   {
     this.gameOver = true;
     this.gameWon = win;
   }
   
   public boolean isGameOver()
   {
     return this.gameOver;
   }
   
   public void finishGame()
   {
     this.gameFinished = true;
   }
   
   public void update(Observable o, Object arg)
   {
     AbstractGameModifier modifier = (AbstractGameModifier)o;
     modifier.read(this);
   }
}

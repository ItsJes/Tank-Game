package tank;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;
import javax.swing.JPanel;

import game.*;


public abstract class GameWorld
  extends JPanel
  implements Runnable, Observer
{
   private static final long serialVersionUID = 1L;
   public static HashMap<String, Image> sprites = new HashMap();
   protected static Point speed = new Point(0, 0);
   public static final GameClock clock = new GameClock();
   protected BufferedImage bimg;
   protected ArrayList<BackgroundObject> background;
   
   public abstract void addBullet(Bullet... paramVarArgs);
   
   public abstract void addClockObserver(Observer paramObserver);
   
   protected abstract void loadSprites();
   
   public static void setSpeed(Point speed)
   {
     TankWorld.speed = speed;
   }
   
   public static Point getSpeed()
   {
     return new Point(speed);
   }
   
   public Graphics2D createGraphics2D(int w, int h)
   {
     Graphics2D g2 = null;
     if ((this.bimg == null) || (this.bimg.getWidth() != w) || (this.bimg.getHeight() != h)) {
       this.bimg = ((BufferedImage)createImage(w, h));
     }
     g2 = this.bimg.createGraphics();
     g2.setBackground(getBackground());
     g2.setRenderingHint(RenderingHints.KEY_RENDERING, 
           RenderingHints.VALUE_RENDER_QUALITY);
     
     g2.clearRect(0, 0, w, h);
     return g2;
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
}

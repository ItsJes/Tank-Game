package bullet;

import java.awt.*;

import java.awt.image.ImageObserver;
import game.Bullet;
import motion.TankMotion;
import tank.Tank;
import tank.TankWorld;

public class TankBullet
  extends Bullet 
{
   public TankBullet(Point location, Point speed, int strength, Tank owner)
   {
      this(location, speed, strength, 0, owner);
      
   }
     
   public TankBullet(Point location, Point speed, int strength, int offset, Tank owner)
   {
      super(location, speed, strength, new TankMotion(owner.direction + offset), owner);
      setImage((Image)TankWorld.sprites.get("bullet"));
   }
   
   public void draw(Graphics g, ImageObserver obs) 
   {
      if (this.show) 
         g.drawImage(this.img, this.location.x, this.location.y, null);
   }
}

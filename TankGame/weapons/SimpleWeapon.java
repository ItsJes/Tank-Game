package weapons;

import java.awt.*;

import bullet.TankBullet;
import game.Bullet;
import game.Ship;
import motion.SimpleMotion;

public class SimpleWeapon
  extends AbstractWeapon
{
   int strength;
   
   public SimpleWeapon()
   {
     this(5, 10);
   }
   
   public SimpleWeapon(int reload)
   {
     this(5, reload);
   }
   
   public SimpleWeapon(int strength, int reload)
   {
     this.reload = reload;
     this.strength = strength;
   }
  
   public void fireWeapon(Ship theShip)
   { 
     super.fireWeapon(theShip);
     Point location = theShip.getLocationPoint();
     Point offset = theShip.getGunLocation();
     location.x += offset.x;
     location.y += offset.y;
     Point speed = new Point(0, -15 * this.direction);
     
     Bullet bullet = new Bullet(location, speed, this.strength, new SimpleMotion(), theShip);
     this.bullets = (TankBullet[]) new Bullet[1];
     this.bullets[0] = (TankBullet) bullet;
     
     setChanged();
     
     notifyObservers();
   }
}

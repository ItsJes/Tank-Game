package bullet;

import java.awt.Point;

import game.Bullet;
import game.Ship;
import tank.Tank;
import tank.TankWorld;
import weapons.AbstractWeapon;

public class TankWeapon
  extends AbstractWeapon
{
   public TankWeapon() 
   {
     super(TankWorld.getInstance());
   }
    
   public void fireWeapon(Ship theShip)
   {
      super.fireWeapon(theShip);
      Point location = theShip.getLocationPoint();
      Point offset = theShip.getGunLocation();
      location.x += offset.x;
      location.y += offset.y;
      Point speed = new Point(0, -15 * this.direction);
      int strength = 10;
      this.reload = 15;
       
      TankBullet bullet = new TankBullet(location, speed, strength, (Tank) theShip);
       
      this.bullets = new Bullet[1];
      this.bullets[0] = bullet;
       
      setChanged();
      notifyObservers();
   }
}

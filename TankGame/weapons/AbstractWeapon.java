package weapons;

import java.util.Observer;
import game.*;
import modifiers.AbstractGameModifier;
import tank.GameWorld;
import tank.TankWorld;

public abstract class AbstractWeapon
  extends AbstractGameModifier
{
  protected Bullet[] bullets;
  boolean friendly;
  int lastFired = 0;
  int reloadTime;
  protected int direction;
  public int reload = 5;
   
  public AbstractWeapon()
  {
     this(TankWorld.getInstance());
   }
   
   public AbstractWeapon(Observer world)
   {
     addObserver(world);
   }
  
  public void fireWeapon(Ship theShip)
  {
    if ((theShip instanceof PlayerShip)) {
      this.direction = 1;
    } else {
      this.direction = -1;
    }
  }
  
  public void read(Object theObject)
  {
    GameWorld world = (GameWorld)theObject;
    world.addBullet(this.bullets);
  }
  
  public void remove()
  {
    deleteObserver(TankWorld.getInstance());
  }
}

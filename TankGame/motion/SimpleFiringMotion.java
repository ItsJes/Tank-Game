package motion;

import game.Ship;
import tank.TankWorld;

public class SimpleFiringMotion
  extends SimpleMotion
{
   public SimpleFiringMotion(int interval)
   {
     this.fireInterval = interval;
   }
   
   public void read(Object theObject)
   {
     super.read(theObject);
     
     Ship ship = (Ship)theObject;
     if (TankWorld.getInstance().getFrameNumber() % this.fireInterval == 0)
       ship.fire(); 
   }
} 
  
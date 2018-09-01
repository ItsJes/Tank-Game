package motion;

import game.MoveableObject;
import tank.TankWorld;

public class TankMotion
  extends MotionController
{
   int dy;
   int dx;
   
   public TankMotion(int direction)
   {
      super(TankWorld.getInstance());
      this.dy = ((int)(10.0D * Math.cos(Math.toRadians(direction + 90))));
      this.dx = ((int)(10.0D * Math.sin(Math.toRadians(direction + 90))));
   }
  
   public void read(Object theObject)
   {
      MoveableObject object = (MoveableObject)theObject;
      object.move(this.dx, this.dy);
   }
}  

package motion;

import game.MoveableObject;

public class SimpleMotion
  extends MotionController
{
   public void read(Object theObject)
   {
     MoveableObject object = (MoveableObject)theObject;
     object.move(); 
   }
}
  
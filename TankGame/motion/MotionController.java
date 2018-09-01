package motion;

import java.util.*;

import game.Ship;
import modifiers.AbstractGameModifier;
import tank.GameWorld;
import tank.TankWorld;

public class MotionController 
  extends AbstractGameModifier
  implements Observer
{
   int fireInterval;
     
   public MotionController()
   {
      TankWorld.getInstance().addClockObserver(this);
      this.fireInterval = -1;
   } 
   
   public MotionController(GameWorld tankWorld)
   {
      tankWorld.addClockObserver(this);
   } 
  
   public void delete(Observer theObject)
   {
      TankWorld.getInstance().removeClockObserver(this);
      deleteObserver(theObject);
   }
   
   public void update(Observable o, Object arg)
   {
      setChanged();
      notifyObservers();
   }
   
   public void read(Object theObject)
   {
      Ship object = (Ship)theObject;
      object.move();
      if(TankWorld.getInstance().getFrameNumber() % this.fireInterval == 0)
         object.fire();
      
   }
}

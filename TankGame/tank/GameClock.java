package tank;

import java.util.*;

public class GameClock
  extends Observable
{
   private int startTime;
   private int frame;
   
   public GameClock()
   {
     this.startTime = ((int)System.currentTimeMillis());
     this.frame = 0;
   }
   
   public void tick()
   {
     this.frame += 1;
     setChanged();
     notifyObservers();
   } 
   
   public int getFrame()
   {
     return this.frame;
   }
   
   public int getTime()
   {
     return (int)System.currentTimeMillis() - this.startTime;
   }
}

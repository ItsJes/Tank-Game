package motion;

import java.awt.Component;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;

import game.PlayerShip;
import tank.TankWorld;

public class InputController
  extends MotionController
  implements KeyListener
{
   Field field;
   Method action;
   int moveState;
   int[] keys;
   boolean player;
    
   public InputController(PlayerShip player)
   {
      this(player, new int[] { 37, 38, 39, 40, 32 });
      this.moveState = 0;
      this.player = true;
   } 
  
   public InputController(PlayerShip player, int[] keys)
   {
      this(player, keys, TankWorld.getInstance());
   }
   
   public InputController(PlayerShip player, int[] keys, Component world)
   {
      this.addObserver(player);
      this.action = null;
      this.field = null;
      this.keys = keys;
   
      world.addKeyListener(this);
   }
   
   public void signalKeyPress(KeyEvent e) {}
   
   private void setMove(String direction)
   {
      try
      {
         this.field = PlayerShip.class.getDeclaredField(direction);
         this.moveState = 1;
         setChanged();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      notifyObservers();
   }
    
   private void setFire()
   {
     this.field = null;
     try
     {
       this.action = PlayerShip.class.getMethod("startFiring", new Class[0]);
       setChanged();
     }
     catch (NoSuchMethodException e)
     {
       e.printStackTrace();
     }
     notifyObservers();
   }
   
   private void unsetMove(String direction)
   {
      try
      {
         this.field = PlayerShip.class.getDeclaredField(direction);
         this.moveState = 0;
         setChanged();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      notifyObservers();
   }
   
   private void unsetFire()
   {
      this.field = null;
      try
      {
         this.action = PlayerShip.class.getMethod("stopFiring", new Class[0]);
         setChanged();
      }
      catch (NoSuchMethodException e)
      {
         e.printStackTrace();
      }
      notifyObservers();
   }
   
   public void read(Object theObject)
   {
      PlayerShip player = (PlayerShip)theObject;
      try
      {
         this.field.setInt(player, this.moveState);
      }
      catch (Exception e)
      {
         try
         {
            this.action.invoke(player, new Object[0]);
         }
         catch (Exception localException1) {}
      }
   }
   
   public void clearChanged()
   {
      super.clearChanged();
   }
   
   public void update(Observable o, Object arg) {}
    
   public void keyPressed(KeyEvent e)
   {
      int code = e.getKeyCode();
      
      if (code == this.keys[0]) 
         setMove("left");
      
      else if (code == this.keys[1]) 
         setMove("up");
     
      else if (code == this.keys[2]) 
         setMove("right");
      
      else if (code == this.keys[3]) 
         setMove("down");
     
      else if (code == this.keys[4]) 
         setFire();
     
     setChanged();
     notifyObservers();
   }
   
   public void keyReleased(KeyEvent e)
   {
      int code = e.getKeyCode();
      
      if (code == this.keys[0]) 
         unsetMove("left");
      
      else if (code == this.keys[1]) 
         unsetMove("up");
     
      else if (code == this.keys[2]) 
         unsetMove("right");
      
      else if (code == this.keys[3]) 
         unsetMove("down");
     
      else if (code == this.keys[4]) 
         unsetFire();
     
     setChanged();
     notifyObservers();
   }
   
   public void keyTyped(KeyEvent e) {}
}

package motion;

import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import game.PlayerShip;
import tank.TankWorld;
import ui.GameMenu;

public class MenuController
  extends MotionController
  implements KeyListener
{
   KeyListener listener;
   Field field;
   Method action;
   int moveState;
   int[] keys;
   boolean player;
   
   public MenuController(GameMenu menu)
   {
      this(menu, new int[] { 37, 38, 39, 40, 32 });
      this.player = false;
   }
   
   public MenuController(PlayerShip player)
   {
      this(player, new int[] { 37, 38, 39, 40, 32 });
      this.moveState = 0;
      this.player = true;
   }
   
   public MenuController(Observer theObject, int[] keys)
   {
      addObserver(theObject);
      this.action = null;
      this.field = null;
      this.keys = keys;
      TankWorld world = TankWorld.getInstance();
      world.addKeyListener(this);
   }
   
   public void signalKeyPress(KeyEvent e) {}
   
   private void setMove(String direction)
   {
      try
      {
         this.action = GameMenu.class.getMethod(direction, new Class[0]);
         setChanged();
      }
      catch (Exception localException) {}
   }
   
   private void setFire()
   {
      try
      {
         this.action = GameMenu.class.getMethod("applySelection", new Class[0]);
      }
      catch (NoSuchMethodException|SecurityException localNoSuchMethodException) {}
      notifyObservers();
   }
   
   public void read(Object theObject)
   {
      try
      {
         this.action.invoke(theObject, new Object[0]);
         this.action = null;
      }
      catch (Exception localException) {}
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
      
      else if(code == 10)
         setFire();
     
     setChanged();
     notifyObservers();
   }
   
   public void keyReleased(KeyEvent e) {}
   
   public void keyTyped(KeyEvent e) {}
}

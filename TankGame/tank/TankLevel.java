package tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import modifiers.AbstractGameModifier;
import wall.*;

public class TankLevel
  extends AbstractGameModifier
  implements Observer
{
   int start;
   Integer position;
   String filename;
   BufferedReader level;
   int w; 
   int h;
   int endgameDelay = 30;   
 
    
   public TankLevel(String filename) 
   {
      super(); 
      this.filename = filename;
      String line;
      try 
      {
         this.level = new BufferedReader(new InputStreamReader(TankWorld.class.getResource(filename).openStream()));
         line = this.level.readLine();
         this.w = line.length();
         this.h = 0;
         while(line != null)
         {
            this.h++;
            line = this.level.readLine();
         }
         this.level.close();
      } 
      catch (IOException e) 
      {
         e.printStackTrace();
         System.exit(1);
      }
   }
    public void read(Object theObject){
   }

    public void load()
    {
       TankWorld world = TankWorld.getInstance();
       
       String line;
       try 
       {
          this.level = new BufferedReader(new InputStreamReader(TankWorld.class.getResource(filename).openStream()));
       }
       catch (IOException e) 
       {
          e.printStackTrace();
          System.exit(1);
       }
       try 
       {
          line = this.level.readLine();
          this.w = line.length();
          this.h=0;
          
          while(line != null)
          {
             for(int i = 0, n = line.length() ; i < n ; i++) 
             { 
                char c = line.charAt(i); 
                
                if(c == '1')
                {
                   Wall wall = new Wall(i, h);
                   world.addBackground(wall);
                }
                
                if(c == '2')
                {
                   BreakableWall wall = new BreakableWall(i, h);
                   world.addBackground(wall);
                }
                
                if(c == '3')
                {
                  int[] controls = {KeyEvent.VK_A,KeyEvent.VK_W, 
                        KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_F};
                  world.addPlayer(new Tank(new Point(i * 32, h * 32),
                        TankWorld.sprites.get("player1"), controls, "1"));
                }
                
                if(c == '4')
                {
                   int[] controls = new int[] {KeyEvent.VK_LEFT,KeyEvent.VK_UP, 
                         KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER};
                   world.addPlayer(new Tank(new Point(i * 32, h * 32),
                         TankWorld.sprites.get("player2"), controls, "2"));
                }
                
            }
            this.h++;
            line = this.level.readLine();
         } 
         level.close();
      } 
       catch (IOException e) 
       { 
         e.printStackTrace();
      }
   } 
    
   @Override
   public void update(Observable o, Object arg) 
   {
      TankWorld world = TankWorld.getInstance();
      if(world.isGameOver())
      {
         if(endgameDelay <= 0)
         {
            world.removeClockObserver(this);
            world.finishGame();
         } 
         else 
            endgameDelay--;
      }
   } 
}
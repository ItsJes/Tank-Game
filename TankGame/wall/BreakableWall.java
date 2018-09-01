package wall;

import java.awt.*;
import java.awt.image.ImageObserver;

import bullet.TankBullet;
import game.BackgroundObject;
import game.GameObject;
import tank.TankWorld;

public class BreakableWall
  extends BackgroundObject
{
int timer = 400;
   
   public BreakableWall(int x, int y)
   {
     super(new Point(x * 32, y * 32), new Point(0, 0), (Image)TankWorld.sprites.get("wall2"));
   }
   
   public boolean collision(GameObject otherObject)
   {
     if (this.location.intersects(otherObject.getLocation()))
     {
       if ((otherObject instanceof TankBullet)) 
         this.show = false;
       return true;
     } 
     return false;
   }
   
   public void draw(Graphics g, ImageObserver obs)
   {
     if (this.show == false)
     {
       this.timer--;
       if (this.timer <= 0)
       {
         this.timer = 400;
         this.show = true;
       }
     }
     else
     {
       super.draw(g, obs);
     }
   }
}
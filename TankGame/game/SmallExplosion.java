package game;

import java.awt.*;

import tank.Sound;
import tank.TankWorld;

public class SmallExplosion
  extends BackgroundObject
{ 
   int timer; 
   int frame;
   static Image[] animation = { (Image)TankWorld.sprites.get("Explosion_small") };
   
   public SmallExplosion(Point location) 
   {
      super(location, animation[0]);
      this.timer = 0;
      this.frame=0;
      Sound.player("Resources/Explosion_small.wav", false);
   }
   public void update(int w, int h)
   {
      super.update(w, h);
      this.timer++;
      
      if(timer % 5 ==0)
      {
         this.frame++;
         if(frame < 6)
            this.img = animation[this.frame];
         else
            this.show = false;
      }
   }

   public boolean collision(GameObject otherObject)
   {
      return false;
   }
}

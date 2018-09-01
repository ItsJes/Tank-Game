package game;

import java.awt.*;

import motion.MotionController;
import tank.TankWorld;
import weapons.AbstractWeapon;


public class Bullet 
  extends MoveableObject
{ 
   PlayerShip owner;
   boolean friendly; 
   protected AbstractWeapon weapon;
     
   public Bullet(Point location, Point speed, int strength, MotionController motion, GameObject owner)
   { 
      super(location, speed, (Image)TankWorld.sprites.get("bullet"));
      this.strength = strength;
      
      if ((owner instanceof PlayerShip))
      { 
         this.owner = ((PlayerShip)owner);
         this.friendly = true;
         setImage((Image)TankWorld.sprites.get("bullet"));
      }
      this.motion = motion;
      motion.addObserver(this);
   } 
   
   public PlayerShip getOwner()
   {
     return this.owner;
   }
   
   public boolean isFriendly()
   {
      if (this.friendly) 
         return true;
      return false;
   }
   
   public void die()
   {
      this.show = false;
      SmallExplosion explosion = new SmallExplosion(new Point(this.location.x, this.location.y));
      TankWorld.getInstance().addBackground(new BackgroundObject[] { explosion });
      this.weapon.deleteObserver(this);
      this.motion.deleteObserver(this);
      TankWorld.getInstance().removeClockObserver(this.motion);
   }
   
   public void setWeapon(AbstractWeapon weapon)
   {
      this.weapon.remove();
      this.weapon = weapon;
   }
   
   public AbstractWeapon getWeapon()
   {
      return this.weapon;
   }
}


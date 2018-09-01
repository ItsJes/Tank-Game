package game;

import java.awt.*;

import motion.MotionController;
import tank.TankWorld;
import weapons.AbstractWeapon;
 
public class Ship
  extends MoveableObject
{
   protected AbstractWeapon weapon;
   protected int health;
   protected Point gunLocation;
   
   public Ship(Point location, Point speed, int strength, Image img)
   { 
      super(location, speed, img);
      this.strength = strength; 
      this.health = strength;
      this.gunLocation = new Point(15, 20);
   }
   
   public Ship(int x, Point speed, int strength, Image img)
   {
      this(new Point(x, -90), speed, strength, img);
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
   
   public void damage(int damageDone)
   {
      this.health -= damageDone;
      
      if (this.health <= 0) 
         die();
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
   
   public void collide(GameObject otherObject) {}
   
   public void fire()
   {
      this.weapon.fireWeapon(this);
   }
   
   public void setHealth(int health)
   {
      this.health = health;
   }
   
   public int getHealth()
   {
      return this.health;
   }
   
   public MotionController getMotion()
   {
      return this.motion;
   }
   
   public void setMotion(MotionController motion)
   {
      this.motion = motion;
   }
   
   public Point getGunLocation()
   {
      return this.gunLocation;
   }
}

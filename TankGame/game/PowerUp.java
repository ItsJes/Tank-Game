package game;

import java.awt.*;
import java.util.Observable;

import modifiers.AbstractGameModifier;
import motion.SimpleMotion;
import tank.TankWorld;
import weapons.AbstractWeapon;

public class PowerUp
  extends Ship
{ 
   public PowerUp(Ship theShip)
   {
     super(theShip.getLocationPoint(), theShip.getSpeed(), 1, 
           (Image)TankWorld.sprites.get("powerup"));
     this.motion = new SimpleMotion();
     this.motion.addObserver(this);
     this.weapon = theShip.getWeapon();
   }
   
   public PowerUp(int location, int health, 
         AbstractWeapon weapon)
   {
     this(new Point(location, -100), health, weapon);
     this.motion = new SimpleMotion();
     this.motion.addObserver(this);
     this.weapon = weapon;
   }
   
   public PowerUp(Point location, int health,
         AbstractWeapon weapon)
   {
     super(new Point(location), new Point(0, 2), health, 
           (Image)TankWorld.sprites.get("powerup"));
     this.motion = new SimpleMotion();
     this.motion.addObserver(this);
     this.weapon = weapon;
   }
   
   public void update(Observable o, Object arg)
   {
     AbstractGameModifier modifier = (AbstractGameModifier)o;
     modifier.read(this);
   }
   
   public void die()
   {
     this.show = false;
     this.weapon.deleteObserver(this);
     this.motion.deleteObserver(this);
     TankWorld.getInstance().removeClockObserver(this.motion);
   }
}


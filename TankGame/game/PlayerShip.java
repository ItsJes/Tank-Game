package game;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.*;
import modifiers.AbstractGameModifier;
import motion.InputController;
import tank.Sound;
import tank.TankWorld;
import weapons.SimpleWeapon;
import game.BigExplosion;
 
public class PlayerShip
  extends Ship 
  implements Observer
{ 
   protected int lives; 
   protected int score;
   protected Point resetPoint;
   public int respawnCounter;
   protected int lastFired = 0;
   protected boolean isFiring = false;
   public int left = 0; 
   public int right = 0; 
   public int up = 0;
   public int down = 0;
   protected String name;
   
   public PlayerShip(Point location, Point speed, Image img, int[] controls, String name)
   {
      super(location, speed, 100, img);
      this.resetPoint = new Point(location);
      this.gunLocation = new Point(18, 0);
     
      this.name = name;
      this.weapon = new SimpleWeapon();
      this.motion = new InputController(this, controls);
      this.lives = 2;
      this.health = 100;
      this.strength = 100;
      this.score = 0;
      this.respawnCounter = 0;
   }
   
   public void draw(Graphics g, ImageObserver observer)
   {
      if (this.respawnCounter <= 0)
      {
         g.drawImage(this.img, this.location.x, this.location.y, observer);
      }
      else if (this.respawnCounter == 80)
      {
         TankWorld.getInstance().addClockObserver(this.motion);
         this.respawnCounter--;
      }
      else if (this.respawnCounter < 80)
      {
         if (this.respawnCounter % 2 == 0) 
         {
            g.drawImage(this.img, this.location.x, this.location.y, observer);
         }
         this.respawnCounter--;
      }
      else
      {
         this.respawnCounter--;
      }
   }
   
   public void damage(int damageDone)
   {
      if (this.respawnCounter <= 0) 
         super.damage(damageDone);
   }
   
   public void update(int w, int h)
   {
      if (this.isFiring)
      {
         int frame = TankWorld.getInstance().getFrameNumber();
         if (frame >= this.lastFired + this.weapon.reload)
         {
            fire();
            this.lastFired = frame;
         }
      }
      
      if (((this.location.x > 0) || (this.right == 1)) && ((this.location.x < w - this.width) || (this.left == 1))) 
         this.location.x += (this.right - this.left) * this.speed.x;
      
      if (((this.location.y > 0) || (this.down == 1)) && ((this.location.y < h - this.height) || (this.up == 1))) 
         this.location.y += (this.down - this.up) * this.speed.x;
     
   }
   
   public void startFiring()
   {
      this.isFiring = true;
   }
   
   public void stopFiring()
   {
      this.isFiring = false;
   }
   
   public void fire()
   {
      if (this.respawnCounter <= 0)
      {
         this.weapon.fireWeapon(this);
         Sound.player("/Resources/Explosion_small.wav", true);
      }
   }
   
   public void die()
   {
      this.show = false;
      BigExplosion explosion = new BigExplosion(new Point(this.location.x, this.location.y));
      TankWorld.getInstance().addBackground(new BackgroundObject[] { explosion });
      this.lives--;
       
      if (this.lives >= 0)
      {
         TankWorld.getInstance().removeClockObserver(this.motion);
         reset();
      }
      else
      {
        this.motion.delete(this);
      }
   }
   
   public void reset()
   {
      setLocation(this.resetPoint);
      this.health = this.strength;
      this.respawnCounter = 160;
      this.weapon = new SimpleWeapon();
   }
   
   public int getLives()
   {
      return this.lives;
   }
   
   public int getScore()
   {
      return this.score;
   }
   
   public String getName()
   {
      return this.name;
   }
    
   public void incrementScore(int increment)
   {
      this.score += increment;
   }
   
   public boolean isDead()
   {
      if ((this.lives < 0) && (this.health <= 0)) 
         return true;
      return false;
   }
   
   public void update(Observable o, Object arg)
   {
      AbstractGameModifier modifier = (AbstractGameModifier)o;
      modifier.read(this);
   }
}

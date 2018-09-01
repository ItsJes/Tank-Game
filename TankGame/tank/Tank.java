package tank;

import java.awt.*;
import java.awt.image.ImageObserver;

import bullet.TankWeapon;
import game.BackgroundObject;
import game.BigExplosion;
//import game.BigExplosion;
import game.PlayerShip;
import motion.InputController;

public class Tank
  extends PlayerShip
{

   public int direction; 
   
   public Tank(Point location, Image img, int[] controls, String name)
   {
      super(location, new Point(0, 0), img, controls, name);
      this.resetPoint = new Point(location);
      this.gunLocation = new Point(32, 32);
     
      this.name = name;
      this.weapon = new TankWeapon();
      this.motion = new InputController(this, controls, TankWorld.getInstance());
      this.lives = 3;
      this.health = 100;
      this.strength = 100;
      this.score = 0;
      this.respawnCounter = 0;
      this.height = 64;
      this.width = 64;
      this.direction = 180;
      this.location = new Rectangle(location.x, location.y, this.width, this.height);
   } 
   
   public void turn(int angle)
   {
      this.direction += angle;
      if (this.direction < 0) 
         this.direction = 359;
     
     if (this.direction >= 360) 
        this.direction = 0;
   }
   
   public void update(int w, int h)
   {
      if ((this.up == 1) || (this.down == 1))
      {
         int dx = (int)(5.0D * Math.sin(Math.toRadians(this.direction + 90)));
         int dy = (int)(5.0D * Math.cos(Math.toRadians(this.direction + 90)));
         this.location.x += dx * (this.up - this.down);
        this.location.y += dy * (this.up - this.down);
      }
      if ((this.left == 1) || (this.right == 1)) 
         turn(4 * (this.left - this.right));
     
     if (this.location.x < 0) 
        this.location.x = 0;
     
     if (this.location.y < 0) 
        this.location.y = 0;
     
     if (this.location.x > w - this.width) 
        this.location.x = (w - this.width);
     
     if (this.location.y > h - this.height) 
        this.location.y = (h - this.height);
     
     if (this.isFiring)
     {
        int bulletFrame = TankWorld.getInstance().getFrameNumber();
        if (bulletFrame >= this.lastFired + this.weapon.reload)
        {
           fire();
           this.lastFired = bulletFrame;
        }
     }
   }
      
   public void draw(Graphics g, ImageObserver obs)
   {
      if (this.respawnCounter <= 0)
      {
         g.drawImage(this.img, this.location.x, this.location.y, this.location.x + 
       
         getSizeX(), this.location.y + getSizeY(), this.direction / 6 * 
         getSizeX(), 0, this.direction / 6 * 
         getSizeX() + getSizeX(), getSizeY(), obs);
     }
     else if (this.respawnCounter == 80)
     {
        TankWorld.getInstance().addClockObserver(this.motion);
        this.respawnCounter--;
        
        System.out.println(Integer.toString(this.respawnCounter));
     }
     else if (this.respawnCounter < 80)
     {
        if (this.respawnCounter % 2 == 0) 
        {
           g.drawImage(this.img, this.location.x, this.location.y, this.location.x + 
         
           getSizeX(), this.location.y + getSizeY(), this.direction / 6 * 
           getSizeX(), 0, this.direction / 6 * 
           getSizeX() + getSizeX(), getSizeY(), obs);
       }
       this.respawnCounter--;
     }
     else
     {
       this.respawnCounter--;
     }
   }
   
   public void die()
   {
      this.show = false;
      GameWorld.setSpeed(new Point(0, 0));
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
      this.weapon = new TankWeapon();
   }
}

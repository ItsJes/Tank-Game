package game;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.*;
import modifiers.AbstractGameModifier;

public abstract class GameObject
  implements Observer
{
   protected Point speed;
   protected Rectangle location;
   public Image img;
   protected int height;
   protected int width;
   ImageObserver observer;
   public boolean show;
   
   //public GameObject() {}
   
   public GameObject(Point location, Point speed, Image img)
   {
      this.speed = speed;
      this.img = img;
      this.show = true; 
      this.height = img.getWidth(this.observer);
      this.width = img.getHeight(this.observer);
      this.location = new Rectangle(location.x, location.y, this.width, this.height);
   }
   
   public GameObject(Point location, Image img)
   {
     this(location, new Point(0, 0), img);
   }
   
   public void draw(Graphics g, ImageObserver obs)
   {
      if (this.show) 
         g.drawImage(this.img, this.location.x, this.location.y, obs);
     
   }
   
   public void setImage(Image img)
   {
      this.img = img;
      this.height = img.getWidth(this.observer);
      this.width = img.getHeight(this.observer);
   }
   
   public void update(int w, int h)
   {
      this.location.x += this.speed.x;
      this.location.y += this.speed.y;
      if ((this.location.y < -100) || (this.location.y == h + 100)) 
         this.show = false;
   }
   
   public void update(Observable o, Object arg)
   {
      AbstractGameModifier modifier = (AbstractGameModifier)o;
      modifier.read(this);
   }
   
   public int getX()
   {
      return this.location.x;
   }
   
   public int getY()
   {
      return this.location.y;
   }
   
   public int getSizeX()
   {
      return this.width;
   }
   
   public int getSizeY()
   {
      return this.height;
   }
   
   public Point getSpeed()
   {
      return this.speed;
   }
   
   public void setLocation(Point newLocation)
   {
      this.location.setLocation(newLocation);
   }
   
   public Rectangle getLocation()
   {
      return new Rectangle(this.location);
   }
   
   public Point getLocationPoint()
   {
      return new Point(this.location.x, this.location.y);
   }
   
   public void move(int dx, int dy)
   {
      this.location.translate(dx, dy);
   }
   
   public void move()
   { 
      this.location.translate(this.speed.x, this.speed.y);
   }
   
   public boolean collision(GameObject otherObject)
   { 
      if (!(this.location.intersects(otherObject.getLocation()))) 
         return false;
      return true;
   } 
   
   public void hide()
   {
      this.show = false;
   }
   
   public void show()
   {
      this.show = true;
   }
}

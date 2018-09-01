package game;

import java.awt.*;

import motion.MotionController;
import motion.NullMotion;

public class MoveableObject
  extends GameObject
{ 
   protected int strength;
   protected MotionController motion;
   
   public MoveableObject(Point location, Point speed, Image img)
   {
     super(location, speed, img);
     this.strength = 0;
     this.motion = new NullMotion();
   }
   
   public int getStrength()
   {
     return this.strength;
   } 
   
   public void setStrength(int strength)
   {
     this.strength = strength;
   }
   
   public void update(int w, int h)
   {
     this.motion.read(this);
   }
   
   public void start()
   {
     this.motion.addObserver(this);
   }
}

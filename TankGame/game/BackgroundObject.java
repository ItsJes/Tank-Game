package game;

import java.awt.*;

import tank.GameWorld;

public class BackgroundObject 
  extends GameObject
{
   public BackgroundObject(Point location, Image img)
   {
     super(location, GameWorld.getSpeed(), img);
   }
   
   public BackgroundObject(Point location, Point speed, Image img)
   {
     super(location, speed, img);
   }
}

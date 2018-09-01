package wall;

import java.awt.*;
import game.BackgroundObject;
import tank.TankWorld;

public class Wall
  extends BackgroundObject
{
   public Wall(int x, int y)
   {
     super(new Point(x * 32, y * 32), new Point(0, 0), (Image)TankWorld.sprites.get("wall1"));
   }
   
   public void damage(int damage) {}
}

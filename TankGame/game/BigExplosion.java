package game;

import java.awt.*;

import tank.Sound;
import tank.TankWorld;

public class BigExplosion
  extends BackgroundObject
{
  int timer;
  int frame; 
  
  static Image[] animation = { (Image)TankWorld.sprites.get("Explosion_large")};
  
  public BigExplosion(Point location) 
  {
    super(location, animation[0]);
    this.timer = 0;
    this.frame = 0;
    Sound.player("Resources/Explosion_large.wav", false);
  }
  
  public void update(int w, int h)
  {
    super.update(w, h);
    this.timer ++;
    
    if (this.timer % 5 == 0)
    {
      this.frame ++;
      if (this.frame < 7) 
      {
        this.img = animation[this.frame];
      } 
      else 
      {
        this.show = false;
      }
    }
  }
}

package game;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Background 
  extends BackgroundObject
{
   int move;
   int w;
   int h; 
   
   public Background(int w, int h, Point speed, Image img)
   {
      super(new Point(0, 0), speed, img);
      this.img = img;
      this.w = w;
      this.h = h;
      this.move = 0;
   }
  
   public void update(int w, int h) {}
   
   public void draw(Graphics g, ImageObserver obs)
   {
      int TileWidth = this.img.getWidth(obs);
      int TileHeight = this.img.getHeight(obs);
        
      int NumberX = this.w / TileWidth;
      int NumberY = this.h / TileHeight;
      
      for (int i = -1; i <= NumberY; i++) 
      {
         for (int j = 0; j <= NumberX; j++) 
            g.drawImage(this.img, j * TileWidth, i * TileHeight + this.move % TileHeight, TileWidth, TileHeight, obs);
      }   
      
      this.move += this.speed.y;
  } 
   
   public boolean collision(GameObject otherObject)
   {
      return false;
   }
}

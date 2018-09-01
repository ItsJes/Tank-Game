package ui;

import java.awt.*;
import game.PlayerShip;
import tank.GameWorld;
import tank.TankWorld;

public class InfoBar
  extends InterfaceObject
{
   PlayerShip player;
   String name;
   
   public InfoBar(PlayerShip player, String name)
   {
     this.player = player;
     this.name = name;
   } 
 
   
   public void draw(Graphics g2, int x, int y)
   {
     g2.setFont(new Font("Calibri", 0, 24));
     if (this.player.getHealth() > 40) {
       g2.setColor(Color.BLUE);
     } else if (this.player.getHealth() > 20) {
       g2.setColor(Color.YELLOW);
     } else {
       g2.setColor(Color.RED);
     }
     g2.fillRect(x + 2, y - 25 , (int)Math.round(this.player.getHealth() * 1.1D), 20);
     for (int i = 0; i < this.player.getLives(); i++) 
     {
        if (GameWorld.sprites.get("life" + this.name) != null)
        {
           g2.drawImage((Image)TankWorld.sprites.get("life" + this.name), x + 110 + i * 30, y - 30, this.observer);
        }
        else
        {
           g2.setColor(Color.PINK);
           g2.fillOval(x + 130 + i * 30, y - 25, 15 , 15);
        }
     }
   } 
}

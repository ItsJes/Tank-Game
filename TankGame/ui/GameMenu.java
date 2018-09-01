package ui;

import java.awt.*;
import java.util.*;
import game.PlayerShip;
import modifiers.AbstractGameModifier;
import motion.MenuController;
import tank.Tank;
import tank.TankWorld;

public class GameMenu
  extends InterfaceObject
{
   int selection;
   MenuController controller;
   boolean waiting;
   
   public GameMenu()
   {
     this.selection = 0;
     this.controller = new MenuController(this);
     this.waiting = true;
   }
   
   public void draw(Graphics g2, int x, int y)
   {
     g2.setFont(new Font("Calibri", 0, 24));
     if (this.selection == 0) {
       g2.setColor(Color.RED);
     } else {
       g2.setColor(Color.WHITE);
     }
     g2.drawString("1 Player", 200, 150);
     if (this.selection == 1) {
       g2.setColor(Color.RED);
     } else {
       g2.setColor(Color.WHITE);
     }
     g2.drawString("2 Player", 200, 250);
     if (this.selection == 2) {
       g2.setColor(Color.RED);
     } else {
       g2.setColor(Color.WHITE);
     }
     g2.drawString("Quit", 200, 350);
   }
   
   public void down()
   {
     if (this.selection < 2) {
       this.selection += 1;
     }
   }
   
   public void up()
   {
     if (this.selection > 0) {
       this.selection -= 1;
     }
   }
   
   public void applySelection()
   {
     TankWorld world = TankWorld.getInstance();
     Dimension size = world.getSize(); 
     if (this.selection == 0)
     {
       int[] controls = { 37, 38, 39, 40, 32 };
       PlayerShip player = new PlayerShip(new Point(240, size.height - 50), new Point(6, 6), (Image)TankWorld.sprites.get("player1"), controls, "Play");
       world.addPlayer((Tank[]) new PlayerShip[] { player });
     }
     else
     {
       System.exit(0);
     }
     
     this.controller.deleteObservers();
     world.removeKeyListener(this.controller);
     world.level.load();
     this.waiting = false;
   }
   
   public void update(Observable o, Object arg)
   {
     AbstractGameModifier modifier = (AbstractGameModifier)o;
     modifier.read(this);
   }
   
   public boolean isWaiting()
   {
     return this.waiting;
   }
}

package tank;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JApplet;


public class GameApplet
  extends JApplet
{ 

   private static final long serialVersionUID = 1L;
   TankWorld game;
   Thread gameThread;
   
   public void init()
   {
      setBackground(Color.white);
      this.game = TankWorld.getInstance();
      this.game.init();
     
      getRootPane().add("Center", this.game);
      this.game.setFocusable(true);
      this.game.requestFocusInWindow();
   }
   
   public void start()
   {
      this.gameThread = new Thread(this.game);
      this.gameThread.setPriority(1);
      this.game.start();
   }
   
   public void paint(Graphics g)
   {
      this.game.paint(g);
   }
}

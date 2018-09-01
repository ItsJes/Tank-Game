package tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class GameApplication
{
  TankWorld game;
  Thread thread;
   
  public static void main(String[] argv)
  {
     final TankWorld game = TankWorld.getInstance();
     JFrame f = new JFrame("Tank Game");
     f.addWindowListener(new WindowAdapter() {
         @Override
         public void windowGainedFocus(WindowEvent e) {
             game.requestFocusInWindow();
         }
     });
     f.getContentPane().add("Center", game);
     f.pack();
     f.setSize(new Dimension(1200, 1000));
     game.setDimensions(800, 600);
     game.init();
     f.setVisible(true);
     f.setResizable(false);
     f.setDefaultCloseOperation(3);
     //Sound.player("Resources/Rave 128.wav", true);
     game.start();
  }

}

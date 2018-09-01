package ui;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.*;

public abstract class InterfaceObject
  implements Observer
{
   Point location;
   ImageObserver observer;
   
   public abstract void draw(Graphics paramGraphics, int paramInt1, int paramInt2);
   
   public void update(Observable o, Object arg) {}
}

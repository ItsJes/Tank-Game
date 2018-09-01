package tank;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound
{ 
  URL soundFile;
  
  public static void player(String file, boolean isLoop)
  {
     try 
     {
        URL link = TankWorld.getInstance().getClass().getClassLoader().getResource(file);
      
        AudioClip clip = Applet.newAudioClip(link);
        if (isLoop)
        {
           AudioInputStream inputStream = AudioSystem.getAudioInputStream(link);
           Clip clipLoop = AudioSystem.getClip();
           clipLoop.open(inputStream);
           clipLoop.loop(-1);
        }
        else
        {
           clip.play();
        }
    }
     catch (Exception e) {}
  }
}

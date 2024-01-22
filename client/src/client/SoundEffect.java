package client;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum SoundEffect {
    
    //  Ringtone for Chat message receive
    MessageReceive("/audio/receive_message.wav", false), 
    //   Ringtone for income file
    FileSharing("/audio/alarm.wav", false); 
    
    // Properties
    private Clip clip;
    private boolean loop;
    
    /**
     * Constructor SoundEffect
     * @param filename
     * @param loop 
     */
    SoundEffect(String filename, boolean loop){
        try {
            this.loop = loop;
            URL url = this.getClass().getResource(filename);
            AudioInputStream audioIS = AudioSystem.getAudioInputStream(url);
            
            clip = AudioSystem.getClip();
            clip.open(audioIS);
        } catch (IOException iOException) {
            System.out.println("[SoundEffect]" + iOException.getMessage());
        } catch (LineUnavailableException lineUnavailableException) {
            System.out.println("[SoundEffect]" + lineUnavailableException.getMessage());
        } catch (UnsupportedAudioFileException unsupportedAudioFileException){
            System.out.println("[SoundEffect]" + unsupportedAudioFileException.getMessage());
        }
    }
    
    /**
     * 
     */
    public void play(){
        if(clip.isRunning()){
            clip.stop(); //  Stop Audio
        }
        //  Reset Audio from the beginning
        clip.setFramePosition(0);
        clip.start();
        //  Check if audio play contineously
        if(loop){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    /**
     * 
     */
    public void stop(){
        if(clip.isRunning()){
            clip.stop(); //   Stop Audio
        }
    }
}

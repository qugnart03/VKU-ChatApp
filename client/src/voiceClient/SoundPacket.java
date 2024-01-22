package voiceClient;

import java.io.Serializable;
import javax.sound.sampled.AudioFormat;

public class SoundPacket implements Serializable{
    //11.025khz, 8bit, mono, signed, big endian (changes nothing in 8 bit) ~8kb/s
    public static AudioFormat defaultFormat = new AudioFormat(11025f, 8, 1, true, true); 
    //send 1000 samples/packet by default
    public static int defaultDataLenght = 900; 
    //actual data. if null, comfort noise will be played
    private byte[] data;

    public SoundPacket(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
}

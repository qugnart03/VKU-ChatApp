package voiceClient;

import Utils.Utils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class VoiceClient extends Thread{
    
    private Socket s;
    private ArrayList<AudioChannel> chs = new ArrayList<AudioChannel>();
    private MicThread st;

    public VoiceClient(String serverIp, int serverPort) throws UnknownHostException, IOException {
        s = new Socket(serverIp, serverPort);
    }

    @Override
    public void run() {
        try {
            ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());  //create object streams with the server
            ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
            try {
                Utils.sleep(100); //wait for the GUI microphone test to release the microphone
                st = new MicThread(toServer);  //creates a MicThread that sends microphone data to the server
                st.start(); //starts the MicThread
            } catch (Exception e) { //error acquiring microphone. causes: no microphone or microphone busy
                System.out.println("mic unavailable " + e);
            }
            for (;;) { //this infinite cycle checks for new data from the server, then sends it to the correct AudioChannel. if needed, a new AudioChannel is created
                
                if (s.getInputStream().available() > 0) { //we got something from the server (workaround: used available method from InputStream instead of the one from ObjetInputStream because of a bug in the JRE)
                    VoiceMessage in = (VoiceMessage) (fromServer.readObject()); //read message
                    //decide which audio channel should get this message
                    AudioChannel sendTo = null; 
                    for (AudioChannel ch : chs) {
                        if (ch.getChId() == in.getChId()) {
                            sendTo = ch;
                        }
                    }
                    if (sendTo != null) {
                        sendTo.addToQueue(in);
                    } else { //new AudioChannel is needed
                        AudioChannel ch = new AudioChannel(in.getChId());
                        ch.addToQueue(in);
                        ch.start();
                        chs.add(ch);
                    }
                }else{ //see if some channels need to be killed and kill them
                    ArrayList<AudioChannel> killMe=new ArrayList<AudioChannel>();
                    for(AudioChannel c:chs) if(c.canKill()) killMe.add(c);
                    for(AudioChannel c:killMe){c.closeAndKill(); chs.remove(c);}
                    Utils.sleep(1); //avoid busy wait
                }
            }
        } catch (Exception e) { //connection error
            System.out.println("client err " + e.toString());
        }
    }
}

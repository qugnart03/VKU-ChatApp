package voiceClient;

import javax.swing.JOptionPane;

public class ErrorVoiceMessage {
    public static void main(String args[]){
        JOptionPane.showMessageDialog(new JOptionPane(),"This is a library, not a program.\nPlease run VoiceChat_Server or VoiceChat_Client");
    }   
}

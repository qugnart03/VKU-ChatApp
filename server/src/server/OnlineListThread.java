package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class OnlineListThread implements Runnable {
    
    ServerMainForm serverMainForm;
    
    public OnlineListThread(ServerMainForm serverMainForm){
        this.serverMainForm = serverMainForm;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                String msg = "";
                for(int i = 0; i < serverMainForm.clientList.size(); i++){
                    msg = msg + " " + serverMainForm.clientList.elementAt(i);
                }
                
                for(int i = 0; i < serverMainForm.socketList.size(); i++){
                    Socket tsoc = (Socket) serverMainForm.socketList.elementAt(i);
                    DataOutputStream dos = new DataOutputStream(tsoc.getOutputStream());
                    /** CMD_ONLINE [user1] [user2] [user3] **/
                    if(msg.length() > 0){
                        dos.writeUTF("CMD_ONLINE " + msg);
                    }
                }
                Thread.sleep(1900);
            }
        } catch(InterruptedException interruptedException){
            System.out.println(interruptedException.getMessage());
            serverMainForm.appendMessage("[InterruptedException]: " + interruptedException.getMessage());
        } catch (IOException iOException) {
            System.out.println(iOException.getMessage());
            serverMainForm.appendMessage("[IOException]: " + iOException.getMessage());
        }
    }
    
    
}

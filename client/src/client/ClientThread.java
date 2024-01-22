package client;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ClientThread implements Runnable{
    
    // Properties
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    ClientMainForm main;
    StringTokenizer st;
    protected DecimalFormat df = new DecimalFormat("##,#00");
    
    /**
     * 
     * @param socket
     * @param main 
     */
    public ClientThread(Socket socket, ClientMainForm main){
        this.main = main;
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException iOException) {
            main.appendMessage("[IOException]: " + iOException.getMessage(), "Error", Color.RED, Color.RED);
        }
    }

    /**
     * 
     */
    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()){
                String data = dis.readUTF();
                st = new StringTokenizer(data);
                /** Get Message CMD **/
                String CMD = st.nextToken();
                switch(CMD){
                    case "CMD_MESSAGE":
                        SoundEffect.MessageReceive.play(); //  Play Audio clip
                        String msg = "";
                        String frm = st.nextToken();
                        while(st.hasMoreTokens()){
                            msg = msg + " " + st.nextToken();
                        }
                        main.appendMessage(msg, frm, Color.MAGENTA, Color.BLUE);
                        break;
                        
                    case "CMD_ONLINE":
                        Vector online = new Vector();
                        while(st.hasMoreTokens()){
                            String list = st.nextToken();
                            if(!list.equalsIgnoreCase(main.username)){
                                online.add(list);
                            }
                        }
                        main.appendOnlineList(online);
                        break;
                    
                        
                    //  This will inform the client that there's a file receive, Accept or Reject the file  
                    case "CMD_FILE_XD":  // Format:  CMD_FILE_XD [sender] [receiver] [filename]
                        String sender = st.nextToken();
                        String receiver = st.nextToken();
                        String fname = st.nextToken();
                        int confirm = JOptionPane.showConfirmDialog(main, "From: " + sender + "\nFilename: " + fname + "\nwould you like to Accept.?");
                        //SoundEffect.FileSharing.play(); //   Play Audio
                        if(confirm == 0){ // client accepted the request, then inform the sender to send the file now
                            /* Select were to save this file   */
                            main.openFolder();
                            try {
                                dos = new DataOutputStream(socket.getOutputStream());
                                // Format:  CMD_SEND_FILE_ACCEPT [ToSender] [Message]
                                String format = "CMD_SEND_FILE_ACCEPT " + sender + " accepted";
                                dos.writeUTF(format);
                                
                                /*  this will create a filesharing socket to handle incoming file and this socket will automatically closed when it's done.  */
                                Socket fSoc = new Socket(main.getMyHost(), main.getMyPort());
                                DataOutputStream fdos = new DataOutputStream(fSoc.getOutputStream());
                                fdos.writeUTF("CMD_SHARINGSOCKET " + main.getMyUsername());
                                /*  Run Thread for this   */
                                new Thread(new ReceivingFileThread(fSoc, main)).start();
                            } catch (IOException iOException) {
                                System.out.println("[CMD_FILE_XD]: " + iOException.getMessage());
                            }
                        } else { // client rejected the request, then send back result to sender
                            try {
                                dos = new DataOutputStream(socket.getOutputStream());
                                // Format:  CMD_SEND_FILE_ERROR [ToSender] [Message]
                                String format = "CMD_SEND_FILE_ERROR " + sender + " Client rejected your request or connection was lost.!";
                                dos.writeUTF(format);
                            } catch (IOException iOException) {
                                System.out.println("[CMD_FILE_XD]: " + iOException.getMessage());
                            }
                        }                       
                        break;   
                        
                    default: 
                        main.appendMessage("[CMDException]: Unknown Command " + CMD, "CMDException", Color.RED, Color.RED);
                    break;
                }
            }
        } catch(IOException iOException){
            main.appendMessage(" Server Connection was lost, please try again later.!", "Error", Color.RED, Color.RED);
            System.out.println(iOException.getMessage());
        }
    }
}

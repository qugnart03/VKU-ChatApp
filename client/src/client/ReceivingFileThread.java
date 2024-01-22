package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitorInputStream;

public class ReceivingFileThread implements Runnable {
    
    // Properties
    protected Socket socket;
    protected DataInputStream dataInputStream;
    protected DataOutputStream dataOutputStream;
    protected ClientMainForm main;
    protected StringTokenizer stringTokenizer;
    protected DecimalFormat decimalFormat = new DecimalFormat("##,#00");
    private final int BUFFER_SIZE = 100;
    
    
    /**
     * Constructor ReceivingFileThread
     * @param socket
     * @param mainForm 
     */
    public ReceivingFileThread(Socket socket, ClientMainForm mainForm){
        this.socket = socket;
        this.main = mainForm;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException iOException) {
            System.out.println("[ReceivingFileThread]: " + iOException.getMessage());
        }
    }

    
    /**
     * 
     */
    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()){
                String data = dataInputStream.readUTF();
                stringTokenizer = new StringTokenizer(data);
                String CMD = stringTokenizer.nextToken();
                
                switch(CMD){
                    //   This will handle the recieving of a file in background process from other user
                    case "CMD_SENDFILE":
                        String consignee = null;
                            try {
                                String filename = stringTokenizer.nextToken();
                                int filesize = Integer.parseInt(stringTokenizer.nextToken());
                                consignee = stringTokenizer.nextToken(); // Get the Sender Username
                                main.setMyTitle("Downloading File....");
                                System.out.println("Downloading File....");
                                System.out.println("From: " + consignee);
                                String path = main.getMyDownloadFolder() + filename;                                
                                /*  Creat Stream   */
                                try (FileOutputStream fos = new FileOutputStream(path)) {
                                    InputStream input = socket.getInputStream();
                                    /*  Monitor Progress   */
                                    ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(main, "Downloading file please wait...", input);
                                    /*  Buffer   */
                                    BufferedInputStream bis = new BufferedInputStream(pmis);
                                    /**  Create a temporary file **/
                                    byte[] buffer = new byte[BUFFER_SIZE];
                                    int count, percent = 0;
                                    while((count = bis.read(buffer)) != -1){
                                        percent = percent + count;
                                        int p = (percent / filesize);
                                        main.setMyTitle("Downloading File  " + p + "%");
                                        fos.write(buffer, 0, count);
                                    }
                                    fos.flush();
                                }
                                main.setMyTitle("you are logged in as: " + main.getMyUsername());
                                JOptionPane.showMessageDialog(null, "File has been downloaded to \n'" + path + "'");
                                System.out.println("File was saved: " + path);
                            } catch (IOException iOException) {
                                /*
                                Send back an error message to sender
                                Format: CMD_SENDFILERESPONSE [username] [Message]
                                */
                                DataOutputStream eDos = new DataOutputStream(socket.getOutputStream());
                                eDos.writeUTF("CMD_SENDFILERESPONSE " + consignee + " Connection was lost, please try again later.!");
                                
                                System.out.println(iOException.getMessage());
                                main.setMyTitle("you are logged in as: " + main.getMyUsername());
                                JOptionPane.showMessageDialog(main, iOException.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                                socket.close();
                            }
                        break;
                }
            }
        } catch (IOException iOException) {
            System.out.println("[ReceivingFileThread]: " + iOException.getMessage());
        }
    }
}

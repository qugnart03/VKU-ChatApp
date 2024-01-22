package client;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class SendingFileThread implements Runnable {
    
    // Properties
    protected Socket socket;
    private DataOutputStream dos;
    protected SendFile form;
    protected String file;
    protected String receiver;
    protected String sender;
    protected DecimalFormat df = new DecimalFormat("##,#00");
    private final int BUFFER_SIZE = 100;
    
    /**
     * 
     * @param socket
     * @param file
     * @param receiver
     * @param sender
     * @param frm 
     */
    public SendingFileThread(Socket socket, String file, String receiver, String sender, SendFile frm){
        this.socket = socket;
        this.file = file;
        this.receiver = receiver;
        this.sender = sender;
        this.form = frm;
    }

    
    /**
     * 
     */
    @Override
    public void run() {
        try {
            form.disableGUI(true);
            System.out.println("Sending File..!");
            dos = new DataOutputStream(socket.getOutputStream());
            /** Write filename, recipient, username  **/
            //  Format: CMD_SENDFILE [Filename] [Size] [Recipient] [Consignee]
            File filename = new File(file);
            int len = (int) filename.length();
            int filesize = (int)Math.ceil(len / BUFFER_SIZE); // get the file size
            String clean_filename = filename.getName();
            dos.writeUTF("CMD_SENDFILE "
                    + clean_filename.replace(" ", "_") 
                    + " " + filesize 
                    + " " + receiver 
                    + " " + sender);
            System.out.println("From: " + sender);
            System.out.println("To: " + receiver);
            /** Create an stream **/
            InputStream input = new FileInputStream(filename);
            /*  Monitor progress   */
            //ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(form, "Sending file please wait...", input);
            /** Read file ***/
            try (OutputStream output = socket.getOutputStream()) {
                /*  Monitor progress   */
                //ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(form, "Sending file please wait...", input);
                /** Read file ***/
                BufferedInputStream bis = new BufferedInputStream(input);
                /** Create a temporary file storage **/
                byte[] buffer = new byte[BUFFER_SIZE];
                int count, percent = 0;
                while((count = bis.read(buffer)) > 0){
                    percent = percent + count;
                    int p = (percent / filesize);
                    //form.setMyTitle(p +"% Sending File...");
                    form.updateProgress(p);
                    output.write(buffer, 0, count);
                }   /* Update AttachmentForm GUI */
                form.setMyTitle("File was sent.!");
                form.updateAttachment(false); //  Update Attachment 
                JOptionPane.showMessageDialog(form, "File successfully sent.!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
                form.closeThis();
                /* Close Streams */
                output.flush();
            }
            System.out.println("File was sent..!");
        } catch (IOException iOException) {
            form.updateAttachment(false); //  Update Attachment
            System.out.println("[SendFile]: " + iOException.getMessage());
        }
    }
}

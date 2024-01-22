package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {
    
    ServerSocket server;
    int backlog = 500;
    ServerMainForm serverMainForm;
    boolean keepGoing = true;
    
    public ServerThread(int port, ServerMainForm serverMainForm){
        serverMainForm.appendMessage("[Server]: Starting server in port " + port);
        try {
            this.serverMainForm = serverMainForm;
            server = new ServerSocket(port, this.backlog, InetAddress.getByName("127.0.0.1"));
            serverMainForm.appendMessage("[Server]: Host:" 
                    + InetAddress.getLocalHost()
                    + ",IP:" + server.getInetAddress()
                    + ",Port:" + port 
                    + ",Backlog:" + backlog);
            serverMainForm.appendMessage("[Server]: Server started.!");
        } 
        catch (IOException iOException) { 
            serverMainForm.appendMessage("[IOException]: " + iOException.getMessage()); 
        }
    }
    
    public ServerThread(int port, int backlog, String ipAddress, ServerMainForm serverMainForm){
        serverMainForm.appendMessage("[Server]: Starting server in port " + port);
        try {
            this.serverMainForm = serverMainForm;
            server = new ServerSocket(port, backlog, InetAddress.getByName(ipAddress));
            serverMainForm.appendMessage("[Server]: IP:" 
                    + server.getInetAddress().toString() 
                    + ",Port:" + port 
                    + ",Backlog:" + backlog);
            serverMainForm.appendMessage("[Server]: Server started.!");
        } 
        catch (IOException iOException) { 
            serverMainForm.appendMessage("[IOException]: " + iOException.getMessage()); 
        }
    }
     
     
    public ServerThread(int port, int backlog, ServerMainForm serverMainForm){
        serverMainForm.appendMessage("[Server]: Starting server in port " + port);
        try {
            this.serverMainForm = serverMainForm;
            server = new ServerSocket(port, backlog, InetAddress.getByName("127.0.0.1"));
            serverMainForm.appendMessage("[Server]: IP:" 
                    + server.getInetAddress().toString() 
                    + ",Port:" + port +",Backlog:" 
                    + backlog);
            serverMainForm.appendMessage("[Server]: Server started.!");
        } 
        catch (IOException iOException) { 
            serverMainForm.appendMessage("[IOException]: " + iOException.getMessage()); 
        }
    }
     
     

    @Override
    public void run() {
        try {
            while(keepGoing){
                Socket socket = server.accept();
                serverMainForm.appendMessage("[Socket]: " + socket);
                /** SOcket thread **/
                new Thread(new SocketThread(socket, serverMainForm)).start();
            }
        } catch (IOException iOException) {
            serverMainForm.appendMessage("[ServerThreadIOException]: " + iOException.getMessage());
        }
    }
    
    
    public void stop(){
        try {
            server.close();
            keepGoing = false;
            System.out.println("Server is now closed..!");
        } catch (IOException iOException) {
            System.out.println(iOException.getMessage());
        }
    }
    
}

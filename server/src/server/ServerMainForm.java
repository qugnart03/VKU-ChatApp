package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class ServerMainForm extends javax.swing.JFrame {
 
    // Properties
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
    Thread thread;
    ServerThread serverThread;
    /** Chat List  **/
    public Vector socketList = new Vector();
    public Vector clientList = new Vector();
    /** File Sharing List **/
    public Vector clientFileSharingUsername = new Vector();
    public Vector clientFileSharingSocket = new Vector();
    /** Server **/
    ServerSocket server;
    
    /**
     * Creates new form MainForm
     */
    public ServerMainForm() {
        initComponents();
    }
    
    /**
     * 
     * @param msg 
     */
    public void appendMessage(String msg){
        Date date = new Date();
        jTextAreaChatServerConsole.append(simpleDateFormat.format(date) + ": " + msg + "\n");
        jTextAreaChatServerConsole.setCaretPosition(jTextAreaChatServerConsole.getText().length() - 1);
    }
    
    /**
     * 
     * @param msg 
     */

    
    /** Setters **/
    /**
     * 
     * @param socket 
     */
    public void setSocketList(Socket socket){
        try {
            socketList.add(socket);
            appendMessage("[setSocketList]: Added");
        } catch (Exception exception) { 
            appendMessage("[setSocketList]: " + exception.getMessage()); 
        }
    }
    
    /**
     * 
     * @param client 
     */
    public void setClientList(String client){
        try {
            clientList.add(client);
            appendMessage("[setClientList]: Added");
        } catch (Exception exception) { 
            appendMessage("[setClientList]: " + exception.getMessage()); 
        }
    }
    
    /**
     * 
     * @param user 
     */
    public void setClientFileSharingUsername(String user){
        try {
            clientFileSharingUsername.add(user);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    
    /**
     * 
     * @param soc 
     */
    public void setClientFileSharingSocket(Socket soc){
        try {
            clientFileSharingSocket.add(soc);
        } catch (Exception exception) { 
            System.out.println(exception.getMessage());
        }
    }
    
    /** Getters
     * 
     * @param client
     * @return  **/
    public Socket getClientList(String client){
        Socket tsoc = null;
        for(int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).equals(client)){
                tsoc = (Socket) socketList.get(i);
                break;
            }
        }
        return tsoc;
    }
    
    
    /**
     * 
     * @param client 
     */
    public void removeFromTheList(String client){
        try {
            for(int i = 0; i < clientList.size(); i++){
                if(clientList.elementAt(i).equals(client)){
                    clientList.removeElementAt(i);
                    socketList.removeElementAt(i);
                    appendMessage("[Removed]: " + client);
                    break;
                }
            }
        } catch (Exception exception) {
            appendMessage("[RemovedException]: " +  exception.getMessage());
        }
    }
    
    /**
     * 
     * @param username
     * @return 
     */
    public Socket getClientFileSharingSocket(String username){
        Socket tsoc = null;
        for(int i = 0; i < clientFileSharingUsername.size(); i++){
            if(clientFileSharingUsername.elementAt(i).equals(username)){
                tsoc = (Socket) clientFileSharingSocket.elementAt(i);
                break;
            }
        }
        return tsoc;
    }
    
    
    /*
    Remove Client File Sharing List
    */
    /**
     * 
     * @param username 
     */
    public void removeClientFileSharing(String username){
        for(int i = 0; i < clientFileSharingUsername.size(); i++){
            if(clientFileSharingUsername.elementAt(i).equals(username)){
                try {
                    Socket rSock = getClientFileSharingSocket(username);
                    if(rSock != null){
                        rSock.close();
                    }
                    clientFileSharingUsername.removeElementAt(i);
                    clientFileSharingSocket.removeElementAt(i);
                    appendMessage("[FileSharing]: Removed "+ username);
                } catch (IOException iOException) {
                    appendMessage("[FileSharing]: " + iOException.getMessage());
                    appendMessage("[FileSharing]: Unable to Remove "+ username);
                }
                break;
            }
        }
    }
    
    /**
     * 
     * @return 
     */
    String getIPAddress(){
        return jSpinnerFirstByte.getValue().toString() 
                + "." + jSpinnerSecondByte.getValue().toString() 
                + "." +jSpinnerThirdByte.getValue().toString() 
                + "." + jSpinnerFourthByte.getValue().toString();
    }

    /**
     * 
     * @return 
     */
    private Integer getPort(){
        return (Integer) jSpinnerPortNumber.getValue();
    }    
    

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonStartChatServer = new javax.swing.JButton();
        jButtonStopChatServer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaChatServerConsole = new javax.swing.JTextArea();
        jButtonTestChatServer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerFirstByte = new javax.swing.JSpinner();
        jSpinnerSecondByte = new javax.swing.JSpinner();
        jSpinnerThirdByte = new javax.swing.JSpinner();
        jSpinnerFourthByte = new javax.swing.JSpinner();
        jSpinnerPortNumber = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server Control Panel");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButtonStartChatServer.setBackground(new java.awt.Color(0, 102, 255));
        jButtonStartChatServer.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 14)); // NOI18N
        jButtonStartChatServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-start-24.png"))); // NOI18N
        jButtonStartChatServer.setText("Start Server");
        jButtonStartChatServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartChatServerActionPerformed(evt);
            }
        });

        jButtonStopChatServer.setBackground(new java.awt.Color(255, 51, 51));
        jButtonStopChatServer.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 14)); // NOI18N
        jButtonStopChatServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-stop-sign-24.png"))); // NOI18N
        jButtonStopChatServer.setText("Stop Server");
        jButtonStopChatServer.setEnabled(false);
        jButtonStopChatServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopChatServerActionPerformed(evt);
            }
        });

        jTextAreaChatServerConsole.setEditable(false);
        jTextAreaChatServerConsole.setBackground(new java.awt.Color(0, 0, 0));
        jTextAreaChatServerConsole.setColumns(20);
        jTextAreaChatServerConsole.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        jTextAreaChatServerConsole.setForeground(new java.awt.Color(51, 255, 0));
        jTextAreaChatServerConsole.setRows(5);
        jTextAreaChatServerConsole.setPreferredSize(null);
        jScrollPane1.setViewportView(jTextAreaChatServerConsole);

        jButtonTestChatServer.setBackground(new java.awt.Color(204, 204, 0));
        jButtonTestChatServer.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 14)); // NOI18N
        jButtonTestChatServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-test-program-24.png"))); // NOI18N
        jButtonTestChatServer.setText("Test Server");
        jButtonTestChatServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestChatServerActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 14)); // NOI18N
        jLabel1.setText("IP Address");

        jLabel2.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 14)); // NOI18N
        jLabel2.setText("Port Number");

        jSpinnerFirstByte.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 13)); // NOI18N
        jSpinnerFirstByte.setModel(new javax.swing.SpinnerNumberModel(127, 0, 255, 1));

        jSpinnerSecondByte.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 13)); // NOI18N
        jSpinnerSecondByte.setModel(new javax.swing.SpinnerNumberModel(0, 0, 255, 1));

        jSpinnerThirdByte.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 13)); // NOI18N
        jSpinnerThirdByte.setModel(new javax.swing.SpinnerNumberModel(0, 0, 255, 1));

        jSpinnerFourthByte.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 13)); // NOI18N
        jSpinnerFourthByte.setModel(new javax.swing.SpinnerNumberModel(1, 0, 255, 1));

        jSpinnerPortNumber.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 13)); // NOI18N
        jSpinnerPortNumber.setModel(new javax.swing.SpinnerNumberModel(4000, 1, 65536, 1));

        jLabel4.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 18)); // NOI18N
        jLabel4.setText("Chat control area");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(20, 20, 20)
                        .addComponent(jSpinnerPortNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonStartChatServer)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonTestChatServer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonStopChatServer))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jSpinnerFirstByte, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jSpinnerSecondByte, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jSpinnerThirdByte, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addComponent(jSpinnerFourthByte, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerFirstByte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerSecondByte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerThirdByte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerFourthByte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jSpinnerPortNumber)
                                .addComponent(jButtonStartChatServer)
                                .addComponent(jButtonTestChatServer)
                                .addComponent(jButtonStopChatServer))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonTestChatServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestChatServerActionPerformed
        // TODO add your handling code here:
        int port = this.getPort();
        String ip = this.getIPAddress();
        appendMessage("[Server]: Prepare running at " + ip + " in port " + port);
    }//GEN-LAST:event_jButtonTestChatServerActionPerformed

    private void jButtonStopChatServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopChatServerActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null, "Close Server.?");
        if(confirm == 0){
            serverThread.stop();
        }
        jButtonStartChatServer.setEnabled(true);
        jButtonTestChatServer.setEnabled(true);
        jButtonStopChatServer.setEnabled(false);
    }//GEN-LAST:event_jButtonStopChatServerActionPerformed

    private void jButtonStartChatServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartChatServerActionPerformed
        // TODO add your handling code here:
        int port = this.getPort();
        serverThread = new ServerThread(port, this);
        thread = new Thread(serverThread);
        thread.start();

        new Thread(new OnlineListThread(this)).start();

        jButtonStartChatServer.setEnabled(false);
        jButtonTestChatServer.setEnabled(false);
        jButtonStopChatServer.setEnabled(true);
    }//GEN-LAST:event_jButtonStartChatServerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException classNotFoundException) {
            java.util.logging.Logger.getLogger(ServerMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, classNotFoundException);
        } catch (InstantiationException instantiationException) {
            java.util.logging.Logger.getLogger(ServerMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, instantiationException);
        } catch (IllegalAccessException illegalAccessException) {
            java.util.logging.Logger.getLogger(ServerMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, illegalAccessException);
        } catch (javax.swing.UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
            java.util.logging.Logger.getLogger(ServerMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, unsupportedLookAndFeelException);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            ServerMainForm serverMainForm =  new ServerMainForm();
            ImageIcon img = new ImageIcon("src/icons/icons8-server-96.png");
            serverMainForm.setIconImage(img.getImage());
            serverMainForm.setLocationRelativeTo(null);
            serverMainForm.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonStartChatServer;
    private javax.swing.JButton jButtonStopChatServer;
    private javax.swing.JButton jButtonTestChatServer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerFirstByte;
    private javax.swing.JSpinner jSpinnerFourthByte;
    private javax.swing.JSpinner jSpinnerPortNumber;
    private javax.swing.JSpinner jSpinnerSecondByte;
    private javax.swing.JSpinner jSpinnerThirdByte;
    private javax.swing.JTextArea jTextAreaChatServerConsole;
    // End of variables declaration//GEN-END:variables


}

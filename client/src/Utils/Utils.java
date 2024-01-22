package Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class Utils {
    
    public static void sleep(int ms){
        try {Thread.sleep(ms);} catch (InterruptedException ex) {}
    }
    
    public static String getExternalIP(){
        try {
            URL myIp = new URL("http://checkip.dyndns.org/");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myIp.openStream()));
            String string = bufferedReader.readLine();
            return string.substring(string.lastIndexOf(":")+2, string.lastIndexOf("</body>"));
        } catch (Exception exception) {
            return "[Exception error]:  " + exception;
        }     
    }
    
    public static String getInternalIP(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException unknownHostException) {
            return "[Exception error]:  " + unknownHostException;
        }
    }    
}

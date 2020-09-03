package compute;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// --------------------------------------------------------------
// use this socket logger to debug java code when multi machines
public class SocketLogger {

    public Socket socket = null;

    public void log(String str) {
        try {
            if (this.socket == null) {
                this.socket = new Socket("10.10.25.51", 18000);
            }
            
            // write string to socket by PrintWriter
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println(str);

        } catch (Exception e) {
            System.out.println("logger socket error: " + e.getMessage());
            this.close();
        }        
    }
    
     public void close() {
         this.socket.close(); 
    }
    
}




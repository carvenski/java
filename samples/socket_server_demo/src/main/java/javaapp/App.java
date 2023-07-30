package javaapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class App{
    public static void main(String[] args) throws IOException {
        ServerSocket server_socket = new ServerSocket(18000);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        try {
            System.out.println("Server socket started at :18000.");
            // use threadpool to handle connections            
            while (true) {
                System.out.println("Main thread is waiting for new connection...");
                Socket client_socket = server_socket.accept();
                System.out.println("=> connected with " +client_socket.toString());
                // submit a connection to threadpool
                Runnable worker = new WorkerThread(client_socket);
                executor.execute(worker);
            }
        }
        finally {
            executor.shutdown();
            server_socket.close();
            System.out.println("Server socket stopped.");
        }
    }
}

class WorkerThread implements Runnable {
    private Socket sock;

    public WorkerThread(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {        
        try {
            System.out.println("create a new thread for " + this.sock.toString());
            // read data from InputStream to byte[], then convert byte[] to string.
            byte[] buffer = new byte[1024];
            this.sock.getInputStream().read(buffer);
            System.out.println("=> got data bytes from" + this.sock.toString());
            System.out.println(Arrays.toString(buffer));
            System.out.println("=> got data string from" + this.sock.toString());
            System.out.println(new String(buffer));
            /*  
             in java: 
             byte[] to string:   String s = new String(new byte[]{110, 112, 113});
             string to byte[]:   byte[] bytes = "asd".getBytes(); 
             print byte[]:       Arrays.toString(bytes); 
            */
 
            // read string from socket by Scanner
            Scanner in = new Scanner(this.sock.getInputStream());
            if (in.hasNext()) {
                System.out.println("=> got second data string from" + this.sock.toString());
                System.out.println(in.next());
            }
         
            // write string to socket by PrintWriter
            PrintWriter out = new PrintWriter(this.sock.getOutputStream(), true);
            out.println(new Date().toString());

            // close socket
            this.sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {            
        }
    }
}




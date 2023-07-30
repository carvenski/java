package javaapp;

/**
 *use java netty to write all kinds of protocol server, just like twisted...
*/
import javaapp.DiscardServer;
import javaapp.EchoServer;
import javaapp.HttpServer;
import javaapp.TimeServer;


public class App{
    public static void main( String[] args ) throws Exception{

        int port = 18000;

        // new DiscardServer(port).run();
        // new EchoServer(port).run();
        new HttpServer(port).run();
        // new TimeServer(port).run();

    }


}



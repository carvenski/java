package javaapp;

import java.util.*;
import java.io.*;

public class App {
    public static void main(String[] args) {
         Util util = new Util();
         util.go();
    }
}

class Util {
    public void go() {
         Properties prop = new Properties();
         String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
         System.out.println(path);
         try {
//            InputStream InputStream =  new FileInputStream(
//                    new File(path));
//            prop.load(InputStream);
//            String name = prop.getProperty("name");
//            System.out.println(name);
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
}

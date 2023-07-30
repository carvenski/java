package hellojava;

import java.util.*;


public class Main {
    public static int i = 0;
    public static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();  

    public static void main(String[] args) throws Exception {   
        
   		new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000); // thread sleep 5s
                        System.out.println("=> thread is checking bp size ...");
                        System.out.println(i);
                        System.out.println(map);
                    } catch (Exception e) {
                        System.out.println("Thread Runing Error");
                        e.printStackTrace();
                    }
                }
            }
            }.start();     

      while (true) {
      		Thread.sleep(3000);
      		System.out.println("main thread...");
      		i++;
      		map.put(i, i+1);
      }

    }
}

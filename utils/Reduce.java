import java.util.*;

public class Reduce {
    public static void main(String[] args) {
        int[] l1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Float> l2 = new ArrayList<Float>();
        for (int i = 1; i < l1.length; i++) {
           l2.add( (l1[i] - l1[i-1])/2 );
        }
        System.out.println(l2);
    }
}


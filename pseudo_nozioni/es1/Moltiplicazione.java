
import java.util.Random;

class Moltiplicazione {
    public static void main(String[] args) {
        Random rnd = new Random();
        int n = 10;
        int v = 0;

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt(100);
            System.err.println(a[i]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
               v += a[i]*a[j]; 
            }
        }

        System.err.println(v);
    }
}
import java.util.Random;
import java.util.Scanner;

class RicercaBinaria{
    public static void main(String[] args) {
        Random rnd = new Random();
        int n = 10;
        Scanner scanner = new Scanner(System.in);

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
            System.err.println(a[i]);
        }

        System.err.println("Cerca un numero");
        int x = scanner.nextInt();

        System.out.println("Ricorsivo: " + ricercaBinaria(a, x, 0, a.length - 1));
        System.out.println("Iterativo: " + ricBinIt(a, x));
    }

    public static int ricercaBinaria(int[] a, int x, int i, int j) {
        if (i > j)
            return -1;

        int k = (i + j) / 2;
        int mid = a[k];


        if (mid == x)
            return k;
        
        if (x > mid) 
            return ricercaBinaria(a, x, k + 1, j);
        else if (x < mid)
            return ricercaBinaria(a, x, i, k - 1);

        return -1;
    }

    public static int ricBinIt(int[] a, int x) {
        int i = 0; 
        int j = a.length - 1;
        int k = 0;
        int value = 0;


        while (i <= j) { 
            k = ( i + j ) / 2;
            value = a[k];

            if (value == x) {
                return k;
            }
            else if ( x < value ) {
                j = k - 1;
            }
            else {
                i = k + 1;
            }
        }
        return -1;
    }
}
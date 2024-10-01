/*
    I -> Istanze: { M : M = matrici di dimensioni n}
    S -> Soluzioni: { N : N = matrici di dimensioni n}
    TT: { (M, N) : M ∈ I, N ∈ S, N trasposta di M }
*/


import java.util.Random;

public class TraspostaMatrice{
    public static void main(String[] args) {
        int n = 4;
        int[][] matrice = new int[n][n];

        stampa(matrice, true);

        for ( int i = 0; i < matrice.length; i++)
        {
            for (int j = i + 1; j < matrice.length; j++)
            {
                int tmp = matrice[i][j];
                matrice[i][j] = matrice[j][i];
                matrice[j][i] = tmp;
            }
        }

        System.out.println("La trasposta della matrice è: ");

        stampa(matrice, false);
    }

    public static void stampa(int[][] matrice, Boolean popola)
    {
        Random rnd = new Random();

        for(int i = 0; i < matrice.length; i++)
        {
            System.out.print("[ ");
            for (int j = 0; j < matrice.length; j++)
            {
                if (popola)
                    matrice[i][j] = rnd.nextInt(10);

                System.out.print(matrice[i][j] + " ");
            }

            System.out.print("]\n");
        }
    }
}
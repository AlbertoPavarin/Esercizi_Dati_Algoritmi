class Matrici1{
    public static void main(String[] args) {
        int n = 16;
        double n_column = Math.sqrt(n);
        int[] matrice = {0, 0, 2, 3, 1, 0, 2, 3, 1, 1, 2, 3, 1, 0, 2, 3,};

        for (int i = 0; i < n; i++) {
            System.err.print(matrice[i] + " ");
            if (i % n_column == n_column - 1)
                System.err.println();
        }

        System.err.println();


        System.err.println(searchColumn(matrice, n, n_column));
    }

    public static int searchColumn(int[] matrice, int n, double n_col)
    {
        int streak = 0;
        int col = 0;
        for (int i = col; i < n && col < n_col; i += n_col) {
            if (matrice[i] == 0)
            {
                streak++;
                System.err.println(streak);
                if (streak == n_col)
                    return col;
            }
            else
                streak = 0;

            if (i >= n - n_col)
            {
                col++;
                i = col - (int)n_col;
            }
        }

        return -1;
    }
}
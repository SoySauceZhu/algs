public class Percolation {

    private int[][] gird;
    private int n;

    public Percolation(int n) {
        this.n = n;
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        gird = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                gird[i][j] = 0;
            }
        }
    }

    public void open(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new java.lang.IllegalArgumentException();
        }

        this.gird[row - 1][col - 1] = 1;
    }


}


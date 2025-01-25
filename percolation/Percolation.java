import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private int[][] grid;
    private WeightedQuickUnionUF uf;
    private int[] dx = { -1, 1, 0, 0 };
    private int[] dy = { 0, 0, -1, 1 };
    private int opened = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        this.n = n;
        grid = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }

        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    private boolean isInGrid(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            return false;
        }
        return true;
    }

    private int toIndex(int row, int col) {
        if (isInGrid(row, col)) {
            return (row - 1) * this.n + col - 1;
        }
        return -1;
    }

    public boolean isOpen(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IllegalArgumentException("row or col out of bounds");
        }
        return (grid[row - 1][col - 1] == 1);
    }

    public void open(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IllegalArgumentException("row or col out of bounds");
        }

        if (isOpen(row, col)) {
            return;
        }

        // update grid
        this.grid[row - 1][col - 1] = 1;
        opened = opened + 1;

        // update UF
        // Union to the neighbor if it inGrid and isOpen
        for (int i = 0; i < 4; i++) {
            int x = row + dx[i];
            int y = col + dy[i];

            if (isInGrid(x, y) && isOpen(x, y)) {
                uf.union(toIndex(row, col), toIndex(x, y));
            }
        }

        if (row == 1) {
            uf.union(toIndex(row, col), n * n);     // All first row elements are union to n^2 index
        }

        if (row == n) {
            uf.union(toIndex(row, col),
                     n * n + 1); // All last row elements are union to n^2+1 index
        }
    }

    public boolean isFull(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IllegalArgumentException("row or col out of bounds");
        }

        if (!isOpen(row, col)) {
            return false;
        }

        int fullRoot = uf.find(n * n);
        int root = uf.find(toIndex(row, col));

        return fullRoot == root;
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        return uf.find(n * n) == uf.find(n * n + 1);
    }


    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        perc.open(1, 3);
        perc.open(2, 3);
        perc.open(3, 3);
        perc.open(3, 1);
    }
}


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashSet;
import java.util.Set;

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

        uf = new WeightedQuickUnionUF(n * n);
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

    // private int[] toCordinate(int index) {
    //     if (index < 0 || index >= n) {
    //         return null;
    //     }
    //     return new int[] { index / n + 1, index % n + 1 };
    // }
    //
    // private int[] findNeighborIndex(int row, int col) {
    //     // left right up down
    //     int[] index = new int[4];
    //
    //     for (int i = 0; i < 4; i++) {
    //         int x = row + dx[i];
    //         int y = col + dy[i];
    //
    //         if (isInGrid(x, y)) {
    //             index[i] = toIndex(x, y);
    //         }
    //         else {
    //             index[i] = -1;
    //         }
    //     }
    //     return index;
    // }

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
    }

    public boolean isFull(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IllegalArgumentException("row or col out of bounds");
        }

        if (!isOpen(row, col)) {
            return false;
        }

        Set<Integer> roots = new HashSet<>();

        for (int i = 0; i < n; i++) {
            if (isOpen(1, i + 1)) {
                roots.add(uf.find(i));
            }
        }

        int root = uf.find(toIndex(row, col));

        for (int i : roots) {
            if (i == root) {
                return true;
            }
        }

        return false;
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        int row = n;
        for (int col = 1; col < n + 1; col++) {
            if (isOpen(row, col)) {
                if (isFull(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        perc.open(1, 3);
        perc.open(2, 3);
        perc.open(3, 3);
        perc.open(3, 1);
    }
}


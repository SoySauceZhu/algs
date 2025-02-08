/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int[][] tiles;
    private int[][] goal;
    private int size;
    private int hamming;
    private int manhattan;
    private int x0;
    private int y0;

    public Board(int[][] tiles) {
        if (tiles.length != tiles[0].length) {
            throw new IllegalArgumentException("Board must be square");
        }

        this.tiles = tiles;
        size = tiles.length;
        goal = new int[size][size];

        int n = 1;
        int hamming = 0;
        int manhattan = 0;
        int x0 = 0, y0 = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                goal[i][j] = n++;
                if (tiles[i][j] == 0) {
                    x0 = j;
                    y0 = i;
                }

                int tile = tiles[i][j];
                int dx = Math.abs((tile - 1) % size - j);
                int dy = Math.abs((tile - 1) / size - i);
                if (tile != 0) {
                    manhattan += dx + dy;
                }

                if (dx + dy != 0 && tile != 0) {
                    hamming++;
                }

            }
        }
        goal[size - 1][size - 1] = 0;
        this.hamming = hamming;
        this.manhattan = manhattan;
        this.x0 = x0;
        this.y0 = y0;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int dimension() {
        return size;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return hamming == 0;
    }


    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        return Arrays.deepEquals(this.tiles, ((Board) y).tiles);
    }

    private boolean inBounds(int x, int y) {
        if (x < 0 || y < 0 || x >= size || y >= size) {
            return false;
        }
        return true;
    }

    private int[][] copyTiles() {
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        return newTiles;
    }

    public Iterable<Board> neighbors() {
        int[][] dirs = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        ArrayList<Board> neighbors = new ArrayList<Board>();

        for (int[] dir : dirs) {
            int swapx = x0 + dir[0];
            int swapy = y0 + dir[1];
            if (inBounds(swapy, swapx)) {
                int[][] copy = copyTiles();
                copy[y0][x0] = tiles[swapy][swapx];
                copy[swapy][swapx] = 0;
                neighbors.add(new Board(copy));
            }
        }

        return neighbors;
    }

    public Board twin() {
        int[][] newTiles = copyTiles();
        int[] indices = new int[4];
        int k = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != y0 || j != x0) {
                    indices[k++] = i;
                    indices[k++] = j;
                    if (k == indices.length) break;
                }
            }
            if (k == indices.length) break;
        }
        int t = newTiles[indices[0]][indices[1]];
        newTiles[indices[0]][indices[1]] = newTiles[indices[2]][indices[3]];
        newTiles[indices[2]][indices[3]] = t;
        return new Board(newTiles);
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Board inital2 = new Board(tiles);
        StdOut.println(initial.equals(inital2));
        StdOut.println(initial.equals(initial.twin()));

        StdOut.println(initial);
        StdOut.println("hanmming dis: " + initial.hamming());
        StdOut.println("manhattan dis: " + initial.manhattan());
        StdOut.println("Twin: ");
        StdOut.println(initial.twin());
        StdOut.println("Neighbors: ");
        ArrayList<Board> ns = (ArrayList<Board>) initial.neighbors();
        for (Board b : ns) StdOut.println(b);
    }
}

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private class Node implements Comparable<Node> {
        private Node parent;
        private Board board;
        private int move;
        private int priority;

        public Node(Node parent, Board board) {
            this.parent = parent;
            this.board = board;
            int manhattan = board.manhattan();
            this.move = parent == null ? 0 : parent.move + 1;
            this.priority = manhattan + move;
        }

        public int compareTo(Node o) {
            return this.priority - o.priority;
        }

        public List<Node> children() {
            List<Node> children = new ArrayList<Node>();
            for (Board child : this.board.neighbors()) {
                Node ptr = this;
                boolean found = false;
                while (ptr != null) {
                    if (child.equals(ptr.board)) {
                        found = true;
                    }
                    ptr = ptr.parent;
                }
                if (!found) {
                    children.add(new Node(this, child));
                }
            }
            return children;
        }
    }

    private Stack<Board> solutionBoards;
    private boolean solved = false;
    private int moves;

    public Solver(Board board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }
        Node currNode = new Node(null, board);

        MinPQ<Node> minPQ = new MinPQ<>();
        minPQ.insert(currNode);
        // private List<Node> solution = new ArrayList<>();

        while (minPQ.size() > 0) {
            currNode = minPQ.delMin();

            if (currNode.board.isGoal()) {
                solved = true;
                break;
            }

            for (Node child : currNode.children()) {
                minPQ.insert(child);
            }
        }

        if (solved) {
            int m = 0;
            solutionBoards = new Stack<>();
            Node ptr = currNode;
            solutionBoards.push(ptr.board);
            while (ptr.parent != null) {
                m++;
                ptr = ptr.parent;
                solutionBoards.push(ptr.board);
            }
            moves = m;
        }
    }

    public boolean isSolvable() {
        return solved;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return solutionBoards;
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

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
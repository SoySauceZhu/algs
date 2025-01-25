/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        double[] ratios = new double[trials];
        for (int i = 0; i < trials; i++) {
            double ratio = runTrial();
            ratios[i] = ratio;
        }

        this.mean = StdStats.mean(ratios);
        this.stddev = StdStats.stddev(ratios);
        this.confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);
        this.confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);
    }

    private double runTrial() {
        // return ratio of open sites when percolate
        int[] indexs = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            indexs[i] = i;
        }

        StdRandom.shuffle(indexs);
        int i = 0;

        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int index = indexs[i++];
            int row = index / n + 1;
            int col = index % n + 1;
            p.open(row, col);
        }

        return p.numberOfOpenSites() / (double) (n * n);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, trials);
        System.out.println("mean                    = " + p.mean());
        System.out.println("stddev                  = " + p.stddev());
        System.out.println(
                "95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }
}

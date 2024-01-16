/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONSTANT = 1.96;
    private Percolation perc;
    private int trials;
    private int n;
    private double[] percolationTresholds;


    // perform independent trials on an n-by-n open
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        percolationTresholds = new double[trials];
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationTresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        /*
        double stdDeviation = 0;
        for (double d : percolationTresholds) {
            stdDeviation += Math.pow(d - mean(), 2);
        }
        stdDeviation /= trials - 1;
        stdDeviation = Math.sqrt(stdDeviation);

        return stdDeviation;
         */
        return StdStats.stddev(percolationTresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - (CONSTANT * stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (CONSTANT * stddev()) / Math.sqrt(trials));
    }

    private void openRandomSite() {
        int row = StdRandom.uniformInt(1, n + 1);
        int col = StdRandom.uniformInt(1, n + 1);
        perc.open(row, col);
        // StdOut.println("open : (" + row + ", " + col + ")");
    }

    private void run() {
        for (int i = 1; i <= trials; i++) {
            perc = new Percolation(n);
            while (!perc.percolates()) {
                openRandomSite();
            }
            percolationTresholds[i - 1] = (double) perc.numberOfOpenSites() / (n * n);
            // StdOut.println("percolation status : " + perc.percolates());
        }
    }


    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percs = new PercolationStats(Integer.parseInt(args[0]),
                                                      Integer.parseInt(args[1]));
        percs.run();
        StdOut.println("mean = " + percs.mean());
        StdOut.println("stddev = " + percs.stddev());
        StdOut.println(
                "95% confidence interval = [" + percs.confidenceLo() + ", " + percs.confidenceHi()
                        + "]");
    }


}

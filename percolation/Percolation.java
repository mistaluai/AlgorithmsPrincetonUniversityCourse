import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;
    private int size;
    private boolean[][] open;
    private int numberOfOpenSites;
    private WeightedQuickUnionUF full;
    private WeightedQuickUnionUF perc;
    private int bottom;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.size = n;
        bottom = size * size + 1;
        open = new boolean[size][size];
        full = new WeightedQuickUnionUF(size * size + 1);
        perc = new WeightedQuickUnionUF(size * size + 2);

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            open[row - 1][col - 1] = true;
            numberOfOpenSites++;
        }

        // check if it was at the TOP
        if (row == 1) {
            full.union(position(row, col), TOP);
            perc.union(position(row, col), TOP);
        }
        // check if it was at the bottom
        else if (row == size) {
            perc.union(position(row, col), bottom);
        }

        // check the four basic connections

        if (row > 1 && isOpen(row - 1, col)) {
            full.union(position(row, col), position(row - 1, col));
            perc.union(position(row, col), position(row - 1, col));
            // StdOut.println(
            //         "unioned (" + row + "," + col + ") and (" + (row - 1) + "," + col + ")");
        }
        if (row < size && isOpen(row + 1, col)) {
            full.union(position(row, col), position(row + 1, col));
            perc.union(position(row, col), position(row + 1, col));
            // StdOut.println(
            //        "unioned (" + row + "," + col + ") and (" + (row + 1) + "," + col + ")");
        }
        if (col > 1 && isOpen(row, col - 1)) {
            full.union(position(row, col), position(row, col - 1));
            perc.union(position(row, col), position(row, col - 1));
            // StdOut.println(
            //        "unioned (" + row + "," + col + ") and (" + (row) + "," + (col - 1) + ")");
        }
        if (col < size && isOpen(row, col + 1)) {
            full.union(position(row, col), position(row, col + 1));
            perc.union(position(row, col), position(row, col + 1));
            // StdOut.println(
            //         "unioned (" + row + "," + col + ") and (" + (row) + "," + (col + 1) + ")");
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return open[row - 1][col - 1];
    }

    // is the site (row, col) full? which means does it connects to the TOP or not
    public boolean isFull(int row, int col) {
        return (full.find(position(row, col)) == full.find(0)) && isOpen(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate? which means does it connect to the TOP and the bottom at the same time or not
    public boolean percolates() {
        return perc.find(0) == perc.find(bottom);
    }

    private int position(int row,
                         int col) { // returns the position of the element in the open array
        if (row < 0 || col < 0 || row > size + 1 || col > size + 1)
            throw new IllegalArgumentException("the col:" + col + " and row: " + row);
        return (row - 1) * size + (col - 1);
    }
}

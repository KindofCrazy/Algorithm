import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int n;
    private int numberOfOpenSites = 0;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private boolean [][] grid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.grid = new boolean[n + 1][n + 1];
        this.n = n;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF((int) Math.pow(n + 1, 2) + 1);

        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                grid[i][j] = false;
            }
        }
    }

    private int indexInUF(int row, int col) {
        return row * (n + 1) + col;
    }

    private boolean isValidSite(int row, int col) {
        return row >= 1 && row <= n && col >= 1 && col <= n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isValidSite(row, col)) {
            throw new IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            return;
        }

        grid[row][col] = true;
        numberOfOpenSites++;

        /* A false node represented as 0 connects the first row */
        if (row == 1) {
            weightedQuickUnionUF.union(0, indexInUF(row, col));
        }
        /* A false node represented as 1 connects the last row */
        if (row == n) {
            weightedQuickUnionUF.union(1, indexInUF(row, col));
        }

        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] direction: directions) {
            int neighborRow = row + direction[0];
            int neighborCol = col + direction[1];
            if (isValidSite(neighborRow, neighborCol) && isOpen(neighborRow, neighborCol)) {
                if (weightedQuickUnionUF.find(indexInUF(row, col)) != weightedQuickUnionUF.find(indexInUF(neighborRow, neighborCol))) {
                    weightedQuickUnionUF.union(indexInUF(row, col), indexInUF(neighborRow, neighborCol));
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValidSite(row, col)) {
            throw new IllegalArgumentException();
        }

        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValidSite(row, col)) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            return false;
        }

        return weightedQuickUnionUF.find(0) == weightedQuickUnionUF.find(indexInUF(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(0) == weightedQuickUnionUF.find(1);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
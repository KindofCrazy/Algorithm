import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;

public class Board {

    private final int[][] tiles;
    private final int n;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, n);
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n).append("\n");
        for (int[] row : tiles) {
            for (int tile : row) {
                stringBuilder.append(tile).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }

                if (tiles[i][j] != n * i + j + 1) {
                    count++;
                }
            }
        }

        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }

                if (tiles[i][j] != n * i + j + 1) {
                    int value = tiles[i][j];
                    int goalX = (value - 1) / n;
                    int goalY = value - n * goalX - 1;
                    sum += Math.abs(goalX - i) + Math.abs(goalY - j);
                }
            }
        }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }

                if (tiles[i][j] != n * i + j + 1) {
                    return false;
                }
            }
        }

        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        } else if (y == this) {
            return true;
        } else if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;
        if (this.dimension() != that.dimension()) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isValid(int x, int y) {
        return  x >= 0 && x <= n-1 && y >= 0 && y <= n-1;
    }

    private Board swap(int x, int y, int newX, int newY) {
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, newTiles[i], 0, n);
        }

        int tmp = newTiles[x][y];
        newTiles[x][y] = newTiles[newX][newY];
        newTiles[newX][newY] = tmp;

        return new Board(newTiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int blankX = 0, blankY = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                    break;
                }
            }
        }

        LinkedList<Board> neighbor = new LinkedList<>();
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int[] direction : directions) {
            int newX = blankX + direction[0], newY = blankY + direction[1];
            if (isValid(newX, newY)) {
                neighbor.add(swap(blankX, blankY, newX, newY));
            }
        }

        return neighbor;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        boolean first = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    x1 = i;
                    y1 = j;
                    for (int[] direction : directions) {
                        x2 = x1 + direction[0];
                        y2 = y1 + direction[1];
                        if (isValid(x2, y2) && tiles[x2][y2] != 0) {
                            break;
                        }
                    }
                }
            }
        }

        return swap(x1, y1, x2, y2);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] a = new int[][]{{0, 3}, {1, 2}};
        Board b = new Board(a);
        System.out.println(b.twin());
    }

}
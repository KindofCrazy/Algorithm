import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.LinkedList;

public class Solver {
    private SearchNode solutionNode = null;
    private boolean solvable = false;


    private static class SearchNode {
        private final Board board;
        private final int moves;
        private final SearchNode pre;
        private final int priority;

        public SearchNode(Board board, int moves, SearchNode pre) {
            this.board = board;
            this.moves = moves;
            this.pre = pre;
            this.priority = this.moves + board.manhattan();
        }
    }

    private static class SearchNodeComparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return o1.priority - o2.priority;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("argument is null");
        }

        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        SearchNode initialNode = new SearchNode(initial, 0, null);
        pq.insert(initialNode);

        MinPQ<SearchNode> pqTwin = new MinPQ<>(new SearchNodeComparator());
        SearchNode initialTwinNode = new SearchNode(initial.twin(), 0, null);
        pqTwin.insert(initialTwinNode);

        MinPQ<SearchNode> executor = pq;
        SearchNode LastNode = null;
        while (!pq.isEmpty()) {
            SearchNode node = executor.delMin();
            if (node.board.isGoal()) {
                LastNode = node;
                break;
            }

            for (Board neighborBoard : node.board.neighbors()) {
                if (!(node.pre == null) && neighborBoard.equals(node.pre.board)) {
                    continue;
                }
                SearchNode neighborNode = new SearchNode(neighborBoard, node.moves + 1, node);
                executor.insert(neighborNode);
            }

            executor = (executor == pq ? pqTwin : pq);
        }

        solvable = (executor == pq);
        solutionNode = LastNode;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return solutionNode.moves;
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        LinkedList<Board> solution = new LinkedList<>();
        SearchNode p = solutionNode;
        while (p != null) {
            solution.addFirst(p.board);
            p = p.pre;
        }

        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In("puzzle04.txt");
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
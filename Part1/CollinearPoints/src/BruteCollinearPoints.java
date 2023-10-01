import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In; 

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> segments = new LinkedList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }


        Point[] tmp = Arrays.copyOf(points, points.length);
        Arrays.sort(tmp);
        for (int p = 0; p < tmp.length; p++) {
            for (int q = p + 1; q < tmp.length; q++) {
                for (int r = q + 1; r < tmp.length; r++) {
                    for (int s = r + 1; s < tmp.length; s++) {
                        double pqSlope = tmp[p].slopeTo(tmp[q]);
                        double prSlope = tmp[p].slopeTo(tmp[r]);
                        double psSlope = tmp[p].slopeTo(tmp[s]);

                        if (pqSlope == prSlope && prSlope == psSlope) {
                            segments.add(new LineSegment(tmp[p], tmp[s]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[numberOfSegments()];

        for (int i = 0; i < result.length; i++) {
            result[i] = segments.get(i);
        }

        return result;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("input6.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

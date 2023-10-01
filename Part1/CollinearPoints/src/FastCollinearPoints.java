import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int number = 0;

    public FastCollinearPoints(Point[] points) {
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

        segments = new LineSegment[points.length * points.length];

        Point[] tmp = Arrays.copyOf(points, points.length);
        for (Point p : points) {
            Arrays.sort(tmp, p.slopeOrder());
            for (int i = 1; i < tmp.length;) {
                int endIndex = i;
                Point highEnd = tmp[i];
                Point lowEnd = p;
                if (tmp[i].compareTo(p) < 0) {
                    highEnd = p;
                    lowEnd = tmp[i];
                }

                double slope = p.slopeTo(tmp[i]);
                while (endIndex + 1 < tmp.length && p.slopeTo(tmp[endIndex + 1]) == slope) {
                    endIndex++;
                    if (tmp[endIndex].compareTo(highEnd) > 0) {
                        highEnd = tmp[endIndex];
                    }
                    if (tmp[endIndex].compareTo(lowEnd) < 0) {
                        lowEnd = tmp[endIndex];
                    }
                }

                if (endIndex - i >= 2 && lowEnd == p) {
                    if (number > segments.length / 2) {
                        resize();
                    }
                    segments[number] = new LineSegment(lowEnd, highEnd);
                    number++;
                }

                i = endIndex + 1;
            }
        }
    }


    private void resize() {
        LineSegment[] newArray = new LineSegment[segments.length * 2];
        for (int i = 0; i < segments.length; i++) {
            newArray[i] = segments[i];
        }
        segments = newArray;
    }

    public int numberOfSegments() {
        return number;
    }

    public LineSegment[] segments() {
        LineSegment[] returnArray = new LineSegment[number];
        for (int i = 0; i < number; i++) {
            returnArray[i] = segments[i];
        }
        return returnArray;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("input8.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}

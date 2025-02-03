/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("argument to constructor is null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("some point is null");
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("repeated point");
                }
            }
        }

        Point[] tmp = Arrays.copyOf(points, points.length);
        if (tmp.length < 4) {
            return;
        }
        Arrays.sort(tmp);

        Point[] tmp2 = Arrays.copyOf(tmp, tmp.length);

        for (int m = 0; m < tmp.length; m++) {
            Point p = tmp[m];
            Arrays.sort(tmp2, p.slopeOrder());

            for (int i = 1; i < tmp2.length - 2; i++) {
                double s1 = p.slopeTo(tmp2[i]);
                double s2 = p.slopeTo(tmp2[i + 1]);
                double s3 = p.slopeTo(tmp2[i + 2]);
                if (s1 == s2 && s1 == s3) {
                    Point[] candidate = { p, tmp2[i], tmp2[i + 1], tmp2[i + 2] };
                    Arrays.sort(candidate);
                    if (p.compareTo(candidate[0]) == 0) {
                        LineSegment line = new LineSegment(p, candidate[3]);
                        segments.add(line);
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        int len = numberOfSegments();
        int i = 0;
        LineSegment[] segments = new LineSegment[len];
        for (LineSegment segment : this.segments) {
            segments[i++] = segment;
        }
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
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

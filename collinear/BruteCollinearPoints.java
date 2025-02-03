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

public class BruteCollinearPoints {
    private List<LineSegment> collinearSegments;

    public BruteCollinearPoints(Point[] points) {
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
        Arrays.sort(tmp);

        if (tmp.length < 4) {
            return;
        }

        collinearSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < tmp.length; i++) {
            for (int j = i + 1; j < tmp.length; j++) {
                for (int k = j + 1; k < tmp.length; k++) {
                    for (int l = k + 1; l < tmp.length; l++) {
                        if (isCollinear(tmp[i], tmp[j], tmp[k], tmp[l])) {
                            LineSegment line = new LineSegment(tmp[i], tmp[l]);
                            collinearSegments.add(line);
                        }
                    }
                }
            }
        }
    }

    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[numberOfSegments()];
        int i = 0;
        for (LineSegment segment : collinearSegments) {
            res[i++] = segment;
        }
        return res;
    }

    public int numberOfSegments() {
        return collinearSegments.size();
    }

    private boolean isCollinear(Point a, Point b, Point c, Point d) {
        double s1 = a.slopeTo(b);
        double s2 = a.slopeTo(c);
        double s3 = a.slopeTo(d);
        return s1 == s2 && s2 == s3;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

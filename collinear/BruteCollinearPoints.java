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
    private Point[] points;
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
            this.points = points.clone();
        }
        Arrays.sort(this.points);

        collinearSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if (isCollinear(points[i], points[j], points[k], points[l])) {
                            LineSegment line = new LineSegment(points[i], points[l]);
                            collinearSegments.add(line);
                        }
                    }
                }
            }
        }
    }

    public LineSegment[] segments() {
        int n = collinearSegments.size();
        LineSegment[] res = new LineSegment[n];
        for (int i = 0; i < n; i++) {
            res[i] = collinearSegments.get(i);
        }
        return res;
    }

    public int numberOfSegments() {
        return collinearSegments.size();
    }

    private boolean isCollinear(Point a, Point b, Point c, Point d) {
        return a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(c) == a.slopeTo(d);
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

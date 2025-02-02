/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    List<LineSegment> segments = new ArrayList<>();

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
        Arrays.sort(tmp);

        for (Point p : tmp) {
            Arrays.sort(tmp, p.slopeOrder());

            for (int i = 0; i < tmp.length - 2; i++) {
                double s1 = p.slopeTo(tmp[i]);
                double s2 = p.slopeTo(tmp[i + 1]);
                double s3 = p.slopeTo(tmp[i + 2]);
                if (s1 == s2 && s1 == s3) {
                    LineSegment line = new LineSegment(p, tmp[i + 2]);
                    segments.add(line);
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
        for (LineSegment segment : segments) {
            segments[i++] = segment;
        }
        return segments;
    }

    public static void main(String[] args) {

    }
}

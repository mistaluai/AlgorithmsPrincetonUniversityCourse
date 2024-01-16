/* *****************************************************************************
examines 4 points at a time and checks whether they all lie on the same line segment,
returning all such line segments.
To check whether the 4 points p, q, r, and s are collinear,
check whether the three slopes between
p and q, between p and r, and between p and s are all equal.

//////
Notes::
the extra space isn't n line segments, it is much more...
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private Point pPoint, qPoint, rPoint, sPoint;
    private Point[] points;
    private SegmentsArray lineSegments;

    private class SegmentsArray {
        LineSegment[] array;
        int index;
        int capacity;
        // Point[][] lines;


        SegmentsArray(int initialCapacity) {
            array = new LineSegment[initialCapacity];
            // lines = new Point[initialCapacity][2];
            capacity = initialCapacity;
            index = 0;
        }

        int numberOfSegments() {
            return index;
        }

        LineSegment[] segmentsArray() {
            return Arrays.copyOfRange(array, 0, index);
        }

        private void resizeArray() {
            LineSegment[] oldArray = array;
            // Point[][] oldLines = lines;
            array = new LineSegment[capacity * 2];
            // lines = new Point[capacity * 2][2];

            for (int i = 0; i < capacity; i++)
                array[i] = oldArray[i];

            /*
            for (int i = 0; i < capacity; i++) {
                lines[i][0] = oldLines[i][0];
                lines[i][1] = oldLines[i][1];
            }
             */

            capacity *= 2;

        }


        void add(LineSegment ls) {
            if (index < capacity) {
                array[index] = ls;
                index++;
            }
            else {
                resizeArray();
                add(ls);
            }
        }

        void add(Point p, Point s) {

            LineSegment ls = new LineSegment(p, s);
            //   lines[index][0] = p;
            //   lines[index][1] = s;
            if (index < capacity) {
                array[index] = ls;
                index++;
            }
            else {
                resizeArray();
                add(ls);
            }
        }

    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("The constructor argument is null");

        for (Point point : points)
            if (point == null)
                throw new IllegalArgumentException("One point is null");
        checkForDuplicates(points);

        this.points = Arrays.copyOf(points, points.length);
        lineSegments = new SegmentsArray(points.length * 4);
        bruteforce();
    }

    private void checkForDuplicates(Point[] pointArray) {
        Point[] arr = Arrays.copyOf(pointArray, pointArray.length);
        MergeX.sort(arr);
        for (int i = 1; i < pointArray.length; i++)
            if (pointArray[i - 1].compareTo(pointArray[i]) == 0)
                throw new IllegalArgumentException();
    }

    private void bruteforce() {
        MergeX.sort(points);
        for (int p = 0; p < points.length; p++) {
            pPoint = points[p];
            for (int q = p + 1; q < points.length; q++) {
                if (p == q) // skips if they're the same index
                    continue;
                qPoint = points[q];
                for (int r = q + 1; r < points.length; r++) {
                    if (p == r || q == r) // skips if they're the same index
                        continue;
                    rPoint = points[r];
                    for (int s = r + 1; s < points.length; s++) {
                        if (p == s || q == s || r == s) // skips if they're the same index
                            continue;
                        sPoint = points[s];

                        if (comparePoints(pPoint, qPoint, rPoint, sPoint)) {
                            lineSegments.add(pPoint, sPoint);
                        }

                    }
                }
            }
        }
    }

    private boolean comparePoints(Point p, Point q, Point r, Point s) {
        // throws exception if any two points are equal
        if (p.compareTo(q) == 0 || p.compareTo(r) == 0 || p.compareTo(s) == 0)
            throw new IllegalArgumentException("The constructor contains repeated points");
        if (q.compareTo(r) == 0 || q.compareTo(s) == 0)
            throw new IllegalArgumentException("The constructor contains repeated points");
        if (r.compareTo(s) == 0)
            throw new IllegalArgumentException("The constructor contains repeated points");

        // StdOut.println("Comparing : " + p + " " + q + " " + r + " " + s);
        // checks whether the three slopes between p and q, between p and r, and between p and s are all equal.
        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s))
            return true;

        return false;

    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.numberOfSegments();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.segmentsArray();
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

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private Point originP, qPoint;
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

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("The constructor argument is null");

        for (Point point : points)
            if (point == null)
                throw new IllegalArgumentException("One point is null");
        checkForDuplicates(points);

        this.points = Arrays.copyOf(points, points.length);
        lineSegments = new SegmentsArray(points.length * 4);
        originP = points[0];
        fastCollinear();
    }

    private void checkForDuplicates(Point[] pointArray) {
        Point[] arr = Arrays.copyOf(pointArray, pointArray.length);
        MergeX.sort(arr);
        for (int i = 1; i < pointArray.length; i++)
            if (pointArray[i - 1].compareTo(pointArray[i]) == 0)
                throw new IllegalArgumentException();
    }

    private void fastCollinear() {
        MergeX.sort(points);
        for (int i = 0; i < points.length; i++) {
            Point[] copyOfPoints = Arrays.copyOf(points, points.length);
            originP = copyOfPoints[i];

            copyOfPoints = Arrays.copyOfRange(copyOfPoints, i + 1, copyOfPoints.length);
            MergeX.sort(copyOfPoints, originP.slopeOrder());

            /* Arrays.sort(copyOfPoints, new Comparator<Point>() {
                public int compare(Point o1, Point o2) {
                    if (originP.slopeTo(o1) < originP.slopeTo(o2))
                        return -1;
                    else if (originP.slopeTo(o1) > originP.slopeTo(o2))
                        return 1;
                    return 0;
                }
            });*/
            int numberOfPoints = 2;
            for (int j = 1; j < copyOfPoints.length; j++) {
                if (originP.slopeTo(copyOfPoints[j]) == originP.slopeTo(copyOfPoints[j - 1])) {
                    numberOfPoints++;
                }
                else {
                    if (numberOfPoints >= 4)
                        lineSegments.add(originP, copyOfPoints[j - 1]);
                    numberOfPoints = 2;
                }
            }
            if (numberOfPoints >= 4)
                lineSegments.add(originP, copyOfPoints[copyOfPoints.length - 1]);
        }


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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

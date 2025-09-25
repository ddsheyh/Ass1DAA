package geometry;

import metrics.ComplexityMetrics;
import util.Point;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public static double findClosestPair(Point[] points, ComplexityMetrics metrics) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("at least 2 points required");
        }

        Point[] pointsSortedByX = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSortedByX, Comparator.comparingDouble(p -> p.x));
        metrics.allocations += points.length;
        metrics.comparisons += points.length * (int) Math.log(points.length);

        Point[] pointsSortedByY = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSortedByY, Comparator.comparingDouble(p -> p.y));
        metrics.allocations += points.length;
        metrics.comparisons += points.length * (int) Math.log(points.length);

        return closestPair(pointsSortedByX, pointsSortedByY, 0, points.length - 1, metrics);
    }

    private static double closestPair(Point[] pointsX, Point[] pointsY, int left, int right, ComplexityMetrics metrics) {
        metrics.enterRecursion();

        int n = right - left + 1;

        if (n <= 3) {
            double minDist = bruteForce(pointsX, left, right, metrics);
            metrics.exitRecursion();
            return minDist;
        }

        int mid = left + (right - left) / 2;
        Point midPoint = pointsX[mid];

        Point[] leftPointsY = new Point[mid - left + 1];
        Point[] rightPointsY = new Point[right - mid];
        metrics.allocations += leftPointsY.length + rightPointsY.length;

        int leftIdx = 0, rightIdx = 0;
        for (Point p : pointsY) {
            metrics.comparisons++;
            if (p.x <= midPoint.x) {
                leftPointsY[leftIdx++] = p;
            } else {
                rightPointsY[rightIdx++] = p;
            }
        }

        double leftDist = closestPair(pointsX, leftPointsY, left, mid, metrics);
        double rightDist = closestPair(pointsX, rightPointsY, mid+1, right, metrics);
        double minDist = Math.min(leftDist, rightDist);

        Point[] strip = new Point[n];
        metrics.allocations += n;
        int stripSize = 0;

        for (Point p : pointsY) {
            metrics.comparisons++;
            if (Math.abs(p.x - midPoint.x) < minDist) {
                strip[stripSize++] = p;
            }
        }

        double stripDist = stripClosest(strip, stripSize, minDist, metrics);
        minDist = Math.min(minDist, stripDist);

        metrics.exitRecursion();
        return minDist;
    }

    private static double bruteForce(Point[] points, int left, int right, ComplexityMetrics metrics) {
        double minDist = Double.MAX_VALUE;

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.comparisons++;
                double dist = points[i].distanceTo(points[j]);
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }
        return minDist;
    }

    private static double stripClosest(Point[] strip, int size, double minDist, ComplexityMetrics metrics) {
        double min = minDist;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; j++) {
                metrics.comparisons++;
                double dist = strip[i].distanceTo(strip[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }
}

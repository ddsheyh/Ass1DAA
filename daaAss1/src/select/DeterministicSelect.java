package select;

import metrics.ComplexityMetrics;
import util.ArrayUtils;

public class DeterministicSelect {
    public static int select(int[] array, int k, ComplexityMetrics metrics) {
        if (array == null || k < 0 || k > array.length) {
            throw new IllegalArgumentException("invalid input");
        }

        return select(array, 0, array.length - 1, k, metrics);
    }

    private static int select(int[] array, int left, int right, int k, ComplexityMetrics metrics) {
        metrics.enterRecursion();

        if (left == right) {
            metrics.exitRecursion();
            return array[left];
        }

        int pivotIndex = pivot(array, left, right, metrics);
        pivotIndex = partition(array, left, right, pivotIndex, metrics);

        if (k==pivotIndex) {
            metrics.exitRecursion();
            return array[k];
        } else if (k < pivotIndex) {
            int result = select(array, left, pivotIndex - 1, k, metrics);
            metrics.exitRecursion();
            return result;
        } else {
            int result = select(array, pivotIndex + 1, right, k, metrics);
            metrics.exitRecursion();
            return result;
        }
    }

    private static int pivot(int[] array, int left, int right, ComplexityMetrics metrics) {
        if (right - left < 5) {
            return medianOfSmallArray(array, left, right, metrics);
        }

        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            int medianPos = medianOfSmallArray(array, i, subRight, metrics);

            ArrayUtils.swap(array, medianPos, left + (i-left) / 5);
        }

        int mid = (right - left) / 10 + left + 1;
        return select(array, left, left + (right - left) / 5, mid, metrics);
    }

    private static int medianOfSmallArray(int[] array, int left, int right, ComplexityMetrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= left && array[j] > key) {
                metrics.comparisons++;
                array[j + 1] = array[j];
                j--;
            }

            metrics.comparisons++;

            array[j + 1] = key;
        }
        return left + (right - left) / 2;
    }

    private static int partition(int[] array, int left, int right, int pivotIndex, ComplexityMetrics metrics) {
        int pivotValue = array[pivotIndex];
        ArrayUtils.swap(array, pivotIndex, right);

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            metrics.comparisons++;
            if (array[i] < pivotValue) {
                ArrayUtils.swap(array, storeIndex, i);
                storeIndex++;
            }
        }
        ArrayUtils.swap(array, right, storeIndex);
        return storeIndex;
    }
}

package sort;

import metrics.ComplexityMetrics;
import util.ArrayUtils;

public class MergeSort {
    public static void sort(int[] array, ComplexityMetrics metrics) {
        if (array == null || array.length == 1) return;

        int [] buffer = new int [array.length];
        metrics.allocations += array.length;

        mergeSort(array, 0, array.length - 1, buffer, metrics);
    }

    private static void mergeSort(int[] array, int left, int right, int[] buffer, ComplexityMetrics metrics) {
        metrics.enterRecursion();

        if (right - left <= 15) {
            insertionSort(array, left, right, metrics);
            metrics.exitRecursion();
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(array, left, mid, buffer, metrics);
        mergeSort(array, mid +1, right, buffer, metrics);

        merge(array, left, mid, right, buffer, metrics);

        metrics.exitRecursion();
    }

    private static void merge(int[] array, int left, int mid, int right, int[] buffer, ComplexityMetrics metrics) {
        System.arraycopy(array, left, buffer, left, right - left + 1);
        metrics.comparisons += 3;

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            metrics.comparisons++;
            if (buffer[i] < buffer[j]) {
                array[k++] = buffer[i++];
            } else {
                array[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            array[k++] = buffer[i++];
        }
    }

    private static void insertionSort(int[] array, int left, int right, ComplexityMetrics metrics) {
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
    }
}

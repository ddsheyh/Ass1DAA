package sort;

import metrics.ComplexityMetrics;
import util.ArrayUtils;

public class QuickSort {
    public static void sort(int[] array, ComplexityMetrics metrics) {
        if (array == null || array.length <= 1) return;
        ArrayUtils.shuffle(array);
        quickSort(array, 0, array.length - 1, metrics);
    }

    private static void quickSort(int[] array, int low, int high, ComplexityMetrics metrics) {
        metrics.enterRecursion();

        while (low < high) {
            int pivotIndex = partition(array, low, high, metrics);

            if (pivotIndex - low < high - pivotIndex) {
                quickSort(array, low, pivotIndex - 1, metrics);
                low = pivotIndex + 1;
            } else {
                quickSort(array, pivotIndex + 1, high, metrics);
                high = pivotIndex - 1;
            }
        }

        metrics.exitRecursion();
    }

    private static int partition(int[] array, int low, int high, ComplexityMetrics metrics) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            metrics.comparisons++;
            if (array[j] <= pivot) {
                i++;
                ArrayUtils.swap(array, i, j);
            }
        }

        ArrayUtils.swap(array, i + 1, high);
        return i + 1;
    }
}

import geometry.ClosestPair;
import metrics.ComplexityMetrics;
import select.DeterministicSelect;
import sort.MergeSort;
import sort.QuickSort;
import util.ArrayUtils;
import util.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            interactiveMode();
        } else {
            commandLineMode(args);
        }
    }

    private static void interactiveMode() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n    Divide-and-Conquer Algorithms    ");
            System.out.println("1. MergeSort");
            System.out.println("2. QuickSort");
            System.out.println("3. Deterministic Select");
            System.out.println("4. Closest Pair of Points");
            System.out.println("5. Run All Benchmarks");
            System.out.println("0. Exit");
            System.out.print("Choose an option (0-5): ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear buffer
                continue;
            }

            if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }

            if (choice < 1 || choice > 5) {
                System.out.println("Please choose between 0 and 5");
                continue;
            }

            System.out.print("Enter array size: ");
            int size = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        runAlgorithm("mergesort", size, size/2);
                        break;
                    case 2:
                        runAlgorithm("quicksort", size, size/2);
                        break;
                    case 3:
                        System.out.print("Enter k (0 to " + (size-1) + "): ");
                        int k = scanner.nextInt();
                        runAlgorithm("select", size, k);
                        break;
                    case 4:
                        runAlgorithm("closestpair", size, size/2);
                        break;
                    case 5:
                        runAllBenchmarks(size);
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
    }

    private static void commandLineMode(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        String algorithm = args[0];
        int size = Integer.parseInt(args[1]);
        int k = args.length > 2 ? Integer.parseInt(args[2]) : size / 2;

        runAlgorithm(algorithm, size, k);
    }

    private static void runAlgorithm(String algorithm, int size, int k) {
        ComplexityMetrics metrics = new ComplexityMetrics();

        try {
            System.out.println("\n    Running " + algorithm + " with size " + size + "    ");
            metrics.startTimer();

            switch (algorithm.toLowerCase()) {
                case "mergesort":
                    testMergeSort(size, metrics);
                    break;
                case "quicksort":
                    testQuickSort(size, metrics);
                    break;
                case "select":
                    testSelect(size, k, metrics);
                    break;
                case "closestpair":
                    testClosestPair(size, metrics);
                    break;
                case "all":
                    runAllBenchmarks(size);
                    break;
                default:
                    System.out.println("Unknown algorithm: " + algorithm);
                    printUsage();
                    return;
            }

            metrics.stopTimer();
            outputResults(algorithm, size, metrics);

        } catch (Exception e) {
            System.err.println("Error running " + algorithm + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runAllBenchmarks(int size) {
        System.out.println("Running all benchmarks with size: " + size);

        String[] algorithms = {"mergesort", "quicksort", "select", "closestpair"};
        for (String algo : algorithms) {
            ComplexityMetrics metrics = new ComplexityMetrics();
            metrics.startTimer();

            try {
                switch (algo) {
                    case "mergesort":
                        testMergeSort(size, metrics);
                        break;
                    case "quicksort":
                        testQuickSort(size, metrics);
                        break;
                    case "select":
                        testSelect(size, size / 2, metrics);
                        break;
                    case "closestpair":
                        testClosestPair(Math.min(size, 10000), metrics);
                        break;
                }

                metrics.stopTimer();
                outputResults(algo, size, metrics);

            } catch (Exception e) {
                System.err.println("Error running " + algo + ": " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void testMergeSort(int size, ComplexityMetrics metrics) {
        System.out.println("Testing MergeSort with size: " + size);
        int[] array = ArrayUtils.generateRandomArray(size);
        int[] original = ArrayUtils.copyArray(array);

        MergeSort.sort(array, metrics);

        if (!ArrayUtils.isSorted(array)) {
            System.err.println("MergeSort failed to sort the array");
            ArrayUtils.printArray(original);
            ArrayUtils.printArray(array);
        } else {
            System.out.println("MergeSort completed successfully");
        }
    }

    private static void testQuickSort(int size, ComplexityMetrics metrics) {
        System.out.println("Testing QuickSort with size: " + size);
        int[] array = ArrayUtils.generateRandomArray(size);
        int[] original = ArrayUtils.copyArray(array);

        QuickSort.sort(array, metrics);

        if (!ArrayUtils.isSorted(array)) {
            System.err.println("QuickSort failed to sort the array");
        } else {
            System.out.println("QuickSort completed successfully");
        }
    }

    private static void testSelect(int size, int k, ComplexityMetrics metrics) {
        System.out.println("Testing DeterministicSelect with size: " + size + ", k: " + k);
        int[] array = ArrayUtils.generateRandomArray(size);
        int[] copy = ArrayUtils.copyArray(array);

        int result = DeterministicSelect.select(array, k, metrics);

        Arrays.sort(copy);
        int expected = copy[k];

        if (result != expected) {
            System.err.printf("Select failed: expected %d, got %d%n", expected, result);
        } else {
            System.out.println("Select completed successfully. Result: " + result);
        }
    }

    private static void testClosestPair(int size, ComplexityMetrics metrics) {
        System.out.println("Testing ClosestPair with size: " + size);
        Random random = new Random();
        Point[] points = new Point[size];

        for (int i = 0; i < size; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }

        double distance = ClosestPair.findClosestPair(points, metrics);
        System.out.printf("Closest pair distance: %.4f%n", distance);
    }

    private static void outputResults(String algorithm, int size, ComplexityMetrics metrics) {
        System.out.printf("Results: %s, Size: %d, Comparisons: %d, Recursions: %d, MaxDepth: %d, Time: %d ns%n",
                algorithm, size, metrics.comparisons, metrics.recursions,
                metrics.getMaxDepth(), metrics.getElapsedTime());

        try (FileWriter writer = new FileWriter("results.csv", true)) {
            writer.write(String.format("%s,%d,%d,%d,%d,%d%n",
                    algorithm, size, metrics.comparisons, metrics.recursions,
                    metrics.getMaxDepth(), metrics.getElapsedTime()));
            System.out.println("Results written to results.csv");
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }

        System.out.println("    Metrics Summary    ");
        System.out.println("Algorithm: " + algorithm);
        System.out.println("Input size: " + size);
        System.out.println("Comparisons: " + metrics.comparisons);
        System.out.println("Recursive calls: " + metrics.recursions);
        System.out.println("Max recursion depth: " + metrics.getMaxDepth());
        System.out.println("Memory allocations: " + metrics.allocations);
        System.out.println("Time elapsed: " + metrics.getElapsedTime() + " ns");
        System.out.println("Time elapsed: " + (metrics.getElapsedTime() / 1_000_000.0) + " ms");
    }

    private static void printUsage() {
        System.out.println("Usage: java Main <algorithm> <size> [k for select]");
        System.out.println("Algorithms: mergesort, quicksort, select, closestpair, all");
        System.out.println("Examples:");
        System.out.println("  java Main mergesort 10000");
        System.out.println("  java Main quicksort 10000");
        System.out.println("  java Main select 10000 5000");
        System.out.println("  java Main closestpair 1000");
        System.out.println("  java Main all 1000");
        System.out.println("\nOr run without arguments for interactive mode");
    }
}
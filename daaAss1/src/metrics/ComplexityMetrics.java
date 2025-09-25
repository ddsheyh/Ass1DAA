package metrics;

public class ComplexityMetrics {
    public int comparisons;
    public int recursions;
    public int allocations;
    public long startTime;
    public long endTime;

    private DepthTracker depthTracker;

    public ComplexityMetrics() {
        this.depthTracker = new DepthTracker();
        reset();
    }

    public void startTimer() {
        this.startTime = System.nanoTime();
    }

    public void stopTimer() {
        this.endTime = System.nanoTime();
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }

    public void enterRecursion() {
        depthTracker.enter();
        recursions++;
    }

    public void exitRecursion() {
        depthTracker.exit();
    }

    public int getCurrentDepth() {
        return depthTracker.getCurrentDepth();
    }

    public int getMaxDepth() {
        return depthTracker.getMaxDepth();
    }

    public void reset() {
        comparisons = 0;
        recursions = 0;
        allocations = 0;
        startTime = 0;
        endTime = 0;
        depthTracker.reset();
    }

    @Override
    public String toString() {
        return String.format(
                "ComplexityMetrics{comparisons=%d, recursions=%d, allocations=%d, maxDepth=%d, time=%d ns",
                comparisons, recursions, allocations, getMaxDepth(), getElapsedTime()
        );
    }
}

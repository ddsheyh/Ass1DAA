package metrics;

public class DepthTracker {
    private int currentDepth;
    private int maxDepth;

    public DepthTracker() {
        this.currentDepth = 0;
        this.maxDepth = 0;
    }

    public void enter() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exit() {
        if (currentDepth > 0) {
            currentDepth--;
        }
    }

    public int getCurrentDepth() {
        return currentDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void reset() {
        currentDepth = 0;
        maxDepth = 0;
    }

    @Override
    public String toString() {
        return String.format("DepthTracker{current=%d, max=%d}", currentDepth, maxDepth);
    }
}

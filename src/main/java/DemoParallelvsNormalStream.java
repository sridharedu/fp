import java.util.LongSummaryStatistics;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.time.Duration;
import java.time.Instant;

/**
 * This class demonstrates and compares the performance difference between
 * sequential and parallel streams in Java.
 */
public class DemoParallelvsNormalStream {
    // Configuration constants
    private static final int UPPER_LIMIT = 1_000_000;  // Size of the data to process
    private static final int WARM_UP_RUNS = 3;         // Number of warm-up iterations
    private static final int MEASUREMENT_RUNS = 5;     // Number of actual measurement iterations
    private static final int COMPUTATION_INTENSITY = 1000; // Controls how intensive each computation is

    public static void main(String[] args) {
        // First, warm up the JVM to get more accurate measurements
        performWarmup();

        // Then perform the actual measurements
        performMeasurements();
    }

    /**
     * Performs warm-up runs to ensure JVM optimization before actual measurements
     */
    private static void performWarmup() {
        System.out.println("Warming up JVM to ensure accurate measurements...");
        for (int i = 0; i < WARM_UP_RUNS; i++) {
            System.out.printf("Warm-up iteration %d of %d%n", i + 1, WARM_UP_RUNS);
            processSequential();
            processParallel();
        }
    }

    /**
     * Performs actual measurements for both sequential and parallel processing
     */
    private static void performMeasurements() {
        System.out.println("\nStarting performance measurements...");

        // Measure sequential stream performance
        System.out.println("\nMeasuring sequential stream performance:");
        printStatistics("Sequential Stream",
                () -> measureExecution(DemoParallelvsNormalStream::processSequential));

        // Measure parallel stream performance
        System.out.println("\nMeasuring parallel stream performance:");
        printStatistics("Parallel Stream",
                () -> measureExecution(DemoParallelvsNormalStream::processParallel));
    }

    /**
     * Prints statistical information about the execution times
     * @param label Description of what's being measured
     * @param measurement Supplier that provides execution time measurements
     */
    private static void printStatistics(String label, Supplier<Long> measurement) {
        // Collect statistics over multiple runs
        LongSummaryStatistics stats = collectExecutionStatistics(measurement);

        // Print detailed statistics
        System.out.println("----------------------------------------");
        System.out.printf("Statistics for: %s%n", label);
        System.out.printf("Number of measurements: %d%n", MEASUREMENT_RUNS);
        System.out.printf("Average execution time: %.2f ms%n", stats.getAverage());
        System.out.printf("Minimum execution time: %d ms%n", stats.getMin());
        System.out.printf("Maximum execution time: %d ms%n", stats.getMax());
        System.out.printf("Standard deviation: %.2f ms%n", calculateStdDev(stats));
        System.out.println("----------------------------------------");
    }

    /**
     * Collects execution statistics over multiple runs
     */
    private static LongSummaryStatistics collectExecutionStatistics(Supplier<Long> measurement) {
        return IntStream.range(0, MEASUREMENT_RUNS)
                .mapToLong(i -> {
                    long executionTime = measurement.get();
                    System.out.printf("Run %d: %d ms%n", i + 1, executionTime);
                    return executionTime;
                })
                .summaryStatistics();
    }

    /**
     * Processes data using a sequential stream
     */
    private static long processSequential() {
        return IntStream.range(0, UPPER_LIMIT)
                .map(DemoParallelvsNormalStream::computeIntensive)
                .sum();
    }

    /**
     * Processes data using a parallel stream
     */
    private static long processParallel() {
        return IntStream.range(0, UPPER_LIMIT)
                .parallel()
                .map(DemoParallelvsNormalStream::computeIntensive)
                .sum();
    }

    /**
     * Simulates a CPU-intensive computation
     * @param number Input number for computation
     * @return Result of the computation
     */
    private static int computeIntensive(int number) {
        return IntStream.range(0, COMPUTATION_INTENSITY)
                .map(i -> {
                    // Perform a calculation that can't be easily optimized away
                    return (number + i) * i;
                })
                .sum();
    }

    /**
     * Measures the execution time of a given task
     * @param task The task to measure
     * @return Execution time in milliseconds
     */
    private static long measureExecution(Runnable task) {
        Instant startTime = Instant.now();
        task.run();
        Instant endTime = Instant.now();
        return Duration.between(startTime, endTime).toMillis();
    }

    /**
     * Calculates the standard deviation of execution times
     * @param stats Summary statistics of execution times
     * @return Standard deviation in milliseconds
     */
    private static double calculateStdDev(LongSummaryStatistics stats) {
        double mean = stats.getAverage();
        double sumSquaredDiff = IntStream.range(0, MEASUREMENT_RUNS)
                .mapToDouble(i -> Math.pow(stats.getAverage() - mean, 2))
                .sum();
        return Math.sqrt(sumSquaredDiff / MEASUREMENT_RUNS);
    }
}

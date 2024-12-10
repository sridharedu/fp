import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindPrimeNumbers {
        private static final int DEFAULT_UPPER_LIMIT = 100;

        public static void main(String[] args) {
            printPrimeNumbers(DEFAULT_UPPER_LIMIT);
        }

        public static void printPrimeNumbers(int upperLimit) {
            validateInput(upperLimit);
            IntStream.rangeClosed(2, upperLimit)
                    .filter(FindPrimeNumbers::isPrime)
                    .forEach(System.out::println);
        }

        public static List<Integer> findPrimeNumbers(int upperLimit) {
            validateInput(upperLimit);
            return IntStream.rangeClosed(2, upperLimit)
                    .filter(FindPrimeNumbers::isPrime)
                    .boxed()
                    .collect(Collectors.toList());
        }

        private static boolean isPrime(int number) {
            if (number <= 1) {
                return false;
            }
            if (number == 2) {
                return true;
            }
            if (number % 2 == 0) {
                return false;
            }

            return IntStream.rangeClosed(3, (int) Math.sqrt(number))
                    .filter(n -> n % 2 != 0)  // only check odd numbers
                    .noneMatch(divisor -> number % divisor == 0);
        }

        private static void validateInput(int upperLimit) {
            if (upperLimit < 2) {
                throw new IllegalArgumentException("Upper limit must be at least 2");
            }
        }

        // Optional: Parallel processing version for large numbers
        public static List<Integer> findPrimeNumbersParallel(int upperLimit) {
            validateInput(upperLimit);
            return IntStream.rangeClosed(2, upperLimit)
                    .parallel()
                    .filter(FindPrimeNumbers::isPrime)
                    .boxed()
                    .collect(Collectors.toList());
        }

        // Optional: Method to print with statistics
        public static void printPrimeNumbersWithStats(int upperLimit) {
            validateInput(upperLimit);
            List<Integer> primes = findPrimeNumbers(upperLimit);

            System.out.printf("Found %d prime numbers up to %d:%n", primes.size(), upperLimit);
            primes.forEach(prime -> System.out.printf("%d ", prime));
            System.out.println();

            // Calculate some statistics using streams
            OptionalDouble average = primes.stream()
                    .mapToInt(Integer::intValue)
                    .average();

            average.ifPresent(avg ->
                    System.out.printf("Average value of prime numbers: %.2f%n", avg));
        }
    }



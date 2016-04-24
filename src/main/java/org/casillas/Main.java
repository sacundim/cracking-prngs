package org.casillas;

import java.util.Random;

/**
 * A simple program demonstrating how easy it is to predict the output of {@link java.util.Random}.  Based on the
 * code here, but refactored a bit, and updated to Java 8:
 *
 * <ul>
 *     <li>https://jazzy.id.au/2010/09/20/cracking_random_number_generators_part_1.html</li>
 * </ul>
 */
public class Main {
    // These three values come from `java.util.Random`.  Note that these changed between Java 6 and 7.
    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;

    public static void main(String[] args) {
        Random target = new Random();
        int v1 = target.nextInt();
        int v2 = target.nextInt();
        long seed = guessSeed(v1, v2);
        System.out.printf("Guessed seed: 0x%016x.  Testing...\n", seed);
        testGuess(seed, target, v2);
        System.out.println("Success!!!");
    }


    /**
     * We can guess the state of the generator just from observing two consecutive {@code int}s.
     */
    private static long guessSeed(int v1, int v2) {
        System.out.printf("v1 = 0x%08x, v2 = 0x%08x\n", v1, v2);
        return guessSeed(maskToLong(v1), maskToLong(v2));
    }

    private static long guessSeed(long v1, long v2) {
        for (int i = 0; i < 65536; i++) {
            long guess = v1 * 65536 + i;
            if ((((guess * multiplier + addend) & mask) >>> 16) == v2) {
                return (guess ^ multiplier) & mask;
            }
        }
        throw new IllegalStateException("CAN'T HAPPEN: Could not guess the seed!");
    }

    /**
     * Convert an int to a long in a way that preserves all the bits (negative numbers/twos complement issues).
     */
    private static long maskToLong(int n) {
        return n & 0x00000000ffffffffL;
    }


    private static void testGuess(long seed, Random target, int expected) {
        Random experiment = new Random(seed);
        int actual = experiment.nextInt();
        for (int i = 0; i < 100_000; i++) {
            if (actual != expected) {
                // This will never actually happen.
                String msg = "i = %d, expected = 0x%08x, actual = 0x%08x";
                throw new IllegalStateException(String.format(msg, i, expected, actual));
            }
            expected = target.nextInt();
            actual = experiment.nextInt();
        }
    }

}

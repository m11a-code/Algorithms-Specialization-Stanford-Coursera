import java.math.BigInteger;
import java.util.Random;

import static java.math.BigInteger.*;

public class Karatsuba {

    /**
     * @param first
     * @param second
     * @return
     * @throws IllegalArgumentException
     */
    private static BigInteger karatsuba(BigInteger first, BigInteger second) throws IllegalArgumentException {
        // Check if BigInteger values are negative
        if (first.signum() == -1) {
            throw new IllegalArgumentException("BigInteger value " + first + " cannot be a negative value.");
        }
        if (second.signum() == -1) {
            throw new IllegalArgumentException("BigInteger value " + second + " cannot be a negative value.");
        }

        if (first.compareTo(TEN) < 0 || second.compareTo(TEN) < 0) {
            return first.multiply(second);
        }

        String length = getLength(first.max(second));
        BigInteger power = new BigInteger(length);
        if (power.mod(TWO).equals(ONE)) {
            power = power.add(ONE);
        }

        BigInteger val = TEN.pow(power.divide(TWO).intValue());
        BigInteger a = first.divide(val);
        BigInteger b = first.mod(val);
        BigInteger c = second.divide(val);
        BigInteger d = second.mod(val);

        BigInteger ac = karatsuba(a, c);
        BigInteger bd = karatsuba(b, d);
        BigInteger abcd = karatsuba(a.add(b), c.add(d));

        return ((ac.multiply(TEN.pow(power.intValue()))).add((((abcd.subtract(ac)).subtract(bd))).multiply(val)).add(bd));
    }

    public static String getLength(BigInteger bigInteger) {
        String length = bigInteger.toString();
        return Integer.toString(length.length());
    }

    public static void main(String[] args) {
        // Make some really large numbers.
        Random random = new Random();
        int power = 200000;
        BigInteger first = new BigInteger(power, random);
        BigInteger second = new BigInteger(power, random);

        // Specified input from Coursera course.
        // first = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592");
        //  second = new BigInteger("2718281828459045235360287471352662497757247093699959574966967627");

        System.out.println("First: " + first);
        System.out.println("Second: " + second);

        long start = System.currentTimeMillis();
        System.out.println("Answer: " + karatsuba(first, second));
        long stop = System.currentTimeMillis();

        System.out.println();
        System.out.println("Execution time (s): " + ((stop - start) / 1000.0));
    }
}

import java.math.BigInteger;
import java.util.Random;

import static java.math.BigInteger.*;

public class Karatsuba {

    /**
     * @param first  first value to multiply with Karatsuba multiplication.
     * @param second second value to multiply with Karatsuba multiplication.
     * @return multiplication result from Karatsuba multiplication.
     * @throws IllegalArgumentException if either first or second provided value is invalid.
     */
    private static BigInteger karatsuba(BigInteger first, BigInteger second) throws IllegalArgumentException {
        // Check if BigInteger values are negative
        if (first.signum() == -1) {
            throw new IllegalArgumentException("BigInteger value " + first + " cannot be a negative value.");
        }
        if (second.signum() == -1) {
            throw new IllegalArgumentException("BigInteger value " + second + " cannot be a negative value.");
        }

        // Check if we really even need to use Karatsuba to multiply.
        // This should be adjusted, but for our needs, this is fine.
        if (first.compareTo(TEN) < 0 || second.compareTo(TEN) < 0) {
            return first.multiply(second);
        }

        // Get the length of the larger of the two numbers.
        String length = getLength(first.max(second));
        BigInteger pow = new BigInteger(length);

        // Adjust the power value so that it's even.
        if (pow.mod(TWO).equals(ONE)) {
            pow = pow.add(ONE);
        }

        // Get the half of the power to use to split the numbers.
        BigInteger powOverTwo = TEN.pow(pow.divide(TWO).intValue());
        BigInteger a = first.divide(powOverTwo);
        BigInteger b = first.mod(powOverTwo);
        BigInteger c = second.divide(powOverTwo);
        BigInteger d = second.mod(powOverTwo);

        // Karatsuba's three steps:
        // (1) Recursively compute ac
        BigInteger ac = karatsuba(a, c);
        // (2) Recursively compute bd
        BigInteger bd = karatsuba(b, d);
        // (3) Recursively compute (a+b)(c+d)
        BigInteger abcd = karatsuba(a.add(b), c.add(d));

        // Karatsuba's calculation using Gauss' trick.
        // Reminder: Gauss' trick is (3) - (1) - (2) = ad + bc
        // Reminder: Karatsuba's is (10^n)ac + (10^(n/2))(ad + bc) + bd
        return ((ac.multiply(TEN.pow(pow.intValue()))).add((((abcd.subtract(ac)).subtract(bd))).multiply(powOverTwo)).add(bd));
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

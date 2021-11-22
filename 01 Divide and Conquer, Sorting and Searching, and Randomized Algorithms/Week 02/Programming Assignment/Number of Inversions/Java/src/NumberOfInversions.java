import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class NumberOfInversions {

    public static long countInversions(int[] nums) {
        // Check whether the input array is null or empty
        if ((nums == null) || (nums.length == 0)) {
            return 0;
        }

        // O(n log n)
        return mergeSortHelper(nums, new int[nums.length], 0, nums.length - 1);
    }

    public static long mergeSortHelper(int[] nums, int[] aux, int left, int right) {
        long numInversions = 0;
        int mid;

        // Base case
        if (right > left) {
            // Calculate the mid to divide the array into two parts and loop.
            mid = (left + right) / 2;
            long leftInversions = mergeSortHelper(nums, aux, left, mid);
            long rightInversions = mergeSortHelper(nums, aux, mid + 1, right);

            numInversions = leftInversions + rightInversions + merge(nums, aux, left, mid + 1, right);
        }
        return numInversions;
    }

    public static int merge(int[] nums, int[] aux, int left, int mid, int right) {
        int leftPtr = left, midPtr = mid, auxPtr = left, inversionsCount = 0;

        while ((leftPtr <= mid - 1) && (midPtr <= right)) {
            if (nums[leftPtr] <= nums[midPtr]) {
                aux[auxPtr++] = nums[leftPtr++];
            } else {
                aux[auxPtr++] = nums[midPtr++];

                inversionsCount += (mid - leftPtr);
            }
        }

        while (leftPtr <= mid - 1) {
            aux[auxPtr++] = nums[leftPtr++];
        }

        while (midPtr <= right) {
            aux[auxPtr++] = nums[midPtr++];
        }

        for (leftPtr = left; leftPtr <= right; leftPtr++) {
            nums[leftPtr] = aux[leftPtr];
        }

        return inversionsCount;
    }

    // Driver method to test the above function
    public static void main(String[] args) throws FileNotFoundException {
        int[] arr = new int[100000];
        try (Scanner s = new Scanner(new FileReader("src/IntegerArray.txt"))) {
            for (int i = 0; i < 100000; i++) {
                arr[i] = s.nextInt();
            }
        }

//        System.out.println(Arrays.toString(arr));

        System.out.println("Number of inversions are " + countInversions(arr));
    }
}

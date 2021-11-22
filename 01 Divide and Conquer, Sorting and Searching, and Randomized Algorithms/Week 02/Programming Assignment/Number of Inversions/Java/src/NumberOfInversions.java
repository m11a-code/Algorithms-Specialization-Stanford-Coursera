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
        // Base case
        if (right <= left) {
            return 0;
        }

        // Calculate the mid to divide the array into two parts and loop.
        // Divide
        int mid = (left + right) / 2;
        // Conquer
        long leftInversions = mergeSortHelper(nums, aux, left, mid);
        long rightInversions = mergeSortHelper(nums, aux, mid + 1, right);

        // Merge and add up inversion counts
        return leftInversions + rightInversions + merge(nums, aux, left, mid + 1, right);
        // T(n) = 2T(n/2) + O(n)
        // a = 2, b = 2, d = 1
        // According to Master Method, the running time complexity is O(n log n).
    }

    public static int merge(int[] nums, int[] aux, int left, int mid, int right) {
        int leftPtr = left, midPtr = mid, auxPtr = left, inversionsCount = 0;

        // Work through each array and place into sorted order in  aux array while counting inversions.
        while ((leftPtr <= mid - 1) && (midPtr <= right)) {
            // if left value <= right value.
            if (nums[leftPtr] <= nums[midPtr]) {
                // Just put left value into aux array.
                aux[auxPtr++] = nums[leftPtr++];
            } else {
                // If right > left, put right value into aux array.
                aux[auxPtr++] = nums[midPtr++];
                // And count all values from mid to current left value in left array as inversions.
                inversionsCount += (mid - leftPtr);
            }
        }

        // Once we've reached the end of one of the two arrays, add all remaining values to the aux array.
        // Do this for the left array.
        while (leftPtr <= mid - 1) {
            aux[auxPtr++] = nums[leftPtr++];
        }

        // Do thsi for the right array.
        while (midPtr <= right) {
            aux[auxPtr++] = nums[midPtr++];
        }

        // Copy newly ordered values from aux array into nums array.
        System.arraycopy(aux, left, nums, left, right - left + 1);

        return inversionsCount;
        // Running time complexity: O(n)
    }

    // Driver method to test the above function
    public static void main(String[] args) throws FileNotFoundException {
        int[] arr = new int[100000];
        try (Scanner s = new Scanner(new FileReader("src/IntegerArray.txt"))) {
            for (int i = 0; i < 100000; i++) {
                arr[i] = s.nextInt();
            }
        }

        System.out.println("Number of inversions are " + countInversions(arr));
    }
}

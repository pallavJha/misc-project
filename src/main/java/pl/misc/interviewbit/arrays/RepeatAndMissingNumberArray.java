package pl.misc.interviewbit.arrays;

import java.util.Arrays;

/**
 * interviewbit Repeat And Missing Number Array solution
 * https://www.interviewbit.com/problems/repeat-and-missing-number-array/
 * You are given a read only array of n integers from 1 to n.
 * <p>
 * Each integer appears exactly once except A which appears twice and B which is missing.
 * <p>
 * Return A and B.
 * <p>
 * Note: Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
 * <p>
 * Note that in your output A should precede B.
 * <p>
 * Example:
 * <p>
 * Input:[3 1 2 5 3]
 * <p>
 * Output:[3, 4]
 * <p>
 * A = 3, B = 4
 *
 * @author pallav
 * @version 1.0
 * @since 1/3/18
 */
public class RepeatAndMissingNumberArray {


    public static int[] repeatedNumber(final int[] A) {
        int a = 0;
        for (int i = 0; i < A.length; i++) {
            int actualPosition = A[Math.abs(A[i]) - 1];
            if (actualPosition > 0) {
                A[Math.abs(A[i]) - 1] = -actualPosition;
            } else {
                a = Math.abs(A[i]);
            }
        }

        int b = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] > 0) {
                b = i + 1;
            }
        }

        //System.out.println(Arrays.toString(A));
        System.out.println(a + ", " + b);
        return new int[]{a, b};
    }

    public static int[] repeatedNumber2(final int[] A) {
        int max = A.length;
        int sum = max * (max + 1) / 2;
        int thisSum = Arrays.stream(A).sum();

        int a = 0;
        for (int i = 0; i < A.length; i++) {
            int actualPosition = A[Math.abs(A[i]) - 1];
            if (actualPosition > 0) {
                A[Math.abs(A[i]) - 1] = -actualPosition;
            } else {
                a = Math.abs(A[i]);
                break;
            }
        }

        System.out.println(Arrays.toString(A));
        System.out.println(a + ", " + Math.abs(thisSum - sum - a));
        return new int[]{a, Math.abs(thisSum - sum - a)};
    }


    /**
     * let a, b be the duplicate number and missing number resp.
     * find sum of n terms which will be sum;
     * find sum of all terms present in the array which will be thisSum;
     * thisSum - sum = a - b
     * find sum of n terms which will be sqSum;
     * find sum of all terms present in the array which will be thisSqSum;
     * thisSqSum - sqSum = a^2 -b^2
     * (a^2 - b^2) / (a - b) = a + b
     * Now a + b and a - b is available.
     * These two equations can be solved to find the values of a & b.
     *
     * @param A Array
     * @return
     */
    public int[] repeatedNumberSecondName(final int[] A) {
        int max = A.length;
        int sum = max * (max + 1) / 2;
        int thisSum = Arrays.stream(A).sum();


        int sqSum = max * (max + 1) * (2 * max + 1) / 6;
        int thisSqSum = Arrays.stream(A).map(i -> i * i).sum();

        int abDiff = thisSum - sum;
        int abSum = (thisSqSum - sqSum) / (abDiff);

        int a = (abSum + abDiff) / 2;
        int b = abSum - a;

        return new int[]{a, b};
    }
}

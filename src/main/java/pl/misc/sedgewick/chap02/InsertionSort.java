package pl.misc.sedgewick.chap02;

import java.io.PrintStream;

import org.apache.commons.lang.ArrayUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class InsertionSort {

	private static PrintStream StdOut = System.out;

	public static void sort(Comparable[] a) {
		show(a);
		for (int i = 1; i < a.length; i++) {

			for (int j = i; j > 0; j--) {
				if (less(a[j], a[j - 1])) {
					exch(a, j, j - 1);
				}
			}
		}
	}

	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	private static void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	public static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			StdOut.print(a[i] + " ");
		}
		StdOut.println();
	}

	public static boolean isSorted(Comparable[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i - 1])) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String s = "SORTEXAMPLE";
		Character[] c_arr = ArrayUtils.toObject(s.toCharArray());
		InsertionSort.sort(c_arr);
		InsertionSort.show(c_arr);
	}
}

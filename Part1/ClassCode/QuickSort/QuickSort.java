import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;


public class QuickSort {
    private int count = 0;
    private int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        int v = a[lo];
        while(true) {
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }

            while (less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }

            swap(a, i, j);
        }
        swap(a, lo, j);
        return j;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private boolean less(int i, int j) {
        count++;
        return i < j;
    }


    public int select(int[] a, int k) {
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k) lo = j + 1;
            else if (j > k) hi = j - 1;
            else return a[k];
        }
        return a[k];
    }

    public void partition3way(int[] a, int lo, int hi) {
        int lt = lo, i = lo + 1, gt = hi;
        int v = a[lo];

        while (i <= gt) {
            if (a[i] < v) swap(a, lt++, i++);
            else if (a[i] > v) swap(a, i, gt--);
            else i++;
        }

    }

    public static void main(String[] args) {
        int[] b = new int[10];
        QuickSort t = new QuickSort();
        for (int i = 0; i < 10; i++) {
            b[i] = StdRandom.uniformInt(3);
        }
        System.out.println(Arrays.toString(b));
        t.partition3way(b, 0, b.length - 1);
        System.out.println(Arrays.toString(b));
    }
}

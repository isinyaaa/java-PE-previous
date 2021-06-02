public class Partitions {
    private static long[][] p;
    public static void main(String[] args) {

        // get wanted partition number
        int num = Integer.parseInt(args[0]);
        p = new long[num][num];

        StdOut.println(partitionNum(num, num));
    }

    /*
    define p(n, m):= #partitions of n where every parcel k is smaller or equal to m
    then we can notice a pattern that (as suggested in the task page):
    p(n, m) = p(n, m - 1) + p(n - m, m)
    or that, in other words,
    the difference between two subsequent partitions number functions with a maximum parcel of m and m - 1 is
    p(n - m, m)
    which is the partition number you get when you fix a parcel of size m and look at the rest
    as we've fixed this parcel, we can guarantee we have a parcel *greater than* m - 1, so that we're not repeating
    elements between the p(n, m) and p(n, m - 1).
    It also accounts for all their different elements, as when we write the fixed parcel
    m as (m - 1) + 1
    we're going to the p(n, m - 1) case.
    */

    private static long partitionNum(int n, int m) {
        // if both are greater than 1 we can proceed with the formula
        if (n > 1 && m > 1) {
            if (p[n - 1][m - 1] == 0)
                p[n - 1][m - 1] = partitionNum(n, m - 1) + partitionNum(n - m, m);;
            return p[n - 1][m - 1];
        }
        else // if n < 0 we must return 0, and in the last case we can return 1 (count the partition)
            return (n < 0 ? 0 : 1);
    }
}

/******************************************************************************
 *  Compilation:  javac Diff
 *  Execution:    java Diff filename1 filename2
 *  Dependencies: In.java
 *  
 *  Reads in two files and compute their diff.
 *  A bare bones version.
 * 
 *  % java Diff input1.txt input2.txt
 * 
 *
 *  Limitations
 *  -----------
 *   - Could hash the lines to avoid potentially more expensive 
 *     string comparisons.
 *
 ******************************************************************************/

public class Diff {

    public static void main(String[] args) {

        // read in lines of each file
        In in0 = new In(args[0]);
        In in1 = new In(args[1]);
        String[] x = in0.readAllLines();
        String[] y = in1.readAllLines();

        // number of lines of each file
        int m = x.length;
        int n = y.length;

        // opt[i][j] = length of LCS of x[i..m] and y[j..n]
        int[][] opt = new int[m+1][n+1];

        // compute length of LCS and all subproblems via dynamic programming
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (x[i].equals(y[j]))
                    opt[i][j] = opt[i+1][j+1] + 1;
                else 
                    opt[i][j] = Math.max(opt[i+1][j], opt[i][j+1]);
            }
        }

        // recover LCS itself and print out non-matching lines to standard output
        int i = 0, j = 0;
        while (i < m && j < n) {
            if (x[i].equals(y[j])) {
                i++;
                j++;
            }
            else if (opt[i+1][j] >= opt[i][j+1]) StdOut.println("< " + x[i++]);
            else                                 StdOut.println("> " + y[j++]);
        }

        // dump out one remainder of one string if the other is exhausted
        while (i < m || j < n) {
            if      (i == m) StdOut.println("> " + y[j++]);
            else if (j == n) StdOut.println("< " + x[i++]);
        }
    }

}

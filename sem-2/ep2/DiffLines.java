// a interpretação é deixada a cargo do leitor

public class DiffLines {
    public static void main(String[] args) {

        // read in lines of each file
        String[] x = new In(args[0]).readLine().split("\\s+");
        String[] y = new In(args[1]).readLine().split("\\s+");

        // number of words of each file
        int m = x.length;
        int n = y.length;

        // opt[i][j] = length of LCS of x[i..m] and y[j..n]
        int[][] opt = new int[m+1][n+1];

        // compute length of LCS and all subproblems via dynamic programming
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                // this was the best part :cry:
                if (x[i].equals(y[j]))
                    opt[i][j] = opt[i+1][j+1] + 1;
                else
                    opt[i][j] = Math.max(opt[i+1][j], opt[i][j+1]);
            }
        }

        // recover LCS itself and sub out non-matching words by their marked versions
        int i = 0, j = 0;
        String line1 = "", line2 = "";
        while (i < m && j < n) {
            if (x[i].equals(y[j])) {
                line1 += x[i++] + " ";
                line2 += y[j++] + " ";
            }
            else if (opt[i+1][j] >= opt[i][j+1]) line1 += "*" + x[i++] + "* ";
            else line2 += "*" + y[j++] + "* ";
        }

        // dump out one remainder of one string if the other is exhausted
        while (i < m || j < n) {
            if      (i == m) line2 += y[j++] + " ";
            else if (j == n) line1 += x[i++] + " ";
        }

        StdOut.println(line1);
        StdOut.println(line2);
    }
}
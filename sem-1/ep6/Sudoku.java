/**
 * @author : Isabella B
 * @created : 2020-11-23
**/

public class Sudoku {
  
  public static void main(String[] args) {
    // current gameboard size
    int size = 9;

    // check if there's an argument
    print = args.length == 0;

    // create gameboard
    int[][] board = new int[size][size];

    // get the board from text file
    for (int i = 0; i < size; i++)
      for (int j = 0; j < size; j++) {
        String curNum = StdIn.readString();
        board[i][j] = curNum.equals(".") ? 0 : Integer.parseInt(curNum);
      }

    // run initial check
    for (int i = 0; i < size; i++) {
      int[] pos = {(i*3)%9 + (i/3), i}; // every iteration we get 3 cells ahead of the last, wrapping, and every 3 cells we add 1 to this.
      if (!checkValid(board, pos)) {
        // System.out so that it always prints in the terminal
        System.out.println("Invalid instance");
        return;
      }
    }
    
    // start at (0, 0)
    int[] initialPos = {0, 0};
    solve(board, initialPos, 0);

    System.out.println(count + " solution(s)");
  }

  static boolean print = true;

  // counts the solutions
  static int count = 0;

  // main solve method
  static void solve(int[][] wb, int[] pos, int num) {
    int size = wb.length;

    // those will be the indexes of the first 0 element
    int i = pos[0];
    int j = pos[1];

    // this records whether we've found our indexes
    boolean done = false;

    while (i < size) {
      if (wb[i][j] == 0) { done = true; break; }
      j++;
      if (j == size) { j = 0; i++; }
    }

    // if there are no 0ed elements, we've won
    if (!done) {
      // check if we have to print
      if(print) printSolution(wb);
      
      // count a new solution
      count++;
      return;
    }
    
    // else, we just get a new position
    int[] newpos = {i, j};
    
    // and test it for available number options
    boolean[] possibilities = whatValid(wb, newpos);
    if (possibilities[0]) {
      wb[i][j] = num; // clean up
      return; // if the current position is invalid, return
    }
    
    // then we (deep) copy the whole array
    //int[][] nb = new int[size][size];
    //for (int k = 0; k < size; k++) nb[k] = wb[k].clone();
    // or we clean up (...)
    
    // and iterate through each possibility
    for (int k = 1; k < size + 1; k++) {
      if (possibilities[k]) {
        int prev = wb[i][j]; // save for clean up
        wb[i][j] = k;
        solve(wb, newpos, prev);
      }
    }

    // clean up
    wb[i][j] = 0;
  }

  // print a solution
  static void printSolution(int[][] solution) {
    int size = solution.length;

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) StdOut.print(solution[i][j] + " ");
      StdOut.println();
    }
    StdOut.println();
  }

  // checks if cell is in accordance to game rules
  static boolean checkValid(int[][] board, int[] pos) { return !whatValid(board, pos)[0]; }

  // returns a list of valid options for the given cell
  static boolean[] whatValid(int[][] board, int[] pos) {
    int size = board.length;

    // each vector represents a check
    boolean[] row = new boolean[size + 1]; // are rows consistent with game rules?
    boolean[] col = new boolean[size + 1]; // same for columns
    boolean[] sqr = new boolean[size + 1]; // and then for squares

    // init arrays with false
    for (int i = 0; i < size + 1; i++) row[i] = col[i] = sqr[i] = false; // Arrays.fill

    // this is a step counter for squares
    int lRowSt, lColSt;
    lRowSt = lColSt = 0;

    for (int i = 0; i < size; i++) {

      // steps the counters in each turn so as to zig zag
      if (i != 0) {
        if (i % 3 == 0) { lRowSt++; lColSt = 0; }
        else lColSt++;
      }
      
      // then we get the corresponding elements from the game board
      int re = board[i][pos[1]]; // according to the row
      int ce = board[pos[0]][i]; // and then to the column
      
      // as for the square element...
      // first we get its row index
      int rsi = 
        (pos[0]/3)*3 // by getting its leading row (0, 3 or 6)
        + lRowSt; // then we add our step (0, 1 or 2)
      
      // and the same is true for the column index
      int csi = (pos[1]/3)*3 + lColSt;
      int se = board[rsi][csi];

      // then we check if there are any matching numbers
      if (col[ce] || row[re] || sqr[se]) {
        boolean[] ret = {true}; // if there are, we just flag this as invalid
        return ret;
      }

      // and we mark the current element as true only if it's not "empty" (0)
      if (!col[ce]) col[ce] = ce != 0 ? true : false;
      if (!row[re]) row[re] = re != 0 ? true : false;
      if (!sqr[se]) sqr[se] = se != 0 ? true : false;
    }

    // make an array for storing possible number options
    boolean[] validPositions = new boolean[size + 1];
    boolean available = false; // keeps track of available plays (if there are none, the move will be invalidated)
    for (int i = 1; i < size + 1; i++) {
      available |= row[i] || col[i] || sqr[i];
      validPositions[i] = !(row[i] || col[i] || sqr[i]);
    }
    validPositions[0] = !available;

    return validPositions;
  }
}

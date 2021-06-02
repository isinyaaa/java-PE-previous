/******************************************************************************
 *  Compilation:  javac AnimatedHanoi.java
 *  Execution:    java AnimatedHanoi N
 *  Dependencies: StdDraw.java
 *  
 *  Solves the Towers of Hanoi problem on N discs and displays the
 *  results graphically.
 *
 *
 *  % java AnimatedHanoi 6
 *
 ******************************************************************************/



public class AnimatedHanoi {

  int moves = 0;

  // draw the current state of the Towers of Hanoi game
  public static void draw(int[] pole) {

    int N = pole.length - 1;

    // draw 3 poles
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.004);

	//Desenha no eixo y estando nos pontos 0, 1 e 2
    for (int i = 0; i < 3; i++){
	  StdDraw.line(i, -1, i, N);
	  StdDraw.line(i - (0.7/2), -0.48, i + (0.7/2), -0.48);
	}

    // draw N discs
    int[] discs = new int[3];   // discs[p] = # discs on pole p
    for (int i = N; i >= 1; i--) {
      StdDraw.setPenColor(255*i/N,0 , 10*i/N);
      StdDraw.setPenRadius(0.035);
      double size = 0.5 * i / N; // Define length of the disc
      int p = pole[i]; // p recebe a torre que contém o disco i
      StdDraw.line(p-size/2, discs[p], p + size/2, discs[p]);
	  discs[p]++; // atualiza a qtd de discos na torre p
    }

    StdDraw.show();
	StdDraw.pause(100);
  }

  public static void hanoi(int N) {
    int[] pole = new int[N+1];       // pole[i] indica em que torre está o disco i
    draw(pole);
	
    hanoi(N, 0, 1, 2, pole);
  }





  // n -> qtd de discos
  // from -> de onde será movido
  // aux ->
  public static void hanoi(int n, int from, int aux, int to, int[] pole) {
  
  if (n == 0) return;
    hanoi(n-1, from, to, aux, pole);
    StdOut.println("Move disc " + n + " from pole " + from + " to pole " + to);
    pole[n] = to;
    draw(pole);
    hanoi(n-1, aux, from, to, pole);
  }





    
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);   // número de discos
    int WIDTH  = 200;                    // largura do maior disco
    int HEIGHT = 20;                     // altura dos discos

    // tamanho e escala da imagem
    StdDraw.setCanvasSize(4*WIDTH, (N+3)*HEIGHT);
    StdDraw.setXscale(-1, 3);
    StdDraw.setYscale(-1, N+3);

    // resolve
    hanoi(N);
  }
}




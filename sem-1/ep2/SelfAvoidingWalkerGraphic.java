/******************************************************************************
 *  Compilation:  javac SelfAvoidingWalker.java
 *  Execution:    java SelfAvoidingWalker N T
 *
 *  Generate T self-avoiding walks of length N.
 *  Report the fraction of time the random walk is non self-intersecting.
 *
 ******************************************************************************/

public class SelfAvoidingWalkerGraphic {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);       // lattice size
    int T = Integer.parseInt(args[1]);       // number of trials
    int deadEnds = 0;                        // trials resulting in a dead end
    
    // Formatação da saída no terminal
    StdOut.println("---------------------------------------");
    StdOut.printf("%39s","Simulação  ---  quantidade de passos\n");
    StdOut.println("---------------------------------------");
    
    // Dimensões do espaço que o gato tem pra se mover
    StdDraw.setXscale(0.0,(float)N);
    StdDraw.setYscale(0.0,(float)N);
    
    // simulate T self-avoiding walks
    for (int t = 0; t < T; t++) {
      boolean[][] a = new boolean[N][N];   // intersections visited
      int x = N/2, y = N/2;                // current position
      
      // Double buffer para fazer grade mais rápido
      StdDraw.enableDoubleBuffering();
      
      // Desenhar grade
      StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
      for (int i = 0; i < N; i++) {
        StdDraw.line(0, i, N, i);
        StdDraw.line(i, 0, i, N);
      }
      // Mostrar grade
      StdDraw.show();
      // Desabilitar double buffer para desenhar trajetória do gato
      StdDraw.disableDoubleBuffering();

      // Cor da trajetória do gato
      StdDraw.setPenColor(StdDraw.BLUE);
      
      // repeatedly take a random step, unless you've already escaped
      while (x > 0 && x < N-1 && y > 0 && y < N-1)  {
        
        // dead-end, so break out of loop
        if (a[x-1][y] && a[x+1][y] && a[x][y-1] && a[x][y+1]) {
          deadEnds++;
          break;
        }
        
        // mark (x, y) as visited
        a[x][y] = true;
        
        // Salvar posição anterior do gato
        int px = x, py = y;
        
        // take a random step to unvisited neighbor
        double r = Math.random();
        if (r < 0.25) {
          if (!a[x+1][y])
            x++;
        }
        else if (r < 0.50) {
          if (!a[x-1][y])
            x--;
        }
        else if (r < 0.75) {
          if (!a[x][y+1])
            y++;
        }
        else if (r < 1.00) {
          if (!a[x][y-1])
            y--;
        }
        
        // Desenhar caminho do gato
        if (x != px || y != py) {
          StdDraw.line(px, py, x, y);
        }
        
        // Pintar bolinha na posição inicial do gato
        if (px == N/2 && py == N/2) {
          StdDraw.setPenColor(StdDraw.RED);
          StdDraw.filledCircle(px, py, 0.25);
          StdDraw.setPenColor(StdDraw.BLUE);
        }
      }
      // Contar passos do gato
      int steps = 0;
      
      for(int i = 0; i < a.length; i++)
        for(int j = 0; j < a[0].length; j++)
          if(a[i][j]) steps++;
      
      // Formatar passos do gato
      StdOut.printf("%7d       ---  %12d        \n",t,steps);
      
      // Pause + limpar a tela
      StdDraw.pause(500);
      StdDraw.clear();
    }
    
    // Formatar saída
    StdOut.println("---------------------------------------");
    String output = String.format("O gato ficou sem saída %.2f%% das vezes\n",100.0*((float)deadEnds/(float)T));
    StdOut.printf("%39s",output);
    StdOut.println("---------------------------------------");
  }
}

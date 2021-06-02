/*
 * Autora: Isabella B
 * 
 * Neste programa, resolvo o problema das torres de hanoi com restrição,
 * utilizando (A) uma adaptação da animação dada no arquivo
 * AnimatedHanoi.java e (B) a estratégia de contagem ternária, explicada
 * aqui: https://www.youtube.com/watch?v=bdMfjfT0lKk 
 *
 */

public class HanoiRevisited {

  // variável que guarda os movimentos
  static int moves = 0;

  // guarda se o desenho já foi feito para o caso final
  static boolean drawnLast = false;

  // método principal
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);  // quantidade de discos

    int WIDTH  = 200;                   // largura do maior disco
    int HEIGHT = 20;                    // altura dos discos

    // tamanho e escala da imagem
    StdDraw.setCanvasSize(4*WIDTH, (N+3)*HEIGHT);
    StdDraw.setXscale(-1, 3);
    StdDraw.setYscale(-1, N+3);


    int[] pole = new int[N]; // inicializa o vetor das torres
    boolean[] reset = new boolean[N]; // inicializa o vetor das torres
    for (int i = 0; i < N; i++) reset[i] = false;

    // resolve o puzzle recursivamente
    hanoi(0, pole,reset);

    // imprime as informações finais
    StdOut.println("------------------------------------------");
    StdOut.printf("Qtd discos = %d, total de movimentos = %d\n", N, moves); 
  }

  // desenha o estado atual do jogo para um dado tabuleiro de torres pole[]
  public static void draw(int[] pole) {

    // pega a quantidade de discos
    int N = pole.length;

    // reinicia o desenho, a cor base e a grossura da caneta
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.004);                   
                                                   
    // desenha o tabuleiro
    for (int i = 0; i < 3; i++){                   
      StdDraw.line(i, -1, i, N);                   
      StdDraw.line(i - (0.7/2), -0.48, i + (0.7/2), -0.48);                                           
    }

    // seta a grossura (fixa) para todos discos
    StdDraw.setPenRadius(0.035);
    // desenha N discos
    int[] discs = new int[3];   // o vetor discs guarda quantos discos estão em cada torre
    for (int i = N; i >= 1; i--) {
      StdDraw.setPenColor(0, 10*i/N, 255*i/N); // seta a cor de cada disco

      double size = 0.5 * i / N; // define a largura de um dado disco (visto que sua altura é fixa)

      int p = adderNext(i, pole) % 2 == 0 ? pole[i - 1] : 2 - pole[i - 1]; // p recebe a torre que contém o disco i
      
      StdDraw.line(p - size/2, discs[p], p + size/2, discs[p]);
      
      discs[p]++; // atualiza a qtd de discos na torre p
    }
    StdDraw.show();
    StdDraw.pause(50);
  } 

  // resolve o puzzle
  // i representa o disco que estamos olhando
  // pole[] é o tabuleiro de torres
  // reset[i] indica se o disco i foi resetado na última rodada
  static void hanoi(int i, int[] pole, boolean[] reset) {
    // caso cheguemos ao final do trit ("bit" na base 3)
    if (pole[i] == 2) {
      if (pole[pole.length - 1] == 2 // caso o último disco já tenha chegado lá também
          && adderNext(0, pole) == pole.length * 2) // e se a soma dos trits der 2 em todas as casas
      {
        if (!drawnLast) { drawnLast = true; draw(pole); } // desenhamos o estado final
        return; // e acabou o jogo
      }
      
      if (i + 1 != pole.length) // caso não estejamos no último disco
      {
        if (!resetted(i, reset, false)) draw(pole); // caso não tenhamos resetado os últimos, desenhe
        
        pole[i] = 0; // zeramos o atual
        reset[i] = true;
        hanoi(i + 1, pole, reset); // chamamos o próximo caso (repare que o próximo só é chamado
                            //quando o anterior chegou a 2)
      }  
    }
    else {
      int from = pole[i]; // no caso normal, saímos da torre atual
      int to = pole[i] + 1; // para a próxima
      if (adderNext(i + 1, pole) % 2 != 0)  // porém, caso o as próximas casas sejam
                                            //ímpares, estamos voltando na contagem
      {
        from = 2 - from;
        to = 2 - to;
      }
      StdOut.printf("Move disc %d from pole %d to pole %d\n", i + 1, from, to);
      if (!resetted(i, reset, true)) draw(pole);  // caso não tenhamos resetado os últimos, resete eles 
                                                  //leia (ocorre sempre que zeramos todos)
      moves++;
      pole[i]++;
    }
    // chamamos o caso base
    hanoi(0, pole, reset);
  }

  // adiciona e retorna os indíces maiores que i
  static int adderNext(int i, int[] pole) {
    if (i == pole.length) return 0;
    return pole[i] + adderNext(i + 1, pole);
  }

  // indica se algum disco <=i foi resetado
  // caso resetCurrent = true, resetamos o vetor na rodada atual
  static boolean resetted(int i, boolean[] reset, boolean resetCurrent) {
    boolean wasIt = false;
    for (int j = 0; j < i; j++) {
      if (reset[j]) { wasIt = true; 
        if (resetCurrent) reset[j] = false;
      }
    }
    return wasIt;
  }

}

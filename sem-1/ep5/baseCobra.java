import java.awt.event.KeyEvent;

public class baseCobra { 

  // função responsável pelo movimento da cobra
  public static void flow(int n, int[][] snekPos, int[] apple) {
    
    // para conferir se a posição da cobra de fato foi alterada quando a tecla foi apertada
    boolean updated = false;

	  // Espera para apertar a próxima tecla
	  while (!StdDraw.hasNextKeyTyped()) ;
	
    StdDraw.nextKeyTyped();

    // guarda uma cópia da posição da cabeça da cobra
	  int[] head = snekPos[0].clone();

	  if (StdDraw.isKeyPressed (KeyEvent.VK_W)) {
	    StdOut.println("Move - cima");

      // atualiza a cópia
      head[1]++;

      // conferimos se o movimento é possível e se há auto intercessão
      if (head[1] < n && !checkSI(snekPos, head)) updated = true; // caso não haja, marcamos que o movimento de fato ocorreu

    } else if (StdDraw.isKeyPressed (KeyEvent.VK_D)) {
	    StdOut.println("Move - direita");
	    
      // idem ao primeiro caso
      head[0]++;
      if (head[0] < n && !checkSI(snekPos, head)) updated = true;
	  
	  } else if (StdDraw.isKeyPressed (KeyEvent.VK_S)) {
	    StdOut.println("Move - baixo");
 
      head[1]--;
	    if (head[1] >= 0 && !checkSI(snekPos, head)) updated = true;
      
	  } else if (StdDraw.isKeyPressed (KeyEvent.VK_A)) {
	    StdOut.println("Move - esquerda");
      
      head[0]--;
      if (head[0] >= 0 && !checkSI(snekPos, head)) updated = true;
	  }
    
    // caso de fato o movimento tenha ocorrido
    if (updated) {

      // primeiro vamos apagar a cauda da cobra
      int[] tail = snekPos[snekPos.length - 1].clone();
      uncolor(tail);
      
      // e, então, desenhamos a cabeça
      color(head);
      // e atualizamos o vetor com as posições de cada ponto do corpo da cobra
      updateSnek(snekPos, head);

      // e então conferimos se a cobra comeu a maçã
      if (checkSI(snekPos, apple)) {
        // caso tenha comido, vamos achar outra e desenhá-la
        apple = newApple(snekPos, n);
        drawApple(apple);
      }
    }

	  flow(n, snekPos, apple);
  }

  // gera uma nova maçã
  static int[] newApple(int[][] snekPos, int n) {

    // sorteamos uma posição aleatória na grade
    int[] apple = { (int)(Math.random() * n), (int)(Math.random() * n) };
    
    // caso esteja em cima da cobra, geramos novamente, caso contrário entregamos a posição gerada
    return checkSI(snekPos, apple) ? newApple(snekPos, n) : apple;
  }

  // confere se há alguma intercessão entre o corpo da cobra e um vetor qualquer
  static boolean checkSI(int[][] snekPos, int[] pos) {
    for (int i = 0; i < snekPos.length; i++) {
      if (snekPos[i][0] == pos[0] && snekPos[i][1] == pos[1]) return true;
    }

    return false;
  }

  // atualiza o vetor do corpo da cobra
  static void updateSnek(int[][] snekPos, int[] head) {
    for (int i = 0; i < snekPos.length; i++) {
      int[] aux = snekPos[i].clone();
      snekPos[i] = head;
      head = aux;
    }
  } 

  // colore a cabeça da cobra (ou o corpo, no inicio)
  static void color(int[] snekAt) {
    StdDraw.setPenColor(StdDraw.BLUE);
	  StdDraw.filledCircle(snekAt[0], snekAt[1], .48);
  }
  
  // desenha a maçã
  static void drawApple(int[] apple) {
    StdDraw.setPenColor(StdDraw.RED);
	  StdDraw.filledCircle(apple[0], apple[1], .48);
  }

  // descolore o rabo da cobra
  static void uncolor(int[] snekAt) {
    StdDraw.setPenColor(StdDraw.WHITE);
	  StdDraw.filledCircle(snekAt[0], snekAt[1], .52);
  }

  public static void main(String[] args) {
	
    // recebe o tamanho da grid
	  int n = Integer.parseInt(args[0]);

    // põe as escalas
	  StdDraw.setXscale(-1, n);
    StdDraw.setYscale(-1, n);

    StdDraw.setPenColor(StdDraw.BOOK_RED);
    StdDraw.filledRectangle(-1, -1, n + 1, 0.48);
    StdDraw.filledRectangle(-1, n, n + 1, 0.48);
    StdDraw.filledRectangle(-1, -1, 0.48, n + 1);
    StdDraw.filledRectangle(n, -1, 0.48, n + 1);
    
    // guarda as posições cobrinha (snek), ponto à ponto
    int[][] snekPos = new int[13][2];

    // forma o corpo da cobra fazendo uma curva caso seja necessário
    for (int i = 0; i < snekPos.length; i++) {
      
      // caso i passe de metade do tamanho do grid (-1 pra não encostar na borda)
      // vamos fazer a cobra virar como um L
      int j = i <= n/2 - 1 ? 0 : n/2 - 1;
      
      snekPos[i][0] = j == 0 ? n/2 - i : n/2 - j;
      snekPos[i][1] = j == 0 ? n/2 : n/2 - (i - j);

      color(snekPos[i]);
    }

    // gera e guarda uma maçã
    int[] apple = newApple(snekPos, n);
    drawApple(apple);

    // inicia o joguinho
	  flow(n, snekPos, apple);
  }
}
